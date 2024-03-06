package net.faceless.aurelius.core.packets;

import io.netty.channel.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PacketListener {

    private static PacketListener listener;

    private PacketListener() {}

    public static PacketListener getListener() {
        return listener == null ? listener = new PacketListener() : listener;
    }

    public void injectPlayer(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer serverPlayer = craftPlayer.getHandle();
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){
            @Override //-> [SERVER BOUND]
            public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
                //Cancels ServerboundUseItemOnPacket if BlockPos contains a PacketBuild
                if(packet instanceof ServerboundUseItemOnPacket useItemOnPacket) {
                    BlockPos pos = useItemOnPacket.getHitResult().getBlockPos();
                    if(PacketBuild.containsBlockPos(pos)) return;
                }
                super.channelRead(context, packet);
            }

            @Override //-> [CLIENT BOUND]
            public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) throws Exception {
                super.write(context, packet, promise);
            }
        };
        ChannelPipeline pipeline = serverPlayer.connection.connection.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

    public void removePlayer(@NotNull Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer serverPlayer = craftPlayer.getHandle();

        Channel channel = serverPlayer.connection.connection.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
        });
    }

}

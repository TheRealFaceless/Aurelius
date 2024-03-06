package net.faceless.aurelius.core.packets;

import net.faceless.aurelius.utilities.NMSUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketBuild {

    private final HashMap<BlockPos, BlockState> blockStateMap;
    private static final HashMap<String, PacketBuild> builds = new HashMap<>();

    public PacketBuild(String name, List<Block> blocks) {
        this.blockStateMap = new HashMap<>();
        blocks.forEach(block -> {
            BlockPos pos = new BlockPos(block.getX(), block.getY(), block.getZ());
            BlockState state = ((CraftBlock)block).getNMS();
            blockStateMap.put(pos, state);
        });
        builds.put(name, this);
    }

    public void send(Player player) {
        blockStateMap.forEach((blockPos, blockState) -> {
            ClientboundBlockUpdatePacket packet = new ClientboundBlockUpdatePacket(blockPos, blockState);
            ((CraftPlayer)player).getHandle().connection.send(packet);
        });
    }

    public void hide(Player player) {
        blockStateMap.forEach((blockPos, blockState) -> {
            blockState = Blocks.AIR.defaultBlockState();
            ClientboundBlockUpdatePacket packet = new ClientboundBlockUpdatePacket(blockPos, blockState);
            ((CraftPlayer)player).getHandle().connection.send(packet);
        });
    }

    private HashMap<BlockPos, BlockState> getBlockStateMap() {
        return blockStateMap;
    }

    @Nullable
    public static PacketBuild getBuild(String name) {
         return builds.get(name);
    }

    public static boolean exists(String name) {
        return builds.containsKey(name);
    }

    public static boolean containsLocation(Location location) {
        for (PacketBuild packetBuild : builds.values()) {
            for (Map.Entry<BlockPos, BlockState> entry : packetBuild.getBlockStateMap().entrySet()) {
                BlockPos blockPos = entry.getKey();
                if (NMSUtil.toLocation(blockPos, location.getWorld()).equals(location)) return true;
            }
        }
        return false;
    }

    public static boolean containsBlockPos(BlockPos blockPos) {
        for (PacketBuild packetBuild : builds.values()) {
            for (Map.Entry<BlockPos, BlockState> entry : packetBuild.getBlockStateMap().entrySet()) {
                BlockPos blockPos1 = entry.getKey();
                if (blockPos1.equals(blockPos)) return true;
            }
        }
        return false;
    }

    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        builds.forEach((name, build) -> names.add(name));
        return names;
    }
}

package net.faceless.aurelius.listeners;

import net.faceless.aurelius.core.packets.PacketBuildState;
import net.faceless.aurelius.core.packets.PacketListener;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PacketEventListener implements Listener {

    private static final HashMap<UUID, List<Block>> blockHash = new HashMap<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!PacketBuildState.isBuilding(event.getPlayer())) return;
        List<Block> blocks = blockHash.getOrDefault(event.getPlayer().getUniqueId(), new ArrayList<>());
        blocks.add(event.getBlock());
        blockHash.put(event.getPlayer().getUniqueId(), blocks);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!PacketBuildState.isBuilding(event.getPlayer())) return;
        List<Block> blocks = blockHash.getOrDefault(event.getPlayer().getUniqueId(), new ArrayList<>());
        if(!blocks.contains(event.getBlock())) return;
        blocks.remove(event.getBlock());
        blockHash.put(event.getPlayer().getUniqueId(), blocks);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PacketListener.getListener().injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PacketListener.getListener().removePlayer(event.getPlayer());
    }

    public static HashMap<UUID, List<Block>> getBlockHash() {
        return blockHash;
    }

    public static void clear(Player player) {
        blockHash.remove(player.getUniqueId());
    }
}

package net.faceless.aurelius.commands.packet;

import net.faceless.aurelius.commands.util.SubCommand;
import net.faceless.aurelius.core.packets.PacketBuild;
import net.faceless.aurelius.core.packets.PacketBuildState;
import net.faceless.aurelius.listeners.PacketEventListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PacketBuildSave extends SubCommand {

    public PacketBuildSave(JavaPlugin plugin) {
        super(plugin);
        setName("save");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length != 2) return;
        String name = args[1];

        if(!PacketBuildState.isBuilding(player)) return;
        HashMap<UUID, List<Block>> blocksHash = PacketEventListener.getBlockHash();
        if (blocksHash == null) return;

        List<Block> blocks = blocksHash.get(player.getUniqueId());
        if (blocks == null || blocks.isEmpty()) return;
        new PacketBuild(name, blocks);
        blocks.forEach(block -> block.setType(Material.AIR));
        PacketBuildState.exitBuildMode(player);
        PacketEventListener.clear(player);
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}

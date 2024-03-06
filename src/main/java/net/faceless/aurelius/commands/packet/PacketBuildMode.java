package net.faceless.aurelius.commands.packet;

import net.faceless.aurelius.commands.util.SubCommand;
import net.faceless.aurelius.core.packets.PacketBuildState;
import net.faceless.aurelius.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PacketBuildMode extends SubCommand {

    public PacketBuildMode(JavaPlugin plugin) {
        super(plugin);
        setName("buildmode");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(PacketBuildState.isBuilding(player)) {
            player.sendMessage(ChatUtil.format("<red>Toggled off"));
            PacketBuildState.exitBuildMode(player);
        }else {
            PacketBuildState.enterBuildMode(player);
            player.sendMessage(ChatUtil.format("<green>Toggled on"));
        }
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}

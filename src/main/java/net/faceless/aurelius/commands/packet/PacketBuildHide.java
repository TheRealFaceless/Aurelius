package net.faceless.aurelius.commands.packet;

import net.faceless.aurelius.commands.util.SubCommand;
import net.faceless.aurelius.core.packets.PacketBuild;
import net.faceless.aurelius.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class PacketBuildHide extends SubCommand {

    public PacketBuildHide(JavaPlugin plugin) {
        super(plugin);
        setName("hide");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length != 2) return;
        String name = args[1];
        PacketBuild packetBuild = PacketBuild.getBuild(name);
        if(packetBuild == null) {
            player.sendMessage(ChatUtil.format("<red>Build not found!"));
            return;
        }
        packetBuild.hide(player);
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return PacketBuild.getNames().stream().filter(string -> args.length == 2).collect(Collectors.toList());
    }
}

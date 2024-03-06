package net.faceless.aurelius.commands.packet;

import net.faceless.aurelius.commands.util.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketMainCommand extends CommandManager {

    public PacketMainCommand(JavaPlugin plugin) {
        super(plugin);
        register(new PacketBuildMode(plugin));
        register(new PacketBuildSave(plugin));
        register(new PacketBuildDisplay(plugin));
        register(new PacketBuildHide(plugin));
    }
}

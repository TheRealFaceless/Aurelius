package net.faceless.aurelius.commands.aurelius;

import net.faceless.aurelius.commands.util.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AureliusMainCommand extends CommandManager {

    public AureliusMainCommand(JavaPlugin plugin) {
        super(plugin);
        register(new AureliusReload(plugin));
    }
}

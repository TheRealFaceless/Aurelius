package net.faceless.aurelius.commands.aurelius;

import net.faceless.aurelius.commands.util.SubCommand;
import net.faceless.aurelius.configuration.ConfigManager;
import net.faceless.aurelius.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AureliusReload extends SubCommand {

    public AureliusReload(JavaPlugin plugin) {
        super(plugin);
        setName("reload");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        ConfigManager.getManager().loadAll();
        player.sendMessage(ChatUtil.format("<green>Configs reloaded!"));
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}

package net.faceless.aurelius;

import net.faceless.aurelius.commands.aurelius.AureliusMainCommand;
import net.faceless.aurelius.commands.packet.PacketMainCommand;
import net.faceless.aurelius.configuration.ConfigManager;
import net.faceless.aurelius.core.packets.PacketListener;
import net.faceless.aurelius.listeners.MenuListener;
import net.faceless.aurelius.listeners.PacketEventListener;
import net.faceless.aurelius.utilities.ChatUtil;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;

public class Aurelius extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigManager.getManager().register(this);

        Objects.requireNonNull(getCommand("aurelius")).setExecutor(new AureliusMainCommand(this));
        Objects.requireNonNull(getCommand("packet")).setExecutor(new PacketMainCommand(this));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PacketEventListener(), this);
    }

    @Override
    public void onDisable() {
        ConfigManager.getManager().saveAll();
    }
}

package net.faceless.aurelius.utilities;

import net.faceless.aurelius.Aurelius;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class ChatUtil {

    public static Component format(String msg) {
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public static String formatLegacy(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendMessage(Player player, String msg) {
        player.sendMessage(format(msg));
    }

    public static void sendLegacyMessage(Player player, String msg) {
        player.sendMessage(formatLegacy(msg));
    }

    public static void sendConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(format(msg));
    }

    public static void sendLegacyConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(formatLegacy(msg));
    }

    public static void logConsoleMessage(String message, MessageType type) {
        String prefix = "[Aurelius] ";
        switch (type) {
            case INFO -> Bukkit.getLogger().info(MessageColor.BLUE.getCode() +prefix+ MessageColor.RESET.getCode() + message);
            case WARN -> Bukkit.getLogger().warning(MessageColor.YELLOW.getCode() +prefix+ MessageColor.RESET.getCode() + message );
            case ERROR -> Bukkit.getLogger().severe(MessageColor.RED.getCode() +prefix+ MessageColor.RESET.getCode() + message );
        }
    }

    public enum MessageType {
        INFO,
        WARN,
        ERROR
    }

    public enum MessageColor {
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        MessageColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}

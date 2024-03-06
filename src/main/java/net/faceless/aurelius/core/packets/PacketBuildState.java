package net.faceless.aurelius.core.packets;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PacketBuildState {

    private static final HashMap<UUID, PacketBuildStateEnum> states = new HashMap<>();

    public static PacketBuildStateEnum getState(Player player) {
        return states.getOrDefault(player.getUniqueId(), PacketBuildStateEnum.DEFAULT);
    }

    public static void enterBuildMode(Player player) {
        states.put(player.getUniqueId(), PacketBuildStateEnum.BUILDING);
    }

    public static void exitBuildMode(Player player) {
        states.remove(player.getUniqueId());
    }

    public static boolean isBuilding(Player player) {
        if (!states.containsKey(player.getUniqueId())) return false;
        return getState(player) == PacketBuildStateEnum.BUILDING;
    }

    public enum PacketBuildStateEnum {
        BUILDING,
        DEFAULT
    }
}

package net.faceless.aurelius.utilities;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void playSound(Player player, Sound sound){
        player.playSound(player.getLocation(), sound, 1, 1);
    }
    public static void UI_CLICK(Player player) {
        playSound(player, Sound.UI_BUTTON_CLICK);
    }
    public static void PAGE_FLIP(Player player) {
        playSound(player, Sound.ITEM_BOOK_PAGE_TURN);
    }
    public static void LAUNCH(Player player){
        playSound(player, Sound.ENTITY_BAT_TAKEOFF);
    }
}

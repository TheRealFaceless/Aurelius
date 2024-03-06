package net.faceless.aurelius.utilities.scoreboard;

import net.faceless.aurelius.utilities.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"deprecation", "unused"})
public class Board {
    private final Scoreboard scoreboard;
    private BoardFormat format;
    private Objective objective;
    private final Map<String, Integer> scores;
    private String title;

    /**
     * Scoreboard Manager
     *
     *
     * @param title Scoreboard Title
     *
     */
    public Board(String title, BoardFormat format) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.scores = new LinkedHashMap<>();
        this.objective = scoreboard.registerNewObjective(title, Criteria.DUMMY, Component.text(title));
        this.title = title;
        this.format = format;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        switch (format) {
            case LEGACY -> objective.setDisplayName(ChatUtil.formatLegacy(title));
            case MINI_MESSAGE -> objective.displayName(ChatUtil.format(title));
        }
    }

    public Board blankLine() {
        add(" ");
        return this;
    }

    public Board add(String text) {
        add(text, null);
        return this;
    }

    public Board add(String text, Integer score) {
        scores.put(text, score);
        return this;
    }

    public void build() {
        int index = scores.size();
        for (String text : scores.keySet()) {

            int score = scores.get(text) != null ? scores.get(text) : index;

            switch (format) {
                case LEGACY -> {
                    text = ChatUtil.formatLegacy(text);
                    Score s1 = objective.getScore(text);
                    s1.setScore(score);

                    index -= 1;
                }
                case MINI_MESSAGE -> {
                    Score s1 = objective.getScore(text);
                    s1.customName(ChatUtil.format(text));
                    s1.setScore(score);

                    index -= 1;
                }
            }

        }
    }

    public Board reset() {
        scores.clear();
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
        return this;
    }

    public void send(Player... players) {
        for (Player p : players) {
            p.setScoreboard(scoreboard);
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setFormat(BoardFormat format){
        this.format = format;
    }

    public void setCriteria(Criteria criteria) {
        if (objective != null) {
            objective.unregister();
        }
        objective = scoreboard.registerNewObjective(title, criteria, Component.text(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        switch (format) {
            case LEGACY:
                objective.setDisplayName(ChatUtil.formatLegacy(title));
                break;
            case MINI_MESSAGE:
                objective.displayName(ChatUtil.format(title));
                break;
        }
    }

    public void setDisplaySlot(DisplaySlot displaySlot) {
        objective.setDisplaySlot(displaySlot);
    }

    public void setTitle(String title) {
        this.title = title;
        if(format == null) format = BoardFormat.LEGACY;
        switch (format) {
            case LEGACY -> objective.setDisplayName(ChatUtil.formatLegacy(title));
            case MINI_MESSAGE -> objective.displayName(ChatUtil.format(title));
        }
    }
}

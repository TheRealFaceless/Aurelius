package net.faceless.aurelius.configuration;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class ConfigManager {

    private final Map<String, Config> configs = new HashMap<>();
    private static ConfigManager manager;

    private ConfigManager(){}

    public static ConfigManager getManager() {
        return manager == null ? manager = new ConfigManager() : manager;
    }

    /**
     * Create Static Configs here.
     * @param plugin Plugin Instance
     */
    public void register(JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            loadConfigsFromFolder(dataFolder, plugin, new HashSet<>());
        }
    }

    private void loadConfigsFromFolder(File folder, JavaPlugin plugin, Set<String> loadedFiles) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                loadConfigsFromFolder(file, plugin, loadedFiles);
            } else if (file.getName().endsWith(".yml") && !loadedFiles.contains(file.getName())) {
                createConfig(folder.getPath(), file.getName(), plugin);
                loadedFiles.add(file.getName());
            }
        }
    }

    public Config createConfig(String path, String name, JavaPlugin plugin){
        Config config = new Config(path, name, plugin);

        if(name.endsWith(".yml")) name = name.replace(".yml", "");
        configs.put(name, config);
        return config;
    }

    @Nullable
    public Config getConfig(String name) {
        if(configs.containsKey(name)) return configs.get(name);
        if(name.endsWith(".yml")) name = name.replace(".yml", "");
        if(configs.containsKey(name)) return configs.get(name);
        return null;
    }

    public void saveAll() {
        configs.values().forEach(Config::save);
    }

    public void loadAll() {
        configs.values().forEach(Config::reload);
    }

}

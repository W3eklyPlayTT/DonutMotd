package com.w3eklyplaytt.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

public class MotdManager {

    private final JavaPlugin plugin;
    private final MiniMessage miniMessage;
    private String cachedMotd;

    private static final String CONFIG_KEY = "motd";
    private static final String DEFAULT_MOTD =
            "<gray>Earn money in Minecraft\n" +
            "<blue>donutsmp.net";

    public MotdManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.miniMessage = MiniMessage.miniMessage();
        loadMotd();
    }

    public void loadMotd() {
        if (!plugin.getConfig().contains(CONFIG_KEY)) {
            plugin.getConfig().set(CONFIG_KEY, DEFAULT_MOTD);
            plugin.saveConfig();
            plugin.getLogger().info("Saved default MOTD to config.yml");
        }

        String raw = plugin.getConfig().getString(CONFIG_KEY, DEFAULT_MOTD);

        try {
            Component component = miniMessage.deserialize(raw);
            cachedMotd = LegacyComponentSerializer.legacySection().serialize(component);
            plugin.getLogger().info("Loaded MOTD from config.yml");
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to parse MOTD MiniMessage: " + e.getMessage());
            cachedMotd = "A Minecraft Server";
        }
    }

    public void setMotd(String miniMessageText) {
        plugin.getConfig().set(CONFIG_KEY, miniMessageText);
        plugin.saveConfig();
        loadMotd();
    }

    public String getMotd() {
        return cachedMotd;
    }

    public String getRawMotd() {
        return plugin.getConfig().getString(CONFIG_KEY, DEFAULT_MOTD);
    }
}
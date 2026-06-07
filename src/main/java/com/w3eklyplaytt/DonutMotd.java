package com.w3eklyplaytt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.w3eklyplaytt.managers.MotdManager;
import com.w3eklyplaytt.managers.PluginManager;
import com.w3eklyplaytt.listeners.PlayerListener;
import com.w3eklyplaytt.commands.ReloadCommand;

public class DonutMotd extends JavaPlugin implements Listener {

    public static final String GREEN = "\u001B[32m";

    private MotdManager motdManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        motdManager = new MotdManager(this);

        registerCommands();
        PluginManager.getInstance().initialize();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info(GREEN + "================================");
        getLogger().info(GREEN + "DonutMotd has been enabled!");
        getLogger().info(GREEN + "================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("DonutMotd has been disabled!");
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd(motdManager.getMotd());
    }

    public void registerCommands() {
        Map<String, CommandExecutor> commands = new HashMap<>();
        commands.put("motdreload", new ReloadCommand(this));

        for (Map.Entry<String, CommandExecutor> entry : commands.entrySet()) {
            PluginCommand cmd = getCommand(entry.getKey());
            if (cmd != null) {
                cmd.setExecutor(entry.getValue());
            } else {
                getLogger().warning("Command not found in plugin.yml: " + entry.getKey());
            }
        }
    }

    public MotdManager getMotdManager() {
        return motdManager;
    }
}
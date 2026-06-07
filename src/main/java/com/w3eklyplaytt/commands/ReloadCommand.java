package com.w3eklyplaytt.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.w3eklyplaytt.*;

public class ReloadCommand implements CommandExecutor {
    private final DonutMotd plugin;

    public ReloadCommand(DonutMotd plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("wannounce.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        
        String reloadMessage = plugin.getConfig().getString("reload-message");
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + reloadMessage);
        return true;
    }
}

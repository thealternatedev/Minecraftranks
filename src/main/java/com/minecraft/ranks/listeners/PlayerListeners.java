package com.minecraft.ranks.listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minecraft.ranks.App;
import com.minecraft.ranks.utils.YamlMaker;

import net.md_5.bungee.api.ChatColor;

public class PlayerListeners implements Listener {
    
    private App plugin;
    private YamlMaker ranksConfig;

    public PlayerListeners(App plugin) {
        this.plugin = plugin;
        this.ranksConfig = plugin.getRanksConfig();
    }

    public App getPlugin() {
        return plugin;
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        this.plugin.getPlayersConfig().reloadConfig();
        this.ranksConfig.reloadConfig();
        this.plugin.reloadConfig();
        if (this.plugin.getPlayersConfig().getConfig().contains(event.getPlayer().getUniqueId().toString() + ".rank")) {
            String playerRank = this.plugin.getPlayersConfig().getConfig().getString(event.getPlayer().getUniqueId().toString() + ".rank");
            Player player = event.getPlayer();
            if (this.ranksConfig.getConfig().contains(playerRank)) {
                ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(playerRank);
                String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                String suffix = null;
                if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                String full = prefix + " " + player.getName();
                if (suffix != null) full += " " + suffix;
                full += ": " + ChatColor.translateAlternateColorCodes('&', "&f") + event.getMessage();
                event.setMessage(full);
            } else {
                if (!this.ranksConfig.getConfig().contains("defaultRank")) {
                } else {
                    String defaultRank = this.ranksConfig.getConfig().getString("defaultRank");

                    ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(defaultRank);
                    String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                    String suffix = null;
                    if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                    String full = prefix + " " + player.getName();
                    if (suffix != null) full += " " + suffix;
                    full += ": " + ChatColor.translateAlternateColorCodes('&', "&f") + event.getMessage();
                    event.setFormat("hello");
                    event.setMessage(full);
                }
            }
        } else {
            String playerRank = this.ranksConfig.getConfig().getString("defaultRank");
            Player player = event.getPlayer();
            if (this.ranksConfig.getConfig().contains(playerRank)) {
                ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(playerRank);
                String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                String suffix = null;
                if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                String full = prefix + " " + player.getName();
                if (suffix != null) full += " " + suffix;
                full += ": " + ChatColor.translateAlternateColorCodes('&', "&f")  + event.getMessage();
                event.setMessage(full);
            } else {
                if (!this.ranksConfig.getConfig().contains("defaultRank")) {
                } else {
                    String defaultRank = this.ranksConfig.getConfig().getString("defaultRank");

                    if (this.ranksConfig.getConfig().contains(defaultRank)) {
                        ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(defaultRank);
                        String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                        String suffix = null;
                        if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));
    
                        String full = prefix + " " + player.getName();
                        if (suffix != null) full += " " + suffix;
                        full += ": " + ChatColor.translateAlternateColorCodes('&', "&f") + event.getMessage();
                        event.setFormat("hello");
                        event.setMessage(full);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean allowNewbieRanks = this.plugin.getConfig().getBoolean("allowNewbieRanks");
        if (allowNewbieRanks) {
            if (!event.getPlayer().hasPlayedBefore()) {
                if (!this.ranksConfig.getConfig().contains("defaultRank")) {
                } else {
                    String defaultRank = this.ranksConfig.getConfig().getString("defaultRank");
    
                    this.plugin.getPlayersConfig().getConfig().set(event.getPlayer().getUniqueId().toString() + ".rank", defaultRank);
                    this.plugin.getPlayersConfig().saveConfig();
                }
            }
        }
        this.plugin.getPlayersConfig().reloadConfig();
        this.ranksConfig.reloadConfig();
        this.plugin.reloadConfig();

        if (this.plugin.getPlayersConfig().getConfig().contains(event.getPlayer().getUniqueId().toString() + ".rank")) {
            String playerRank = this.plugin.getPlayersConfig().getConfig().getString(event.getPlayer().getUniqueId().toString() + ".rank");
            Player player = event.getPlayer();
            if (this.ranksConfig.getConfig().contains(playerRank)) {
                ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(playerRank);
                String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                String suffix = null;
                if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                String full = prefix + " " + player.getName();
                if (suffix != null) full += " " + suffix;
                player.getPlayer().setDisplayName(full);
                player.getPlayer().setPlayerListName(full);
            } else {
                if (!this.ranksConfig.getConfig().contains("defaultRank")) {
                } else {
                    String defaultRank = this.ranksConfig.getConfig().getString("defaultRank");

                    ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(defaultRank);
                    String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                    String suffix = null;
                    if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                    String full = prefix + " " + player.getName();
                    if (suffix != null) full += " " + suffix;
                    player.getPlayer().setDisplayName(full);
                    player.getPlayer().setPlayerListName(full);
                }
            }
        } else {
            String playerRank = this.ranksConfig.getConfig().getString("defaultRank");
            Player player = event.getPlayer();
            if (this.ranksConfig.getConfig().contains(playerRank)) {
                ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(playerRank);
                String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                String suffix = null;
                if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                String full = prefix + " " + player.getName();
                if (suffix != null) full += " " + suffix;
                player.getPlayer().setDisplayName(full);
                player.getPlayer().setPlayerListName(full);
            } else {
                if (!this.ranksConfig.getConfig().contains("defaultRank")) {
                } else {
                    String defaultRank = this.ranksConfig.getConfig().getString("defaultRank");

                    if (this.ranksConfig.getConfig().contains(defaultRank)) {
                        ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(defaultRank);
                        String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
                        String suffix = null;
                        if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

                        String full = prefix + " " + player.getName();
                        if (suffix != null) full += " " + suffix;
                        player.getPlayer().setDisplayName(full);
                        player.getPlayer().setPlayerListName(full);
                    }
                }
            }
        }
    }
    
}

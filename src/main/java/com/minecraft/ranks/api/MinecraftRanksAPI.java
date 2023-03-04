package com.minecraft.ranks.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import com.minecraft.ranks.App;
import com.minecraft.ranks.api.events.PlayerRankChangeEvent;
import com.minecraft.ranks.api.ranks.Rank;

public class MinecraftRanksAPI {
    
    public static App getPlugin() {
        return (App) JavaPlugin.getPlugin(App.class);
    }

    public static boolean isRankCreated(Rank rank) {
        App plugin = getPlugin();
        plugin.getRanksConfig().reloadConfig();
        return plugin.getRanksConfig().getConfig().contains(rank.getName());
    }

    public static boolean isRankCreated(String rankName) {
        App plugin = getPlugin();
        plugin.getRanksConfig().reloadConfig();
        return plugin.getRanksConfig().getConfig().contains(rankName);
    }

    public static boolean saveRank(Rank rank) {
        return rank.save();
    }

    public static void rankPlayer(OfflinePlayer player, Rank rank) {
        App plugin = getPlugin();
        ConfigurationSection rankSection = plugin.getRanksConfig().getConfig().getConfigurationSection(rank.getName());
        String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
        String suffix = null;
        List<String> permissions = rankSection.getStringList("permissions");
        if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

        if (player.isOnline()) {
            String full = prefix + " " + player.getName();
            if (suffix != null) full += " " + suffix;
            player.getPlayer().setDisplayName(full);
            player.getPlayer().setPlayerListName(full);
            PermissionAttachment attachment = player.getPlayer().addAttachment(plugin);
            attachment.getPermissions().forEach((perm, value) -> {
                attachment.setPermission(perm, false);
            });
            for (String permission : permissions) {
                attachment.setPermission(Bukkit.getPluginManager().getPermission(permission), true);
            }
        }

        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, rank);
        plugin.getServer().getPluginManager().callEvent(event);

        plugin.getPlayersConfig().getConfig().set(player.getUniqueId().toString() + ".rank", rank);
        plugin.getPlayersConfig().saveConfig();
    }

    public static void rankPlayer(OfflinePlayer player, String rank) {
        App plugin = getPlugin();
        ConfigurationSection rankSection = plugin.getRanksConfig().getConfig().getConfigurationSection(rank);
        String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
        String suffix = null;
        if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));

        if (player.isOnline()) {
            String full = prefix + " " + player.getName();
            if (suffix != null) full += " " + suffix;
            player.getPlayer().setDisplayName(full);
            player.getPlayer().setPlayerListName(full);
            List<String> permissions;
            if (rankSection.contains("permissions")) {
                permissions = rankSection.getStringList("permissions");
            } else {
                permissions = new ArrayList<>();
            }
            PermissionAttachment attachment = player.getPlayer().addAttachment(plugin);
            attachment.getPermissions().forEach((perm, value) -> {
                attachment.setPermission(perm, false);
            });
            for (String permission : permissions) {
                attachment.setPermission(Bukkit.getPluginManager().getPermission(permission), true);
            }
        }

        Rank rankC = getRankFromConfig(rank);

        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, rankC);
        plugin.getServer().getPluginManager().callEvent(event);

        plugin.getPlayersConfig().getConfig().set(player.getUniqueId().toString() + ".rank", rank);
        plugin.getPlayersConfig().saveConfig();
    }

    public static Rank getRankFromConfig(String name) {
        App plugin = getPlugin();
        plugin.getRanksConfig().reloadConfig();
        if (!plugin.getRanksConfig().getConfig().contains(name)) return null;
        ConfigurationSection rankSection = plugin.getRanksConfig().getConfig().getConfigurationSection(name);
        String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix"));
        String suffix = null;
        String displayName = null;
        if (rankSection.contains("suffix")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("suffix"));
        if (rankSection.contains("displayName")) suffix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("displayName"));
        Rank rank = new Rank() {

            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public String getName() {
                return name;
            }
            
            
        };

        rank.setSuffix(suffix != null ? suffix : null);
        rank.setDisplayName(displayName != null ? displayName : null);
        return rank;
    }
}

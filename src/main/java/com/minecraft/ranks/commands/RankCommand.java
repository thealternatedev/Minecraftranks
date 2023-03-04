package com.minecraft.ranks.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.PermissionAttachment;

import com.minecraft.ranks.App;
import com.minecraft.ranks.api.MinecraftRanksAPI;
import com.minecraft.ranks.api.events.PlayerRankChangeEvent;
import com.minecraft.ranks.api.ranks.Rank;

public class RankCommand implements CommandExecutor {

    private App plugin;

    public RankCommand(App plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /rank <player> <rank|none>"));
            return false;
        }

        String playername = args[0];
        String rank;

        if (args.length <= 1) {
            if (this.plugin.getRanksConfig().getConfig().contains("defaultRank")) {
                rank = this.plugin.getRanksConfig().getConfig().getString("defaultRank");
            } else {
                rank = "none";
            }
        } else {
            rank = args[1];
        }

        if (rank.equals("none")) {
            return false;
        }

        if (rank.equals("defaultRank"))  {
            sender.sendMessage("You cannot use this rank name because it's a main field.");
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playername);

        if (player == null) return false;

        if (!this.plugin.getRanksConfig().getConfig().contains(rank)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThere was no ranks found with this name: &6" + rank));
            return false;
        }

        ConfigurationSection rankSection = this.plugin.getRanksConfig().getConfig().getConfigurationSection(rank);
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

        Rank rankC = MinecraftRanksAPI.getRankFromConfig(rank);

        PlayerRankChangeEvent event = new PlayerRankChangeEvent(player, rankC);
        this.plugin.getServer().getPluginManager().callEvent(event);

        this.plugin.getPlayersConfig().getConfig().set(player.getUniqueId().toString() + ".rank", rank);
        this.plugin.getPlayersConfig().saveConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " (" + player.getUniqueId().toString() + ") has been given the rank &6" + rank));

        return true;
    }

}

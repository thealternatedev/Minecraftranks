package com.minecraft.ranks.expansions;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.minecraft.ranks.App;
import com.minecraft.ranks.utils.YamlMaker;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class RankNameExpansion extends PlaceholderExpansion {

    private App plugin;
    private YamlMaker players;
    private YamlMaker ranks;

    public RankNameExpansion(App plugin) {
        this.plugin = plugin;
        this.players = plugin.getPlayersConfig();
        this.ranks = plugin.getRanksConfig();
    }

    @Override
    public @NotNull String getAuthor() {
        return "MinecraftRanks";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rank_name";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equals("rank_name")) {

            String rankName = this.players.getConfig().getString(player.getUniqueId().toString() + ".rank");

            if (!this.ranks.getConfig().contains(rankName)) return null;

            ConfigurationSection rankSection = this.ranks.getConfig().getConfigurationSection(rankName);
            String display = "";

            if (rankSection.contains("displayName")) {
                display = rankSection.getString("displayName");
            } else {
                display = rankName;
            }
            String rankDisplayName = org.bukkit.ChatColor.translateAlternateColorCodes('&', display);

            return rankDisplayName;

        }     

        return null;
    }
    
}

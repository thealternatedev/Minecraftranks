package com.minecraft.ranks;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.minecraft.ranks.commands.MinecraftRanks;
import com.minecraft.ranks.commands.Rank;
import com.minecraft.ranks.expansions.RankNameExpansion;
import com.minecraft.ranks.listeners.PlayerListeners;
import com.minecraft.ranks.utils.StringUtils;
import com.minecraft.ranks.utils.YamlMaker;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;


public class App extends JavaPlugin {

    private YamlMaker ranksConfig = new YamlMaker(this, "ranks.yml");
    private YamlMaker playersConfig = new YamlMaker(this, "players.yml");

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            getLogger().info(StringUtils.createSeparator(24));

            getLogger().info("PlaceholderAPI found! Registering Expansions.");

            getLogger().info(StringUtils.createSeparator(24));

            this.registerExpansions(
                new RankNameExpansion(this)
            );

        } else {

            getLogger().info(StringUtils.createSeparator(24)
            + '\n' + "PlaceholderAPI isn't included in the server plugins, No expansions registered."
            + '\n' + StringUtils.createSeparator(24));

        }

        getCommand("rank").setExecutor(new Rank(this));
        getCommand("minecraftranks").setExecutor(new MinecraftRanks(this));
        ranksConfig.saveDefaultConfig();
        playersConfig.saveDefaultConfig();
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        String s = StringUtils.createSeparator(24);
        this.log(s);
        this.log(s + " Minecraft ranks " + s);
        this.log("&2MinecraftRanks &6v" + this.getDescription().getVersion());
        this.log("Ready for use. Use /rank to rank players or /minecraftranks for more plugins and ranks stuff.");
        this.log(s);
    }

    public void log(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', "&3[&d" + this.getName() + "&3]&r " + msg);
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public void reloadRanks(CommandSender sender) {
        this.ranksConfig.reloadConfig();
        for (String v : this.ranksConfig.getConfig().getKeys(false)) {
            sender.sendMessage("Reloaded: " + v);
        }
    }

    public void registerExpansions(PlaceholderExpansion ...expansions) {
        int i = 0;

        for (PlaceholderExpansion ex : expansions) {
            i++;
            getLogger().info("Registering expansion (" + i + "/" + expansions.length + "): " + ex.getIdentifier());
            if (ex.canRegister()) {
                ex.register();
            } else {
                getLogger().log(Level.WARNING, "Cannot register expansion: " + ex.getIdentifier() + ".");
            }

        }

        getLogger().info("Registered " + expansions.length + " amount of expansions.");
    }

    public YamlMaker getPlayersConfig() {
        return playersConfig;
    }

    public YamlMaker getRanksConfig() {
        return ranksConfig;
    }
}

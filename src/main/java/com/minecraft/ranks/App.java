package com.minecraft.ranks;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


import com.minecraft.ranks.commands.MinecraftRanksCommand;
import com.minecraft.ranks.commands.RankCommand;
import com.minecraft.ranks.expansions.RankNameExpansion;
import com.minecraft.ranks.listeners.PlayerListeners;
import com.minecraft.ranks.updates.UpdateChecker;
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

        getCommand("rank").setExecutor(new RankCommand(this));
        getCommand("minecraftranks").setExecutor(new MinecraftRanksCommand(this));
        ranksConfig.saveDefaultConfig();
        playersConfig.saveDefaultConfig();
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        String s = StringUtils.createSeparator(24);
        String mr = " Minecraft Ranks ";
        String s2 = StringUtils.createSeparator(12) + mr + StringUtils.createSeparator(12);
        this.log(s);
        this.log("&6Checking for updates please wait...");
        UpdateChecker.checkUpdates((ver) -> {
            if (this.getDescription().getVersion().equals(ver)) {
                this.log("&aThere is no new updates avaliable on spigot.");
            } else {
                this.log("&aThere is a new update to &6MinecraftRanks &6which is &av" + ver + " &aPlease update at &6https://www.spigotmc.org/resources/minecraftranks.108300/");
            }
        });

        this.log("&6" + s2);
        this.log("&aEnabled Minecraft Ranks &6v" + this.getDescription().getVersion() + "");
        this.log("&a                Created By &6Max Jackson");
        this.log(StringUtils.createSeparator(24 + mr.length()));
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

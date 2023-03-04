package com.minecraft.ranks.api.ranks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;

import com.minecraft.ranks.App;
import com.minecraft.ranks.api.MinecraftRanksAPI;

public abstract class Rank implements IRank {
    
    private String suffix = null;
    private String displayName = null;
    private List<String> permissions = new ArrayList<>();

    public String getSuffix() {
        return suffix;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean addPermission(String permission) {
        if (Bukkit.getPluginManager().getPermission(permission) != null) {
            this.permissions.add(permission);
            return true;
        } else {
            return false;
        }
    }

    public boolean addPermission(Permission permission) {
        if (Bukkit.getPluginManager().getPermission(permission.getName()) != null) {
            this.permissions.add(permission.getName());
            return true;
        } else {
            return false;
        }
    }

    public boolean save() {
        App plugin = MinecraftRanksAPI.getPlugin();

        try {
            plugin.getRanksConfig().reloadConfig();
            ConfigurationSection rankSection;
            if (plugin.getRanksConfig().getConfig().contains(this.getName())) {
                rankSection = plugin.getRanksConfig().getConfig().getConfigurationSection(this.getName());
            } else {
                rankSection = plugin.getRanksConfig().getConfig().createSection(this.getName());
            }

            rankSection.set("prefix", this.getPrefix());
            rankSection.set("suffix", this.getSuffix());
            rankSection.set("displayName", this.getDisplayName());
            rankSection.set("permissions", this.getPermissions());

            plugin.getRanksConfig().saveConfig();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

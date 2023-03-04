package com.minecraft.ranks.updates;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.minecraft.ranks.App;

public class UpdateChecker {
    
    private static String resourceURL = "https://api.spigotmc.org/legacy/update.php?resource=108300";
    private static App plugin = (App) JavaPlugin.getPlugin(App.class);

    public static void checkUpdates(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL(resourceURL).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (Exception e) {
                plugin.log("&cUnable to check for updates: " + e.getMessage());
            }
        });
    }

}

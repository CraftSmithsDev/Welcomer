package org.free.welcomer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Welcomer extends JavaPlugin implements Listener {

    private MiniMessage miniMessage;
    private String joinMessage;
    private String leaveMessage;
    private boolean motdEnabled;
    private List<String> welcomeMotd;
    private ColorCodeSystem colorCodeSystem;

    @Override
    public void onEnable() {
        // Initialize MiniMessage
        miniMessage = MiniMessage.miniMessage();

        // Load or create config
        saveDefaultConfig();
        loadConfig();

        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Log enabled message
        getLogger().info("Welcomer has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Welcomer has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wr")) {
            reloadConfig();
            loadConfig();
            sender.sendMessage(parseMessage("&aConfig reloaded!"));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(parseMessage(joinMessage.replace("%player%", player.getName())));

        // Send custom join message (like motd.txt) if enabled
        if (motdEnabled) {
            for (String line : welcomeMotd) {
                player.sendMessage(parseMessage(line.replace("%player%", player.getName())));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(parseMessage(leaveMessage.replace("%player%", event.getPlayer().getName())));
    }

    private void loadConfig() {
        // Load color code system
        String colorCodeSystemConfig = getConfig().getString("colorcode-system", "MINIMESSAGE");
        colorCodeSystem = ColorCodeSystem.valueOf(colorCodeSystemConfig.toUpperCase());

        // Load messages
        joinMessage = getConfig().getString("join-message", "&e%player% &7has joined the game!");
        leaveMessage = getConfig().getString("leave-message", "&e%player% &7has left the game!");
        motdEnabled = getConfig().getBoolean("motd", true);
        welcomeMotd = getConfig().getStringList("welcome-motd");

        // Add default MOTD if the list is empty
        if (welcomeMotd.isEmpty()) {
            welcomeMotd.add("");
            welcomeMotd.add("<gray> Welcome %player% to the server");
            welcomeMotd.add("");
            getConfig().set("welcome-motd", welcomeMotd);
            saveConfig();
        }
    }

    private Component parseMessage(String message) {
        if (colorCodeSystem == ColorCodeSystem.MINIMESSAGE) {
            try {
                // Try parsing with MiniMessage
                return miniMessage.deserialize(message);
            } catch (Exception e) {
                // Fallback to legacy if MiniMessage fails
                return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
            }
        } else {
            // Use legacy color codes
            return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        }
    }

    private enum ColorCodeSystem {
        MINIMESSAGE,
        LEGACY
    }
}
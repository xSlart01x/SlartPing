package eu.slart.ping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.slart.ping.commands.CommandPing;
import eu.slart.ping.commands.CommandReload;

public class Main extends JavaPlugin {

	@Override
	public void onDisable() {
		// Plugin disabled
		this.getLogger().info("SlartPing has been successfully disabled!");
	}

	@Override
	public void onEnable() {
		// Saving default configuration
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		// Registering events
		this.registerEvents();
		// Plugin enabled
		this.getLogger().info("SlartPing has been successfully enabled!");
	}

	private void registerEvents() {
		// Registering commands:
		// Registering /ping
		Bukkit.getPluginCommand("ping").setExecutor(new CommandPing(this));
		// Registering /pingreload
		Bukkit.getPluginCommand("pingreload").setExecutor(new CommandReload(this));
	}

	// End of code.
}

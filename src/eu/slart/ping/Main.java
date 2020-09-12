package eu.slart.ping;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.slart.ping.commands.CommandPing;
import eu.slart.ping.commands.CommandReload;

@SuppressWarnings("nls")
public class Main extends JavaPlugin {

	@Override
	public void onDisable() {
		this.getLogger().info("SlartPing e' stato disabilitato correttamente!");
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		this.registerEvents();
		this.getLogger().info("SlartPing e' stato abilitato correttamente!");
	}

	private void registerEvents() {
		Bukkit.getPluginCommand("ping").setExecutor(new CommandPing(this));
		Bukkit.getPluginCommand("pingreload").setExecutor(new CommandReload(this));
	}
}

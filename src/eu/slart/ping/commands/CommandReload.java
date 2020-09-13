package eu.slart.ping.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import eu.slart.ping.Main;
import eu.slart.ping.util.ChatReplacer;

public class CommandReload implements CommandExecutor {
	private final Main instance;

	public CommandReload(final Main plugin) {
		// Init instance
		this.instance = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
		// If sender has permission to reload the plugin, it will be reloaded.
		if (sender.hasPermission("slartping.reload")) {
			// Reloading plugin's configuration.
			this.instance.reloadConfig();
			// Getting prefix & Sending message to the sender.
			final String prefix = ChatReplacer.colorize(this.instance.getConfig().getString("prefix"));
			sender.sendMessage(prefix + ChatReplacer.colorize(this.instance.getConfig().getString("config-reload")));
		}
		// Otherwise, an error message will be returned.
		else {
			// Getting prefix & Sending message to the sender.
			final String prefix = ChatReplacer.colorize(this.instance.getConfig().getString("prefix"));
			sender.sendMessage(prefix + ChatReplacer.colorize(this.instance.getConfig().getString("no-permission")));
		}
		// Then the command stops.
		return true;
	}

	// End of code.
}

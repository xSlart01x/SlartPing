package eu.slart.ping.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import eu.slart.ping.Main;
import eu.slart.ping.util.ChatReplacer;

@SuppressWarnings("nls")
public class CommandReload implements CommandExecutor {
	private final Main instance;

	public CommandReload(final Main plugin) {
		this.instance = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
		final String prefix = ChatReplacer.colorize(this.instance.getConfig().getString("prefix"));
		if (sender.hasPermission("slartping.reload")) {
			this.instance.reloadConfig();
			sender.sendMessage(prefix + ChatReplacer.colorize(this.instance.getConfig().getString("config-reload")));
		} else {
			sender.sendMessage(prefix + ChatReplacer.colorize(this.instance.getConfig().getString("no-permission")));
		}
		return true;
	}

}

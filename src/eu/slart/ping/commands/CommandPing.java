package eu.slart.ping.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import eu.slart.ping.Main;
import eu.slart.ping.util.ChatReplacer;

@SuppressWarnings("nls")
public class CommandPing implements CommandExecutor {

	private final Main instance;
	private final String prefix;

	public CommandPing(final Main plugin) {
		this.instance = plugin;
		this.prefix = ChatReplacer.colorize(this.instance.getConfig().getString("prefix"));
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
		final int len = args.length;
		if (len == 0) {
			if (!(sender instanceof Player)) { return this.notPlayer(sender); }
			if (!sender.hasPermission("slartping.me")) { return this.noPermission((Player) sender); }
			return this.pingMe((Player) sender);
		}
		final String playerArg = args[0];
		if (playerArg.equals(sender.getName())) {
			if (!sender.hasPermission("slartping.me")) { return this.noPermission((Player) sender); }
			return this.pingMe((Player) sender);
		}
		if (!sender.hasPermission("slartping.others")) { return this.noPermissionOthers((Player) sender); }
		final Player player = this.instance.getServer().getPlayer(playerArg);
		if ((player == null) || !player.isOnline()) { return this.notOnline((Player) sender, playerArg); }
		return this.pingOther(sender, player);
	}

	private boolean noPermission(final Player clientPlayer) {
		clientPlayer.sendMessage(ChatReplacer.colorize(this.instance.getConfig().getString("no-permission")));
		return true;
	}

	private boolean noPermissionOthers(final Player clientPlayer) {
		clientPlayer.sendMessage(this.prefix + ChatReplacer.colorize(this.instance.getConfig().getString("no-permission-others")));
		return true;
	}

	private boolean notOnline(final Player clientPlayer, final String pingedPlayer) {
		clientPlayer.sendMessage(this.prefix + ChatReplacer.replacePlayer(this.instance.getConfig().getString("not-online"), pingedPlayer));
		return true;
	}

	private boolean notPlayer(final CommandSender commandSender) {
		commandSender.sendMessage(ChatReplacer.colorize(this.instance.getConfig().getString("not-player")));
		return true;
	}

	private boolean pingMe(final Player player) {
		final int ping = ((CraftPlayer) player).getHandle().ping;
		player.sendMessage(this.prefix + ChatReplacer.replacePing(this.instance.getConfig().getString("ping-msg"), ping));
		return true;
	}

	private boolean pingOther(final CommandSender clientPlayer, final Player pingedPlayer) {
		final int ping = ((CraftPlayer) pingedPlayer).getHandle().ping;
		clientPlayer.sendMessage(
				this.prefix + ChatReplacer.replacePingPlayer(this.instance.getConfig().getString("ping-other-msg"), ping, pingedPlayer.getName()));
		return true;
	}
}

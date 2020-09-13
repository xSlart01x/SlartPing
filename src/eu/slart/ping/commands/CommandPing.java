package eu.slart.ping.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import eu.slart.ping.Main;
import eu.slart.ping.util.ChatReplacer;

public class CommandPing implements CommandExecutor {

	private final Main instance;
	private String prefix;

	public CommandPing(final Main plugin) {
		// Init instance
		this.instance = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
		// Init prefix
		this.prefix = ChatReplacer.colorize(this.instance.getConfig().getString("prefix"));
		// len == lenght of command arguments array
		final int len = args.length;
		operations: {
			// If len == 0 means that /ping was used without arguments
			if (len == 0) {
				// If sender is not instance of Player means that sender is console
				if (!(sender instanceof Player)) {
					// So, an error message will be returned. Then the command stops.
					this.notPlayer(sender);
					break operations;
				}
				// If sender hasn't permission
				if (!sender.hasPermission("slartping.me")) {
					// An error message will be returned. Then the command stops.
					this.noPermission((Player) sender);
					break operations;
				}
				// Otherwise, the command will send a message to the sender with its ping. Then
				// the command stops.
				this.pingMe((Player) sender);
				break operations;
			}
			// At this point, we are working with arguments > 0
			// So, the first argument is the player that the sender would like to ping.
			final String playerArg = args[0];
			// If pinged player name is equals to the sender name means that the sender
			// would like to ping itself.
			if (playerArg.equals(sender.getName())) {
				// Same logic as before. If the sender hasn't permission to ping itself an error
				// message will be returned. Then the command stops.
				if (!sender.hasPermission("slartping.me")) {
					this.noPermission((Player) sender);
					break operations;
				}
				// Otherwise, the command will send a message to the sender with its ping. Then
				// the command stops.
				this.pingMe((Player) sender);
				break operations;
			}
			// If pinged player is not sender and sender hasn't permission to ping other
			// player,
			// An error message will be returned. Then the command stops.
			if (!sender.hasPermission("slartping.others")) {
				this.noPermissionOthers((Player) sender);
				break operations;
			}
			// Otherwise, we will try to get pinged player
			final Player player = this.instance.getServer().getPlayer(playerArg);
			// If pinged player doesn't exist or it's not online, an error message will be
			// returned. Then the command stops.
			if ((player == null) || !player.isOnline()) {
				this.notOnline(sender, playerArg);
				break operations;
			}
			// Otherwise, the command will send a message to the sender with pinged player's
			// ping. Then the command stops.
			this.pingOther(sender, player);
		}
		return true;
	}

	// Methods to send messages.

	private void noPermission(final Player clientPlayer) {
		clientPlayer.sendMessage(ChatReplacer.colorize(this.instance.getConfig().getString("no-permission")));
	}

	private void noPermissionOthers(final Player clientPlayer) {
		clientPlayer.sendMessage(this.prefix + ChatReplacer.colorize(this.instance.getConfig().getString("no-permission-others")));
	}

	private void notOnline(final CommandSender commandSender, final String pingedPlayer) {
		commandSender.sendMessage(this.prefix + ChatReplacer.replacePlayer(this.instance.getConfig().getString("not-online"), pingedPlayer));
	}

	private void notPlayer(final CommandSender commandSender) {
		commandSender.sendMessage(ChatReplacer.colorize(this.instance.getConfig().getString("not-player")));
	}

	private void pingMe(final Player player) {
		final int ping = ((CraftPlayer) player).getHandle().ping;
		player.sendMessage(this.prefix + ChatReplacer.replacePing(this.instance.getConfig().getString("ping-msg"), ping));
	}

	private void pingOther(final CommandSender clientPlayer, final Player pingedPlayer) {
		final int ping = ((CraftPlayer) pingedPlayer).getHandle().ping;
		clientPlayer.sendMessage(
				this.prefix + ChatReplacer.replacePingPlayer(this.instance.getConfig().getString("ping-other-msg"), ping, pingedPlayer.getName()));
	}

	// End of code.
}

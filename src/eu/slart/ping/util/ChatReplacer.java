package eu.slart.ping.util;

import org.bukkit.ChatColor;

// Utility class:
// Used to send properly formatted chat messages.
public class ChatReplacer {

	public static String colorize(final String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static String replacePing(final String str, final int ping) {
		String newString = ChatReplacer.colorize(str);
		newString = newString.replace("%PING%", Integer.toString(ping));
		return newString;
	}

	public static String replacePingPlayer(final String str, final int ping, final String player) {
		String newString = ChatReplacer.colorize(str);
		newString = newString.replace("%PING%", Integer.toString(ping));
		newString = newString.replace("%PLAYER%", player);
		return newString;
	}

	public static String replacePlayer(final String str, final String player) {
		String newString = ChatReplacer.colorize(str);
		newString = newString.replace("%PLAYER%", player);
		return newString;
	}

}

package com.bukkit.systexpro.mcgrab.irc;

import org.jibble.pircbot.PircBot;
import yetanotherx.bukkitplugin.AssignPermissions.AssignPermissions;

import com.bukkit.systexpro.mcgrab.MCGrab;
import com.matejdro.bukkit.jail.Jail;

public class UnderBot extends PircBot {

	private AssignPermissions p;
	private Jail jail;
	
	public UnderBot() {
		this.setName("BlockBot");
	}

	public void onMessage(String channel, String sender,
			String login, String hostname, String message) {
		String[] cmd = message.split(" ");
		if (message.equalsIgnoreCase("time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
		if (cmd[0].equalsIgnoreCase("!hooker")) {
			if(cmd[1].equalsIgnoreCase("add")) {
				String player = cmd[2];
				this.sendMessage(channel, "Changed " + player + " to group Hooker in World: UnderRealm");
			}
			if(cmd[1].equalsIgnoreCase("del")) {
				String player = cmd[2];
				this.sendMessage(channel, "Removed " + player + " from group Hooker in World: UnderRealm");
			} 
			if(cmd[2].equals(null)) {
				this.sendChannelMessage("Please specify a player");
			}
		} 
		if(cmd[1].equalsIgnoreCase("!cmd")) {
			String command = cmd[2];
		}
		if(cmd[1].equalsIgnoreCase("!define")) {
			String text = cmd[2];
			if(text.equalsIgnoreCase("hooker")) {
				this.sendChannelMessage("A hooker is a player that is not banned, but jailed for griefing and loses the right to build.");
			}
		}
	}
	
	public void sendChannelMessage(String text) {
		this.sendMessage("#underrealmadmins", text);
	}
}

package com.bukkit.systexpro.mcgrab.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.bukkit.systexpro.mcgrab.Configuration;
import com.bukkit.systexpro.mcgrab.MCGrab;
import com.bukkit.systexpro.mcgrab.irc.UnderBot;

public class MCPlayerListener extends PlayerListener {

	@SuppressWarnings("unused")
	private final MCGrab plugin;
	private UnderBot bot;
	
	public MCPlayerListener(MCGrab instance) {
		plugin = instance;
	}


	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		System.out.println("[MCGrab] Stored Player: " + player.getDisplayName());
		try {
			Configuration.writeToLogin(player.getDisplayName(), "logged in");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(player.getDisplayName().equalsIgnoreCase("Christian367")) {
			player.setHealth(200);
		}
		if(player.getDisplayName().equalsIgnoreCase("DISMISS3D")) {
			player.setHealth(200);
		}
		event.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.YELLOW + "Player " + ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " connected");
	}

	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.RED + "[-] " + ChatColor.YELLOW + "Player " + ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " disconnected");
		Configuration.writeToLogin(player.getDisplayName(), "logged out.");
	}

	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		String reason = event.getReason();
		event.setLeaveMessage(ChatColor.RED + "[-] " + ChatColor.YELLOW + "Player " + ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " has been kicked");
		Configuration.writeToLogin(player.getDisplayName(), "was kicked with reason: " + reason + ".");
	}
	
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.isCancelled()) {
            return;
        }
		Player player = event.getPlayer();
        String message = event.getMessage();
        Configuration.writeToChat(player.getDisplayName(), message);
	}


}

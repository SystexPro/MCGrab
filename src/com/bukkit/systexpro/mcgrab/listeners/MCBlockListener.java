package com.bukkit.systexpro.mcgrab.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockListener;
import com.firestar.mcbans.mcbans;
import com.firestar.mcbans.mcbans_handler;
import com.bukkit.systexpro.mcgrab.Configuration;
import com.bukkit.systexpro.mcgrab.MCGrab;

public class MCBlockListener extends BlockListener {

	private final MCGrab plugin;
	private mcbans mcBans;
	private mcbans_handler mHandler;
	
    public MCBlockListener(MCGrab instance) {
       plugin = instance;
    }
	
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if(block.getType() == Material.WATER_BUCKET && player.getDisplayName() != "Iron_Alpha10") {
			block.setType(Material.AIR);
			player.getServer().broadcastMessage(ChatColor.GREEN + "[MCGrab] " + ChatColor.GOLD + "(use) Player: " + player.getDisplayName() + " tried to place WATER_BUCKET");
		}
		
		if(block.getType() == Material.LAVA_BUCKET) {
			block.setType(Material.AIR);
			player.getServer().broadcastMessage(ChatColor.GREEN + "[MCGrab] " + ChatColor.GOLD + "(use) Player: " + player.getDisplayName() + " tried to place LAVA_BUCKET");
		}
		if(block.getType() == Material.BEDROCK && !player.isOp()) {
			block.setType(Material.AIR);
			mHandler.ban(player.getDisplayName(), null, "[MCBans] Global Banned. Reason: Illegal Item", "g");
			Configuration.writeToKick(player.getDisplayName(), "[MCBans] Global Banned. Reason: Illegal Item");
		}
		if(block.getType() == Material.FLINT_AND_STEEL && !player.isOp()) {
			block.setType(Material.AIR);
			mHandler.ban(player.getDisplayName(), null, "[MCBans] Banned. Reason: Illegal Item", null);
			Configuration.writeToKick(player.getDisplayName(), "[MCBans] Banned. Reason: Illegal Item");
		}
	}
	
	
}

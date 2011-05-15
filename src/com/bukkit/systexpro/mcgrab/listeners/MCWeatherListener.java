package com.bukkit.systexpro.mcgrab.listeners;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.weather.WeatherListener;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.bukkit.systexpro.mcgrab.MCGrab;

public class MCWeatherListener extends WeatherListener {
	
	
	private final MCGrab plugin;
	
	public MCWeatherListener(MCGrab instance) {
		plugin = instance;
	}

	public void onWeatherChange(WeatherChangeEvent event) {
		if (!event.isCancelled()) {
			World world = event.getWorld();
				if (event.toWeatherState()) {
					event.setCancelled(true);
					this.plugin.getServer().broadcastMessage(ChatColor.GOLD + "Reverting Weather to Normal. Please stand by...");
				}
		};
	}
}

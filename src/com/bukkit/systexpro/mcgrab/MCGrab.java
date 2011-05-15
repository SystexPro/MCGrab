package com.bukkit.systexpro.mcgrab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.bukkit.systexpro.mcgrab.listeners.MCBlockListener;
import com.bukkit.systexpro.mcgrab.listeners.MCIRCListener;
import com.bukkit.systexpro.mcgrab.listeners.MCPlayerListener;
import com.bukkit.systexpro.mcgrab.listeners.MCWeatherListener;
import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.CraftIRCListener;
import com.ensifera.animosity.craftirc.Minebot;

public class MCGrab extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public final MCPlayerListener playerListener = new MCPlayerListener(this);
	public final MCWeatherListener weatherListener = new MCWeatherListener(this);
	public final MCBlockListener blockListener = new MCBlockListener(this);
	public String ConfigDir = "plugins/MCGrab";
	public String ConfigLogDir = ConfigDir + "/logs";
	public String LogFile = ConfigLogDir + "/logs.txt";
	public String Config = "config.yml";
	protected CraftIRC craftircHandle;
    protected MCIRCListener ircListener;
    ArrayList<String> ircTags = new ArrayList<String>();
	public static PermissionHandler Permissions;
	public Player player;

	@Override
	public void onDisable() {
		logger.log(Level.INFO, "[MCGrab] Unloaded");

	}

	@Override
	public void onEnable() {
		logger.log(Level.INFO, "[MCGrab] Loaded");
		File configFile = new File(ConfigDir + "/" + Config);
		File configDirs = new File(ConfigDir);
		File configLogDir = new File(this.ConfigLogDir);
		File logFile = new File(this.LogFile);
		if(!configDirs.exists()) {
			configDirs.mkdirs();
			logger.log(Level.INFO, "[MCGrab] Created MCGrab Directory");
		}
		if(!configLogDir.exists()) {
			configLogDir.mkdirs();
			logger.log(Level.INFO, "[MCGrab] Created MCGrab's Log Directory");

		}
		if(configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		registerEvents();
		setupPermissions();
		checkCraftIRC();
		checkMCBan();
	}

	private void setupPermissions() {
		Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

		if (this.Permissions == null) {
			if (test != null) {
				this.Permissions = ((Permissions)test).getHandler();
				logger.log(Level.INFO, "[MCGrab] Hooked into Permissions");
			} else {
				logger.info("[MCGrab] Permission system not detected");
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equals("mcgrab"))
		{
			return mainCommand(sender,args);
		}
		

		return super.onCommand(sender, cmd, commandLabel, args);
	}

	/**
	 * Handle Main Commands
	 * @param sender
	 * @param args
	 * @return
	 */
	private boolean mainCommand(CommandSender sender, String[] args) {
		if (!(this).Permissions.has(player, "mcgrab.commands.main")) {
            return false;
        }
		String command = args[0];
		if (args.length == 0) {
            return false;
        }
		if(command.equalsIgnoreCase("help") && sender.isOp()) {
			sender.sendMessage(ChatColor.GOLD + "===MCGrab Help===");
			return true;
		}
		if(command.equalsIgnoreCase("version")) {
			sender.sendMessage("Running MCGrab Version 1.0");
			return true;
		} else if (command.equalsIgnoreCase("reload")) {
			sender.getServer().broadcastMessage(ChatColor.GOLD + "[Global] " + ChatColor.GREEN + "- " + ChatColor.WHITE + "Server is restarting...");
			sender.getServer().reload();
			return true;
		} else if (command.equalsIgnoreCase("global")) {
			sender.getServer().broadcastMessage(ChatColor.GOLD + "[Global] " + ChatColor.GREEN + "- " + ChatColor.WHITE + args[1]);
			return true;
		}  else {
			return false;
		}
	}
	
	private void checkCraftIRC() {
		Plugin checkplugin = this.getServer().getPluginManager().getPlugin("CraftIRC");
        if (checkplugin == null || !checkplugin.isEnabled()) {
            logger.warning("CraftIRCPluginExample cannot be loaded because CraftIRC is not enabled on the server!");
            getServer().getPluginManager().disablePlugin(((org.bukkit.plugin.Plugin) (this)));
        } else {
            try {
                logger.info("[MCGrab] Found CraftIRC. Hooked into API System.");
                craftircHandle = (CraftIRC) checkplugin;
                ircListener = new MCIRCListener(craftircHandle);
                ircTags.add("community");
                ircTags.add("admin");
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                logger.warning("CraftIRCHookExample can't cast plugin handle as CraftIRC plugin! Inform Animosity.");
                getServer().getPluginManager().disablePlugin(((org.bukkit.plugin.Plugin) (this)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	private void checkMCBan() {
		Plugin checkplugin = this.getServer().getPluginManager().getPlugin("mcbans");
		if(!checkplugin.isEnabled()) {
			logger.warning("[MCGrab] MCBans not found.");
			getServer().getPluginManager().disablePlugin(((org.bukkit.plugin.Plugin) (this)));
		} else {
			try {
				logger.info("[MCGrab] Found MCBans. Hooked into API System.");
			} catch(ClassCastException e) {
				e.printStackTrace();
                logger.warning("CraftIRCHookExample can't cast plugin handle as CraftIRC plugin! Inform Animosity.");
                getServer().getPluginManager().disablePlugin(((org.bukkit.plugin.Plugin) (this)));
			}
		}
	}

	/**
	 * Registered Events
	 */
	private void registerEvents()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_LOGIN, this.playerListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, this.playerListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener , Priority.Normal, this);
		pm.registerEvent(Event.Type.WEATHER_CHANGE, this.weatherListener , Priority.Normal, this);
		//pm.registerEvent(Event.Type.CUSTOM_EVENT, this.ircListener, Priority.Normal, this);
		logger.log(Level.INFO, "[MCGrab] Registered Events");
	}

	public void sendMessage(Player p, String ag) {
		p.sendMessage("[MCGrab] " + ag);
	}

}

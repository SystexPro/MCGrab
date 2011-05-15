/**
 * 
 */
package com.bukkit.systexpro.mcgrab.listeners;

import org.bukkit.event.Event.Type;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.CustomEventListener;
import dk.monkeyboy.WelcomeMe.WelcomeMe;

import com.bukkit.systexpro.mcgrab.MCGrab;
import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.IRCEvent;

/**
 * @author Animosity
 * 
 */
public class MCIRCListener extends CustomEventListener implements Listener {
    private CraftIRC plugin = null;
    private WelcomeMe welcomeMe;
    private MCGrab mcGrab;

    public MCIRCListener(CraftIRC plugin) {
        this.plugin = (CraftIRC) plugin;
    }

    public void onCustomEvent(Event event) {
        if (!(event instanceof IRCEvent))
            return;
        else {
            IRCEvent ircEvent = (IRCEvent) event;
            if (!ircEvent.isHandled()) {
                if(ircEvent.eventMode.COMMAND != null) {
                	if (ircEvent.msgData.message.startsWith("example")) {
                        this.plugin.sendMessageToTag("This is an example custom CraftIRC command. The pen is %red%rrrrrrr%blue%oyal blue!",
                                ircEvent.msgData.srcChannelTag);
                        ircEvent.setHandled(true);
                    }
                }
            }

        }
    }

}
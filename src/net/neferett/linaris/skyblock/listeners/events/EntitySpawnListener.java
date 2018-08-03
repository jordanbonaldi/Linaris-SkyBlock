package net.neferett.linaris.skyblock.listeners.events;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawnListener implements Listener {

	@EventHandler
	public void onSpawnEntity(final CreatureSpawnEvent e) {
		if (e.getEntity() instanceof Monster && e.getEntity().getLocation().getWorld().getName().equals("world"))
			e.setCancelled(true);
	}

}

package net.neferett.linaris.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelledEvents implements Listener {

	@EventHandler
	public void onBlockBreak(final BlockBreakEvent e) {
		if (!e.getPlayer().getLocation().getWorld().getName().equals("world"))
			return;
		if (e.getBlock().getLocation().getX() == -22 && e.getBlock().getLocation().getY() == 102
				&& e.getBlock().getLocation().getZ() == 22)
			return;
		if (!e.getPlayer().isOp())
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e) {
		if (!e.getPlayer().getLocation().getWorld().getName().equals("world"))
			return;

		if (!e.getPlayer().isOp())
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityExplode(final EntityExplodeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onWeatherChange(final WeatherChangeEvent e) {
		if (e.getWorld().hasStorm())
			return;
		e.setCancelled(true);
	}

}

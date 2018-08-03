package net.neferett.linaris.skyblock.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.neferett.linaris.skyblock.handlers.Chests;
import net.neferett.linaris.skyblock.handlers.ConfigReader;

public class ChestListener implements Listener {

	protected static List<Chests> chests = new ArrayList<>();

	public ChestListener() {
		ConfigReader.getInstance().getLocationChest().forEach(loc -> {
			chests.add(new Chests(loc, ((Chest) loc.getBlock().getState()).getInventory().getContents()));
		});
	}

	@EventHandler
	public void InteractOnChest(final PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.CHEST)) {
			final Location loc = e.getClickedBlock().getLocation();
			final Chests c = chests.stream()
					.filter(chest -> (int) chest.getLoc().getX() == (int) loc.getX()
							&& (int) chest.getLoc().getY() == (int) loc.getY()
							&& (int) chest.getLoc().getZ() == (int) loc.getZ())
					.findFirst().orElse(null);
			if (c != null) {
				e.setCancelled(true);
				if (c.isCoolDownOK(e.getPlayer())) {
					Arrays.asList(c.getItems()).forEach(item -> {
						if (item != null && item.getType() != null)
							e.getPlayer().getInventory().addItem(item);
					});
					c.SetCooldown(e.getPlayer());
					e.getPlayer().sendMessage("§aLes items ont été ajouté dans ton inventaire !");
					return;
				} else {
					e.getPlayer().sendMessage(c.getTimeLeftForPlayer(e.getPlayer()));
					return;
				}
			}
		}
	}

}

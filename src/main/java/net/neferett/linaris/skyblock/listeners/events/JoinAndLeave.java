package net.neferett.linaris.skyblock.listeners.events;

import java.io.IOException;
import java.util.Arrays;

import net.neferett.linaris.skyblock.shop.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.TextLine;

import net.neferett.linaris.skyblock.Main;
import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;
import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.utils.ScoreboardSign;
import net.neferett.linaris.utils.TimeUtils;
import net.neferett.linaris.utils.tasksmanager.TaskManager;

public class JoinAndLeave extends PlayerManagers implements Listener {

	private boolean spawner = false;

	@EventHandler
	public void JoinEvent(final PlayerJoinEvent e) {
		e.setJoinMessage("");
		final M_Player p = PlayerManagers.get().getPlayer(e.getPlayer());

		if (p.getMoney() < 0)
			p.setMoney(0);

		if (!spawner && (spawner = true)) {
			ConfigReader.getInstance().loadHolos();

			Arrays.stream(NPC.values()).forEach(NPC::loadHolo);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onPlayerLeft(final PlayerQuitEvent event) {
		Main.getInstanceMain().getDatas().remove(event.getPlayer().getName().toLowerCase());
		PlayerManagers.get().removePlayer(event.getPlayer());
		if (DamageEvent.time.containsKey(event.getPlayer())
				&& TimeUtils.CreateTestCoolDown(15).test(DamageEvent.time.get(event.getPlayer()))) {
			event.getPlayer().getInventory().forEach(item -> {
				if (item != null && item.getType() != null && !item.getType().equals(org.bukkit.Material.AIR)
						&& item.getTypeId() != 397)
					event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
			});
			Arrays.asList(event.getPlayer().getInventory().getArmorContents()).forEach(item -> {
				if (item != null && item.getType() != null && !item.getType().equals(org.bukkit.Material.AIR))
					event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
			});
			event.getPlayer().getInventory().setBoots(null);
			event.getPlayer().getInventory().setHelmet(null);
			event.getPlayer().getInventory().setChestplate(null);
			event.getPlayer().getInventory().setLeggings(null);
			event.getPlayer().getInventory().clear();
		}
		final ScoreboardSign bar = ScoreboardSign.get(event.getPlayer());
		if (bar != null)
			bar.destroy();
		event.setQuitMessage("");
	}

}

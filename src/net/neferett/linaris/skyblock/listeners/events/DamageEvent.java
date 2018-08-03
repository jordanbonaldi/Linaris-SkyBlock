package net.neferett.linaris.skyblock.listeners.events;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.skyblock.utils.CuboidRegion;
import net.neferett.linaris.utils.TimeUtils;

public class DamageEvent implements Listener {

	public static HashMap<Player, Long>	time	= new HashMap<>();

	private final CuboidRegion			cb		= new CuboidRegion(ConfigReader.getInstance().getPos1(),
			ConfigReader.getInstance().getPos2());

	private final CuboidRegion			cb2		= new CuboidRegion(ConfigReader.getInstance().getPos3(),
			ConfigReader.getInstance().getPos4());

	@EventHandler
	public void damage(final EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player || e.getDamager() instanceof CraftArrow)
				&& e.getEntity() instanceof Player)
			if (this.cb.isInside(e.getEntity().getLocation()) || this.cb2.isInside(e.getEntity().getLocation()))
				return;
			else {
				final CraftArrow arr = e.getDamager() instanceof CraftArrow ? (CraftArrow) e.getDamager() : null;
				final Player p = arr != null && arr.getShooter() instanceof Player ? (Player) arr.getShooter()
						: (Player) e.getDamager();
				if (!time.containsKey(p) || time.containsKey(p) && !TimeUtils.CreateTestCoolDown(15).test(time.get(p)))
					p.sendMessage(
							"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
				if (!time.containsKey(e.getEntity()) || time.containsKey(e.getEntity())
						&& !TimeUtils.CreateTestCoolDown(15).test(time.get(e.getEntity())))
					e.getEntity().sendMessage(
							"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
				time.put(p, System.currentTimeMillis());
				time.put((Player) e.getEntity(), System.currentTimeMillis());
			}
	}

	@EventHandler
	public void damageatspawn(final EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (this.cb.isInside(e.getEntity().getLocation()))
			e.setCancelled(true);
		else if (this.cb2.isInside(e.getEntity().getLocation()))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPerform(final PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().contains("is") && time.containsKey(e.getPlayer())
				&& TimeUtils.CreateTestCoolDown(15).test(time.get(e.getPlayer()))) {
			e.getPlayer().sendMessage("§cTu ne peux pas faire cette commande en combat !");
			e.setCancelled(true);
		}
	}
}

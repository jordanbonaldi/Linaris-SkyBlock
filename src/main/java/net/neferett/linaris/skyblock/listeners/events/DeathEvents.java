package net.neferett.linaris.skyblock.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;
import net.neferett.linaris.utils.PlayerUtils;

import java.util.Objects;

public class DeathEvents extends PlayerManagers implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onFallDamage(final EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent e) {
		final M_Player p = PlayerManagers.get().getPlayer(e.getEntity());

		e.setDeathMessage("");
		p.addDeath();
		if (e.getEntity() != null)
			PlayerUtils.sendForceRespawn(e.getEntity(), 1);
		if (Objects.requireNonNull(e.getEntity()).getKiller() == null)
			return;
		final M_Player killer = PlayerManagers.get().getPlayer(e.getEntity().getKiller());
		this.ActionOnPlayers((pl) -> {
			pl.sendMessage("§e" + p.getName() + " §7a été tué par §e" + killer.getName());
		});

		killer.addKill();
		killer.addMoney(3, true);
		killer.delScore();
		if (killer.CanLevelUp())
			killer.LevelUP(e.getEntity().getKiller());

		DamageEvent.time.remove(p.getPlayer());
		if (p.getMoney() > 0 && p.getMoney() - 1 > 0)
			p.delMoney(1, true);
		p.setBackPos(p.getPlayer().getLocation());
	}

}

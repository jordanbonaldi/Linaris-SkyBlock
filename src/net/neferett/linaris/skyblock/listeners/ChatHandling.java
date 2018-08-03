package net.neferett.linaris.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;

public class ChatHandling extends PlayerManagers implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		final ASkyBlockAPI as = ASkyBlockAPI.getInstance();

		event.setCancelled(true);
		final PlayerData pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(player.getName());
		if (pd.getRank().getModerationLevel() < 1)
			this.ActionOnPlayers((p) -> {
				p.sendMessage("§" + pd.getRank().getColor() + "Lvl" + as.getIslandLevel(player.getUniqueId()) + " "
						+ pd.getRank().getPrefix(pd) + player.getName() + "§" + pd.getRank().getColor() + " : "
						+ event.getMessage().trim());
			});
		else
			this.ActionOnPlayers((p) -> {
				p.sendMessage("§" + pd.getRank().getColor() + "Lvl" + as.getIslandLevel(player.getUniqueId()) + " "
						+ pd.getRank().getPrefix(pd) + player.getName() + "§" + pd.getRank().getColor() + " : "
						+ event.getMessage().trim().replace("&", "§"));
			});

	}

}

package net.neferett.linaris.skyblock.listeners.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.skyblock.utils.CuboidRegion;

public class MoveListener implements Listener {

	private final CuboidRegion cb = new CuboidRegion(ConfigReader.getInstance().getPos1(),
			ConfigReader.getInstance().getPos2());

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {

		if (this.cb.isInside(e.getPlayer().getLocation()) && e.getPlayer().getLocation().getBlockY() < 10) {
			e.getPlayer().teleport(ConfigReader.getInstance().getSpawn());
			return;
		}

		if (ConfigReader.getInstance().getGameName().equals("PvPBox") && e.getPlayer().getLocation().getBlock()
				.getRelative(BlockFace.DOWN).getType() == Material.STONE_PLATE) {
			e.getPlayer()
					.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4.0).add(new Vector(0, 8, 0)));
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 5.0f, 2.0f);
			return;
		}

		if (ConfigReader.getInstance().getTeleporter().isInside(e.getPlayer().getLocation())) {
			e.getPlayer().teleport(ConfigReader.getInstance().getPvPSpawn());
			return;
		}
	}

	@EventHandler
	public void onTP(final PlayerTeleportEvent e) {
		final PlayerData pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(e.getPlayer().getName());

		if (pd.contains("invisible") && pd.getBoolean("invisible"))
			return;
		if (pd.getRank().getModerationLevel() > 1)
			return;
		e.getPlayer().setFlying(false);
		e.getPlayer().setAllowFlight(false);
	}

}

package net.neferett.linaris.skyblock.listeners.events;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.skyblock.shop.Shop;
import net.neferett.linaris.skyblock.shop.npc.NPC;
import net.neferett.linaris.skyblock.utils.CuboidRegion;
import net.neferett.linaris.skyblock.utils.GuiManager;

public class InteractListener implements Listener {

	private final CuboidRegion	cb	= new CuboidRegion(ConfigReader.getInstance().getPos1(),
			ConfigReader.getInstance().getPos2());

	@EventHandler
	public void blockChestInterract(final PlayerInteractEvent event) {
		if (event.getItem() != null && event.getItem().getType() != null
				&& (event.getItem().getType().equals(Material.LAVA_BUCKET)
						|| event.getItem().getType().equals(Material.WATER_BUCKET))
				&& event.getPlayer().getLocation().getWorld().getName().equals("world") && !event.getPlayer().isOp())
			event.setCancelled(true);
		if (!ConfigReader.getInstance().getChectClickable() && event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if (event.getClickedBlock().getType().equals(Material.CHEST))
				event.setCancelled(true);
		if ((this.cb.isInside(event.getPlayer().getLocation()))
				&& (event.getPlayer().getItemInHand().getType().equals(Material.POTION)
						|| event.getPlayer().getItemInHand().getType().equals(Material.BOW)))
			event.setCancelled(true);

	}

	@EventHandler
	public void InteractWithNPCSWithLeftClick(final EntityDamageByEntityEvent e) {
		if (!e.getDamager().getLocation().getWorld().getName().equals("world"))
			return;
		if (e.getEntity() instanceof Villager)
			Arrays.asList(NPC.values()).forEach(npc -> {
				if (npc.getEntity().equals(e.getEntity())) {
					if (npc.getName().contains("VIP") && BukkitAPI.get().getPlayerDataManager()
							.getPlayerData(((Player) e.getDamager()).getName()).getRank().getVipLevel() > 1)
						GuiManager.openGui(new Shop(npc.getName(), npc.getType(), (Player) e.getDamager()));
					else if (!npc.getName().contains("VIP"))
						GuiManager.openGui(new Shop(npc.getName(), npc.getType(), (Player) e.getDamager()));
					else
						((Player) e.getDamager()).sendMessage("?cIl faut ?tre VIP pour ouvrir ce SHOP !");
					e.setCancelled(true);
					return;
				}
			});
	}

	@EventHandler
	public void InteractWithNPCSWithRightClick(final PlayerInteractEntityEvent e) {
		if (!e.getPlayer().getLocation().getWorld().getName().equals("world"))
			return;
		if (e.getRightClicked() instanceof Villager)
			Arrays.asList(NPC.values()).forEach(npc -> {
				if (npc.getEntity().equals(e.getRightClicked())) {
					if (npc.getName().contains("VIP") && BukkitAPI.get().getPlayerDataManager()
							.getPlayerData(e.getPlayer().getName()).getRank().getVipLevel() > 1)
						GuiManager.openGui(new Shop(npc.getName(), npc.getType(), e.getPlayer()));
					else if (!npc.getName().contains("VIP"))
						GuiManager.openGui(new Shop(npc.getName(), npc.getType(), e.getPlayer()));
					else
						e.getPlayer().sendMessage("§cIl faut être gradé pour ouvrir ce SHOP");
					e.setCancelled(true);
					return;
				}
			});
	}

	@EventHandler
	public void onDoubleClick(final InventoryClickEvent e) {
		e.setCancelled(e.getClick().isLeftClick() && e.getClick().equals(ClickType.DOUBLE_CLICK));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractonGUISkyBlock(final InventoryClickEvent e) {
		if (e.getInventory().getTitle().contains("Protection") || e.getInventory().getTitle().contains("Challenges"))
			e.setCancelled(true);
	}
}

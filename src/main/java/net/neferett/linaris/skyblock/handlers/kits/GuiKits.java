package net.neferett.linaris.skyblock.handlers.kits;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.api.ranks.RankManager;
import net.neferett.linaris.skyblock.events.players.M_Player;
import net.neferett.linaris.skyblock.events.players.PlayerManagers;
import net.neferett.linaris.skyblock.handlers.ConfigReader;
import net.neferett.linaris.skyblock.utils.GuiManager;
import net.neferett.linaris.skyblock.utils.GuiScreen;
import net.neferett.linaris.utils.TimeUtils;

public class GuiKits extends GuiScreen {

	public class CoolDown {
		private final String	kitname;
		private final long		time;

		public CoolDown(final String kitname, final long time) {
			this.time = time;
			this.kitname = kitname;
		}

		public String getKitname() {
			return this.kitname;
		}

		public long getTime() {
			return this.time;
		}
	}

	public static HashMap<String, CoolDown> cool = new HashMap<>();

	public GuiKits(final Player p) {
		super("Choisi un Kit", ConfigReader.getInstance().getKitSlots(), p, false);
		this.build();
	}

	@Override
	public void drawScreen() {
		KitsManager.getInstance().getKits().forEach((kit) -> {
			this.setItem(kit.getKitdisp(), kit.getSlot());
		});
	}

	@Override
	public void onClick(final ItemStack item, final InventoryClickEvent e) {
		e.setCancelled(true);
		final M_Player p = PlayerManagers.get().getPlayer((Player) e.getWhoClicked());
		if (item.getType() != null && e.isRightClick())
			GuiManager.openGui(new GuiShowKits(item.getItemMeta().getDisplayName(), (Player) e.getWhoClicked(), this));
		else if (item.getType() != null && e.isLeftClick()) {
			final Kits kit = KitsManager.getInstance().getKitByName(item.getItemMeta().getDisplayName());

			if (cool.containsKey(p.getName().toLowerCase() + kit.getName())
					&& TimeUtils.CreateTestCoolDown(kit.getCooldown())
							.test(cool.get(p.getName().toLowerCase() + kit.getName()).getTime())) {
				final int minutes = (int) (TimeUtils.getTimeLeft(
						cool.get(p.getName().toLowerCase() + kit.getName()).getTime(), kit.getCooldown()) / 60);
				final int secondes = (int) (TimeUtils.getTimeLeft(
						cool.get(p.getName().toLowerCase() + kit.getName()).getTime(), kit.getCooldown()) % 60);
				this.getPlayer()
						.sendMessage("�7Tu dois attente encore �e"
								+ (minutes != 0 ? minutes + " minute" + (minutes > 1 ? "s " : " ") : " ") + secondes
								+ " seconde" + (secondes > 1 ? "s" : ""));
				this.getPlayer().closeInventory();
				return;
			} else if (!kit.getRank().equals("none") && RankManager.getInstance()
					.getRank(Integer.parseInt(kit.getRank())).getVipLevel() > p.getRank().getVipLevel()) {
				this.getPlayer()
						.sendMessage("�cVous devez �tre �a" + kit.getRank() + "�c pour pouvoir acceder a ce kit !");
				this.getPlayer().closeInventory();
				return;
			} else if (p.getMoney() < kit.getPrice()) {
				this.getPlayer().sendMessage(
						"�cIl vous manque �e" + (kit.getPrice() - p.getMoney()) + "$ �cpour acheter ce kit !");
				this.getPlayer().closeInventory();
				return;
			} else if (p.getMoney() >= kit.getPrice()) {
				p.delMoney(kit.getPrice(), false);
				cool.put(p.getName().toLowerCase() + kit.getName(),
						new CoolDown(kit.getName(), System.currentTimeMillis()));
				this.getPlayer().sendMessage("�aVous venez d'acheter le kit �b" + kit.getName());
				kit.giveKits(this.getPlayer());
				this.getPlayer().closeInventory();
			}
		}
	}

	@Override
	public void onClose() {}

	@Override
	public void onOpen() {}

}

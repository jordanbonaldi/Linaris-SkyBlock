package net.neferett.linaris.skyblock.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.skyblock.shop.npc.NPC.VillagerType;

public class AddItem extends CommandHandler {

	private File				file;

	private YamlConfiguration	shop;

	public AddItem() {
		super("additem", p -> p.getRank().getModerationLevel() > 1);
		this.setErrorMsg("�cTu dois �tre �6Mod�rateur�c !");
	}

	@SuppressWarnings("deprecation")
	public void addItem(final Players p, final String name, final String buy, final String sell,
			final String sellamount) {
		final List<String> list = this.shop.getStringList("Items");
		list.add(name + ":" + p.getItemInHand().getTypeId() + ":" + p.getItemInHand().getAmount() + ":"
				+ p.getItemInHand().getDurability() + ":" + buy + ":" + sell + ":" + sellamount
				+ (p.getItemInHand().getEnchantments().size() >= 1 ? this.SerializeEnchant(p) : ""));
		this.shop.set("Items", list);
		try {
			this.shop.save(this.file);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (arg2.size() < 6) {
			arg0.sendMessage("�cUtilisation: /additem <nom> <Type> <buyprice> <sellprice> <sellamount>");
			return;
		}

		final VillagerType type = Arrays.asList(VillagerType.values()).stream()
				.filter(t -> t.getName().equalsIgnoreCase(arg2.get(2))).findFirst().orElse(null);

		if (type == null) {
			arg0.sendMessage("�cType inconnu.");
			final StringBuilder sb = new StringBuilder();
			Arrays.asList(VillagerType.values()).forEach(t -> {
				sb.append(t.getName() + " ");
			});
			arg0.sendMessage("�cType possible�f: �e" + sb.toString());
			return;
		}

		this.file = new File("plugins/SkyBlock/shop/" + type.getName());
		this.shop = YamlConfiguration.loadConfiguration(this.file);
		try {
			this.shop.save(this.file);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		this.addItem(arg0, arg2.get(1), arg2.get(3), arg2.get(4), arg2.get(5));

		arg0.sendMessage("�aItem ajout� !");
	}

	@Override
	public void onError(final Players arg0) {
		arg0.DisplayErrorMessage();
	}

	@SuppressWarnings("deprecation")
	String SerializeEnchant(final Players p) {
		final StringBuilder sb = new StringBuilder();
		p.getItemInHand().getEnchantments().forEach((e, l) -> {
			sb.append(":" + e.getId() + "-" + l);
		});
		return sb.toString();
	}

}

package net.neferett.linaris.skyblock.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;

public class SpawnerCommand extends CommandHandler {

	public SpawnerCommand() {
		super("spawner", p -> p.getRank().getModerationLevel() > 3);
		this.setErrorMsg("§cCommande uniquement pour les §bTitan");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {

		if (arg2.size() != 2) {
			arg0.sendMessage("§cUtilisation: /spawner <type>");
			return;
		}

		final EntityType type = EntityType.fromName(arg2.get(1));

		if (type == null || !type.isSpawnable() || !type.isAlive() || type.getName().contains("DRAGON")
				|| type.getName().contains("WITHER")) {
			arg0.sendMessage("§cType inconnu !");
			arg0.sendMessage("§7Types possibles§f: ");
			final StringBuilder b = new StringBuilder();

			Arrays.asList(EntityType.values()).forEach(e -> {
				if (e.isSpawnable() && e.isAlive()
						&& !(e.getName().contains("DRAGON") || e.getName().contains("WITHER")))
					b.append(e + " ");
			});
			arg0.sendMessage("§c" + b.toString());
			return;
		}

		final Player p = Bukkit.getPlayer(arg0.getName());

		final Block b = p.getTargetBlock((HashSet<Byte>) null, 10);

		System.out.println(b);

		if (b == null) {
			arg0.sendMessage("§cBloc trop loin !");
			return;
		} else if (!b.getType().equals(Material.MOB_SPAWNER)) {
			arg0.sendMessage("§cLe bloc n'est pas un spawner !");
			return;
		}
		final BlockState state = b.getState();

		final CreatureSpawner spawner = (CreatureSpawner) state;
		spawner.setSpawnedType(type);

		arg0.sendMessage("§aSpawner changé en §f: §e" + type.getName());

	}

	@Override
	public void onError(final Players arg0) {
		arg0.DisplayErrorMessage();
	}

}

package net.neferett.linaris.skyblock.shop.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.TextLine;

import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.World;
import net.neferett.linaris.skyblock.Main;

public enum NPC {

	Armes("Armes", VillagerType.COMBAT, Profession.LIBRARIAN, "world", -7.5,63, 43.5),

	Blocs("Blocs", VillagerType.BLOCS, Profession.BLACKSMITH, "world", -6.5, 63, 46.5),

	Decoration("D�coration", VillagerType.DECORATION, Profession.PRIEST, "world", -3.5, 63, 47.5),

	Divers("Divers", VillagerType.MISC, Profession.BUTCHER, "world", 4.5, 63, 52.5),

	Nourriture("Nourriture", VillagerType.FOOD, Profession.BLACKSMITH, "world", 10.5, 63, 42.5),

//	Outils("Outils", VillagerType.TOOLS, Profession.LIBRARIAN, "world", 12.5, 63, 44.5),

	Redstone("Redstone", VillagerType.REDSTONE, Profession.PRIEST, "world", 12.5, 63, 47.5),

//	Spawner("Spawner", VillagerType.SPAWNER, Profession.FARMER, "world", 9.5, 63, 49.5),

	VIP("VIP", VillagerType.VIP, Profession.BLACKSMITH, "world", 0.5, 63, 75.5);

	public enum VillagerType {
		BLOCS("Blocs"),
		COMBAT("Combat"),
		DECORATION("Decoration"),
		FOOD("Food"),
		MISC("Misc"),
		REDSTONE("Redstone"),
		SPAWNER("Spawner"),
		TOOLS("Tools"),
		VIP("VIP");

		private String name;

		private VillagerType(final String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	private Villager		entity;
	private Hologram		h;
	private String			name;
	private Profession		p;
	private VillagerType	type;
	private String			worldName;

	private double			x, y, z;

	private NPC(final String name, final VillagerType type, final Profession p, final String worldName, final double x,
			final double y, final double z) {
		this.type = type;
		this.name = name;
		this.worldName = worldName;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void dispawn() {
		this.entity.remove();
		this.h.despawn();
	}

	public Villager getEntity() {
		return this.entity;
	}

	public String getName() {
		return this.name;
	}

	public VillagerType getType() {
		return this.type;
	}

	public String getWorldName() {
		return this.worldName;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	@SuppressWarnings("deprecation")
	public void spawn() {
		Bukkit.getServer().getWorld(this.worldName).getChunkAt(
				new Location(Bukkit.getServer().getWorld(this.worldName), this.getX(), this.getY(), this.getZ()))
				.load(true);
		final World w = ((CraftWorld) Bukkit.getServer().getWorld(this.worldName)).getHandle();
		final EntityVillager v = new SpecialVillager(w);

		v.setPosition(this.getX(), this.getY(), this.getZ());
		v.setProfession(this.p.getId());

		w.addEntity(v);

		this.entity = (Villager) v.getBukkitEntity();
		this.entity.setRemoveWhenFarAway(false);

		this.loadHolo();
	}

	public void loadHolo() {
		this.h = new Hologram(this.entity.toString() + this.entity.getLocation(),
				new Location(Bukkit.getWorld(this.getWorldName()), this.getX(), 2.2 + this.getY(), this.getZ()));
		Main.getInstanceMain().getHologramManager().addActiveHologram(this.h);
		this.h.addLine(new TextLine(this.h, "�e�lSHOP �b�l" + this.name()));
	}

}

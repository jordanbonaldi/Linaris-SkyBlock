package net.neferett.linaris.skyblock.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DataReader {

	protected FileConfiguration	configfile;

	protected File				data;
	private final String		p;

	public DataReader(final Player p) throws IOException {
		this.p = p.getName().toLowerCase();
		this.data = new File("plugins/SkyBlock/data/" + this.p + ".dat");
		this.configfile = YamlConfiguration.loadConfiguration(this.data);
		this.configfile.save(this.data);
	}

	public DataReader(final String p) {
		this.p = p.toLowerCase();
	}

	public void addHome(final Location loc, final String homename) {
		List<String> homes = this.getHomes();

		if (homes == null)
			homes = new ArrayList<>();
		homes.add(homename);
		this.configfile.set(this.p + ".homeslist", homes);
		this.configfile.set(this.p + ".homes." + homename + ".world", loc.getWorld().getName());
		this.configfile.set(this.p + ".homes." + homename + ".x", loc.getX());
		this.configfile.set(this.p + ".homes." + homename + ".y", loc.getY());
		this.configfile.set(this.p + ".homes." + homename + ".z", loc.getZ());
		this.configfile.set(this.p + ".homes." + homename + ".yaw", loc.getYaw());
		this.configfile.set(this.p + ".homes." + homename + ".pitch", loc.getPitch());
		this.save();
	}

	public int getDeaths() {
		return this.configfile.getInt(this.p + ".deaths");
	}

	public Location getHome(final String homename) {
		return new Location(Bukkit.getWorld(this.configfile.getString(this.p + ".homes." + homename + ".world")),
				this.configfile.getDouble(this.p + ".homes." + homename + ".x"),
				this.configfile.getDouble(this.p + ".homes." + homename + ".y"),
				this.configfile.getDouble(this.p + ".homes." + homename + ".z"),
				(float) this.configfile.getDouble(this.p + ".homes." + homename + ".yaw"),
				(float) this.configfile.getDouble(this.p + ".homes." + homename + ".pitch"));
	}

	@SuppressWarnings("unchecked")
	public List<String> getHomes() {
		return (List<String>) this.configfile.getList(this.p + ".homeslist");
	}

	public boolean isExists() {
		return new File("plugins/SkyBlock/data/" + this.p + ".dat").exists();
	}

	public void removeHome(final String homename) {
		final List<String> homes = this.getHomes();

		if (homes.contains(homename))
			homes.remove(homename);
		this.configfile.set(this.p + ".homeslist", homes);
		this.configfile.set(this.p + ".homes." + homename, null);
		this.save();
	}

	public void save() {
		try {
			this.configfile.save(this.data);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

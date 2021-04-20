package qc.veko.ranking.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Maps;
import com.massivecraft.factions.Faction;

import lombok.Getter;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.PointsUtils;

public class FactionFileManager {

	@Getter private static Map<String, Map<String, Integer>> factionsPoints = Maps.newHashMap();
	@Getter private static Map<String, List<String>> boughtFactionAddon = Maps.newHashMap();
	
	public void save(String faction) {
		File file = new File(FactionRanking.getInstance().getDataFolder() + "/points/" + faction + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		Map<String, Integer> m = getFactionsPoints().get(faction);
		config.set("kills", m.get("kills"));
		config.set("claims", m.get("claims"));
		config.set("events", m.get("events"));
		config.set("deaths", m.get("deaths"));
		config.set("money", m.get("money"));

		List<String> l = getBoughtFactionAddon().get(faction);
		config.set("bought", l);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void load(Faction faction) {
		String fac = PointsUtils.getFactionName(faction);
		File file = new File(FactionRanking.getInstance().getDataFolder() + "/points/" + fac + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		PointsUtils.createFactionData(faction);
		Map<String, Integer> m = getFactionsPoints().get(fac);
		List<String> l = getBoughtFactionAddon().get(fac);
		if (!file.exists())
			return;
		m.put("kills", config.getInt("kills"));
		m.put("claims", config.getInt("claims"));
		m.put("events", config.getInt("events"));
		m.put("deaths", config.getInt("deaths"));
		m.put("money", config.getInt("money"));

		l = config.getStringList("bought");
	}
}

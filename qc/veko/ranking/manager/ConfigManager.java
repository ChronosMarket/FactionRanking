package qc.veko.ranking.manager;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.ShopUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class ConfigManager {

	private String levelZeroName;
	private Map<String, Integer> levelOne = new HashMap<>();
	private Map<String, Integer> levelTwo = new HashMap<>();
	private Map<String, Integer> levelThree = new HashMap<>();
	private Map<String, Integer> levelFour  = new HashMap<>();
	private Map<String, Integer> levelFive = new HashMap<>();

	private Map<Integer, List<String>> addonPerLevel = Maps.newHashMap();
	private Map<String, Integer> pricePerAddon = Maps.newHashMap();

	public void loadConfig() {
		FileConfiguration config = FactionRanking.getInstance().getConfig();

		setLevelZeroName(getColor("levelZeroName", config));
		getLevelOne().put(getColor("levelOneName", config), config.getInt("levelOnePoints"));
		getLevelTwo().put(getColor("levelTwoName", config), config.getInt("levelTwoPoints"));
		getLevelThree().put(getColor("levelThreeName", config), config.getInt("levelThreePoints"));
		getLevelFour().put(getColor("levelFourName", config), config.getInt("levelFourPoints"));
		getLevelFive().put(getColor("levelFiveName", config), config.getInt("levelFivePoints"));

		FactionRanking.getInstance().setListOfFactions(FactionRanking.getInstance().getFactionListFile().getStringList("factionList"));

		getAddonPerLevel().put(1, config.getStringList("levelOneShop"));
		getAddonPerLevel().put(2, config.getStringList("levelTwoShop"));
		getAddonPerLevel().put(3, config.getStringList("levelThreeShop"));
		getAddonPerLevel().put(4, config.getStringList("levelFourShop"));
		getAddonPerLevel().put(5, config.getStringList("levelFiveShop"));

		getPricePerAddon().put("fchest9", config.getInt("fchest9"));
		getPricePerAddon().put("drop", config.getInt("drop"));
		getPricePerAddon().put("powerboost", config.getInt("powerboost"));
		getPricePerAddon().put("speed", config.getInt("speed"));
		getPricePerAddon().put("fchest18", config.getInt("fchest18"));
		getPricePerAddon().put("fly", config.getInt("fly"));
		getPricePerAddon().put("nofall", config.getInt("nofall"));
		getPricePerAddon().put("force", config.getInt("force"));
		getPricePerAddon().put("fchest27", config.getInt("fchest27"));
	}

	private String getColor(String path, FileConfiguration config) {
		String text = config.getString(path);
		if (text.contains("&"))
			return text.replaceAll("&", "§");
		return text;
	}
}

package qc.veko.ranking.manager;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import qc.veko.ranking.FactionRanking;

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

	public void loadConfig() {
		FileConfiguration config = FactionRanking.getInstance().getConfig();

		setLevelZeroName(getColor("levelZeroName", config));
		getLevelOne().put(getColor("levelOneName", config), config.getInt("levelOnePoints"));
		getLevelTwo().put(getColor("levelTwoName", config), config.getInt("levelTwoPoints"));
		getLevelThree().put(getColor("levelThreeName", config), config.getInt("levelThreePoints"));
		getLevelFour().put(getColor("levelFourName", config), config.getInt("levelFourPoints"));
		getLevelFive().put(getColor("levelFiveName", config), config.getInt("levelFivePoints"));

		List<String> l = FactionRanking.getInstance().getListOfFactions();
		l = FactionRanking.getInstance().getFactionListFile().getStringList("factionList");

	}

	private String getColor(String path, FileConfiguration config) {
		String text = config.getString(path);
		if (text.contains("&"))
			return text.replaceAll("&", "§");
		return text;
	}
}

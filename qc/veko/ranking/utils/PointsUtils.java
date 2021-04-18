package qc.veko.ranking.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.massivecraft.factions.Faction;

import qc.veko.ranking.manager.ConfigManager;
import qc.veko.ranking.manager.FactionFileManager;
import qc.veko.ranking.FactionRanking;

public class PointsUtils {

	public static int getFactionTotalPoints(Faction faction) {
		int kills = getFactionKillsPoints(faction);
		int claims = getFactionClaimsPoints(faction);
		int events = getFactionEventsPoints(faction);
		int death = getFactionDeathsPoints(faction);
		return ((kills + claims + events) - death);
	}
	/**
	 * Getter of Factions points
	 * @param faction
	 * @return
	 */
	public static int getFactionKillsPoints(Faction faction) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		return (m.get("kills"));
	}
	
	public static int getFactionClaimsPoints(Faction faction) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		return (m.get("claims"));
	}
	
	public static int getFactionEventsPoints(Faction faction) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		return (m.get("events"));
	}
	
	public static int getFactionDeathsPoints(Faction faction) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		return (m.get("deaths"));
	}
	
	/**
	 * Setter of Faction points
	 * @param faction
	 * @param points
	 */
	public static void addFactionKillsPoints(Faction faction, int points) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		int pointsBefore = getFactionKillsPoints(faction);
		m.put("kills", pointsBefore + points);
	}
	
	public static void addFactionClaimsPoints(Faction faction, int points) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		int pointsBefore = getFactionClaimsPoints(faction);
		m.put("claims", pointsBefore + points);
	}
	
	public static void addFactionEventsPoints(Faction faction, int points) {
		Map<String, Integer> m = getFactionsPointsMap(faction); 
		int pointsBefore = getFactionEventsPoints(faction);
		m.put("events", pointsBefore + points);
	}
	
	public static void addFactionDeathsPoints(Faction faction, int points) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		int pointsBefore = getFactionDeathsPoints(faction);                 
		m.put("deaths", pointsBefore + points);
	}
	
	/**
	 * Getting the Map for Faction Points Type
	 * @param faction
	 * @return
	 */
	private static Map<String, Integer> getFactionsPointsMap(Faction faction) {
		return FactionFileManager.getFactionsPoints().get(getFactionName(faction));
	}
	
	public static int getFactionRank(Faction faction) {
		return FactionRanking.getInstance().getRankByFaction().get(getFactionName(faction));
	}
	
	public static String getFactionName(Faction faction) {
		return faction.getTag();
	}
	
	public static void deleteFactionData(Faction faction) {
		FactionFileManager.getFactionsPoints().remove(getFactionName(faction));
		File file = new File(FactionRanking.getInstance().getDataFolder() + "/points/" + getFactionName(faction) + ".yml");
        file.delete();
	}

	public static int getFactionMoney(Faction faction) {
		Map<String, Integer> m = getFactionsPointsMap(faction);
		return m.get("money");
	}
	
	public static void createFactionData(Faction faction) {
		Map<String, Integer> m = new HashMap<>();
		m.put("kills", 0);
		m.put("claims", 0);
		m.put("events", 0);
		m.put("deaths", 0);
		m.put("money", 0);
		FactionFileManager.getFactionsPoints().put(getFactionName(faction), m);
	}
	
	public static String getFactionRankName(Faction faction) {
		int points = getFactionTotalPoints(faction);
		ConfigManager config = FactionRanking.getInstance().getConfigManager();
		if (points >= getPointsPerLevel(config.getLevelFive()))
			return getNamePerLevel(config.getLevelFive());
		if (points >= getPointsPerLevel(config.getLevelFour()))
			return getNamePerLevel(config.getLevelFour());
		if (points >= getPointsPerLevel(config.getLevelThree()))
			return getNamePerLevel(config.getLevelThree());
		if (points >= getPointsPerLevel(config.getLevelTwo()))
			return getNamePerLevel(config.getLevelTwo());
		if (points >= getPointsPerLevel(config.getLevelOne()))
			return getNamePerLevel(config.getLevelOne());
		return config.getLevelZeroName();
			
	}

	private static String getNamePerLevel(Map<String, Integer> level){
		return level.keySet().stream().findFirst().get();
	}
	private static int getPointsPerLevel(Map<String, Integer> level){
		return level.values().stream().findFirst().get();
	}
	
}

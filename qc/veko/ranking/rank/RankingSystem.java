package qc.veko.ranking.rank;

import java.util.*;
import java.util.stream.Collectors;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import qc.veko.ranking.manager.FactionFileManager;
import qc.veko.ranking.utils.PointsUtils;
import qc.veko.ranking.FactionRanking;

public class RankingSystem {

	private Map<String, Map<String, Integer>> factionsPoints = new HashMap<>();
	private FactionFileManager manager;

	public RankingSystem(FactionFileManager manager) {
		this.manager = manager;
		rankingCalc();
	}

	private void rankingCalc() {
		if (FactionRanking.getInstance().getListOfFactions().isEmpty())
			Factions.getInstance().getAllFactions().forEach(faction -> {
				if (faction.isWilderness() || faction.isWarZone() || faction.isSafeZone())
					return;
				else if (!FactionRanking.getInstance().getRankByFaction().containsKey(faction.getTag()))
					manager.load(faction);
				else if (!FactionFileManager.getFactionsPoints().containsKey(faction.getTag()))
					PointsUtils.createFactionData(faction);
				int points = PointsUtils.getFactionTotalPoints(faction);
				if (points != 0)
					if (!FactionRanking.getInstance().getListOfFactions().contains(faction.getTag()))
						FactionRanking.getInstance().getListOfFactions().add(faction.getTag());
			});

		FactionRanking.getInstance().getListOfFactions().forEach(factionTag ->{
			Faction faction = Factions.getInstance().getByTag(factionTag);
			if (!FactionRanking.getInstance().getRankByFaction().containsKey(factionTag))
				manager.load(faction);
			int pointsGeneral = PointsUtils.getFactionTotalPoints(faction);
			int pointsEvents = PointsUtils.getFactionEventsPoints(faction);
			int pointsKills = PointsUtils.getFactionKillsPoints(faction);

			Map<String, Integer> general = setMapPerSection(factionTag, pointsGeneral, "General");
			Map<String, Integer> events = setMapPerSection(factionTag, pointsEvents, "Events");
			Map<String, Integer> pvp = setMapPerSection(factionTag, pointsKills, "Pvp");

			factionsPoints.put("General", general);
			factionsPoints.put("Events", events);
			factionsPoints.put("Pvp", pvp);

		});
		System.out.println(factionsPoints.get("General").toString());
		Map<String, Integer> general = getStreamedMap(getMapPerSection("General"));
		Map<String, Integer> events = getStreamedMap(getMapPerSection("Events"));
		Map<String, Integer> pvp = getStreamedMap(getMapPerSection("Pvp"));

		setRankingOther(events, "Events");
		setRankingOther(pvp, "Pvp");

		int calc = 0;
		Map<Integer, String> top10 = new HashMap<>();
		for (Map.Entry<String, Integer> entry : general.entrySet()) {
			calc++;
			if (FactionRanking.getInstance().getTopTen().size() < 10) {
				top10.put(calc, entry.getKey());
				FactionRanking.getInstance().getTopTen().put("General", top10);
			}
			FactionRanking.getInstance().getRankByFaction().put(entry.getKey(), calc);
			setPointsToMap(entry.getKey(), entry.getValue(), "General");
		}
	}

	private Map<String, Integer> setMapPerSection(String factionTag, int points, String section) {
		Map<String, Integer> m = null;
		if (!factionsPoints.containsKey(section))
			m = new HashMap<>();
		else
		 	m = factionsPoints.get(section);
		m.put(factionTag, points);
		return m;
	}
	private Map<String, Integer> getMapPerSection(String section) {
		Map<String, Integer> m = factionsPoints.get(section);
		return m;
	}

	private Map<String, Integer> getStreamedMap(Map<String, Integer> map) {
		Map<String, Integer> result = new LinkedHashMap<>();
		map.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
		return result;
	}

	private void setPointsToMap(String factionTag, int points, String section) {
		if (!FactionRanking.getInstance().getPointsByFaction().containsKey(section)) {
			Map<String, Integer> m = new HashMap<>();
			m.put(factionTag, points);
			FactionRanking.getInstance().getPointsByFaction().put(section, m);
		} else {
			FactionRanking.getInstance().getPointsByFaction().get(section).put(factionTag, points);
		}
	}

	private void setRankingOther(Map<String, Integer> map, String category) {
		Map<Integer, String> top10= new HashMap<>();
		int calc = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			calc++;
			if (FactionRanking.getInstance().getTopTen().size() < 10) {
				top10.put(calc, entry.getKey());
				FactionRanking.getInstance().getTopTen().put(category, top10);
			}
			setPointsToMap(entry.getKey(), entry.getValue(), category);
		}
	}
}

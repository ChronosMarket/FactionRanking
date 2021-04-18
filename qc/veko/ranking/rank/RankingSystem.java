package qc.veko.ranking.rank;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.massivecraft.factions.Factions;

import qc.veko.ranking.manager.FactionFileManager;
import qc.veko.ranking.utils.PointsUtils;
import qc.veko.ranking.FactionRanking;

public class RankingSystem {
	
	private TreeMap<Integer, String> disorderFactionsPoints = new TreeMap<>(Collections.reverseOrder());
	private FactionFileManager manager;

	public RankingSystem(FactionFileManager manager) {
		this.manager = manager;
		rankingCalc();
	}

	private void rankingCalc() {
		Factions.getInstance().getAllFactions().forEach(faction ->{
			if (faction.isWilderness() || faction.isWarZone() || faction.isSafeZone())
				return;
			if (!FactionRanking.getInstance().getRankByFaction().containsKey(faction.getTag()))
				manager.load(faction);
			if (!FactionFileManager.getFactionsPoints().containsKey(faction.getTag()))
				PointsUtils.createFactionData(faction);
			String name = PointsUtils.getFactionName(faction);
			int points = PointsUtils.getFactionTotalPoints(faction);
			disorderFactionsPoints.put(points, name);
		});
		int calc = 0;
		for (Map.Entry<Integer, String> entry : disorderFactionsPoints.entrySet()) {
			calc++;
			if (FactionRanking.getInstance().getTopTen().size() < 10)
				FactionRanking.getInstance().getTopTen().put(calc, entry.getValue());
			FactionRanking.getInstance().getRankByFaction().put(entry.getValue(), calc);
		}
	}
	
}

package qc.veko.ranking.utils;

import com.massivecraft.factions.Faction;
import qc.veko.ranking.manager.FactionFileManager;

import java.util.List;

public class AddonUtils {

    public static List<String> getFactionBoughtAddonList(Faction faction) {
        List<String> l = FactionFileManager.getBoughtFactionAddon().get(getFactionName(faction));
        return l;
    }

    public static String getFactionName(Faction faction) {
        return faction.getTag();
    }

}

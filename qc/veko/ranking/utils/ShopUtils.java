package qc.veko.ranking.utils;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.entity.Player;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.manager.FactionFileManager;

import java.util.List;

public class ShopUtils {
    @SuppressWarnings("deprecation")
    public static void buyAddon(String addon, int money, Player player) {
        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        int invest = FactionFileManager.getFactionsPoints().get(faction.getTag()).get("money");
        if (!verifyAmount(money, invest, player))
            return;
        FactionFileManager.getFactionsPoints().get(faction.getTag()).put("money", invest - money);
        System.out.println(faction.getOnlinePlayers().toString());
        faction.getOnlinePlayers().forEach(p -> {
            p.sendTitle("§6Attout de faction Acheter", "§e" + addon + " a été acheter par " + player.getName());
            p.sendMessage("Félicitation pour votre achat");
        });
        FactionFileManager.getBoughtFactionAddon().get(faction.getTag()).add(addon);
        FactionFileManager.getBoughtFactionAddon().get(faction.getTag()).remove("nothing");
    }

    private static boolean verifyAmount(int money, int invest, Player player) {
        if (money > invest) {
            player.sendMessage("Vous n'avez pas assez d'argent pour acheter cette attout de faction");
            return false;
        }
        return true;
    }

    public static List<String> getAddonPerLevel(int level) {
        return FactionRanking.getInstance().getConfigManager().getAddonPerLevel().get(level);
    }

    public static int getPricePerAddon(String addon) {
        return FactionRanking.getInstance().getConfigManager().getPricePerAddon().get(addon);
    }


}
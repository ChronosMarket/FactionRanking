package qc.veko.ranking.utils;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.entity.Player;
import qc.veko.ranking.manager.FactionFileManager;

public class ShopUtils {

    public static void buyAddon(String addon, int money, Player player) {
        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        int invest = FactionFileManager.getFactionsPoints().get(faction.getTag()).get("money");
        if (verifyAmount(money, invest, player))
            return;
        FactionFileManager.getFactionsPoints().get(faction.getTag()).put("money", invest - money);
        for (Player p : faction.getOnlinePlayers()) {
            p.sendTitle("§6Attout de faction Acheter", "§e" + addon + " a été acheter par " + player.getName());
        }
        FactionFileManager.getBoughtFactionAddon().get(faction.getTag()).add(addon);
    }

    private static boolean verifyAmount(int money, int invest, Player player) {
        if (money > invest) {
            player.sendMessage("Vous n'avez pas assez d'argent pour acheter cette attout de faction");
            return false;
        }
        return true;
    }

}
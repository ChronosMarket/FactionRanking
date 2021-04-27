package qc.veko.ranking.inventory;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.ItemBuilder;
import qc.veko.ranking.utils.PointsUtils;

import java.util.HashMap;
import java.util.List;

public class RankingInventory {

    private List<Integer> glassInGui() {
        return Lists.newArrayList(0, 1, 2, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 33, 34, 35);
    }

    @Getter private HashMap<Integer, Integer> placementPerLevel = new HashMap<Integer, Integer>(){{
        put(1, 11);
        put(2, 12);
        put(3, 13);
        put(4, 14);
        put(5, 15);
        put(6, 20);
        put(7, 21);
        put(8, 22);
        put(9, 23);
        put(10, 24);
    }};

    @SuppressWarnings("serial")
    public HashMap<Integer, String> getCategoryPerPage= new HashMap<Integer, String>(){{
        put(1, "General");
        put(2, "Events");
        put(3, "Pvp");
    }};

    public Inventory getInventory(Faction faction, int page){
        Inventory inv = Bukkit.createInventory(null, 36, "Classement Faction Page : " + page);
        ItemBuilder itemFrame = new ItemBuilder(Material.ITEM_FRAME).addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemBuilder next = new ItemBuilder(Material.PAPER).setName("Suivant");
        ItemBuilder before = new ItemBuilder(Material.PAPER).setName("Precedent");

        String category = getCategoryPerPage.get(page);
        ItemBuilder star = new ItemBuilder(Material.NETHER_STAR).setName("§6Classement Faction").setLore("§eClassement : §6" + category);
        inv.setItem(4, star.toItemStack());
        for (int i = 1; i < 11; ++i){
            itemFrame.toItemStack().setAmount(i);
            Faction rankingFaction = Factions.getInstance().getByTag(getFactionNameByRank(i, category));
            if (faction == rankingFaction)
                itemFrame.addUnsafeEnchantment(Enchantment.LURE, 1);
            if (faction != rankingFaction)
                itemFrame.removeEnchantment(Enchantment.LURE);
            itemFrame.setName("§6#" + i + " §e" + getFactionNameByRank(i, category));
            if (!getFactionNameByRank(i, category).equalsIgnoreCase("Aucune"))
                itemFrame.setLore("§6 Points : §e" + FactionRanking.getInstance().getPointsByFaction().get(category).get(rankingFaction.getTag()));
            else
                itemFrame.setLore("§6 Points : §e0");
            inv.setItem(getPlacementPerLevel().get(i), itemFrame.toItemStack());
        }
        for (int i = 0; i < 36; ++i) {
            if (glassInGui().contains(i))
                inv.setItem(i, yellow);
        }
        if (page != 1)
            inv.setItem(28, before.toItemStack());
        if (page != 3)
            inv.setItem(34, next.toItemStack());
        return inv;
    }

    private String getFactionNameByRank(int rank, String category) {
        if (FactionRanking.getInstance().getTopTen().get(category).containsKey(rank))
            return FactionRanking.getInstance().getTopTen().get(category).get(rank);
        return "Aucune";
    }
}

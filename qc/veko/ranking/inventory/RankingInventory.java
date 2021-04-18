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


    public Inventory getInventory(Faction faction){
        Inventory inv = Bukkit.createInventory(null, 36, "Classement Faction");
        ItemBuilder paper = new ItemBuilder(Material.PAPER);
        paper.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        for (int i = 1; i < 11; ++i){
            paper.toItemStack().setAmount(i);
            Faction rankingFaction = Factions.getInstance().getByTag(getFactionNameByRank(i));
            if (faction == rankingFaction)
                paper.addUnsafeEnchantment(Enchantment.LURE, 1);
            if (faction != rankingFaction)
                paper.removeEnchantment(Enchantment.LURE);
            paper.setName("§6#" + i + " §e" + getFactionNameByRank(i));
            if (!getFactionNameByRank(i).equalsIgnoreCase("Aucune")) {
                paper.setLore("§6 Points : §e" + PointsUtils.getFactionTotalPoints(rankingFaction));
            } else {
                paper.setLore("§6 Points : §e0");
            }
            inv.setItem(getPlacementPerLevel().get(i), paper.toItemStack());
        }
        for (int i = 0; i < 36; ++i) {
            if (glassInGui().contains(i))
                inv.setItem(i, yellow);
        }
        return inv;
    }

    private String getFactionNameByRank(int rank) {
        if (FactionRanking.getInstance().getTopTen().containsKey(rank))
            return FactionRanking.getInstance().getTopTen().get(rank);
        return "Aucune";
    }
}

package qc.veko.ranking.inventory;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;

import com.massivecraft.factions.Faction;
import org.bukkit.inventory.ItemStack;
import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.ItemBuilder;
import qc.veko.ranking.utils.PointsUtils;

import java.util.List;

public class InformationInventory {

	private List<Integer> glassInGui() {
		return Lists.newArrayList(0, 1, 2, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 42, 43, 44);
	}

	public Inventory getInventory(Faction faction) {
		Inventory inv = Bukkit.createInventory(null, 45, "Information de faction : " + faction.getTag());
		ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);

		String owner = faction.getFPlayerAdmin().getName();
		ItemBuilder head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setName("§6" + faction.getTag()).setLore(new String[] { "",
				"§e---------------------------", " ", "§6Chef de faction : §e" + owner, " ",
				"§e---------------------------"});
		head.setSkullOwner(owner);
		inv.setItem(4, head.toItemStack());

		ItemBuilder total = new ItemBuilder(Material.NETHER_STAR).setName("§6Points Total : §e" + PointsUtils.getFactionTotalPoints(faction));
		inv.setItem(13, total.toItemStack());

		ItemBuilder kills = new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Points de Kill : §e" + PointsUtils.getFactionKillsPoints(faction));
		inv.setItem(20, kills.toItemStack());

		ItemBuilder claims = new ItemBuilder(Material.MAP).setName("§6Points de Claims : §e" + PointsUtils.getFactionClaimsPoints(faction));
		inv.setItem(21, claims.toItemStack());

		ItemBuilder events = new ItemBuilder(Material.BEACON).setName("§6Points d'events : §e" + PointsUtils.getFactionEventsPoints(faction));
		inv.setItem(22, events.toItemStack());

		ItemBuilder deaths = new ItemBuilder(Material.BONE).setName("§6Points de mort : §e-" + PointsUtils.getFactionDeathsPoints(faction));
		inv.setItem(23, deaths.toItemStack());

		ItemBuilder money = new ItemBuilder(Material.PAPER).setName("§6Money de faction : §e" + PointsUtils.getFactionMoney(faction));
		inv.setItem(24, money.toItemStack());

		ItemBuilder rank = new ItemBuilder(Material.BOOK_AND_QUILL).setName("§6Classement : §e#" + FactionRanking.getInstance().getRankByFaction().get(faction.getTag()));
		inv.setItem(30, rank.toItemStack());

		ItemBuilder rankName = new ItemBuilder(Material.BOOK).setName("§6Rank §e: §r" +  PointsUtils.getFactionRankName(faction));
		inv.setItem(32, rankName.toItemStack());

		for (int i = 0; i < 45; ++i) {
			if (glassInGui().contains(i))
				inv.setItem(i, yellow);
		}
		return inv;
	}
	
}

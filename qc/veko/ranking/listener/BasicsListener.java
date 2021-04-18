package qc.veko.ranking.listener;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.event.LandClaimEvent;

import qc.veko.ranking.FactionRanking;
import qc.veko.ranking.utils.PointsUtils;

public class BasicsListener implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
	    FPlayer fp = FPlayers.getInstance().getByPlayer(p);
	    if (!e.isCancelled())
	    	if (fp.hasFaction() && FactionRanking.getInstance().getRankByFaction().containsKey(fp.getFaction().getTag())) {
	    		int rank = FactionRanking.getInstance().getRankByFaction().get(fp.getFaction().getTag());
	    		String level = PointsUtils.getFactionRankName(fp.getFaction());
	    		e.setFormat("§8[§4#§a"+ rank + "§8] [" + level + "§8]" + e.getFormat());
	    	} else {
	    		e.setFormat("§8[§3§l?§8]" + e.getFormat());
	    	}  
		}
	
	@EventHandler
	public void onClaim(LandClaimEvent e) {
		PointsUtils.addFactionClaimsPoints(e.getFaction(), 10);
	}

	@EventHandler
	public void onFactionDisband(FactionDisbandEvent e) {
		PointsUtils.deleteFactionData(e.getFaction());
	}

	@EventHandler
	public void onFactionCreate(FactionCreateEvent e) {
		PointsUtils.createFactionData(e.getFaction());
	}
}

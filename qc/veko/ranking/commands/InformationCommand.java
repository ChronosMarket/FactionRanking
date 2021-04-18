package qc.veko.ranking.commands;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.CommandVisibility;
import com.massivecraft.factions.zcore.util.TL;

import org.bukkit.entity.Player;
import qc.veko.ranking.inventory.InformationInventory;

public class InformationCommand extends FCommand{
	
	public InformationCommand() {
		this.aliases.add("info");
		this.visibility = CommandVisibility.VISIBLE;
		this.optionalArgs.put("name", "name");
	}

	@Override
	public TL getUsageTranslation() {
		return null;
	}

	@Override
	public void perform(CommandContext arg0) {
		if (arg0.args.size() > 0) {
			Faction faction = Factions.getInstance().getByTag(arg0.argAsString(0));
			if (Factions.getInstance().getAllFactions().contains(faction)) {
				openInventory(faction, arg0.player);
			} else {
				arg0.sender.sendMessage("§6" + arg0.argAsString(0) + "§e n'est pas une faction valide");
			}
		} else {
			Faction faction = FPlayers.getInstance().getByPlayer(arg0.player.getPlayer()).getFaction();
			openInventory(faction, arg0.player);
		}
	}

	private void openInventory(Faction faction, Player player) {
		InformationInventory informationInventory = new InformationInventory();
		player.openInventory(informationInventory.getInventory(faction));
	}

}

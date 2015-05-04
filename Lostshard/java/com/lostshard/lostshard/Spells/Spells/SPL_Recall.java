package com.lostshard.lostshard.Spells.Spells;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.lostshard.lostshard.Objects.PseudoPlayer;
import com.lostshard.lostshard.Objects.Rune;
import com.lostshard.lostshard.Objects.Runebook;
import com.lostshard.lostshard.Objects.Plot.Plot;
import com.lostshard.lostshard.Spells.Scroll;
import com.lostshard.lostshard.Spells.Spell;
import com.lostshard.lostshard.Utils.Output;
import com.lostshard.lostshard.Utils.SpellUtils;

public class SPL_Recall extends Spell {

	public SPL_Recall(Scroll scroll) {
		super(scroll);
		this.setPrompt("What rune would you like to recall from?");
	}

	/*
	 * The meat of the spell code, this is what happens when the spell is
	 * actually activated and should be doing something.
	 */
	@Override
	public void doAction(Player player) {
		// System.out.println("RSPNS: "+_response);
		final PseudoPlayer pseudoPlayer = this.pm.getPlayer(player);

		final Runebook runebook = pseudoPlayer.getRunebook();
		final ArrayList<Rune> runes = runebook.getRunes();
		Rune runeFound = null;
		int count = 0;
		for (final Rune rune : runes) {
			if (!player.isOp() && !pseudoPlayer.wasSubscribed() && count >= 8)
				break;
			if (rune.getLabel().equalsIgnoreCase(this.getResponse())) {
				runeFound = rune;
				break;
			}
			count++;
		}

		final boolean usingSpawn = false;
		if (runeFound != null) {
			final Location runeLoc = runeFound.getLocation();
			if (!SpellUtils.isValidRuneLocation(player, runeLoc))
				return;

			final Plot plot = this.ptm.findPlotAt(runeLoc);
			if (plot == null || !plot.isPrivatePlot()
					|| plot.isFriendOrAbove(player)) {
				final Location destLoc = new Location(runeLoc.getWorld(),
						runeLoc.getBlockX() + .5, runeLoc.getBlockY(),
						runeLoc.getBlockZ() + .5);

				// check for lapis below your target location
				if (destLoc.getBlock().getRelative(0, -1, 0).getType()
						.equals(Material.LAPIS_BLOCK)
						|| destLoc.getBlock().getRelative(0, -2, 0).getType()
								.equals(Material.LAPIS_BLOCK)) {
					Output.simpleError(player,
							"can't recall to a Lapis Lazuli block.");
					return;
				}

				// Output.sendEffectTextNearbyExcludePlayer(player,
				// "You hear a loud crack and the fizzle of electricity.");
				player.getWorld().strikeLightningEffect(player.getLocation());

				player.teleport(destLoc);

				player.getWorld().strikeLightningEffect(player.getLocation());

				if (usingSpawn) {
					pseudoPlayer.setMana(0);
					pseudoPlayer.setStamina(0);
					player.sendMessage(ChatColor.GRAY
							+ "Teleporting without a rune has exausted you.");
				}
			} else
				Output.simpleError(player,
						"can't recall to there, the plot is private.");
		} else
			Output.simpleError(player,
					"You do not have a rune with that name, re-cast spell.");
	}

	/*
	 * Used for anything that must be handled as soon as the spell is cast, for
	 * example targeting a location for a delayed spell.
	 */
	@Override
	public void preAction(Player player) {
		Output.positiveMessage(player, "You begin casting Recall...");
	}

	@Override
	public boolean verifyCastable(Player player) {
		return true;
	}
}

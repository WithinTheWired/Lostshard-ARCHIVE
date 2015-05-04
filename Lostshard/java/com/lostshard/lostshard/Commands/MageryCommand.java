package com.lostshard.lostshard.Commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lostshard.lostshard.Database.Database;
import com.lostshard.lostshard.Main.Lostshard;
import com.lostshard.lostshard.Manager.PlayerManager;
import com.lostshard.lostshard.Manager.SpellManager;
import com.lostshard.lostshard.Objects.PseudoPlayer;
import com.lostshard.lostshard.Objects.Rune;
import com.lostshard.lostshard.Objects.Runebook;
import com.lostshard.lostshard.Objects.SpellBook;
import com.lostshard.lostshard.Spells.Scroll;
import com.lostshard.lostshard.Spells.Spell;
import com.lostshard.lostshard.Utils.Output;
import com.lostshard.lostshard.Utils.Utils;

public class MageryCommand implements CommandExecutor, TabCompleter {

	PlayerManager pm = PlayerManager.getManager();
	SpellManager sm = SpellManager.getManager();

	/**
	 * @param Lostshard
	 *            as plugin
	 */
	public MageryCommand(Lostshard plugin) {
		plugin.getCommand("cast").setExecutor(this);
		plugin.getCommand("scrolls").setExecutor(this);
		plugin.getCommand("runebook").setExecutor(this);
		plugin.getCommand("spellbook").setExecutor(this);
		plugin.getCommand("bind").setExecutor(this);
		plugin.getCommand("unbind").setExecutor(this);
	}

	private void bind(Player player, String[] args) {
		if (!player.getItemInHand().getType().equals(Material.STICK)) {
			Output.simpleError(player, "You can only bind spells to sticks.");
			return;
		}
		if (args.length < 1) {
			Output.simpleError(player, "/bind (spell)");
			return;
		}
		final PseudoPlayer pPlayer = this.pm.getPlayer(player);
		final String scrollName = StringUtils.join(args, "", 0, args.length);
		final Scroll scroll = Scroll.getByString(scrollName);
		if (scroll == null || !pPlayer.getSpellbook().containSpell(scroll)) {
			Output.simpleError(player, "Your spellbook does not contain "
					+ scrollName + ".");
			return;
		}
		final String spellName = scroll.getName();
		final ItemStack wand = player.getItemInHand();
		final ItemMeta wandMeta = wand.getItemMeta();
		wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		wandMeta.addEnchant(Enchantment.DURABILITY, 0, false);
		wandMeta.setDisplayName(ChatColor.GOLD + spellName);
		final List<String> wandLore = new ArrayList<String>();
		wandLore.add("This magical wand can be used to cast a spell with only a touch of a button.");
		wandMeta.setLore(wandLore);
		wand.setItemMeta(wandMeta);
		player.setItemInHand(wand);
		Output.positiveMessage(player, "You have bound " + spellName
				+ " to the wand.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("cast")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			if (args.length < 1) {
				Output.simpleError(sender, "/cast (spell)");
				return true;
			}
			final String spellName = StringUtils.join(args, " ");
			final Player player = (Player) sender;
			final Spell spell = this.sm.getSpellByName(spellName);
			if (spell == null) {
				Output.simpleError(sender, "No spell with the name \""
						+ spellName + "\".");
				return true;
			}
			this.sm.castSpell(player, spell);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("scrolls")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			final Player player = (Player) sender;
			this.scrolls(player, args);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("runebook")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			final Player player = (Player) sender;
			this.runebook(player, args);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("spellbook")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			final Player player = (Player) sender;
			this.spellbook(player, args);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("bind")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			final Player player = (Player) sender;
			this.bind(player, args);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("unbind")) {
			if (!(sender instanceof Player)) {
				Output.mustBePlayer(sender);
				return true;
			}
			final Player player = (Player) sender;
			this.unbind(player);
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String string, String[] args) {
		return null;
	}

	private void runebook(Player player, String[] args) {
		if (args.length == 0 || args.length >= 11
				&& args[1].equalsIgnoreCase("page"))
			Output.outputRunebook(player, args);
		else if (args.length > 0) {
			final String secondaryCommand = args[0];
			if (secondaryCommand.equalsIgnoreCase("give")) {
				if (args.length >= 3) {
					final String targetName = args[1];
					final Player targetPlayer = Bukkit.getPlayer(targetName);
					if (targetPlayer != null) {
						final PseudoPlayer targetPseudoPlayer = this.pm
								.getPlayer(targetPlayer);

						final PseudoPlayer pseudoPlayer = this.pm
								.getPlayer(player);
						final Runebook runebook = pseudoPlayer.getRunebook();
						final Runebook targetRunebook = targetPseudoPlayer
								.getRunebook();
						if (targetPlayer.isOp()
								|| targetPseudoPlayer.wasSubscribed()
								&& targetRunebook.getNumRunes() < 16
								|| targetRunebook.getNumRunes() < 8) {

							String runeLabel = "";
							final int splitLength = args.length;
							for (int i = 2; i < splitLength; i++) {
								runeLabel += args[i];
								if (i < splitLength - 1)
									runeLabel += " ";
							}
							runeLabel = runeLabel.trim();

							final ArrayList<Rune> runes = runebook.getRunes();
							Rune foundRune = null;
							for (final Rune rune : runes)
								if (rune.getLabel().equalsIgnoreCase(runeLabel)) {
									foundRune = rune;
									break;
								}
							if (foundRune != null) {
								final ArrayList<Rune> targetRunes = targetRunebook
										.getRunes();
								boolean foundMatching = false;
								for (final Rune rune : targetRunes)
									if (rune.getLabel().equalsIgnoreCase(
											foundRune.getLabel())) {
										foundMatching = true;
										break;
									}
								if (!foundMatching) {
									runebook.removeRune(foundRune);
									targetRunebook.addRune(foundRune);
									Database.updateRune(targetPseudoPlayer,
											foundRune);
									Output.positiveMessage(
											player,
											"You have given the rune "
													+ foundRune.getLabel()
													+ " to "
													+ targetPlayer.getName());
									Output.positiveMessage(
											targetPlayer,
											player.getName()
													+ " has given you the rune "
													+ foundRune.getLabel()
													+ ".");
								} else
									Output.simpleError(
											player,
											targetPlayer.getName()
													+ " already has a rune with that label.");
							} else
								Output.simpleError(player,
										"Could not find a rune with that label.");
						} else
							Output.simpleError(player, targetPlayer.getName()
									+ " has too many runes.");
					} else
						Output.simpleError(player, "That player is not online.");
				} else
					Output.simpleError(player,
							"Use /runebook give (player name) (rune name)");
			} else if (secondaryCommand.equalsIgnoreCase("remove"))
				if (args.length >= 2) {

					String runeLabel = "";
					final int splitLength = args.length;
					for (int i = 1; i < splitLength; i++) {
						runeLabel += args[i];
						if (i < splitLength - 1)
							runeLabel += " ";
					}
					runeLabel = runeLabel.trim();

					final PseudoPlayer pseudoPlayer = this.pm.getPlayer(player);
					final Runebook runebook = pseudoPlayer.getRunebook();
					final Rune foundRune = runebook.getRune(runeLabel);
					if (foundRune != null) {
						runebook.removeRune(foundRune);
						Output.positiveMessage(
								player,
								"You have removed the rune "
										+ foundRune.getLabel());
						Database.deleteRune(foundRune);
					} else
						Output.simpleError(player,
								"Could not find a rune with that label.");
				} else
					Output.simpleError(player,
							"Use /runebook remove (rune label)");
		}

	}

	private void scrolls(Player player, String[] args) {
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("use")
					|| args[0].equalsIgnoreCase("cast")) {
				final PseudoPlayer pPlayer = this.pm.getPlayer(player);
				final String scrollName = StringUtils.join(args, "", 1,
						args.length);
				final Scroll scroll = Scroll.getByString(scrollName);
				if (scroll == null || !pPlayer.getScrolls().contains(scroll)) {
					Output.simpleError(player, "You do not have a scroll of "
							+ scrollName + ".");
					return;
				}
				if (this.sm.useScroll(player, scroll)) {
					pPlayer.getScrolls().remove(scroll);
					Database.deleteScroll(scroll, pPlayer.getId());
				}
			} else if (args[0].equalsIgnoreCase("give")) {
				if (args.length < 3) {
					Output.simpleError(player,
							"Use \"/scrolls give (player name) (spell name)\"");
					return;
				}

				final PseudoPlayer pPlayer = this.pm.getPlayer(player);
				final Player targetPlayer = player.getServer().getPlayer(
						args[1]);
				if (targetPlayer == null) {
					Output.simpleError(player, args[1] + " is not online.");
					return;
				}

				final PseudoPlayer tpPlayer = this.pm.getPlayer(targetPlayer);

				if (!player.isOp())
					if (!Utils.isWithin(player.getLocation(),
							targetPlayer.getLocation(), 10)) {
						Output.simpleError(player,
								"You are not close enough to give "
										+ targetPlayer.getName() + " a scroll.");
						return;
					}

				final String scrollName = StringUtils.join(args, "", 2,
						args.length);
				final Scroll scroll = Scroll.getByString(scrollName);
				if (scroll == null || !pPlayer.getScrolls().contains(scroll)) {
					Output.simpleError(player, "You do not have a scroll of "
							+ scrollName + ".");
					return;
				}

				pPlayer.getScrolls().remove(scroll);
				tpPlayer.getScrolls().add(scroll);
				Database.updateScrollOwner(scroll, tpPlayer.getId(),
						pPlayer.getId());
				Output.positiveMessage(player,
						"You have given " + targetPlayer.getName()
								+ " a scroll of " + scroll.getName() + ".");
				Output.positiveMessage(targetPlayer, player.getName()
						+ " has given you a scroll of " + scroll.getName()
						+ ".");
			} else if (args[0].equalsIgnoreCase("spellbook")) {
				final PseudoPlayer pPlayer = this.pm.getPlayer(player);
				final String scrollName = StringUtils.join(args, "", 1,
						args.length);
				final Scroll scroll = Scroll.getByString(scrollName);
				if (scroll == null || !pPlayer.getScrolls().contains(scroll)) {
					Output.simpleError(player, "You do not have a scroll of "
							+ scrollName + ".");
					return;
				}
				final SpellBook spellbook = pPlayer.getSpellbook();
				if (!spellbook.containSpell(scroll)) {
					pPlayer.addSpell(scroll);
					Database.deleteScroll(scroll, pPlayer.getId());
					pPlayer.update();
					Output.positiveMessage(player, "You have transferred "
							+ scroll.getName() + " to your spellbook.");
				} else
					Output.simpleError(
							player,
							"Your spellbook already contains the "
									+ scroll.getName() + " spell.");
			}
			return;
		} else {
			Output.outputScrolls(player, args);
			return;
		}
	}

	private void spellbook(Player player, String[] args) {
		Output.outputSpellbook(player, args);
	}

	private void unbind(Player player) {
		if (!player.getItemInHand().getType().equals(Material.STICK)) {
			Output.simpleError(player, "You can only bind spells to sticks.");
			return;
		}
		final ItemStack wand = player.getItemInHand();
		final ItemMeta wandMeta = wand.getItemMeta();
		if (wandMeta.hasLore()
				&& wandMeta.getLore().size() > 0
				&& wandMeta
						.getLore()
						.get(0)
						.equalsIgnoreCase(
								"This magical wand can be used to cast a spell with only a touch of a button.")) {
			if (wandMeta.hasDisplayName()) {
				wand.setItemMeta(null);
				final String spellName = ChatColor.stripColor(wandMeta
						.getLore().get(0));
				player.setItemInHand(wand);
				Output.positiveMessage(player,
						"You have unbound " + spellName.toLowerCase()
								+ " from the wand.");
			} else
				Output.simpleError(player,
						"Thats just a simple stick you fool.");
		} else
			Output.simpleError(player, "Thats just a simple stick you fool.");
	}
}

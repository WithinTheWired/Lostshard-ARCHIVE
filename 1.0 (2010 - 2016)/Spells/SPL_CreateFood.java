package com.lostshard.RPG.Spells;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lostshard.RPG.PseudoPlayer;
import com.lostshard.RPG.PseudoPlayerHandler;
import com.lostshard.RPG.Utils.Output;

public class SPL_CreateFood extends Spell {
	private static final String 	_name = "Create Food";
	private static final String 	_spellWords = "Magickus Delicious";
	private static final int 		_castingDelay = 0;
	private static final int 		_cooldownTicks = 20;
	private static final int		_manaCost = 10;
	private static final int[]		_reagentCost = {295};
	private static final int 		_minMagery = 120;
	private static final int 		_range = 10;
	
	public String getName() 		{ return _name; }
	public String getSpellWords() 	{ return _spellWords; }
	public int getCastingDelay() 	{ return _castingDelay; }
	public int getCooldownTicks()	{ return _cooldownTicks; }
	public int getManaCost() 		{ return _manaCost; }
	public int[] getReagentCost() 	{ return _reagentCost; }
	public int getMinMagery() 		{ return _minMagery; }
	
	public int getPageNumber()		{ return 2; }
	/* Used to confirm that the spell can be cast, so, for example, if you were
	 * attempting to teleport to some location that was blocked, we would figure
	 * that out here and cancel the spell.
	 */
	
	private Block _blockFound;
	
	public boolean verifyCastable(Player player, PseudoPlayer pseudoPlayer) {
		_blockFound = faceBlockInLOS(player, _range);
		if(_blockFound == null) {
			Output.simpleError(player, "Invalid target.");
			return false;
		}
		/*if(_blockFound.getType().equals(Material.AIR)) {
			Output.simpleError(player, "Invalid target.");
			return false;
		}*/
		return true;
	}
	
	/* Used for anything that must be handled as soon as the spell is cast,
	 * for example targeting a location for a delayed spell.
	 */
	public void preAction(Player player) {
		
	}
	
	/* The meat of the spell code, this is what happens when the spell is
	 * actually activated and should be doing something.
	 */
	public void doAction(Player player) {
		PseudoPlayer pseudoPlayer = PseudoPlayerHandler.getPseudoPlayer(player.getName());
		pseudoPlayer.setCantCastTicks(_cooldownTicks);
		
		int itemToGive;
		int randInt = (int)Math.floor(Math.random()*6);
		
		if(randInt == 0)
			itemToGive = 260;
		else if(randInt == 1)
			itemToGive = 319;
		else if(randInt == 2)
			itemToGive = 296;
		else if(randInt == 3)
			itemToGive = 349;
		else if(randInt == 4)
			itemToGive = 39;
		else
			itemToGive = 40;
		
		player.getWorld().dropItem(_blockFound.getLocation(), new ItemStack(itemToGive, 1));
	}
}

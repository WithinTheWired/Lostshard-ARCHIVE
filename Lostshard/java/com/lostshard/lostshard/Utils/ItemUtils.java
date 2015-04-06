package com.lostshard.lostshard.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;

public class ItemUtils {

	public static void removeItem(PlayerInventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
    }
	
	public static boolean isSword(Material item) {
		return item.equals(Material.DIAMOND_SWORD)
				|| item.equals(Material.IRON_SWORD)
				|| item.equals(Material.GOLD_SWORD)
				|| item.equals(Material.WOOD_SWORD)
				|| item.equals(Material.STONE_SWORD);
	}

	public static boolean isSword(ItemStack item) {
		return isSword(item.getType());
	}

	public static boolean isAxe(Material item) {
		return item.equals(Material.DIAMOND_AXE) 
				|| item.equals(Material.IRON_AXE)
				|| item.equals(Material.GOLD_AXE)
				|| item.equals(Material.WOOD_AXE)
				|| item.equals(Material.STONE_AXE);
	}

	public static boolean isAxe(ItemStack item) {
		return isAxe(item.getType());
	}

	public static boolean isPickAxe(Material item) {
		return item.equals(Material.DIAMOND_PICKAXE)
				|| item.equals(Material.GOLD_PICKAXE)
				|| item.equals(Material.IRON_PICKAXE)
				|| item.equals(Material.STONE_PICKAXE)
				|| item.equals(Material.WOOD_PICKAXE);
	}

	public static boolean isPickAxe(ItemStack item) {
		return isPickAxe(item.getType());
	}
	
	public static boolean isDiamondArmor(Material item) {
		return item.equals(Material.DIAMOND_HELMET)
				|| item.equals(Material.DIAMOND_CHESTPLATE)
				|| item.equals(Material.DIAMOND_LEGGINGS)
				|| item.equals(Material.DIAMOND_BOOTS);
	}
	
	public static boolean isDiamondArmor(ItemStack	item) {
		return isDiamondArmor(item.getType());
	}
	
	public static boolean isIronArmor(Material item) {
		return item.equals(Material.IRON_HELMET)
				|| item.equals(Material.IRON_CHESTPLATE)
				|| item.equals(Material.IRON_LEGGINGS)
				|| item.equals(Material.IRON_BOOTS);
	}
	
	public static boolean isIronArmor(ItemStack	item) {
		return isIronArmor(item.getType());
	}
	
	public static boolean isGoldArmor(Material item) {
		return item.equals(Material.GOLD_HELMET)
				|| item.equals(Material.GOLD_CHESTPLATE)
				|| item.equals(Material.GOLD_LEGGINGS)
				|| item.equals(Material.DIAMOND_BOOTS);
	}
	
	public static boolean isGoldArmor(ItemStack	item) {
		return isGoldArmor(item.getType());
	}
	
	public static boolean isLeatherArmor(Material item) {
		return item.equals(Material.LEATHER_HELMET)
				|| item.equals(Material.LEATHER_CHESTPLATE)
				|| item.equals(Material.LEATHER_LEGGINGS)
				|| item.equals(Material.LEATHER_BOOTS);
	}
	
	public static boolean isLeatherArmor(ItemStack	item) {
		return isLeatherArmor(item.getType());
	}
	
	public static boolean isDiamond(Material item) {
		return item.equals(Material.DIAMOND_AXE)
				|| item.equals(Material.DIAMOND_BOOTS)
				|| item.equals(Material.DIAMOND_CHESTPLATE)
				|| item.equals(Material.DIAMOND_HELMET)
				|| item.equals(Material.DIAMOND_HOE)
				|| item.equals(Material.DIAMOND_LEGGINGS)
				|| item.equals(Material.DIAMOND_SPADE)
				|| item.equals(Material.DIAMOND_SWORD);
	}
	
	public static boolean isDiamond(ItemStack item) {
		return isDiamond(item.getType());
	}
	
	public static boolean isIron(Material item) {
		return item.equals(Material.IRON_AXE)
				|| item.equals(Material.IRON_BOOTS)
				|| item.equals(Material.IRON_CHESTPLATE)
				|| item.equals(Material.IRON_HELMET)
				|| item.equals(Material.IRON_HOE)
				|| item.equals(Material.IRON_LEGGINGS)
				|| item.equals(Material.IRON_PICKAXE)
				|| item.equals(Material.IRON_SPADE)
				|| item.equals(Material.IRON_SWORD);
	}
	
	public static boolean isIron(ItemStack item) {
		return isIron(item.getType());
	}
	
	public static boolean isGold(Material item) {
		return item.equals(Material.GOLD_AXE)
				|| item.equals(Material.GOLD_BOOTS)
				|| item.equals(Material.GOLD_CHESTPLATE)
				|| item.equals(Material.GOLD_HELMET)
				|| item.equals(Material.GOLD_HOE)
				|| item.equals(Material.GOLD_LEGGINGS)
				|| item.equals(Material.GOLD_PICKAXE)
				|| item.equals(Material.GOLD_SPADE)
				|| item.equals(Material.GOLD_SWORD);
	}
	
	public static boolean isGold(ItemStack item) {
		return isGold(item.getType());
	}
	
	public static boolean isStone(Material item) {
		return item.equals(Material.STONE_AXE)
				|| item.equals(Material.STONE_HOE)
				|| item.equals(Material.STONE_PICKAXE)
				|| item.equals(Material.STONE_SPADE)
				|| item.equals(Material.STONE_SWORD)
				|| item.equals(Material.CHAINMAIL_BOOTS)
				|| item.equals(Material.CHAINMAIL_CHESTPLATE)
				|| item.equals(Material.CHAINMAIL_HELMET)
				|| item.equals(Material.CHAINMAIL_LEGGINGS);
	}
	
	public static boolean isStone(ItemStack item) {
		return isStone(item.getType());
	}
	
	public static boolean isWood(Material item) {
		return item.equals(Material.WOOD_AXE)
				|| item.equals(Material.WOOD_HOE)
				|| item.equals(Material.WOOD_PICKAXE)
				|| item.equals(Material.WOOD_SPADE)
				|| item.equals(Material.WOOD_SWORD);
	}
	
	public static boolean isWood(ItemStack item) {
		return isWood(item.getType());
	}

	public static boolean isArmor(Material cost) {
		return isDiamond(cost) || isGoldArmor(cost) || isIron(cost);
	}

	public static boolean isArmor(ItemStack item) {
		return isArmor(item.getType());
	}
	
	public static boolean isWoodTool(Material item) {
		return item.equals(Material.WOOD_HOE)
				|| item.equals(Material.WOOD_PICKAXE)
				|| item.equals(Material.WOOD_SPADE);
	}
	
	public static boolean isWoodTool(ItemStack item) {
		return isWoodTool(item.getType());
	}
	
	public static boolean isStoneTool(Material item) {
		return item.equals(Material.STONE_HOE)
				|| item.equals(Material.STONE_PICKAXE)
				|| item.equals(Material.STONE_SPADE);
	}
	
	public static boolean isStoneTool(ItemStack item) {
		return isStoneTool(item.getType());
	}
	
	public static boolean isIronTool(Material item) {
		return item.equals(Material.IRON_PICKAXE)
				|| item.equals(Material.IRON_SPADE)
				|| item.equals(Material.IRON_HOE);
	}
	
	public static boolean isIronTool(ItemStack item) {
		return isIronTool(item.getType());
	}
	
	public static boolean isGoldTool(Material item) {
		return item.equals(Material.GOLD_PICKAXE)
				|| item.equals(Material.GOLD_SPADE)
				|| item.equals(Material.GOLD_HOE);
	}
	
	public static boolean isGoldTool(ItemStack item) {
		return isGoldTool(item.getType());
	}
	
	public static boolean isDiamondTool(Material item) {
		return item.equals(Material.DIAMOND_PICKAXE)
				|| item.equals(Material.DIAMOND_SPADE)
				|| item.equals(Material.DIAMOND_HOE);
	}
	
	public static boolean isDiamondTool(ItemStack item) {
		return isDiamondTool(item.getType());
	}
	
	public static boolean isTool(Material item) {
		return isWoodTool(item) || isStoneTool(item) || isIronTool(item) || isGoldTool(item) || isDiamondTool(item);
	}

	public static boolean isTool(ItemStack item) {
		return isTool(item.getType());
	}
	
	public static void addChainMail() {
		ShapedRecipe helmet1 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET));
		helmet1.shape("SSS","SAS","AAA");
		helmet1.setIngredient('S', Material.COBBLESTONE);
		helmet1.setIngredient('A', Material.COBBLESTONE);
		ShapedRecipe helmet2 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET));
		helmet2.shape("AAA","SSS","SAS");
		helmet2.setIngredient('S', Material.COBBLESTONE);
		helmet2.setIngredient('A', Material.COBBLESTONE);
		ShapedRecipe chest = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		chest.shape("SAS","SSS","SSS");
		chest.setIngredient('S', Material.COBBLESTONE);
		chest.setIngredient('A', Material.COBBLESTONE);
		ShapedRecipe leggings = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		leggings.shape("SSS","SAS","SAS");
		leggings.setIngredient('S', Material.COBBLESTONE);
		leggings.setIngredient('A', Material.COBBLESTONE);
		ShapedRecipe boots1 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS));
		boots1.shape("AAA","SAS","SAS");
		boots1.setIngredient('S', Material.COBBLESTONE);
		boots1.setIngredient('A', Material.COBBLESTONE);
		ShapedRecipe boots2 = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS));
		boots2.shape("SAS","SAS","AAA");
		boots2.setIngredient('S', Material.COBBLESTONE);
		boots2.setIngredient('A', Material.COBBLESTONE);
		Bukkit.addRecipe(helmet1);
		Bukkit.addRecipe(helmet2);
		Bukkit.addRecipe(chest);
		Bukkit.addRecipe(leggings);
		Bukkit.addRecipe(boots1);
		Bukkit.addRecipe(boots2);
	}
}

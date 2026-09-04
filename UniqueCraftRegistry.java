package com.ultimateweapons.craft;

import com.ultimateweapons.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.Set;

/**
 * Liste aller Items, die nur EIN einziges Mal pro Welt craftbar sein sollen.
 */
public class UniqueCraftRegistry {

	private static final Set<Item> UNIQUE_ITEMS = Set.of(
			ModItems.ULTIMATE_SWORD,
			ModItems.ULTIMATE_AXE,
			ModItems.ULTIMATE_MACE,
			ModItems.ULTIMATE_SPEAR,
			ModItems.ULTIMATE_HELMET,
			ModItems.ULTIMATE_CHESTPLATE,
			ModItems.ULTIMATE_LEGGINGS,
			ModItems.ULTIMATE_BOOTS
	);

	public static boolean isUnique(Item item) {
		return UNIQUE_ITEMS.contains(item);
	}

	public static Identifier idOf(Item item) {
		return BuiltInRegistries.ITEM.getKey(item);
	}
}

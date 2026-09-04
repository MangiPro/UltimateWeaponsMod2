package com.ultimateweapons.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;

/**
 * Ultimate Spear: verhaelt sich im Kampf wie ein Schwert, bekommt aber ueber
 * {@link com.ultimateweapons.armor.ModArmorEffects} +2 Reichweite (7 statt 5
 * Bloecke), solange es in der Hand gehalten wird. Eigene Klasse, damit das
 * Item bei Bedarf spaeter leicht eigenes Verhalten (z.B. Stich-Animation)
 * bekommen kann.
 */
public class UltimateSpearItem extends SwordItem {
	public UltimateSpearItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}

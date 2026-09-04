package com.ultimateweapons.armor;

import com.ultimateweapons.UltimateWeaponsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

/**
 * Ruestungsmaterial "Ultimate": identische Werte wie Netherite-Ruestung
 * (Verteidigung, Zaehigkeit ("toughness"), Rueckstossresistenz, Verzauberbarkeit,
 * Anlege-Sound). Reparatur erfolgt ueber den {@link #REPAIRS_ULTIMATE_ARMOR}-Tag
 * (siehe data/ultimateweapons/tags/item/repairs_ultimate_armor.json).
 */
public class ModArmorMaterials {

	// Netherite-Basis-Haltbarkeit (wird als Basis fuer maxDamage je Ruestungsteil verwendet).
	public static final int BASE_DURABILITY = 37;

	public static final ResourceKey<EquipmentAsset> ULTIMATE_ARMOR_MATERIAL_KEY =
			ResourceKey.create(EquipmentAssets.ROOT_ID, UltimateWeaponsMod.id("ultimate"));

	public static final TagKey<Item> REPAIRS_ULTIMATE_ARMOR =
			TagKey.create(BuiltInRegistries.ITEM.key(), UltimateWeaponsMod.id("repairs_ultimate_armor"));

	public static final ArmorMaterial ULTIMATE = new ArmorMaterial(
			BASE_DURABILITY,
			Map.of(
					ArmorType.HELMET, 3,
					ArmorType.CHESTPLATE, 8,
					ArmorType.LEGGINGS, 6,
					ArmorType.BOOTS, 3
			),
			15, // enchantmentValue (wie Netherite)
			SoundEvents.ARMOR_EQUIP_NETHERITE,
			3.0F, // toughness
			0.1F, // knockbackResistance
			REPAIRS_ULTIMATE_ARMOR,
			ULTIMATE_ARMOR_MATERIAL_KEY
	);

	// Original-Netherite-Haltbarkeitswerte (Basis * Typ-Multiplikator), 1:1 uebernommen.
	public static final Map<ArmorType, Integer> NETHERITE_DURABILITY = Map.of(
			ArmorType.HELMET, 407,
			ArmorType.CHESTPLATE, 592,
			ArmorType.LEGGINGS, 555,
			ArmorType.BOOTS, 481
	);
}

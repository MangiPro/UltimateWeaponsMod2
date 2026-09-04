package com.ultimateweapons.item;

import com.ultimateweapons.UltimateWeaponsMod;
import com.ultimateweapons.armor.ModArmorMaterials;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

/**
 * Registrierung aller "Ultimate"-Items.
 *
 * WICHTIG zu den Verzauberungen:
 * Alle Verzauberungen der Ultimate-Items werden NICHT hier im Java-Code fest
 * vergeben (die Verzauberungs-Registry ist zum Zeitpunkt der Item-Registrierung
 * noch nicht sicher verfuegbar, da Verzauberungen datengetrieben sind), sondern
 * ueber die Crafting-Rezepte in src/main/resources/data/ultimateweapons/recipe/*.json
 * per "result.components.minecraft:enchantments" gesetzt. Dadurch bekommt das
 * Item automatisch beim Craften alle geforderten Verzauberungen inkl. Glanz.
 *
 * Unzerstoerbarkeit (Unbreakable) wird dagegen HIER als Default-Component
 * gesetzt, damit sie unabhaengig vom Erwerbsweg (Crafting, /give, etc.) IMMER gilt.
 */
public class ModItems {

	// ---------- Waffen ----------

	public static final Item ULTIMATE_SWORD = register("ultimate_sword",
			settings -> new SwordItem(ToolMaterial.NETHERITE, 7.0F, -2.4F, settings),
			// Basis 1 (Faust) + 4 (Netherite-Tier-Bonus) + 7 (Parameter) = 12 Attack Damage
			new Item.Properties().rarity(Rarity.EPIC).fireResistant());

	public static final Item ULTIMATE_AXE = register("ultimate_axe",
			settings -> new AxeItem(ToolMaterial.NETHERITE, 5.0F, -2.4F, settings),
			new Item.Properties().rarity(Rarity.EPIC).fireResistant());

	public static final Item ULTIMATE_MACE = register("ultimate_mace",
			UltimateMaceItem::new,
			new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1));

	public static final Item ULTIMATE_SPEAR = register("ultimate_spear",
			settings -> new UltimateSpearItem(ToolMaterial.NETHERITE, 3.0F, -2.4F, settings),
			new Item.Properties().rarity(Rarity.EPIC).fireResistant());

	// Basis-Item, das es in Vanilla nicht gibt, aber als Grundlage fuer den
	// Ultimate Spear benoetigt wird ("normaler Netherite Spear" laut Vorgabe).
	// Verhaelt sich exakt wie ein Netherite-Schwert, nur mit eigenem Namen/Icon.
	public static final Item NETHERITE_SPEAR = register("netherite_spear",
			settings -> new SwordItem(ToolMaterial.NETHERITE, 3.0F, -2.4F, settings),
			new Item.Properties());

	// ---------- Ruestung ----------

	public static final Item ULTIMATE_HELMET = register("ultimate_helmet",
			Item::new,
			new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.humanoidArmor(ModArmorMaterials.ULTIMATE, ArmorType.HELMET)
					.durability(ModArmorMaterials.NETHERITE_DURABILITY.get(ArmorType.HELMET)));

	public static final Item ULTIMATE_CHESTPLATE = register("ultimate_chestplate",
			Item::new,
			new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.humanoidArmor(ModArmorMaterials.ULTIMATE, ArmorType.CHESTPLATE)
					.durability(ModArmorMaterials.NETHERITE_DURABILITY.get(ArmorType.CHESTPLATE)));

	public static final Item ULTIMATE_LEGGINGS = register("ultimate_leggings",
			Item::new,
			new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.humanoidArmor(ModArmorMaterials.ULTIMATE, ArmorType.LEGGINGS)
					.durability(ModArmorMaterials.NETHERITE_DURABILITY.get(ArmorType.LEGGINGS)));

	public static final Item ULTIMATE_BOOTS = register("ultimate_boots",
			Item::new,
			new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.humanoidArmor(ModArmorMaterials.ULTIMATE, ArmorType.BOOTS)
					.durability(ModArmorMaterials.NETHERITE_DURABILITY.get(ArmorType.BOOTS)));

	private static <T extends Item> T register(String path, Function<Item.Properties, T> factory, Item.Properties settings) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(UltimateWeaponsMod.MOD_ID, path));
		settings = settings.setId(key)
				.component(DataComponents.UNBREAKABLE, new Unbreakable(true));
		T item = factory.apply(settings);
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void initialize() {
		UltimateWeaponsMod.LOGGER.info("Registriere Ultimate-Items.");
	}
}

package com.ultimateweapons.armor;

import com.ultimateweapons.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/**
 * Wendet die "solange getragen"-Effekte der Ultimate-Ruestung sowie den
 * Reichweiten-Bonus des Ultimate Spear jeden Server-Tick an.
 * (Analoges Prinzip wie z.B. Vanilla-Frosch-Licht / Turtle-Helm-Atmung:
 * Effekt wird jeden Tick kurz "aufgefrischt", solange die Bedingung gilt.)
 */
public class ModArmorEffects {

	private static final int REFRESH_DURATION = 25; // > 1 Tick Puffer, damit kein Flackern entsteht
	private static final Identifier CHESTPLATE_HEALTH_ID = Identifier.fromNamespaceAndPath("ultimateweapons", "ultimate_chestplate_health");
	private static final Identifier SPEAR_ENTITY_RANGE_ID = Identifier.fromNamespaceAndPath("ultimateweapons", "ultimate_spear_entity_range");
	private static final Identifier SPEAR_BLOCK_RANGE_ID = Identifier.fromNamespaceAndPath("ultimateweapons", "ultimate_spear_block_range");

	public static void tick(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			applyHelmet(player);
			applyChestplate(player);
			applyLeggings(player);
			applyBoots(player);
			applySpearReach(player);
		}
	}

	private static void applyHelmet(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ULTIMATE_HELMET)) {
			player.addEffect(new MobEffectInstance(MobEffects.HASTE, REFRESH_DURATION, 9, true, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, REFRESH_DURATION, 0, true, false, true));
		}
	}

	private static void applyChestplate(ServerPlayer player) {
		AttributeInstance healthAttr = player.getAttribute(Attributes.MAX_HEALTH);
		if (healthAttr == null) return;

		boolean wearing = player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ULTIMATE_CHESTPLATE);
		boolean hasModifier = healthAttr.getModifier(CHESTPLATE_HEALTH_ID) != null;

		if (wearing && !hasModifier) {
			healthAttr.addPermanentModifier(new AttributeModifier(
					CHESTPLATE_HEALTH_ID, 20.0D, AttributeModifier.Operation.ADD_VALUE)); // +10 Herzen
		} else if (!wearing && hasModifier) {
			healthAttr.removeModifier(CHESTPLATE_HEALTH_ID);
		}
	}

	private static void applyLeggings(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ULTIMATE_LEGGINGS)) {
			player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, REFRESH_DURATION, 1, true, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.SATURATION, REFRESH_DURATION, 0, true, false, true));
		}
	}

	private static void applyBoots(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ULTIMATE_BOOTS)) {
			player.addEffect(new MobEffectInstance(MobEffects.SPEED, REFRESH_DURATION, 2, true, false, true));
		}
	}

	private static void applySpearReach(ServerPlayer player) {
		AttributeInstance entityRange = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		AttributeInstance blockRange = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
		if (entityRange == null || blockRange == null) return;

		ItemStack mainHand = player.getMainHandItem();
		boolean holdingSpear = mainHand.is(ModItems.ULTIMATE_SPEAR);

		boolean hasEntityMod = entityRange.getModifier(SPEAR_ENTITY_RANGE_ID) != null;
		boolean hasBlockMod = blockRange.getModifier(SPEAR_BLOCK_RANGE_ID) != null;

		if (holdingSpear && !hasEntityMod) {
			entityRange.addTransientModifier(new AttributeModifier(
					SPEAR_ENTITY_RANGE_ID, 2.0D, AttributeModifier.Operation.ADD_VALUE));
		} else if (!holdingSpear && hasEntityMod) {
			entityRange.removeModifier(SPEAR_ENTITY_RANGE_ID);
		}

		if (holdingSpear && !hasBlockMod) {
			blockRange.addTransientModifier(new AttributeModifier(
					SPEAR_BLOCK_RANGE_ID, 2.0D, AttributeModifier.Operation.ADD_VALUE));
		} else if (!holdingSpear && hasBlockMod) {
			blockRange.removeModifier(SPEAR_BLOCK_RANGE_ID);
		}
	}
}

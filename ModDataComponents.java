package com.ultimateweapons.item;

import com.mojang.serialization.Codec;
import com.ultimateweapons.UltimateWeaponsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * Eigene Data-Component fuer die Ultimate Mace: zaehlt Treffer, um alle 10
 * Treffer den Betaeubungs-Effekt + Blitzeinschlag auszuloesen.
 */
public class ModDataComponents {

	public static final ResourceKey<DataComponentType<?>> MACE_HIT_COUNTER_KEY =
			ResourceKey.create(Registries.DATA_COMPONENT_TYPE, UltimateWeaponsMod.id("mace_hit_counter"));

	public static final DataComponentType<Integer> MACE_HIT_COUNTER =
			DataComponentType.<Integer>builder().persistent(Codec.INT).build();

	public static void initialize() {
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, MACE_HIT_COUNTER_KEY, MACE_HIT_COUNTER);
	}
}

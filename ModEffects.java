package com.ultimateweapons.effect;

import com.ultimateweapons.UltimateWeaponsMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;

/**
 * Registriert den benutzerdefinierten "Stun"-Effekt der Ultimate Mace.
 */
public class ModEffects {

	public static final ResourceKey<MobEffect> STUN_KEY =
			ResourceKey.create(Registries.MOB_EFFECT, UltimateWeaponsMod.id("stun"));

	public static final MobEffect STUN = new StunMobEffect();

	public static void initialize() {
		Registry.register(BuiltInRegistries.MOB_EFFECT, STUN_KEY, STUN);
		UltimateWeaponsMod.LOGGER.info("Ultimate-Weapons Status-Effekte registriert.");
	}

	public static Holder<MobEffect> stunHolder() {
		return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(STUN_KEY);
	}
}

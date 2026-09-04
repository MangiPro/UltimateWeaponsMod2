package com.ultimateweapons;

import com.ultimateweapons.ability.StunManager;
import com.ultimateweapons.armor.ModArmorEffects;
import com.ultimateweapons.craft.UniqueCraftState;
import com.ultimateweapons.effect.ModEffects;
import com.ultimateweapons.item.ModDataComponents;
import com.ultimateweapons.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltimateWeaponsMod implements ModInitializer {

	public static final String MOD_ID = "ultimateweapons";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Ultimate Weapons Mod wird initialisiert...");

		ModEffects.initialize();
		ModDataComponents.initialize();
		ModItems.initialize();
		UniqueCraftState.registerEvents();

		// Server-Tick: Ruestungseffekte anwenden + Betaeubungs-Logik (Bewegung/Blick einfrieren)
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			ModArmorEffects.tick(server);
			StunManager.tick(server);
		});

		LOGGER.info("Ultimate Weapons Mod erfolgreich geladen.");
	}
}

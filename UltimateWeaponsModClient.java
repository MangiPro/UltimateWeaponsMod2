package com.ultimateweapons.client;

import net.fabricmc.api.ClientModInitializer;

public class UltimateWeaponsModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Aktuell kein zusaetzlicher Client-Code noetig: Items/Ruestung werden
		// vollstaendig ueber die JSON-Assets (items/, models/, equipment/) dargestellt.
	}
}

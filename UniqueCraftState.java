package com.ultimateweapons.craft;

import com.mojang.serialization.Codec;
import com.ultimateweapons.UltimateWeaponsMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Speichert dauerhaft (ueberlebt Server-Neustarts), welche einmalig craftbaren
 * Ultimate-Items in dieser Welt bereits hergestellt wurden.
 */
public class UniqueCraftState extends SavedData {

	private final Set<Identifier> craftedItems;

	public UniqueCraftState() {
		this(new HashSet<>());
	}

	private UniqueCraftState(Set<Identifier> craftedItems) {
		this.craftedItems = craftedItems;
	}

	private static final Codec<UniqueCraftState> CODEC = Identifier.CODEC.listOf().xmap(
			list -> new UniqueCraftState(new HashSet<>(list)),
			state -> new ArrayList<>(state.craftedItems)
	);

	private static final SavedDataType<UniqueCraftState> TYPE = new SavedDataType<>(
			UltimateWeaponsMod.id("unique_crafted_items"),
			UniqueCraftState::new,
			CODEC,
			null
	);

	public boolean isCrafted(Identifier itemId) {
		return craftedItems.contains(itemId);
	}

	public void markCrafted(Identifier itemId) {
		craftedItems.add(itemId);
		setDirty();
	}

	public static UniqueCraftState get(MinecraftServer server) {
		ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
		if (overworld == null) {
			return new UniqueCraftState();
		}
		return overworld.getDataStorage().computeIfAbsent(TYPE);
	}

	/**
	 * Aktuell keine Events noetig (Zugriff erfolgt lazy ueber {@link #get}),
	 * Methode existiert fuer zukuenftige Erweiterungen / Klarheit im Hauptmod.
	 */
	public static void registerEvents() {
		ServerLifecycleEvents.SERVER_STARTED.register(server ->
				UltimateWeaponsMod.LOGGER.info("Unique-Craft-Status bereit."));
	}
}

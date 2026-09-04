package com.ultimateweapons.ability;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Verwaltet betaeubte ("gestunnte") Entities: friert Position und Blickrichtung
 * fuer die angegebene Ticksanzahl ein (zusaetzlich zum reinen "Speed = 0"-Effekt,
 * damit der Spieler sich wirklich weder bewegen noch umsehen kann).
 */
public class StunManager {

	private record FrozenState(UUID entityId, double x, double y, double z, float yaw, float pitch, int ticksRemaining) {
	}

	private static final Map<UUID, FrozenState> STUNNED = new HashMap<>();

	public static void stun(LivingEntity entity, int ticks) {
		STUNNED.put(entity.getUUID(), new FrozenState(
				entity.getUUID(), entity.getX(), entity.getY(), entity.getZ(),
				entity.getYRot(), entity.getXRot(), ticks));
	}

	public static boolean isStunned(LivingEntity entity) {
		FrozenState state = STUNNED.get(entity.getUUID());
		return state != null && state.ticksRemaining() > 0;
	}

	public static void tick(MinecraftServer server) {
		if (STUNNED.isEmpty()) {
			return;
		}

		Iterator<Map.Entry<UUID, FrozenState>> iterator = STUNNED.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<UUID, FrozenState> entry = iterator.next();
			FrozenState state = entry.getValue();

			LivingEntity entity = findEntity(server, entry.getKey());
			if (entity == null || !entity.isAlive() || state.ticksRemaining() <= 0) {
				iterator.remove();
				continue;
			}

			// Position + Blickrichtung erzwingen, Bewegungsimpuls loeschen.
			entity.teleportTo(state.x(), state.y(), state.z());
			entity.setYRot(state.yaw());
			entity.setXRot(state.pitch());
			entity.setYHeadRot(state.yaw());
			entity.setDeltaMovement(Vec3.ZERO);

			entry.setValue(new FrozenState(state.entityId(), state.x(), state.y(), state.z(),
					state.yaw(), state.pitch(), state.ticksRemaining() - 1));
		}
	}

	private static LivingEntity findEntity(MinecraftServer server, UUID uuid) {
		for (ServerLevel level : server.getAllLevels()) {
			if (level.getEntity(uuid) instanceof LivingEntity livingEntity) {
				return livingEntity;
			}
		}
		return null;
	}
}

package com.ultimateweapons.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Betaeubungs-Effekt der Ultimate Mace.
 * Setzt die Bewegungsgeschwindigkeit auf 0 (100% Slowness).
 * Das vollstaendige Einfrieren von Blickrichtung / Inventar-Sperre wird
 * zusaetzlich serverseitig durch den {@link com.ultimateweapons.ability.StunManager}
 * und den Slot-Mixin erzwungen, da dies ueber reine Attribute nicht moeglich ist.
 */
public class StunMobEffect extends MobEffect {

	// Feste Identifier fuer den Attribut-Modifier (0 Bewegungsgeschwindigkeit).
	public static final Identifier MOVEMENT_MODIFIER_ID = Identifier.fromNamespaceAndPath("ultimateweapons", "stun_no_movement");

	public StunMobEffect() {
		super(MobEffectCategory.HARMFUL, 0x3B0A45);
		this.addAttributeModifier(
				Attributes.MOVEMENT_SPEED,
				MOVEMENT_MODIFIER_ID,
				-1.0D, // -100% => effektiv 0 Bewegung
				AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
		);
	}
}

/*
 * Copyright� 2015 Yaniv Bokobza
 * Based on Andor's Trail open source game (GPLv2)
 *
 * This file is part of Andor's Trail - Rewarded.
 *
 * Andor's Trail - Rewarded is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Andor's Trail - Rewarded is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Andor's Trail - Rewarded.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wee.boo.AndorsTrail.Rewarded.model.actor;

import android.util.FloatMath;

import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorCondition;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemTraits_OnUse;
import com.wee.boo.AndorsTrail.Rewarded.util.Coord;
import com.wee.boo.AndorsTrail.Rewarded.util.CoordRect;
import com.wee.boo.AndorsTrail.Rewarded.util.Range;
import com.wee.boo.AndorsTrail.Rewarded.util.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Actor {
	public int iconID;
	public final Size tileSize;
	public final Coord position = new Coord();
	public final CoordRect rectPosition;
	public final boolean isPlayer;
	private final boolean isImmuneToCriticalHits;
	protected String name;

	// TODO: Should be privates
	public final Range ap = new Range();
	public final Range health = new Range();
	public final ArrayList<ActorCondition> conditions = new ArrayList<ActorCondition>();
	public int moveCost;
	public int attackCost;
	public int attackChance;
	public int criticalSkill;
	public float criticalMultiplier;
	public final Range damagePotential = new Range();
	public int blockChance;
	public int damageResistance;
	public ItemTraits_OnUse[] onHitEffects;

	public Actor(
			Size tileSize
			, boolean isPlayer
			, boolean isImmuneToCriticalHits
	) {
		this.tileSize = tileSize;
		this.rectPosition = new CoordRect(this.position, this.tileSize);
		this.isPlayer = isPlayer;
		this.isImmuneToCriticalHits = isImmuneToCriticalHits;
	}

	public boolean isImmuneToCriticalHits() { return isImmuneToCriticalHits; }
	public String getName() { return name; }
	public int getCurrentAP() { return ap.current; }
	public int getMaxAP() { return ap.max; }
	public int getCurrentHP() { return health.current; }
	public int getMaxHP() { return health.max; }
	public int getMoveCost() { return moveCost; }
	public int getAttackCost() { return attackCost; }
	public int getAttackChance() { return attackChance; }
	public int getCriticalSkill() { return criticalSkill; }
	public float getCriticalMultiplier() { return criticalMultiplier; }
	public Range getDamagePotential() { return damagePotential; }
	public int getBlockChance() { return blockChance; }
	public int getDamageResistance() { return damageResistance; }
	public ItemTraits_OnUse[] getOnHitEffects() { return onHitEffects; }
	public List<ItemTraits_OnUse> getOnHitEffectsAsList() { return onHitEffects == null ? null : Arrays.asList(onHitEffects); }

	public boolean hasCriticalSkillEffect() { return getCriticalSkill() != 0; }
	public boolean hasCriticalMultiplierEffect() { float m = getCriticalMultiplier(); return m != 0 && m != 1; }
	public boolean hasCriticalAttacks() { return hasCriticalSkillEffect() && hasCriticalMultiplierEffect(); }

	public int getAttacksPerTurn() { return (int) Math.floor(getMaxAP() / getAttackCost()); }
	public int getEffectiveCriticalChance() { return getEffectiveCriticalChance(getCriticalSkill()); }
	public static int getEffectiveCriticalChance(int criticalSkill) {
		if (criticalSkill <= 0) return 0;
		int v = (int) (-5 + 2 * FloatMath.sqrt(5*criticalSkill));
		if (v < 0) return 0;
		return v;
	}

	public boolean isDead() {
		return health.current <= 0;
	}

	public boolean hasAPs(int cost) {
		return ap.current >= cost;
	}

	public boolean hasCondition(final String conditionTypeID) {
		for (ActorCondition c : conditions) {
			if (c.conditionType.conditionTypeID.equals(conditionTypeID)) return true;
		}
		return false;
	}
}

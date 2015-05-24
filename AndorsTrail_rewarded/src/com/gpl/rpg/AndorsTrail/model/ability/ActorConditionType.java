/*
 * Copyright© 2015 Yaniv Bokobza
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
package com.gpl.rpg.AndorsTrail.model.ability;

import com.gpl.rpg.AndorsTrail.model.ability.traits.AbilityModifierTraits;
import com.gpl.rpg.AndorsTrail.model.ability.traits.StatsModifierTraits;

public final class ActorConditionType {
	public static enum ConditionCategory {
		spiritual, mental, physical, blood
	}

	public final String conditionTypeID;
	public final String name;
	public final int iconID;
	public final ConditionCategory conditionCategory;
	public final boolean isStacking;
	public final boolean isPositive;
	public final StatsModifierTraits statsEffect_everyRound;
	public final StatsModifierTraits statsEffect_everyFullRound;
	public final AbilityModifierTraits abilityEffect;

	public ActorConditionType(
			String conditionTypeID
			, String name
			, int iconID
			, ConditionCategory conditionCategory
			, boolean isStacking
			, boolean isPositive
			, StatsModifierTraits statsEffect_everyRound
			, StatsModifierTraits statsEffect_everyFullRound
			, AbilityModifierTraits abilityEffect
	) {
		this.conditionTypeID = conditionTypeID;
		this.name = name;
		this.iconID = iconID;
		this.conditionCategory = conditionCategory;
		this.isStacking = isStacking;
		this.isPositive = isPositive;
		this.statsEffect_everyRound = statsEffect_everyRound;
		this.statsEffect_everyFullRound = statsEffect_everyFullRound;
		this.abilityEffect = abilityEffect;
	}
}

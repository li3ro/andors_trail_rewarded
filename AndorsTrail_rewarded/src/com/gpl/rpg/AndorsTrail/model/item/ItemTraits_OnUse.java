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
package com.gpl.rpg.AndorsTrail.model.item;

import com.gpl.rpg.AndorsTrail.model.ability.ActorConditionEffect;
import com.gpl.rpg.AndorsTrail.model.ability.traits.StatsModifierTraits;

public final class ItemTraits_OnUse {
	public final StatsModifierTraits changedStats;
	public final ActorConditionEffect[] addedConditions_source;
	public final ActorConditionEffect[] addedConditions_target;

	public ItemTraits_OnUse(
			StatsModifierTraits changedStats
			, ActorConditionEffect[] addedConditions_source
			, ActorConditionEffect[] addedConditions_target
	) {
		this.changedStats = changedStats;
		this.addedConditions_source = addedConditions_source;
		this.addedConditions_target = addedConditions_target;
	}


	public int calculateUseCost() {
		final int costStats = changedStats == null ? 0 : changedStats.calculateUseCost();
		return costStats;
	}

	public int calculateHitCost() {
		final int costStats = changedStats == null ? 0 : changedStats.calculateHitCost();
		return costStats;
	}

	public int calculateKillCost() {
		final int costStats = changedStats == null ? 0 : changedStats.calculateKillCost();
		return costStats;
	}
}

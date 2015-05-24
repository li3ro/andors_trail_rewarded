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
package com.wee.boo.AndorsTrail.Rewarded.model.item;

import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionEffect;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.traits.AbilityModifierTraits;

public final class ItemTraits_OnEquip {
	public final AbilityModifierTraits stats;
	public final ActorConditionEffect[] addedConditions;

	public ItemTraits_OnEquip(
			AbilityModifierTraits stats
			, ActorConditionEffect[] addedConditions
	) {
		this.stats = stats;
		this.addedConditions = addedConditions;
	}

	public int calculateEquipCost(boolean isWeapon) {
		final int costStats = stats == null ? 0 : stats.calculateCost(isWeapon);
		return costStats;
	}
}

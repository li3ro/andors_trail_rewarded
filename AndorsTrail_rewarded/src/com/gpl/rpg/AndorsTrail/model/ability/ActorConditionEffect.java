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

import com.gpl.rpg.AndorsTrail.util.ConstRange;

public final class ActorConditionEffect {
	public final ActorConditionType conditionType;
	public final int magnitude;
	public final int duration;
	public final ConstRange chance;

	public ActorConditionEffect(
			ActorConditionType conditionType
			, int magnitude
			, int duration
			, ConstRange chance
	) {
		this.conditionType = conditionType;
		this.magnitude = magnitude;
		this.duration = duration;
		this.chance = chance;
	}

	public boolean isRemovalEffect() {
		return magnitude == ActorCondition.MAGNITUDE_REMOVE_ALL;
	}

	public ActorCondition createCondition() { return createCondition(duration); }
	public ActorCondition createCondition(final int duration) {
		return new ActorCondition(conditionType, magnitude, duration);
	}
}

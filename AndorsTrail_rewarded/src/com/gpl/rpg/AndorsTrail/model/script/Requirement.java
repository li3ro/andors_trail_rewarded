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
package com.gpl.rpg.AndorsTrail.model.script;

public final class Requirement {
	public static enum RequirementType {
		questProgress
		,questLatestProgress // Highest quest stage reached must match.
		,inventoryRemove	// Player must have item(s) in inventory. Items will be removed when selecting reply.
		,inventoryKeep		// Player must have item(s) in inventory. Items will NOT be removed when selecting reply.
		,wear				// Player must be wearing item(s). Items will NOT be removed when selecting reply.
		,skillLevel			// Player needs to have a specific skill equal to or above a certain level
		,killedMonster
		,timerElapsed
		,usedItem
		,spentGold
		,consumedBonemeals
		,hasActorCondition
	}

	public final RequirementType requireType;
	public final String requireID;
	public final int value;
	public final boolean negate;

	public Requirement(
			RequirementType requireType
			, String requireID
			, int value
			, boolean negate
	) {
		this.requireType = requireType;
		this.requireID = requireID;
		this.value = value;
		this.negate = negate;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder(requireType.toString());
		buf.append("--");
		buf.append(requireID);
		buf.append("--");
		if (negate) buf.append('!');
		buf.append(value);
		return buf.toString();
	}
}

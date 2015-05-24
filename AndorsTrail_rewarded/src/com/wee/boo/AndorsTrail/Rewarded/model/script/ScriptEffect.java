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
package com.wee.boo.AndorsTrail.Rewarded.model.script;

public final class ScriptEffect {
	public static enum ScriptEffectType {
		questProgress
		, dropList
		, skillIncrease
		, actorCondition
		, alignmentChange
		, giveItem
		, createTimer
		, spawnAll
		, removeSpawnArea
		, deactivateSpawnArea
		, activateMapChangeArea
		, deactivateMapChangeArea
	}

	public final ScriptEffectType type;
	public final String effectID;
	public final int value;
	public final String mapName;

	public ScriptEffect(
			ScriptEffectType type
			, String effectID
			, int value
			, String mapName
	) {
		this.type = type;
		this.effectID = effectID;
		this.value = value;
		this.mapName = mapName;
	}
}

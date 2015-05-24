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
package com.gpl.rpg.AndorsTrail.model.actor;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.resource.parsers.MonsterTypeParser;
import com.gpl.rpg.AndorsTrail.util.L;

import java.util.ArrayList;
import java.util.HashMap;

public final class MonsterTypeCollection {
	private final HashMap<String, MonsterType> monsterTypesById = new HashMap<String, MonsterType>();

	public MonsterType getMonsterType(String id) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!monsterTypesById.containsKey(id)) {
				L.log("WARNING: Cannot find MonsterType for id \"" + id + "\".");
			}
		}
		return monsterTypesById.get(id);
	}

	public ArrayList<MonsterType> getMonsterTypesFromSpawnGroup(String spawnGroup) {
		ArrayList<MonsterType> result = new ArrayList<MonsterType>();
		for (MonsterType t : monsterTypesById.values()) {
			if (t.spawnGroup.equalsIgnoreCase(spawnGroup)) result.add(t);
		}

		return result;
	}

	public MonsterType guessMonsterTypeFromName(String name) {
		for (MonsterType t : monsterTypesById.values()) {
			if (t.name.equalsIgnoreCase(name)) return t;
		}
		return null;
	}

	public void initialize(MonsterTypeParser parser, String input) {
		parser.parseRows(input, monsterTypesById);
	}

	// Unit test method. Not part of the game logic.
	public HashMap<String, MonsterType> UNITTEST_getAllMonsterTypes() {
		return monsterTypesById;
	}
}

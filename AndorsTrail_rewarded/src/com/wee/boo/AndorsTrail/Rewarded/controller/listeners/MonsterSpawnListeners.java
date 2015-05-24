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
package com.wee.boo.AndorsTrail.Rewarded.controller.listeners;

import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.model.map.PredefinedMap;
import com.wee.boo.AndorsTrail.Rewarded.util.Coord;
import com.wee.boo.AndorsTrail.Rewarded.util.CoordRect;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class MonsterSpawnListeners extends ListOfListeners<MonsterSpawnListener> implements MonsterSpawnListener {

	private final Function2<MonsterSpawnListener, PredefinedMap, Monster> onMonsterSpawned = new Function2<MonsterSpawnListener, PredefinedMap, Monster>() {
		@Override public void call(MonsterSpawnListener listener, PredefinedMap map, Monster monster) { listener.onMonsterSpawned(map, monster); }
	};

	private final Function3<MonsterSpawnListener, PredefinedMap, Monster, CoordRect> onMonsterRemoved = new Function3<MonsterSpawnListener, PredefinedMap, Monster, CoordRect>() {
		@Override public void call(MonsterSpawnListener listener, PredefinedMap map, Monster monster, CoordRect previousPosition) { listener.onMonsterRemoved(map, monster, previousPosition); }
	};

	private final Function2<MonsterSpawnListener, PredefinedMap, Coord> onSplatterAdded = new Function2<MonsterSpawnListener, PredefinedMap, Coord>() {
		@Override public void call(MonsterSpawnListener listener, PredefinedMap map, Coord p) { listener.onSplatterAdded(map, p); }
	};

	private final Function2<MonsterSpawnListener, PredefinedMap, Coord> onSplatterChanged = new Function2<MonsterSpawnListener, PredefinedMap, Coord>() {
		@Override public void call(MonsterSpawnListener listener, PredefinedMap map, Coord p) { listener.onSplatterChanged(map, p); }
	};

	private final Function2<MonsterSpawnListener, PredefinedMap, Coord> onSplatterRemoved = new Function2<MonsterSpawnListener, PredefinedMap, Coord>() {
		@Override public void call(MonsterSpawnListener listener, PredefinedMap map, Coord p) { listener.onSplatterRemoved(map, p); }
	};

	@Override
	public void onMonsterSpawned(PredefinedMap map, Monster m) {
		callAllListeners(this.onMonsterSpawned, map, m);
	}

	@Override
	public void onMonsterRemoved(PredefinedMap map, Monster m, CoordRect previousPosition) {
		callAllListeners(this.onMonsterRemoved, map, m, previousPosition);
	}

	@Override
	public void onSplatterAdded(PredefinedMap map, Coord p) {
		callAllListeners(this.onSplatterAdded, map, p);
	}

	@Override
	public void onSplatterChanged(PredefinedMap map, Coord p) {
		callAllListeners(this.onSplatterChanged, map, p);
	}

	@Override
	public void onSplatterRemoved(PredefinedMap map, Coord p) {
		callAllListeners(this.onSplatterRemoved, map, p);
	}
}

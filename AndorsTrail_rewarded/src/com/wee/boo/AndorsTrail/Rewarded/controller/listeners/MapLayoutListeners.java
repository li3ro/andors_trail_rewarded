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

import com.wee.boo.AndorsTrail.Rewarded.model.map.LayeredTileMap;
import com.wee.boo.AndorsTrail.Rewarded.model.map.PredefinedMap;
import com.wee.boo.AndorsTrail.Rewarded.util.Coord;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class MapLayoutListeners extends ListOfListeners<MapLayoutListener> implements MapLayoutListener {

	private final Function2<MapLayoutListener, PredefinedMap, Coord> onLootBagCreated = new Function2<MapLayoutListener, PredefinedMap, Coord>() {
		@Override public void call(MapLayoutListener listener, PredefinedMap map, Coord p) { listener.onLootBagCreated(map, p); }
	};

	private final Function2<MapLayoutListener, PredefinedMap, Coord> onLootBagRemoved = new Function2<MapLayoutListener, PredefinedMap, Coord>() {
		@Override public void call(MapLayoutListener listener, PredefinedMap map, Coord p) { listener.onLootBagRemoved(map, p); }
	};

	private final Function2<MapLayoutListener, PredefinedMap, LayeredTileMap> onMapTilesChanged = new Function2<MapLayoutListener, PredefinedMap, LayeredTileMap>() {
		@Override public void call(MapLayoutListener listener, PredefinedMap map, LayeredTileMap tileMap) { listener.onMapTilesChanged(map, tileMap); }
	};

	@Override
	public void onLootBagCreated(PredefinedMap map, Coord p) {
		callAllListeners(this.onLootBagCreated, map, p);
	}

	@Override
	public void onLootBagRemoved(PredefinedMap map, Coord p) {
		callAllListeners(this.onLootBagRemoved, map, p);
	}

	@Override
	public void onMapTilesChanged(PredefinedMap map, LayeredTileMap tileMap) {
		callAllListeners(this.onMapTilesChanged, map, tileMap);
	}
}

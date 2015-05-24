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
package com.wee.boo.AndorsTrail.Rewarded.model.map;

import com.wee.boo.AndorsTrail.Rewarded.util.Coord;

import java.util.HashMap;
import java.util.HashSet;

public final class WorldMapSegment {
	public final String name;
	public final HashMap<String, WorldMapSegmentMap> maps = new HashMap<String, WorldMapSegmentMap>();
	public final HashMap<String, NamedWorldMapArea> namedAreas = new HashMap<String, NamedWorldMapArea>();

	public WorldMapSegment(String name) {
		this.name = name;
	}

	public boolean containsMap(String mapName) { return maps.containsKey(mapName); }

	public static final class WorldMapSegmentMap {
		public final String mapName;
		public final Coord worldPosition;
		public WorldMapSegmentMap(String mapName, Coord worldPosition) {
			this.mapName = mapName;
			this.worldPosition = worldPosition;
		}
	}

	// Towns, cities, villages, taverns, named dungeons
	public static final class NamedWorldMapArea {
		public final String id;
		public final String name;
		public final String type; // "settlement" or "other"
		public final HashSet<String> mapNames = new HashSet<String>();
		public NamedWorldMapArea(String id, String name, String type) {
			this.id = id;
			this.name = name;
			this.type = type;
		}
	}
}

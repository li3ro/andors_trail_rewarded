/*
 * Copyrightę 2015 Yaniv Bokobza
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

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.savegames.LegacySavegameFormatReaderForMap;
import com.wee.boo.AndorsTrail.Rewarded.util.L;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class MapCollection {
	private final HashMap<String, PredefinedMap> predefinedMaps = new HashMap<String, PredefinedMap>();
	public final HashMap<String, WorldMapSegment> worldMapSegments = new HashMap<String, WorldMapSegment>();
	public boolean worldMapRequiresUpdate = true;

	public MapCollection() {}

	public void addAll(ArrayList<PredefinedMap> mapsToAdd) {
		for (PredefinedMap map : mapsToAdd) {
			predefinedMaps.put(map.name, map);
		}
	}

	public Collection<PredefinedMap> getAllMaps() {
		return predefinedMaps.values();
	}

	public PredefinedMap findPredefinedMap(String name) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!predefinedMaps.containsKey(name)) {
				L.log("WARNING: Cannot find PredefinedMap for name \"" + name + "\".");
			}
		}
		return predefinedMaps.get(name);
	}

	public void resetForNewGame() {
		for (PredefinedMap m : getAllMaps()) {
			m.resetForNewGame();
		}
		worldMapRequiresUpdate = true;
	}

	public String getWorldMapSegmentNameForMap(String mapName) {
		for (WorldMapSegment segment : worldMapSegments.values()) {
			if (segment.containsMap(mapName)) return segment.name;
		}
		return null;
	}


	// ====== PARCELABLE ===================================================================

	public void readFromParcel(DataInputStream src, WorldContext world, ControllerContext controllers, int fileversion) throws IOException {
		int size;
		if (fileversion == 5) size = 11;
		else size = src.readInt();
		for(int i = 0; i < size; ++i) {
			String name;
			if (fileversion >= 35) {
				name = src.readUTF();
			} else {
				name = LegacySavegameFormatReaderForMap.getMapnameFromIndex(i);
			}
			PredefinedMap map = predefinedMaps.get(name);
			if (map == null) {
				if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
					L.log("WARNING: Tried to load savegame with map \"" + name + "\", but no such map exists.");
				}
				continue;
			}
			map.readFromParcel(src, world, controllers, fileversion);
			if (i >= 40) {
				if (fileversion < 15) map.visited = false;
			}
		}
	}

	public static boolean shouldSaveMap(WorldContext world, PredefinedMap map) {
		if (map.visited) return true;
		if (map.shouldSaveMapData(world)) return true;
		return false;
	}

	public void writeToParcel(DataOutputStream dest, WorldContext world) throws IOException {
		List<PredefinedMap> mapsToExport = new ArrayList<PredefinedMap>();
		for(PredefinedMap map : getAllMaps()) {
			if (shouldSaveMap(world, map)) mapsToExport.add(map);
		}
		dest.writeInt(mapsToExport.size());
		for(PredefinedMap map : mapsToExport) {
			dest.writeUTF(map.name);
			map.writeToParcel(dest, world);
		}
	}
}

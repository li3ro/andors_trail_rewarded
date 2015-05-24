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
package com.gpl.rpg.AndorsTrail.resource;

import android.util.SparseArray;
import android.util.SparseIntArray;
import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.resource.tiles.ResourceFileTileset;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileCache;
import com.gpl.rpg.AndorsTrail.util.L;
import com.gpl.rpg.AndorsTrail.util.Size;

import java.util.HashMap;
import java.util.Map;

public final class DynamicTileLoader {
	private final TileCache tileCache;

	private final SparseArray<ResourceFileTilesetLoadList> preparedTilesetsByResourceId = new SparseArray<ResourceFileTilesetLoadList>();
	private final HashMap<String, ResourceFileTilesetLoadList> preparedTilesetsByResourceName = new HashMap<String, ResourceFileTilesetLoadList>();
	private int currentTileStoreIndex;

	private static final class ResourceFileTilesetLoadList {
		public final ResourceFileTileset tileset;
		public final SparseIntArray tileIDsToLoadPerLocalID = new SparseIntArray();
		public ResourceFileTilesetLoadList(ResourceFileTileset tileset) {
			this.tileset = tileset;
		}
	}

	public DynamicTileLoader(TileCache tileCache) {
		this.tileCache = tileCache;
		initialize();
	}

	private void initialize() {
		preparedTilesetsByResourceId.clear();
		preparedTilesetsByResourceName.clear();
		currentTileStoreIndex = tileCache.getMaxTileID();
	}

	public void prepareTileset(int resourceId, String tilesetName, Size numTiles, Size destinationTileSize) {
		ResourceFileTileset b = new ResourceFileTileset(resourceId, tilesetName, numTiles, destinationTileSize);
		ResourceFileTilesetLoadList loadList = new ResourceFileTilesetLoadList(b);
		preparedTilesetsByResourceId.put(resourceId, loadList);
		preparedTilesetsByResourceName.put(tilesetName, loadList);
	}
	private ResourceFileTilesetLoadList getTilesetBitmap(int tilesetImageResourceID) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (preparedTilesetsByResourceId.get(tilesetImageResourceID) == null) {
				L.log("WARNING: Cannot load tileset " + tilesetImageResourceID);
				return null;
			}
		}
		return preparedTilesetsByResourceId.get(tilesetImageResourceID);
	}
	private ResourceFileTilesetLoadList getTilesetBitmap(String tilesetName) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!preparedTilesetsByResourceName.containsKey(tilesetName)) {
				L.log("WARNING: Cannot load tileset " + tilesetName);
				return null;
			}
		}
		return preparedTilesetsByResourceName.get(tilesetName);
	}

	public int prepareTileID(int tilesetImageResourceID, int localID) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetImageResourceID);
		return prepareTileID(b, localID);
	}

	public int prepareTileID(String tilesetName, int localID) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetName);
		return prepareTileID(b, localID);
	}
	public Size getTilesetSize(String tilesetName) {
		ResourceFileTilesetLoadList b = getTilesetBitmap(tilesetName);
		return b.tileset.destinationTileSize;
	}

	private int prepareTileID(ResourceFileTilesetLoadList tileset, int localID) {
		int tileID = tileset.tileIDsToLoadPerLocalID.get(localID);
		if (tileID == 0) {
			++currentTileStoreIndex;
			tileID = currentTileStoreIndex;
			tileset.tileIDsToLoadPerLocalID.put(localID, tileID);
		}
		return tileID;
	}

	public void prepareAllMapTiles() {
		for (Map.Entry<String, ResourceFileTilesetLoadList> tileset : preparedTilesetsByResourceName.entrySet()) {
			if (!tileset.getKey().startsWith("map_")) continue;
			ResourceFileTilesetLoadList b = tileset.getValue();
			int numTiles = b.tileset.numTiles.width * b.tileset.numTiles.height;
			for(int i = 0; i < numTiles; ++i) {
				prepareTileID(b, i);
			}
		}
	}

	public void flush() {
		tileCache.allocateMaxTileID(currentTileStoreIndex);
		for (int i = 0; i < preparedTilesetsByResourceId.size(); ++i) {
			ResourceFileTilesetLoadList e = preparedTilesetsByResourceId.valueAt(i);
			ResourceFileTileset tileset = e.tileset;
			SparseIntArray tileIDsToLoad = e.tileIDsToLoadPerLocalID;
			for (int j = 0; j < tileIDsToLoad.size(); ++j) {
				tileCache.setTile(tileIDsToLoad.valueAt(j), tileset, tileIDsToLoad.keyAt(j));
			}
		}
	}
}

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
package com.gpl.rpg.AndorsTrail.model.map;

import com.gpl.rpg.AndorsTrail.util.ByteUtils;
import com.gpl.rpg.AndorsTrail.util.CoordRect;

public final class MapSection {
	public final MapLayer layerGround;
	public final MapLayer layerObjects;
	public final MapLayer layerAbove;
	public final boolean[][] isWalkable;
	private final byte[] layoutHash;

	public MapSection(
			MapLayer layerGround
			, MapLayer layerObjects
			, MapLayer layerAbove
			, boolean[][] isWalkable
			, byte[] layoutHash
	) {
		this.layerGround = layerGround;
		this.layerObjects = layerObjects;
		this.layerAbove = layerAbove;
		this.isWalkable = isWalkable;
		this.layoutHash = layoutHash;
	}

	public void replaceLayerContentsWith(final MapSection replaceLayersWith, final CoordRect replacementArea) {
		replaceTileLayerSection(layerGround, replaceLayersWith.layerGround, replacementArea);
		replaceTileLayerSection(layerObjects, replaceLayersWith.layerObjects, replacementArea);
		replaceTileLayerSection(layerAbove, replaceLayersWith.layerAbove, replacementArea);
		if (replaceLayersWith.isWalkable != null) {
			final int dy = replacementArea.topLeft.y;
			final int height = replacementArea.size.height;
			for (int sx = 0, dx = replacementArea.topLeft.x; sx < replacementArea.size.width; ++sx, ++dx) {
				System.arraycopy(replaceLayersWith.isWalkable[sx], 0, isWalkable[dx], dy, height);
			}
		}
		ByteUtils.xorArray(layoutHash, replaceLayersWith.layoutHash);
	}

	private static void replaceTileLayerSection(MapLayer dest, MapLayer src, CoordRect area) {
		if (src == null) return;
		final int dy = area.topLeft.y;
		final int height = area.size.height;
		for (int sx = 0, dx = area.topLeft.x; sx < area.size.width; ++sx, ++dx) {
			System.arraycopy(src.tiles[sx], 0, dest.tiles[dx], dy, height);
		}
	}

	public String calculateHash() {
		return ByteUtils.toHexString(layoutHash, 4);
	}
}

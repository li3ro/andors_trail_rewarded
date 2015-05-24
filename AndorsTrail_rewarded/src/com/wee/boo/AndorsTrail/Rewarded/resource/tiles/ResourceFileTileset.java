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
package com.wee.boo.AndorsTrail.Rewarded.resource.tiles;

import android.graphics.Matrix;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.util.Size;

public final class ResourceFileTileset {
	public final int resourceID;
	public final String tilesetName;
	public final Size destinationTileSize;
	public final Size numTiles;
	public Size sourceTileSize;
	public Matrix scale;

	public ResourceFileTileset(int resourceID, String tilesetName, Size numTiles, Size destinationTileSize) {
		this.resourceID = resourceID;
		this.tilesetName = tilesetName;
		this.destinationTileSize = destinationTileSize;
		this.numTiles = numTiles;
	}

	@Override public int hashCode() { return resourceID; }

	public void calculateFromSourceImageSize(final int sourceWidth, final int sourceHeight) {
		sourceTileSize = new Size(
				sourceWidth / numTiles.width
				,sourceHeight / numTiles.height
			);

		if (destinationTileSize.width == sourceTileSize.width && destinationTileSize.height == sourceTileSize.height) {
			scale = null;
		} else {
			scale = new Matrix();
			scale.postScale(
					((float) destinationTileSize.width) / sourceTileSize.width
					,((float) destinationTileSize.height) / sourceTileSize.height
				);

			if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
				//L.log("OPTIMIZE: Tileset " + tilesetName + " will be resized from " + sourceTileSize.toString() + " to " + destinationTileSize.toString());
			}
		}
	}

}

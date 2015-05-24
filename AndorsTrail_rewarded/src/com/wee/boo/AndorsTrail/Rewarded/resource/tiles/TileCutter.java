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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public final class TileCutter {
	private final ResourceFileTileset sourceFile;
	private final Bitmap tilesetImage;
	private boolean recycle = true;

	public TileCutter(ResourceFileTileset sourceFile, Resources r) {
		this.sourceFile = sourceFile;
		this.tilesetImage = createTilesetImage(r);
	}

	private Bitmap createTilesetImage(Resources r) {
		//return BitmapFactory.decodeResource(r, b.resourceId);
		Options o = new Options();
		o.inScaled = false;
		Bitmap sourceImage = BitmapFactory.decodeResource(r, sourceFile.resourceID, o);
		sourceImage.setDensity(Bitmap.DENSITY_NONE);
		sourceFile.calculateFromSourceImageSize(sourceImage.getWidth(), sourceImage.getHeight());
		return sourceImage;
	}

	public Bitmap createTile(int localID) {
		final int x = localID % sourceFile.numTiles.width;
		final int y = (localID - x) / sourceFile.numTiles.width;
		final int left = x * sourceFile.sourceTileSize.width;
		final int top = y * sourceFile.sourceTileSize.height;
		Bitmap result;
		if (sourceFile.scale != null) {
			result = Bitmap.createBitmap(tilesetImage, left, top, sourceFile.sourceTileSize.width, sourceFile.sourceTileSize.height, sourceFile.scale, true);
		} else {
			result = Bitmap.createBitmap(tilesetImage, left, top, sourceFile.sourceTileSize.width, sourceFile.sourceTileSize.height);
		}
		if (result == tilesetImage) recycle = false;
		return result;
	}

	public void recycle() {
		if (recycle) tilesetImage.recycle();
	}
}

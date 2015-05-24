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
package com.gpl.rpg.AndorsTrail.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.widget.Button;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.context.WorldContext;
import com.gpl.rpg.AndorsTrail.model.item.ItemType;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileCollection;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileManager;

public final class QuickButton extends Button {
	private final ColorFilter grayScaleFilter = new ColorMatrixColorFilter(
			new float[] {
				0.30f, 0.59f, 0.11f, 0.0f, 0.0f,
				0.30f, 0.59f, 0.11f, 0.0f, 0.0f,
				0.30f, 0.59f, 0.11f, 0.0f, 0.0f,
				0.00f, 0.00f, 0.00f, 1.0f, 0.0f
			});
	private boolean empty;
	private final QuickButtonContextMenuInfo menuInfo;
	private final int textPadding;

	public QuickButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		menuInfo = new QuickButtonContextMenuInfo();
		textPadding = getResources().getDimensionPixelSize(R.dimen.boxshape_margin);
	}

	public void setIndex(int index){
		menuInfo.index = index;
	}

	public int getIndex(){
		return menuInfo.index;
	}

	@Override
	protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
		return menuInfo;
	}

	private String currentItemID = "unassigned";
	public void setItemType(ItemType type, WorldContext world, TileCollection tiles) {
		final Resources res = getContext().getResources();

		if (type == null) {
			if (currentItemID == null) return;
			empty = true;
			world.tileManager.setImageViewTileForUIIcon(res, this, TileManager.iconID_unassigned_quickslot);
			currentItemID = null;
			setGrayScale(true);
			setText("");
			setCompoundDrawablePadding(0);
		} else {
			int quantity = world.model.player.inventory.getItemQuantity(type.id);
			empty = quantity == 0;
			if (!type.id.equals(currentItemID)) {
				world.tileManager.setImageViewTile(res, this, type, tiles);
				setCompoundDrawablePadding(textPadding);
				currentItemID = type.id;
			}
			setGrayScale(empty);
			setText(Integer.toString(quantity));
		}
	}

	private void setGrayScale(boolean useGrayscale) {
		getCompoundDrawables()[0].setColorFilter(useGrayscale ? grayScaleFilter : null);
	}

	public boolean isEmpty() {
		return empty;
	}

	public static class QuickButtonContextMenuInfo implements ContextMenu.ContextMenuInfo{
		public int index;
	}
}

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.controller.ItemController;
import com.gpl.rpg.AndorsTrail.model.actor.Player;
import com.gpl.rpg.AndorsTrail.model.item.ItemContainer;
import com.gpl.rpg.AndorsTrail.model.item.ItemContainer.ItemEntry;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileCollection;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileManager;

public final class ItemContainerAdapter extends ArrayAdapter<ItemEntry> {
	private final TileManager tileManager;
	private final TileCollection tileCollection;
	private final Player player;

	public ItemContainerAdapter(Context context, TileManager tileManager, ItemContainer items, Player player) {
		this(context, tileManager, items, player, tileManager.loadTilesFor(items, context.getResources()));
	}
	public ItemContainerAdapter(Context context, TileManager tileManager, ItemContainer items, Player player, TileCollection tileCollection) {
		super(context, 0, items.items);
		this.tileManager = tileManager;
		this.tileCollection = tileCollection;
		this.player = player;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ItemEntry item = getItem(position);

		View result = convertView;
		if (result == null) {
			result = View.inflate(getContext(), R.layout.inventoryitemview, null);
		}
		TextView tv = (TextView) result;

		tileManager.setImageViewTile(getContext().getResources(), tv, item.itemType, tileCollection);
		tv.setText(ItemController.describeItemForListView(item, player));
		return result;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).itemType.id.hashCode();
	}
}

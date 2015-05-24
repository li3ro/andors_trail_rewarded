/*
 * Copyright� 2015 Yaniv Bokobza
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
package com.wee.boo.AndorsTrail.Rewarded.view;

import android.R.color;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailPreferences;
import com.wee.boo.AndorsTrail.Rewarded.activity.MainActivity;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.controller.listeners.QuickSlotListener;
import com.wee.boo.AndorsTrail.Rewarded.model.item.Inventory;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemType;
import com.wee.boo.AndorsTrail.Rewarded.resource.tiles.TileCollection;

import java.util.HashSet;

public final class QuickitemView extends LinearLayout implements OnClickListener, QuickSlotListener {
	private static final int NUM_QUICK_SLOTS = Inventory.NUM_QUICK_SLOTS;

	private final WorldContext world;
	private final ControllerContext controllers;
	private final QuickButton[] buttons = new QuickButton[NUM_QUICK_SLOTS];
	private final HashSet<Integer> loadedTileIDs = new HashSet<Integer>();
	private TileCollection tiles = null;

	public QuickitemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivityContext(context);
		this.world = app.getWorld();
		this.controllers = app.getControllerContext();
		setFocusable(false);

		int position = app.getPreferences().quickslotsPosition;
		switch(position) {
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_LEFT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_RIGHT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM:
				setOrientation(LinearLayout.HORIZONTAL);
				break;
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_BOTTOM_LEFT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_BOTTOM_RIGHT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_CENTER_LEFT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_CENTER_RIGHT:
				setOrientation(LinearLayout.VERTICAL);
				break;
		}

		Resources res = getResources();
		this.setBackgroundColor(res.getColor(color.transparent));

		for(int i = 0; i < NUM_QUICK_SLOTS; ++i) {
			buttons[i] = new QuickButton(context, null);
			QuickButton item = buttons[i];
			item.setIndex(i);
			item.setItemType(null, world, tiles);
			item.setOnClickListener(this);
			addView(item);
		}
	}

	public boolean isQuickButtonId(int id){
		for(QuickButton item: buttons){
			if(item.getId()==id)
				return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		QuickButton button = (QuickButton)v;
		if(button.isEmpty())
			return;
		controllers.itemController.quickitemUse(button.getIndex());
	}

	@Override
	public void setVisibility(int visibility) {
		if(visibility==VISIBLE)
			refreshQuickitems();
		super.setVisibility(visibility);
	}

	public void refreshQuickitems() {
		loadItemTypeImages();

		for (int i = 0; i < NUM_QUICK_SLOTS; ++i){
			ItemType type = world.model.player.inventory.quickitem[i];
			buttons[i].setItemType(type, world, tiles);
		}
	}

	private void loadItemTypeImages() {
		boolean shouldLoadImages = false;
		for (ItemType type : world.model.player.inventory.quickitem) {
			if (type == null) continue;
			if (!loadedTileIDs.contains(type.iconID)) {
				shouldLoadImages = true;
				break;
			}
		}
		if (!shouldLoadImages) return;

		HashSet<Integer> iconIDs = new HashSet<Integer>();

		for (ItemType type : world.model.player.inventory.quickitem) {
			if (type == null) continue;
			iconIDs.add(type.iconID);
		}

		loadedTileIDs.clear();
		loadedTileIDs.addAll(iconIDs);
		tiles = world.tileManager.loadTilesFor(iconIDs, getResources());
	}

	public void registerForContextMenu(MainActivity mainActivity) {
		for(QuickButton item: buttons)
			mainActivity.registerForContextMenu(item);
	}

	public void setPosition(AndorsTrailPreferences preferences) {
		int positionRelativeTo = R.id.main_mainview;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		switch (preferences.quickslotsPosition) {
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_LEFT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_BOTTOM_LEFT:
				params.addRule(RelativeLayout.ALIGN_LEFT, positionRelativeTo);
				params.addRule(RelativeLayout.ALIGN_BOTTOM, positionRelativeTo);
				break;
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_RIGHT:
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_BOTTOM_RIGHT:
				params.addRule(RelativeLayout.ALIGN_RIGHT, positionRelativeTo);
				params.addRule(RelativeLayout.ALIGN_BOTTOM, positionRelativeTo);
				break;
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM:
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.ALIGN_BOTTOM, positionRelativeTo);
				break;
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_CENTER_LEFT:
				params.addRule(RelativeLayout.ALIGN_LEFT, positionRelativeTo);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				break;
			case AndorsTrailPreferences.QUICKSLOTS_POSITION_VERTICAL_CENTER_RIGHT:
				params.addRule(RelativeLayout.ALIGN_RIGHT, positionRelativeTo);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				break;
		}

		setLayoutParams(params);
	}

	@Override
	public void onQuickSlotChanged(int slotId) {
		refreshQuickitems();
	}

	@Override
	public void onQuickSlotUsed(int slotId) {
		refreshQuickitems();
	}

	public void subscribe() {
		controllers.itemController.quickSlotListeners.add(this);
	}
	public void unsubscribe() {
		controllers.itemController.quickSlotListeners.remove(this);
	}
}

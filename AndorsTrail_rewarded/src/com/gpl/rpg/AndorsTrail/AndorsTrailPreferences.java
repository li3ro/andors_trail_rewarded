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
package com.gpl.rpg.AndorsTrail;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class AndorsTrailPreferences {
	public static final int DISPLAYLOOT_DIALOG_ALWAYS = 0;
	public static final int DISPLAYLOOT_DIALOG_FOR_ITEMS = 3;
	public static final int DISPLAYLOOT_DIALOG_FOR_ITEMS_ELSE_TOAST = 4;
	public static final int DISPLAYLOOT_TOAST = 1;
	public static final int DISPLAYLOOT_TOAST_FOR_ITEMS = 5;
	public static final int DISPLAYLOOT_NONE = 2;
	public static final int MOVEMENTMETHOD_STRAIGHT = 0;
	public static final int MOVEMENTMETHOD_DIRECTIONAL = 1;
	public static final int MOVEMENTAGGRESSIVENESS_NORMAL = 0;
	public static final int MOVEMENTAGGRESSIVENESS_AGGRESSIVE = 1;
	public static final int MOVEMENTAGGRESSIVENESS_DEFENSIVE = 2;
	public static final int DPAD_POSITION_DISABLED = 0;
	public static final int DPAD_POSITION_LOWER_RIGHT = 1;
	public static final int DPAD_POSITION_LOWER_LEFT = 2;
	public static final int DPAD_POSITION_LOWER_CENTER = 3;
	public static final int DPAD_POSITION_CENTER_LEFT = 4;
	public static final int DPAD_POSITION_CENTER_RIGHT = 5;
	public static final int DPAD_POSITION_UPPER_LEFT = 6;
	public static final int DPAD_POSITION_UPPER_RIGHT = 7;
	public static final int DPAD_POSITION_UPPER_CENTER = 8;
	public static final int CONFIRM_OVERWRITE_SAVEGAME_ALWAYS = 0;
	public static final int CONFIRM_OVERWRITE_SAVEGAME_WHEN_PLAYERNAME_DIFFERS = 1;
	public static final int CONFIRM_OVERWRITE_SAVEGAME_NEVER = 2;
	public static final int QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM = 0;
	public static final int QUICKSLOTS_POSITION_VERTICAL_CENTER_LEFT = 1;
	public static final int QUICKSLOTS_POSITION_VERTICAL_CENTER_RIGHT = 2;
	public static final int QUICKSLOTS_POSITION_VERTICAL_BOTTOM_LEFT = 3;
	public static final int QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_LEFT = 4;
	public static final int QUICKSLOTS_POSITION_HORIZONTAL_BOTTOM_RIGHT = 5;
	public static final int QUICKSLOTS_POSITION_VERTICAL_BOTTOM_RIGHT = 6;

	public boolean confirmRest = true;
	public boolean confirmAttack = true;
	public int displayLoot = DISPLAYLOOT_DIALOG_ALWAYS;
	public boolean fullscreen = true;
	public int attackspeed_milliseconds = 1000;
	public int movementMethod = MOVEMENTMETHOD_STRAIGHT;
	public int movementAggressiveness = MOVEMENTAGGRESSIVENESS_NORMAL;
	public float scalingFactor = 1.0f;
	public int dpadPosition;
	public boolean dpadMinimizeable = true;
	public boolean optimizedDrawing = false;
	public boolean enableUiAnimations = true;
	public int displayOverwriteSavegame = CONFIRM_OVERWRITE_SAVEGAME_ALWAYS;
	public int quickslotsPosition = QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM;
	public boolean showQuickslotsWhenToolboxIsVisible = false;
	public boolean useLocalizedResources = true;

	public void read(final Context androidContext) {
		AndorsTrailPreferences dest = this;
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(androidContext);
			dest.confirmRest = prefs.getBoolean("confirm_rest", true);
			dest.confirmAttack = prefs.getBoolean("confirm_attack", true);
			dest.displayLoot = Integer.parseInt(prefs.getString("display_lootdialog", Integer.toString(DISPLAYLOOT_DIALOG_ALWAYS)));
			dest.fullscreen = prefs.getBoolean("fullscreen", true);
			dest.attackspeed_milliseconds = Integer.parseInt(prefs.getString("attackspeed", "1000"));
			dest.movementMethod = Integer.parseInt(prefs.getString("movementmethod", Integer.toString(MOVEMENTMETHOD_STRAIGHT)));
			dest.scalingFactor = Float.parseFloat(prefs.getString("scaling_factor", "1.0f"));
			dest.dpadPosition = Integer.parseInt(prefs.getString("dpadposition", Integer.toString(DPAD_POSITION_DISABLED)));
			dest.dpadMinimizeable = prefs.getBoolean("dpadMinimizeable", true);
			dest.optimizedDrawing = prefs.getBoolean("optimized_drawing", false);
			dest.enableUiAnimations = prefs.getBoolean("enableUiAnimations", true);
			dest.displayOverwriteSavegame = Integer.parseInt(prefs.getString("display_overwrite_savegame", Integer.toString(CONFIRM_OVERWRITE_SAVEGAME_ALWAYS)));
			dest.quickslotsPosition = Integer.parseInt(prefs.getString("quickslots_placement", Integer.toString(QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM)));
			dest.showQuickslotsWhenToolboxIsVisible = prefs.getBoolean("showQuickslotsWhenToolboxIsVisible", false);
			dest.useLocalizedResources = prefs.getBoolean("useLocalizedResources", true);

			// This might be implemented as a skill in the future.
			//dest.movementAggressiveness = Integer.parseInt(prefs.getString("movementaggressiveness", Integer.toString(MOVEMENTAGGRESSIVENESS_NORMAL)));
		} catch (Exception e) {
			dest.confirmRest = true;
			dest.confirmAttack = true;
			dest.displayLoot = DISPLAYLOOT_DIALOG_ALWAYS;
			dest.fullscreen = true;
			dest.attackspeed_milliseconds = 1000;
			dest.movementMethod = MOVEMENTMETHOD_STRAIGHT;
			dest.movementAggressiveness = MOVEMENTAGGRESSIVENESS_NORMAL;
			dest.scalingFactor = 1.0f;
			dest.dpadPosition = DPAD_POSITION_DISABLED;
			dest.dpadMinimizeable = true;
			dest.optimizedDrawing = false;
			dest.enableUiAnimations = true;
			dest.displayOverwriteSavegame = CONFIRM_OVERWRITE_SAVEGAME_ALWAYS;
			dest.quickslotsPosition = QUICKSLOTS_POSITION_HORIZONTAL_CENTER_BOTTOM;
			dest.showQuickslotsWhenToolboxIsVisible = false;
			dest.useLocalizedResources = true;
		}
	}
}

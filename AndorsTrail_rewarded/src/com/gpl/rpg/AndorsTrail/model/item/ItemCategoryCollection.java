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
package com.gpl.rpg.AndorsTrail.model.item;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.resource.parsers.ItemCategoryParser;
import com.gpl.rpg.AndorsTrail.util.L;

import java.util.HashMap;

public final class ItemCategoryCollection {
	private final HashMap<String, ItemCategory> itemCategories = new HashMap<String, ItemCategory>();

	public ItemCategory getItemCategory(String id) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!itemCategories.containsKey(id)) {
				L.log("WARNING: Cannot find ItemCategory for id \"" + id + "\".");
			}
		}
		return itemCategories.get(id);
	}

	public void initialize(final ItemCategoryParser parser, String input) {
		parser.parseRows(input, itemCategories);
	}

	// Unit test method. Not part of the game logic.
	public HashMap<String, ItemCategory> UNITTEST_getAllItemCategories() {
		return itemCategories;
	}
}

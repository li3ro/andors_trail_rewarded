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
package com.wee.boo.AndorsTrail.Rewarded.model.item;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.DropListParser;
import com.wee.boo.AndorsTrail.Rewarded.util.L;

import java.util.HashMap;

public final class DropListCollection {
	public static final String DROPLIST_STARTITEMS = "startitems";

	private final HashMap<String, DropList> droplists = new HashMap<String, DropList>();

	public DropList getDropList(String droplistID) {
		if (droplistID == null || droplistID.length() <= 0) return null;

		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!droplists.containsKey(droplistID)) {
				L.log("WARNING: Cannot find droplist \"" + droplistID + "\".");
			}
		}
		return droplists.get(droplistID);
	}

	public void initialize(final DropListParser parser, String input) {
		parser.parseRows(input, droplists);
	}

	// Unit test method. Not part of the game logic.
	public HashMap<String, DropList> UNITTEST_getAllDropLists() {
		return droplists;
	}
}

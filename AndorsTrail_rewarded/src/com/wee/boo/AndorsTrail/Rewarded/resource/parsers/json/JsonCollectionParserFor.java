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
package com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.util.L;
import com.wee.boo.AndorsTrail.Rewarded.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class JsonCollectionParserFor<T> extends JsonParserFor<Pair<String, T>> {
	public HashSet<String> parseRows(String input, HashMap<String, T> dest) {

		HashSet<String> ids = new HashSet<String>();
		ArrayList<Pair<String, T>> objects = new ArrayList<Pair<String, T>>();

		try {
			parseRows(new JSONArray(input), objects);
		} catch (JSONException e) {
			if (AndorsTrailApplication.DEVELOPMENT_DEBUGMESSAGES) {
				L.log("ERROR loading resource data: " + e.toString());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.close();
				sw.flush();
				L.log(sw.toString());
				L.log("Failing data: " + input);
			}
		}

		for (Pair<String, T> o : objects) {
			final String id = o.first;
			if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
				if (id == null || id.length() <= 0) {
					L.log("WARNING: Entity " + o.second.toString() + " has empty id.");
				} else if (dest.containsKey(id)) {
					L.log("WARNING: Entity " + id + " is duplicated.");
				}
			}
			dest.put(id, o.second);
			ids.add(id);
		}
		return ids;
	}
}

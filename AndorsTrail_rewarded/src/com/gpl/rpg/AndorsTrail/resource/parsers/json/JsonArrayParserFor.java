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
package com.gpl.rpg.AndorsTrail.resource.parsers.json;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class JsonArrayParserFor<T> extends JsonParserFor<T> {
	private final Class<T> classType;

	protected JsonArrayParserFor(Class<T> classType) {
		if (classType == null) throw new IllegalArgumentException("classType for parseArray must not be null");
		this.classType = classType;
	}

	public T[] parseArray(JSONArray array) throws JSONException {
		if (array == null) return null;
		final ArrayList<T> arrayList = new ArrayList<T>(array.length());
		parseRows(array, arrayList);
		if (arrayList.isEmpty()) return null;
		return arrayList.toArray(newArray(arrayList.size()));
	}

	@SuppressWarnings("unchecked")
	private T[] newArray(int size) {
		return (T[]) Array.newInstance(classType, size);
	}
}

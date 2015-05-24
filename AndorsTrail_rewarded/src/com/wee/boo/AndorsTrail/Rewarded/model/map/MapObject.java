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
package com.wee.boo.AndorsTrail.Rewarded.model.map;

import com.wee.boo.AndorsTrail.Rewarded.model.item.DropList;
import com.wee.boo.AndorsTrail.Rewarded.model.script.Requirement;
import com.wee.boo.AndorsTrail.Rewarded.util.CoordRect;

public final class MapObject {
	public static enum MapObjectType {
		sign
		,newmap
		,rest
		,keyarea
		,container
		,script
	}

	public static enum MapObjectEvaluationType {
		whenEntering
		,onEveryStep
		,afterEveryRound
		,continuously
	}

	public final CoordRect position;
	public final MapObjectType type;
	public final String id; //placeName on this map or phraseID
	public final String map;
	public final String place;
	private final String group;
	public final Requirement enteringRequirement;
	public final DropList dropList;
	public final MapObjectEvaluationType evaluateWhen;
	public boolean isActive;
	public final boolean isActiveForNewGame;

	private MapObject(
			final CoordRect position
			, final MapObjectType type
			, final String id
			, final String map
			, final String place
			, final Requirement enteringRequirement
			, final DropList dropList
			, final MapObjectEvaluationType evaluateWhen
			, final String group
			, final boolean isActiveForNewGame
	) {
		this.position = new CoordRect(position);
		this.type = type;
		this.id = id;
		this.map = map;
		this.place = place;
		this.enteringRequirement = enteringRequirement;
		this.dropList = dropList;
		this.evaluateWhen = evaluateWhen;
		this.group = group;
		this.isActiveForNewGame = isActiveForNewGame;
		this.isActive = isActiveForNewGame;
	}

	public void resetForNewGame() {
		isActive = isActiveForNewGame;
	}

	public static MapObject createMapSignEvent(
			final CoordRect position
			, final String phraseID
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.sign, phraseID, null, null, null, null, MapObjectEvaluationType.whenEntering, group, isActiveForNewGame);
	}

	public static MapObject createMapChangeArea(
			final CoordRect position
			, final String thisMapTitle
			, final String destinationMap
			, final String destinationPlace
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.newmap, thisMapTitle, destinationMap, destinationPlace, null, null, MapObjectEvaluationType.whenEntering, group, isActiveForNewGame);
	}

	public static MapObject createRestArea(
			final CoordRect position
			, final String placeId
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.rest, placeId, null, null, null, null, MapObjectEvaluationType.whenEntering, group, isActiveForNewGame);
	}

	public static MapObject createKeyArea(
			final CoordRect position
			, final String phraseID
			, final Requirement enteringRequirement
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.keyarea, phraseID, null, null, enteringRequirement, null, MapObjectEvaluationType.whenEntering, group, isActiveForNewGame);
	}

	public static MapObject createContainerArea(
			final CoordRect position
			, final DropList dropList
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.container, null, null, null, null, dropList, MapObjectEvaluationType.whenEntering, group, isActiveForNewGame);
	}

	public static MapObject createScriptArea(
			final CoordRect position
			, final String phraseID
			, final MapObjectEvaluationType evaluateWhen
			, String group
			, boolean isActiveForNewGame
	) {
		return new MapObject(position, MapObjectType.script, phraseID, null, null, null, null, evaluateWhen, group, isActiveForNewGame);
	}
}

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

import com.wee.boo.AndorsTrail.Rewarded.model.quest.QuestProgress;
import com.wee.boo.AndorsTrail.Rewarded.util.CoordRect;

public final class ReplaceableMapSection {
	public boolean isApplied = false;
	public final CoordRect replacementArea;
	public final MapSection replaceLayersWith;
	public final QuestProgress requireQuestStage;
	private final String group;

	public ReplaceableMapSection(
			CoordRect replacementArea
			, MapSection replaceLayersWith
			, QuestProgress requireQuestStage
			, String group
	) {
		this.replacementArea = replacementArea;
		this.replaceLayersWith = replaceLayersWith;
		this.requireQuestStage = requireQuestStage;
		this.group = group;
	}

	public void apply(MapSection dest) {
		dest.replaceLayerContentsWith(replaceLayersWith, replacementArea);
		isApplied = true;
	}
}

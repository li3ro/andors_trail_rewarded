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
package com.gpl.rpg.AndorsTrail.model.quest;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.resource.parsers.ResourceParserUtils;
import com.gpl.rpg.AndorsTrail.util.L;

public final class QuestProgress {
	public final String questID;
	public final int progress;
	public QuestProgress(String questID, int progress) {
		this.questID = questID;
		this.progress = progress;
	}

	public static QuestProgress parseQuestProgress(String v) {
		if (v == null || v.length() <= 0) return null;
		String[] parts = v.split(":");
		int requiresQuestProgress = 0;
		if (parts.length >= 2) {
			requiresQuestProgress = ResourceParserUtils.parseInt(parts[1], 0);
		} else if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			L.log("WARNING: Quest progress \"" + v + "\" does not specify any progress stage.");
		}
		final String requiresQuestID = parts[0];
		return new QuestProgress(requiresQuestID, requiresQuestProgress);
	}

	@Override
	public String toString() {
		return questID + ':' + progress;
	}
}

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
import com.gpl.rpg.AndorsTrail.model.actor.Player;
import com.gpl.rpg.AndorsTrail.util.L;

public final class Quest implements Comparable<Quest> {
	public final String questID;
	public final String name;
	public final QuestLogEntry[] stages; //Must be sorted in ascending stage order
	public final boolean showInLog;
	public final int sortOrder;

	public Quest(
			String questID
			, String name
			, QuestLogEntry[] stages
			, boolean showInLog
			, int sortOrder
	) {
		this.questID = questID;
		this.name = name;
		this.stages = stages;
		this.showInLog = showInLog;
		this.sortOrder = sortOrder;
	}

	public QuestLogEntry getQuestLogEntry(final int progress) {
		for (QuestLogEntry s : stages) {
			if (s.progress == progress) return s;
		}
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			L.log("WARNING: Cannot find stage " + progress + " in quest \"" + questID + "\".");
		}
		return null;
	}

	public boolean isCompleted(final Player player) {
		for (QuestLogEntry e : stages) {
			if (!e.finishesQuest) continue;
			if (player.hasExactQuestProgress(questID, e.progress)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Quest q) {
		return sortOrder - q.sortOrder;
	}
}

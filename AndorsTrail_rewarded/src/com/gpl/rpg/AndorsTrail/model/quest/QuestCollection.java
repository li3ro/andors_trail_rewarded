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
import com.gpl.rpg.AndorsTrail.resource.parsers.QuestParser;
import com.gpl.rpg.AndorsTrail.util.L;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public final class QuestCollection {
	private final HashMap<String, Quest> quests = new HashMap<String, Quest>();

	public Collection<Quest> getAllQuests() {
		ArrayList<Quest> quests = new ArrayList<Quest>(this.quests.values());
		Collections.sort(quests);
		return quests;
	}

	public QuestLogEntry getQuestLogEntry(final QuestProgress stage) {
		Quest q = getQuest(stage.questID);
		if (q == null) return null;
		return q.getQuestLogEntry(stage.progress);
	}

	public Quest getQuest(final String questID) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!quests.containsKey(questID)) {
				L.log("WARNING: Cannot find quest \"" + questID + "\".");
			}
		}
		return quests.get(questID);
	}

	public void initialize(QuestParser parser, String input) {
		parser.parseRows(input, quests);
	}
}

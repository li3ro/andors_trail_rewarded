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
package com.wee.boo.AndorsTrail.Rewarded.model.conversation;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.ConversationListParser;
import com.wee.boo.AndorsTrail.Rewarded.util.L;

import java.util.Collection;
import java.util.HashMap;

public final class ConversationCollection {
	public static final String PHRASE_CLOSE = "X";
	public static final String PHRASE_SHOP = "S";
	public static final String PHRASE_ATTACK = "F";
	public static final String PHRASE_REMOVE = "R";
	public static final String REPLY_NEXT = "N";

	private final HashMap<String, Phrase> phrases = new HashMap<String, Phrase>();

	public boolean hasPhrase(String id) {
		return phrases.containsKey(id);
	}
	public Phrase getPhrase(String id) {
		if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
			if (!phrases.containsKey(id)) {
				L.log("WARNING: Cannot find requested conversation phrase id \"" + id + "\".");
				return null;
			}
		}
		return phrases.get(id);
	}

	public Collection<String> initialize(ConversationListParser parser, String input) {
		return parser.parseRows(input, phrases);
	}

	// Unit test method. Not part of the game logic.
	public HashMap<String, Phrase> UNITTEST_getAllPhrases() {
		return phrases;
	}
}

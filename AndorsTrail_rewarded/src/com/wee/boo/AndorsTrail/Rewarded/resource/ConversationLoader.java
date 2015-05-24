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
package com.wee.boo.AndorsTrail.Rewarded.resource;

import android.content.res.Resources;

import com.wee.boo.AndorsTrail.Rewarded.model.conversation.ConversationCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.conversation.Phrase;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.ConversationListParser;

import java.util.Collection;
import java.util.HashMap;

public final class ConversationLoader {
	private final HashMap<String, Integer> resourceIDsPerPhraseID = new HashMap<String, Integer>();

	public void addIDs(int resourceId, Collection<String> ids) {
		for(String s : ids) resourceIDsPerPhraseID.put(s, resourceId);
	}

	public Phrase loadPhrase(String phraseID, ConversationCollection conversationCollection, Resources r) {
		if (conversationCollection.hasPhrase(phraseID)) {
			return conversationCollection.getPhrase(phraseID);
		}

		TranslationLoader translationLoader = new TranslationLoader(r.getAssets(), r);
		ConversationListParser conversationListParser = new ConversationListParser(translationLoader);
		int resourceID = resourceIDsPerPhraseID.get(phraseID);
		conversationCollection.initialize(conversationListParser, ResourceLoader.readStringFromRaw(r, resourceID));
		translationLoader.close();

		return conversationCollection.getPhrase(phraseID);
	}
}

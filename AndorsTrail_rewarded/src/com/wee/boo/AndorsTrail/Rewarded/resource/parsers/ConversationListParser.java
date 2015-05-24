/*
 * Copyright� 2015 Yaniv Bokobza
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
package com.wee.boo.AndorsTrail.Rewarded.resource.parsers;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.model.conversation.Phrase;
import com.wee.boo.AndorsTrail.Rewarded.model.conversation.Reply;
import com.wee.boo.AndorsTrail.Rewarded.model.script.Requirement;
import com.wee.boo.AndorsTrail.Rewarded.model.script.ScriptEffect;
import com.wee.boo.AndorsTrail.Rewarded.resource.TranslationLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonArrayParserFor;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonCollectionParserFor;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonFieldNames;
import com.wee.boo.AndorsTrail.Rewarded.util.L;
import com.wee.boo.AndorsTrail.Rewarded.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

public final class ConversationListParser extends JsonCollectionParserFor<Phrase> {

	private final TranslationLoader translationLoader;

	private final JsonArrayParserFor<Requirement> requirementParser = new JsonArrayParserFor<Requirement>(Requirement.class) {
		@Override
		protected Requirement parseObject(JSONObject o) throws JSONException {
			return new Requirement(
					Requirement.RequirementType.valueOf(o.getString(JsonFieldNames.ReplyRequires.requireType))
					,o.getString(JsonFieldNames.ReplyRequires.requireID)
					,o.optInt(JsonFieldNames.ReplyRequires.value, 0)
					,o.optBoolean(JsonFieldNames.ReplyRequires.negate, false)
			);
		}
	};

	private final JsonArrayParserFor<Reply> replyParser = new JsonArrayParserFor<Reply>(Reply.class) {
		@Override
		protected Reply parseObject(JSONObject o) throws JSONException {
			return new Reply(
					translationLoader.translateConversationReply(o.optString(JsonFieldNames.Reply.text, ""))
					,o.getString(JsonFieldNames.Reply.nextPhraseID)
					,requirementParser.parseArray(o.optJSONArray(JsonFieldNames.Reply.requires))
			);
		}
	};

	private final JsonArrayParserFor<ScriptEffect> scriptEffectParser = new JsonArrayParserFor<ScriptEffect>(ScriptEffect.class) {
		@Override
		protected ScriptEffect parseObject(JSONObject o) throws JSONException {
			return new ScriptEffect(
					ScriptEffect.ScriptEffectType.valueOf(o.getString(JsonFieldNames.PhraseReward.rewardType))
					,o.getString(JsonFieldNames.PhraseReward.rewardID)
					,o.optInt(JsonFieldNames.PhraseReward.value, 0)
					,o.optString(JsonFieldNames.PhraseReward.mapName, null)
			);
		}
	};

	public ConversationListParser(TranslationLoader translationLoader) {
		this.translationLoader = translationLoader;
	}

	@Override
	protected Pair<String, Phrase> parseObject(JSONObject o) throws JSONException {
		final String id = o.getString(JsonFieldNames.Phrase.phraseID);

		Reply[] _replies = null;
		ScriptEffect[] _scriptEffects = null;
		try {
			_replies = replyParser.parseArray(o.optJSONArray(JsonFieldNames.Phrase.replies));
			_scriptEffects = scriptEffectParser.parseArray(o.optJSONArray(JsonFieldNames.Phrase.rewards));
		} catch (JSONException e) {
			if (AndorsTrailApplication.DEVELOPMENT_DEBUGMESSAGES) {
				L.log("ERROR: parsing phrase " + id + " : " + e.getMessage());
			}
		}

		return new Pair<String, Phrase>(id, new Phrase(
				translationLoader.translateConversationPhrase(o.optString(JsonFieldNames.Phrase.message, null))
				, _replies
				, _scriptEffects
				, o.optString(JsonFieldNames.Phrase.switchToNPC, null)
		));
	}
}

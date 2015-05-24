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
package com.gpl.rpg.AndorsTrail.resource.parsers;

import com.gpl.rpg.AndorsTrail.model.ability.ActorConditionType;
import com.gpl.rpg.AndorsTrail.resource.DynamicTileLoader;
import com.gpl.rpg.AndorsTrail.resource.TranslationLoader;
import com.gpl.rpg.AndorsTrail.resource.parsers.json.JsonCollectionParserFor;
import com.gpl.rpg.AndorsTrail.resource.parsers.json.JsonFieldNames;
import com.gpl.rpg.AndorsTrail.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

public final class ActorConditionsTypeParser extends JsonCollectionParserFor<ActorConditionType> {

	private final DynamicTileLoader tileLoader;
	private final TranslationLoader translationLoader;

	public ActorConditionsTypeParser(final DynamicTileLoader tileLoader, TranslationLoader translationLoader) {
		this.tileLoader = tileLoader;
		this.translationLoader = translationLoader;
	}

	@Override
	protected Pair<String, ActorConditionType> parseObject(JSONObject o) throws JSONException {
		final String conditionTypeID = o.getString(JsonFieldNames.ActorCondition.conditionTypeID);
		ActorConditionType result = new ActorConditionType(
				conditionTypeID
				,translationLoader.translateActorConditionName(o.getString(JsonFieldNames.ActorCondition.name))
				,ResourceParserUtils.parseImageID(tileLoader, o.getString(JsonFieldNames.ActorCondition.iconID))
				,ActorConditionType.ConditionCategory.valueOf(o.getString(JsonFieldNames.ActorCondition.category))
				,o.optInt(JsonFieldNames.ActorCondition.isStacking) > 0
				,o.optInt(JsonFieldNames.ActorCondition.isPositive) > 0
				,ResourceParserUtils.parseStatsModifierTraits(o.optJSONObject(JsonFieldNames.ActorCondition.roundEffect))
				,ResourceParserUtils.parseStatsModifierTraits(o.optJSONObject(JsonFieldNames.ActorCondition.fullRoundEffect))
				,ResourceParserUtils.parseAbilityModifierTraits(o.optJSONObject(JsonFieldNames.ActorCondition.abilityEffect))
		);
		return new Pair<String, ActorConditionType>(conditionTypeID, result);
	}
}

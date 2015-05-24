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

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.model.ability.ActorCondition;
import com.gpl.rpg.AndorsTrail.model.ability.ActorConditionEffect;
import com.gpl.rpg.AndorsTrail.model.ability.ActorConditionTypeCollection;
import com.gpl.rpg.AndorsTrail.model.ability.traits.AbilityModifierTraits;
import com.gpl.rpg.AndorsTrail.model.ability.traits.StatsModifierTraits;
import com.gpl.rpg.AndorsTrail.model.item.ItemTraits_OnEquip;
import com.gpl.rpg.AndorsTrail.model.item.ItemTraits_OnUse;
import com.gpl.rpg.AndorsTrail.resource.parsers.json.JsonArrayParserFor;
import com.gpl.rpg.AndorsTrail.resource.parsers.json.JsonFieldNames;
import com.gpl.rpg.AndorsTrail.util.ConstRange;
import com.gpl.rpg.AndorsTrail.util.L;
import org.json.JSONException;
import org.json.JSONObject;

public final class ItemTraitsParser {
	private final JsonArrayParserFor<ActorConditionEffect> actorConditionEffectParser_withDuration;
	private final JsonArrayParserFor<ActorConditionEffect> actorConditionEffectParser_withoutDuration;

	public ItemTraitsParser(final ActorConditionTypeCollection actorConditionTypes) {
		this.actorConditionEffectParser_withDuration = new JsonArrayParserFor<ActorConditionEffect>(ActorConditionEffect.class) {
			@Override
			protected ActorConditionEffect parseObject(JSONObject o) throws JSONException {
				return new ActorConditionEffect(
						actorConditionTypes.getActorConditionType(o.getString(JsonFieldNames.ActorConditionEffect.condition))
						, o.optInt(JsonFieldNames.ActorConditionEffect.magnitude, ActorCondition.MAGNITUDE_REMOVE_ALL)
						, o.optInt(JsonFieldNames.ActorConditionEffect.duration, ActorCondition.DURATION_FOREVER)
						, ResourceParserUtils.parseChance(o.getString(JsonFieldNames.ActorConditionEffect.chance))
				);
			}
		};
		this.actorConditionEffectParser_withoutDuration = new JsonArrayParserFor<ActorConditionEffect>(ActorConditionEffect.class) {
			@Override
			protected ActorConditionEffect parseObject(JSONObject o) throws JSONException {
				return new ActorConditionEffect(
						actorConditionTypes.getActorConditionType(o.getString(JsonFieldNames.ActorConditionEffect.condition))
						, o.optInt(JsonFieldNames.ActorConditionEffect.magnitude, 1)
						, ActorCondition.DURATION_FOREVER
						, ResourceParserUtils.always
				);
			}
		};
	}

	public ItemTraits_OnUse parseItemTraits_OnUse(JSONObject o) throws JSONException {
		if (o == null) return null;

		ConstRange boostCurrentHP = ResourceParserUtils.parseConstRange(o.optJSONObject(JsonFieldNames.ItemTraits_OnUse.increaseCurrentHP));
		ConstRange boostCurrentAP = ResourceParserUtils.parseConstRange(o.optJSONObject(JsonFieldNames.ItemTraits_OnUse.increaseCurrentAP));
		ActorConditionEffect[] addedConditions_source = actorConditionEffectParser_withDuration.parseArray(o.optJSONArray(JsonFieldNames.ItemTraits_OnUse.conditionsSource));
		ActorConditionEffect[] addedConditions_target = actorConditionEffectParser_withDuration.parseArray(o.optJSONArray(JsonFieldNames.ItemTraits_OnUse.conditionsTarget));
		if (	   boostCurrentHP == null
				&& boostCurrentAP == null
				&& addedConditions_source == null
				&& addedConditions_target == null
			) {
			if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
				L.log("OPTIMIZE: Tried to parseItemTraits_OnUse , where hasEffect=" + o.toString() + ", but all data was empty.");
			}
			return null;
		} else {
			return new ItemTraits_OnUse(
					new StatsModifierTraits(
						null
						,boostCurrentHP
						,boostCurrentAP
					)
					,addedConditions_source
					,addedConditions_target
					);
		}
	}

	public ItemTraits_OnEquip parseItemTraits_OnEquip(JSONObject o) throws JSONException {
		if (o == null) return null;

		AbilityModifierTraits stats = ResourceParserUtils.parseAbilityModifierTraits(o);
		ActorConditionEffect[] addedConditions = actorConditionEffectParser_withoutDuration.parseArray(o.optJSONArray(JsonFieldNames.ItemTraits_OnEquip.addedConditions));

		if (stats == null && addedConditions == null) {
			if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
				L.log("OPTIMIZE: Tried to parseItemTraits_OnEquip , where hasEffect=" + o.toString() + ", but all data was empty.");
			}
			return null;
		} else {
			return new ItemTraits_OnEquip(stats, addedConditions);
		}
	}
}

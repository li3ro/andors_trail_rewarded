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
package com.wee.boo.AndorsTrail.Rewarded.resource.parsers;

import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionTypeCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemCategoryCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemTraits_OnEquip;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemTraits_OnUse;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemType;
import com.wee.boo.AndorsTrail.Rewarded.resource.DynamicTileLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.TranslationLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonCollectionParserFor;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonFieldNames;
import com.wee.boo.AndorsTrail.Rewarded.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

public final class ItemTypeParser extends JsonCollectionParserFor<ItemType> {

	private final DynamicTileLoader tileLoader;
	private final TranslationLoader translationLoader;
	private final ItemTraitsParser itemTraitsParser;
	private final ItemCategoryCollection itemCategories;

	public ItemTypeParser(
			DynamicTileLoader tileLoader,
			ActorConditionTypeCollection actorConditionsTypes,
			ItemCategoryCollection itemCategories,
			TranslationLoader translationLoader) {
		this.tileLoader = tileLoader;
		this.translationLoader = translationLoader;
		this.itemTraitsParser = new ItemTraitsParser(actorConditionsTypes);
		this.itemCategories = itemCategories;
	}

	@Override
	public Pair<String, ItemType> parseObject(JSONObject o) throws JSONException {
		final String id = o.getString(JsonFieldNames.ItemType.itemTypeID);
		final String itemTypeName = translationLoader.translateItemTypeName(o.getString(JsonFieldNames.ItemType.name));
		final String description = translationLoader.translateItemTypeDescription(o.optString(JsonFieldNames.ItemType.description, null));
		final ItemTraits_OnEquip equipEffect = itemTraitsParser.parseItemTraits_OnEquip(o.optJSONObject(JsonFieldNames.ItemType.equipEffect));
		final ItemTraits_OnUse useEffect = itemTraitsParser.parseItemTraits_OnUse(o.optJSONObject(JsonFieldNames.ItemType.useEffect));
		final ItemTraits_OnUse hitEffect = itemTraitsParser.parseItemTraits_OnUse(o.optJSONObject(JsonFieldNames.ItemType.hitEffect));
		final ItemTraits_OnUse killEffect = itemTraitsParser.parseItemTraits_OnUse(o.optJSONObject(JsonFieldNames.ItemType.killEffect));

		final int baseMarketCost = o.optInt(JsonFieldNames.ItemType.baseMarketCost);
		final boolean hasManualPrice = o.optInt(JsonFieldNames.ItemType.hasManualPrice, 0) > 0;
		final ItemType itemType = new ItemType(
				id
				, ResourceParserUtils.parseImageID(tileLoader, o.getString(JsonFieldNames.ItemType.iconID))
				, itemTypeName
				, description
				, itemCategories.getItemCategory(o.getString(JsonFieldNames.ItemType.category))
				, ItemType.DisplayType.fromString(o.optString(JsonFieldNames.ItemType.displaytype, null), ItemType.DisplayType.ordinary)
				, hasManualPrice
				, baseMarketCost
				, equipEffect
				, useEffect
				, hitEffect
				, killEffect
			);
		return new Pair<String, ItemType>(id, itemType);
	}
}

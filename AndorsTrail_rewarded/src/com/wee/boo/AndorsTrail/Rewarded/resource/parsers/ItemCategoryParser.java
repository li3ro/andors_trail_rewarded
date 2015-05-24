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

import com.wee.boo.AndorsTrail.Rewarded.model.item.Inventory;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemCategory;
import com.wee.boo.AndorsTrail.Rewarded.resource.TranslationLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonCollectionParserFor;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonFieldNames;
import com.wee.boo.AndorsTrail.Rewarded.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

public final class ItemCategoryParser extends JsonCollectionParserFor<ItemCategory> {

	private final TranslationLoader translationLoader;

	public ItemCategoryParser(TranslationLoader translationLoader) {

		this.translationLoader = translationLoader;
	}

	@Override
	protected Pair<String, ItemCategory> parseObject(JSONObject o) throws JSONException {
		final String id = o.getString(JsonFieldNames.ItemCategory.itemCategoryID);
		ItemCategory result = new ItemCategory(
				id
				, translationLoader.translateItemCategoryName(o.getString(JsonFieldNames.ItemCategory.name))
				, ItemCategory.ActionType.fromString(o.optString(JsonFieldNames.ItemCategory.actionType, null), ItemCategory.ActionType.none)
				, Inventory.WearSlot.fromString(o.optString(JsonFieldNames.ItemCategory.inventorySlot, null), null)
				, ItemCategory.ItemCategorySize.fromString(o.optString(JsonFieldNames.ItemCategory.size, null), ItemCategory.ItemCategorySize.none)
		);
		return new Pair<String, ItemCategory>(id, result);
	}
}

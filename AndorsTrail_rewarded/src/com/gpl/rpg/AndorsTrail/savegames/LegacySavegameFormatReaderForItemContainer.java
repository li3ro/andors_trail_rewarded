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
package com.gpl.rpg.AndorsTrail.savegames;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.model.item.Inventory;
import com.gpl.rpg.AndorsTrail.model.item.ItemContainer;
import com.gpl.rpg.AndorsTrail.model.item.ItemContainer.ItemEntry;
import com.gpl.rpg.AndorsTrail.model.item.ItemType;
import com.gpl.rpg.AndorsTrail.model.item.Loot;
import com.gpl.rpg.AndorsTrail.util.L;

public final class LegacySavegameFormatReaderForItemContainer {
	public static void refundUpgradedItems(Inventory inventory) {
		inventory.gold += getRefundForUpgradedItems(inventory);
	}
	public static void refundUpgradedItems(Loot loot) {
		loot.gold += getRefundForUpgradedItems(loot.items);
	}

	private static int getRefundForUpgradedItems(ItemContainer container) {
		int removedCost = 0;
		for (ItemEntry e : container.items) {
			if (e.quantity >= 2 && isRefundableItem(e.itemType)) {
				if (AndorsTrailApplication.DEVELOPMENT_DEBUGMESSAGES) {
					L.log("INFO: Refunding " + (e.quantity-1) + " items of type \"" + e.itemType.id + "\" for a total of " + ((e.quantity-1) * e.itemType.fixedBaseMarketCost) + "gc.");
				}
				removedCost += (e.quantity-1) * e.itemType.fixedBaseMarketCost;
				e.quantity = 1;
			}
		}
		return removedCost;
	}

	private static boolean isRefundableItem(ItemType itemType) {
		if (itemType.hasManualPrice) return false;
		if (itemType.isQuestItem()) return false;
		if (itemType.displayType == ItemType.DisplayType.extraordinary) return false;
		if (itemType.displayType == ItemType.DisplayType.legendary) return false;
		return itemType.baseMarketCost > itemType.fixedBaseMarketCost;
	}
}

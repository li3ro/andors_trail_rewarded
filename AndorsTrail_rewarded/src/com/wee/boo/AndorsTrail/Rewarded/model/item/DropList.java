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
package com.wee.boo.AndorsTrail.Rewarded.model.item;

import com.wee.boo.AndorsTrail.Rewarded.controller.Constants;
import com.wee.boo.AndorsTrail.Rewarded.controller.SkillController;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;
import com.wee.boo.AndorsTrail.Rewarded.util.ConstRange;

public final class DropList {
	private final DropItem[] items;

	public DropList(DropItem[] items) {
		this.items = items;
	}
	public void createRandomLoot(Loot loot, Player player) {
		for (DropItem item : items) {

			final int chanceRollBias = SkillController.getDropChanceRollBias(item, player);
			if (Constants.rollResult(item.chance, chanceRollBias)) {

				final int quantityRollBias = SkillController.getDropQuantityRollBias(item, player);
				int quantity = Constants.rollValue(item.quantity, quantityRollBias);

				loot.add(item.itemType, quantity);
			}
		}
	}

	// Unit test method. Not part of the game logic.
	public DropItem[] UNITTEST_getAllDropItems() {
		return items;
	}

	public static class DropItem {
		public final ItemType itemType;
		public final ConstRange chance;
		public final ConstRange quantity;
		public DropItem(ItemType itemType, ConstRange chance, ConstRange quantity) {
			this.itemType = itemType;
			this.chance = chance;
			this.quantity = quantity;
		}
	}
}

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

public final class ItemCategory {

	public static enum ItemCategorySize {
		none, light, std, large;

		public static ItemCategorySize fromString(String s, ItemCategorySize default_) {
			if (s == null) return default_;
			return valueOf(s);
		}
	}

	public final String id;
	public final String displayName;
	public final Inventory.WearSlot inventorySlot;
	private final ActionType actionType;
	private final ItemCategorySize size;

	public ItemCategory(
			String id
			, String displayName
			, ActionType actionType
			, Inventory.WearSlot inventorySlot
			, ItemCategorySize size
	) {
		this.id = id;
		this.displayName = displayName;
		this.inventorySlot = inventorySlot;
		this.size = size;
		this.actionType = actionType;
	}

	public static enum ActionType {
		none, use, equip;

		public static ActionType fromString(String s, ActionType default_) {
			if (s == null) return default_;
			return valueOf(s);
		}
	}
	public ItemCategorySize getSize() { return size; }
	public boolean isEquippable() { return actionType == ActionType.equip; }
	public boolean isUsable() { return actionType == ActionType.use; }
	public boolean isWeapon() { return inventorySlot == Inventory.WearSlot.weapon; }
	public boolean isShield() { return inventorySlot == Inventory.WearSlot.shield; }
	public boolean isArmor() { return Inventory.isArmorSlot(inventorySlot); }
	public boolean isTwohandWeapon() {
		if (!isWeapon()) return false;
		else if (size == ItemCategorySize.large) return true;
		else return false;
	}
	public boolean isOffhandCapableWeapon() {
		if (!isWeapon()) return false;
		else if (size == ItemCategorySize.light) return true;
		else if (size == ItemCategorySize.std) return true;
		else return false;
	}
}

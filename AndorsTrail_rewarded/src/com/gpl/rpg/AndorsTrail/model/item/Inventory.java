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
package com.gpl.rpg.AndorsTrail.model.item;

import com.gpl.rpg.AndorsTrail.context.WorldContext;
import com.gpl.rpg.AndorsTrail.savegames.LegacySavegameFormatReaderForItemContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Inventory extends ItemContainer {

	public static enum WearSlot {
		weapon
		,shield
		,head
		,body
		,hand
		,feet
		,neck
		,leftring
		,rightring;
		public static WearSlot fromString(String s, WearSlot default_) {
			if (s == null) return default_;
			return valueOf(s);
		}
	}

	public int gold = 0;
	private static final int NUM_WORN_SLOTS = WearSlot.values().length;
	public static final int NUM_QUICK_SLOTS = 3;
	private final ItemType[] wear = new ItemType[NUM_WORN_SLOTS];
	public final ItemType[] quickitem = new ItemType[NUM_QUICK_SLOTS];

	public Inventory() { }

	public void clear() {
		for(int i = 0; i < NUM_WORN_SLOTS; ++i) wear[i] = null;
		for(int i = 0; i < NUM_QUICK_SLOTS; ++i) quickitem[i] = null;
		gold = 0;
		items.clear();
	}

	public void add(final Loot loot) {
		this.gold += loot.gold;
		this.add(loot.items);
	}

	public boolean isEmptySlot(WearSlot slot) {
		return wear[slot.ordinal()] == null;
	}

	public ItemType getItemTypeInWearSlot(WearSlot slot) {
		return wear[slot.ordinal()];
	}
	public void setItemTypeInWearSlot(WearSlot slot, ItemType type) {
		wear[slot.ordinal()] = type;
	}

	public boolean isWearing(String itemTypeID) {
		for(int i = 0; i < NUM_WORN_SLOTS; ++i) {
			if (wear[i] == null) continue;
			if (wear[i].id.equals(itemTypeID)) return true;
		}
		return false;
	}

	public boolean isWearing(String itemTypeID, int minNumber) {
		if (minNumber == 0) return isWearing(itemTypeID);
		for(int i = 0; i < NUM_WORN_SLOTS; ++i) {
			if (wear[i] == null) continue;
			if (wear[i].id.equals(itemTypeID)) minNumber--;
		}
		return minNumber <= 0;
	}

	public static boolean isArmorSlot(WearSlot slot) {
		if (slot == null) return false;
		switch (slot) {
			case head:
			case body:
			case hand:
			case feet:
				return true;
			default:
				return false;
		}
	}


	// ====== PARCELABLE ===================================================================

	public Inventory(DataInputStream src, WorldContext world, int fileversion) throws IOException {
		this.readFromParcel(src, world, fileversion);
	}

	@Override
	public void readFromParcel(DataInputStream src, WorldContext world, int fileversion) throws IOException {
		super.readFromParcel(src, world, fileversion);
		gold = src.readInt();

		if (fileversion < 23) LegacySavegameFormatReaderForItemContainer.refundUpgradedItems(this);

		for(int i = 0; i < NUM_WORN_SLOTS; ++i) {
			wear[i] = null;
		}
		final int numWornSlots = src.readInt();
		for(int i = 0; i < numWornSlots; ++i) {
			if (src.readBoolean()) {
				wear[i] = world.itemTypes.getItemType(src.readUTF());
			}
		}
		for(int i = 0; i < NUM_QUICK_SLOTS; ++i) {
			quickitem[i] = null;
		}
		if (fileversion >= 19) {
			final int quickSlots = src.readInt();
			for(int i = 0; i < quickSlots; ++i) {
				if (src.readBoolean()) {
					quickitem[i] = world.itemTypes.getItemType(src.readUTF());
				}
			}
		}
	}

	@Override
	public void writeToParcel(DataOutputStream dest) throws IOException {
		super.writeToParcel(dest);
		dest.writeInt(gold);
		dest.writeInt(NUM_WORN_SLOTS);
		for(int i = 0; i < NUM_WORN_SLOTS; ++i) {
			if (wear[i] != null) {
				dest.writeBoolean(true);
				dest.writeUTF(wear[i].id);
			} else {
				dest.writeBoolean(false);
			}
		}
		dest.writeInt(NUM_QUICK_SLOTS);
		for(int i = 0; i < NUM_QUICK_SLOTS; ++i) {
			if (quickitem[i] != null) {
				dest.writeBoolean(true);
				dest.writeUTF(quickitem[i].id);
			} else {
				dest.writeBoolean(false);
			}
		}
	}
}

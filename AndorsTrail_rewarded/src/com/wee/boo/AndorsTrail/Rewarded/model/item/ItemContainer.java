/*
 * Copyrightę 2015 Yaniv Bokobza
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

import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ItemContainer {
	public final ArrayList<ItemEntry> items = new ArrayList<ItemEntry>();

	public ItemContainer() {}

	public int countItems() {
		int result = 0;
		for (ItemEntry i : items) {
			result += i.quantity;
		}
		return result;
	}

	public static final class ItemEntry {
		public final ItemType itemType;
		public int quantity;
		public ItemEntry(ItemType itemType, int initialQuantity) {
			this.itemType = itemType;
			this.quantity = initialQuantity;
		}

		// ====== PARCELABLE ===================================================================

		public ItemEntry(DataInputStream src, WorldContext world, int fileversion) throws IOException {
			this.itemType = world.itemTypes.getItemType(src.readUTF());
			this.quantity = src.readInt();
		}

		public void writeToParcel(DataOutputStream dest) throws IOException {
			dest.writeUTF(itemType.id);
			dest.writeInt(quantity);
		}
	}

	public void addItem(ItemType itemType, int quantity) {
		if (quantity == 0) return;

		ItemEntry e = findItem(itemType.id);
		if (e != null) {
			e.quantity += quantity;
		} else {
			items.add(new ItemEntry(itemType, quantity));
		}
	}
	public void addItem(ItemType itemType) { addItem(itemType, 1); }
	public void add(final ItemContainer items) {
		for (ItemEntry e : items.items) {
			addItem(e.itemType, e.quantity);
		}
	}
	public boolean isEmpty() { return items.isEmpty(); }

	public boolean removeItem(String itemTypeID) { return removeItem(itemTypeID, 1); }
	public boolean removeItem(String itemTypeID, int quantity) {
		int index = -1;
		ItemEntry e = null;
		for (int i = 0; i < items.size(); ++i) {
			e = items.get(i);
			if (e.itemType.id.equals(itemTypeID)) {
				index = i;
				break;
			}
		}
		if (index < 0) return false;
		if (e.quantity == quantity) {
			items.remove(index);
		} else if (e.quantity > quantity) {
			e.quantity -= quantity;
		} else {
			return false;
		}
		return true;
	}

	public ItemEntry findItem(String itemTypeID) {
		for (ItemEntry e : items) {
			if (e.itemType.id.equals(itemTypeID)) return e;
		}
		return null;
	}
	public int findItemIndex(String itemTypeID) {
		for (int i = 0; i < items.size(); ++i) {
			if (items.get(i).itemType.id.equals(itemTypeID)) return i;
		}
		return -1;
	}
	public boolean hasItem(String itemTypeID) { return findItem(itemTypeID) != null; }
	public boolean hasItem(String itemTypeID, int minimumQuantity) {
		return getItemQuantity(itemTypeID) >= minimumQuantity;
	}

	public int getItemQuantity(String itemTypeID) {
		ItemEntry e = findItem(itemTypeID);
		if (e == null) return 0;
		return e.quantity;
	}

	public void sortToTop(String itemTypeID) {
		int i = findItemIndex(itemTypeID);
		if (i <= 0) return;
		items.add(0, items.remove(i));
	}

	public void sortToBottom(String itemTypeID) {
		int i = findItemIndex(itemTypeID);
		if (i < 0) return;
		items.add(items.remove(i));
	}


	// ====== PARCELABLE ===================================================================

	public static ItemContainer newFromParcel(DataInputStream src, WorldContext world, int fileversion) throws IOException {
		ItemContainer result = new ItemContainer();
		result.readFromParcel(src, world, fileversion);
		return result;
	}

	protected void readFromParcel(DataInputStream src, WorldContext world, int fileversion) throws IOException {
		items.clear();
		final int size = src.readInt();
		for(int i = 0; i < size; ++i) {
			ItemEntry entry = new ItemEntry(src, world, fileversion);
			if (entry.itemType != null) items.add(entry);
		}
	}

	public void writeToParcel(DataOutputStream dest) throws IOException {
		dest.writeInt(items.size());
		for (ItemEntry e : items) {
			e.writeToParcel(dest);
		}
	}
}

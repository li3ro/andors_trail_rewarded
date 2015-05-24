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
package com.gpl.rpg.AndorsTrail.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import com.gpl.rpg.AndorsTrail.Dialogs;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.activity.ItemInfoActivity;
import com.gpl.rpg.AndorsTrail.controller.ItemController;
import com.gpl.rpg.AndorsTrail.model.item.ItemType;

public final class ShopActivity_Sell extends ShopActivityFragment {

	@Override
	protected boolean isSellingInterface() {
		return true;
	}

	@Override
	public void onItemActionClicked(int position, ItemType itemType) {
		showSellingInterface(itemType);
	}

	@Override
	public void onItemInfoClicked(int position, ItemType itemType) {
		int price = ItemController.getSellingPrice(player, itemType);
		boolean enableButton = ItemController.maySellItem(player, itemType);
		String text = getResources().getString(R.string.shop_sellitem, price);
		Intent intent = Dialogs.getIntentForItemInfo(getActivity(), itemType.id, ItemInfoActivity.ItemInfoAction.sell, text, enableButton, null);
		startActivityForResult(intent, INTENTREQUEST_ITEMINFO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) return;

		ItemType itemType = world.itemTypes.getItemType(data.getExtras().getString("itemTypeID"));
		switch (requestCode) {
		case INTENTREQUEST_ITEMINFO:
			showSellingInterface(itemType);
			break;
		case INTENTREQUEST_BULKSELECT:
			int quantity = data.getExtras().getInt("selectedAmount");
			sell(itemType, quantity);
			break;
		}
		update();
	}

	private void showSellingInterface(ItemType itemType) {
		Intent intent = Dialogs.getIntentForBulkSellingInterface(getActivity(), itemType.id, player.inventory.getItemQuantity(itemType.id));
		startActivityForResult(intent, INTENTREQUEST_BULKSELECT);
	}

	private void sell(ItemType itemType, int quantity) {
		if (!ItemController.sell(player, itemType, shopInventory, quantity)) return;
		final String msg = getResources().getString(R.string.shop_item_sold, itemType.getName(player));
		displayStoreAction(msg);
	}
}

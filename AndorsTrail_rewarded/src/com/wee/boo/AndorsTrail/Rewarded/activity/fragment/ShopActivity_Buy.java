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
package com.wee.boo.AndorsTrail.Rewarded.activity.fragment;

import android.app.Activity;
import android.content.Intent;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.activity.ItemInfoActivity;
import com.wee.boo.AndorsTrail.Rewarded.controller.ItemController;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemType;

public final class ShopActivity_Buy extends ShopActivityFragment {

	@Override
	protected boolean isSellingInterface() {
		return false;
	}

	@Override
	public void onItemActionClicked(int position, ItemType itemType) {
		showBuyingInterface(itemType);
	}

	@Override
	public void onItemInfoClicked(int position, ItemType itemType) {
		int price = ItemController.getBuyingPrice(player, itemType);
		boolean enableButton = ItemController.canAfford(player, price);
		String text = getResources().getString(R.string.shop_buyitem, price);
		Intent intent = Dialogs.getIntentForItemInfo(getActivity(), itemType.id, ItemInfoActivity.ItemInfoAction.buy, text, enableButton, null);
		startActivityForResult(intent, INTENTREQUEST_ITEMINFO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) return;

		ItemType itemType = world.itemTypes.getItemType(data.getExtras().getString("itemTypeID"));
		switch (requestCode) {
		case INTENTREQUEST_ITEMINFO:
			showBuyingInterface(itemType);
			break;
		case INTENTREQUEST_BULKSELECT:
			int quantity = data.getExtras().getInt("selectedAmount");
			buy(itemType, quantity);
			break;
		}
		update();
	}

	private void showBuyingInterface(ItemType itemType) {
		Intent intent = Dialogs.getIntentForBulkBuyingInterface(getActivity(), itemType.id, shopInventory.getItemQuantity(itemType.id));
		startActivityForResult(intent, INTENTREQUEST_BULKSELECT);
	}

	private void buy(ItemType itemType, int quantity) {
		if (!ItemController.buy(world.model, player, itemType, shopInventory, quantity)) return;
		final String msg = getResources().getString(R.string.shop_item_bought, itemType.getName(player));
		displayStoreAction(msg);
	}
}

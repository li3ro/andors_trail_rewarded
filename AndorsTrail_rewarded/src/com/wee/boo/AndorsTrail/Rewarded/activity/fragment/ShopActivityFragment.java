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

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemContainer;
import com.wee.boo.AndorsTrail.Rewarded.resource.tiles.TileCollection;
import com.wee.boo.AndorsTrail.Rewarded.view.ShopItemContainerAdapter;
import com.wee.boo.AndorsTrail.Rewarded.view.ShopItemContainerAdapter.OnContainerItemClickedListener;

import java.util.HashSet;

public abstract class ShopActivityFragment extends Fragment implements OnContainerItemClickedListener {

	protected static final int INTENTREQUEST_ITEMINFO = 3;
	protected static final int INTENTREQUEST_BULKSELECT = 9;

	protected WorldContext world;
	protected Player player;

	protected ItemContainer shopInventory;
	private TextView shop_gc;
	private ShopItemContainerAdapter listAdapter;

	protected abstract boolean isSellingInterface();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(getActivity());
		if (!app.isInitialized()) return;
		this.world = app.getWorld();
		this.player = world.model.player;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.shoplist, container, false);

		final Monster npc = Dialogs.getMonsterFromIntent(getActivity().getIntent(), world);

		final Resources res = getResources();

		shop_gc = (TextView) v.findViewById(R.id.shop_gc);

		ListView shoplist = (ListView) v.findViewById(R.id.shop_list);

		shopInventory = npc.getShopItems(player);

		HashSet<Integer> iconIDs = world.tileManager.getTileIDsFor(shopInventory);
		iconIDs.addAll(world.tileManager.getTileIDsFor(player.inventory));
		TileCollection tiles = world.tileManager.loadTilesFor(iconIDs, res);
		final boolean isSelling = isSellingInterface();
		listAdapter = new ShopItemContainerAdapter(getActivity(), tiles, world.tileManager, player, isSelling ? player.inventory : shopInventory, this, isSelling);
		shoplist.setAdapter(listAdapter);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		update();
	}

	private Toast lastToast = null;
	protected void displayStoreAction(final String msg) {
		if (lastToast != null) {
			lastToast.setText(msg);
		} else {
			lastToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
		}
		lastToast.show();
		update();
	}

	@Override
	public void onPause() {
		super.onPause();
		lastToast = null;
	}

	protected void update() {
		listAdapter.notifyDataSetChanged();
		String gc = getResources().getString(R.string.shop_yourgold, player.getGold());
		shop_gc.setText(gc);
	}
}

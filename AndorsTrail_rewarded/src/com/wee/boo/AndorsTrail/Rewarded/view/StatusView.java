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
package com.wee.boo.AndorsTrail.Rewarded.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.activity.HeroinfoActivity;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.controller.listeners.ActorStatsListener;
import com.wee.boo.AndorsTrail.Rewarded.controller.listeners.PlayerStatsListener;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Actor;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;
import com.wee.boo.AndorsTrail.Rewarded.resource.tiles.TileManager;

public final class StatusView extends RelativeLayout implements PlayerStatsListener, ActorStatsListener {

	private final ControllerContext controllers;
	private final WorldContext world;
	private final Player player;

	private final RangeBar healthBar;
	private final RangeBar expBar;
	private final ImageButton heroImage;
	private boolean showingLevelup;
	private final Drawable levelupDrawable;

	public StatusView(final Context context, AttributeSet attr) {
		super(context, attr);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivityContext(context);
		this.controllers = app.getControllerContext();
		this.world = app.getWorld();
		this.player = world.model.player;

		setFocusable(false);
		inflate(context, R.layout.statusview, this);
		this.setBackgroundResource(R.drawable.ui_gradientshape);

		heroImage = (ImageButton) findViewById(R.id.status_image);
		showingLevelup = true;

		heroImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				context.startActivity(new Intent(context, HeroinfoActivity.class));
			}
		});
		healthBar = (RangeBar) findViewById(R.id.statusview_health);
		healthBar.init(R.drawable.ui_progress_health, R.string.status_hp);

		expBar = (RangeBar) findViewById(R.id.statusview_exp);
		expBar.init(R.drawable.ui_progress_exp, R.string.status_exp);

		Resources res = getResources();
		levelupDrawable = new LayerDrawable(new Drawable[] {
				new BitmapDrawable(res, world.tileManager.preloadedTiles.getBitmap(player.iconID))
				,new BitmapDrawable(res, world.tileManager.preloadedTiles.getBitmap(TileManager.iconID_moveselect))
		});

		updateStatus();
		updateIcon(player.canLevelup());
	}

	public void registerToolboxViews(ToolboxView toolbox, QuickitemView quickitemView) {
		toolbox.registerToolboxViews((ImageButton) findViewById(R.id.toolbox_toggle), quickitemView);
	}

	public void updateStatus() {
		updateHealth();
		updateExperience();
	}

	public void subscribe() {
		controllers.actorStatsController.actorStatsListeners.add(this);
		controllers.actorStatsController.playerStatsListeners.add(this);
	}
	public void unsubscribe() {
		controllers.actorStatsController.playerStatsListeners.remove(this);
		controllers.actorStatsController.actorStatsListeners.remove(this);
	}

	private void updateHealth() {
		healthBar.update(player.getMaxHP(), player.getCurrentHP());
	}
	private void updateExperience() {
		expBar.update(player.getMaxLevelExperience(), player.getCurrentLevelExperience());
		boolean canLevelUp = player.canLevelup();
		if (showingLevelup != canLevelUp) {
			updateIcon(canLevelUp);
		}
	}

	private void updateIcon(boolean canLevelUp) {
		showingLevelup = canLevelUp;
		if (canLevelUp) {
			heroImage.setImageDrawable(levelupDrawable);
		} else {
			world.tileManager.setImageViewTile(heroImage, player);
		}
	}

	@Override
	public void onActorHealthChanged(Actor actor) {
		if (actor == player) updateHealth();
	}

	@Override
	public void onActorAPChanged(Actor actor) { }

	@Override
	public void onActorAttackCostChanged(Actor actor, int newAttackCost) { }

	@Override
	public void onActorMoveCostChanged(Actor actor, int newMoveCost) { }

	@Override
	public void onPlayerExperienceChanged(Player p) {
		updateExperience();
	}
}

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
package com.wee.boo.AndorsTrail.Rewarded.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.controller.ActorStatsController;
import com.wee.boo.AndorsTrail.Rewarded.controller.Constants;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;

public final class LevelUpActivity extends Activity {
	private WorldContext world;
	private ControllerContext controllers;
	private Player player;
	private TextView levelup_description;
	private TextView levelup_title;
	private View levelup_adds_new_skillpoint;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		if (!app.isInitialized()) { finish(); return; }
		this.world = app.getWorld();
		this.controllers = app.getControllerContext();
		this.player = world.model.player;

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.levelup);

		levelup_title = (TextView) findViewById(R.id.levelup_title);
		levelup_description = (TextView) findViewById(R.id.levelup_description);
		levelup_adds_new_skillpoint = findViewById(R.id.levelup_adds_new_skillpoint);

		Button b;

		b = (Button) findViewById(R.id.levelup_add_health);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				levelup(ActorStatsController.LevelUpSelection.health);
			}
		});
		b.setText(getString(R.string.levelup_add_health, Constants.LEVELUP_EFFECT_HEALTH));

		b = (Button) findViewById(R.id.levelup_add_attackchance);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				levelup(ActorStatsController.LevelUpSelection.attackChance);
			}
		});
		b.setText(getString(R.string.levelup_add_attackchance, Constants.LEVELUP_EFFECT_ATK_CH));

		b = (Button) findViewById(R.id.levelup_add_attackdamage);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				levelup(ActorStatsController.LevelUpSelection.attackDamage);
			}
		});
		b.setText(getString(R.string.levelup_add_attackdamage, Constants.LEVELUP_EFFECT_ATK_DMG));

		b = (Button) findViewById(R.id.levelup_add_blockchance);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				levelup(ActorStatsController.LevelUpSelection.blockChance);
			}
		});
		b.setText(getString(R.string.levelup_add_blockchance, Constants.LEVELUP_EFFECT_DEF_CH));
	}

	@Override
	protected void onResume() {
		super.onResume();
		final Resources res = getResources();

		if (!player.canLevelup()) {
			this.finish();
			return;
		}

		world.tileManager.setImageViewTile(res, levelup_title, player);
		levelup_description.setText(res.getString(R.string.levelup_description, player.getLevel() + 1));
		if (player.nextLevelAddsNewSkillpoint()) {
			levelup_adds_new_skillpoint.setVisibility(View.VISIBLE);
		} else {
			levelup_adds_new_skillpoint.setVisibility(View.GONE);
		}
	}

	public void levelup(ActorStatsController.LevelUpSelection selectionID) {
		if (LevelUpActivity.this.isFinishing()) return;

		controllers.actorStatsController.addLevelupEffect(player, selectionID);
		finish();
	}
}

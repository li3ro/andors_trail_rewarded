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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.view.ItemEffectsView;
import com.wee.boo.AndorsTrail.Rewarded.view.RangeBar;
import com.wee.boo.AndorsTrail.Rewarded.view.TraitsInfoView;

public final class MonsterInfoActivity extends Activity {

	private WorldContext world;
	private ControllerContext controllers;

	private TextView monsterinfo_title;
	private TextView monsterinfo_difficulty;
	private ItemEffectsView monsterinfo_onhiteffects;
	private RangeBar hp;
	private ViewGroup monsterinfo_container;
	private TextView monsterinfo_max_ap;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		if (!app.isInitialized()) { finish(); return; }
		this.world = app.getWorld();
		this.controllers = app.getControllerContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.monsterinfo);

		monsterinfo_title = (TextView) findViewById(R.id.monsterinfo_title);
		monsterinfo_difficulty = (TextView) findViewById(R.id.monsterinfo_difficulty);
		monsterinfo_max_ap = (TextView) findViewById(R.id.monsterinfo_max_ap);

		Button b = (Button) findViewById(R.id.monsterinfo_close);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MonsterInfoActivity.this.finish();
			}
		});

		monsterinfo_onhiteffects = (ItemEffectsView) findViewById(R.id.actorinfo_onhiteffects);
		hp = (RangeBar) findViewById(R.id.monsterinfo_healthbar);
		hp.init(R.drawable.ui_progress_health, R.string.status_hp);
		monsterinfo_container = (ViewGroup) findViewById(R.id.monsterinfo_container);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Monster monster = Dialogs.getMonsterFromIntent(getIntent(), world);
		if (monster == null) {
			finish();
			return;
		}

		updateTitle(monster);
		updateTraits(monster);
	}

	private void updateTitle(Monster monster) {
		monsterinfo_title.setText(monster.getName());
		world.tileManager.setImageViewTile(getResources(), monsterinfo_title, monster);
		monsterinfo_difficulty.setText(getMonsterDifficultyResource(controllers, monster));
	}

	private void updateTraits(Monster monster) {
		TraitsInfoView.update(monsterinfo_container, monster);
		monsterinfo_onhiteffects.update(
				null,
				null,
				monster.getOnHitEffectsAsList(),
				null,
				false);
		hp.update(monster.getMaxHP(), monster.getCurrentHP());
		monsterinfo_max_ap.setText(Integer.toString(monster.getMaxAP()));
	}

	public static int getMonsterDifficultyResource(ControllerContext controllerContext, Monster monster) {
		final int difficulty = controllerContext.combatController.getMonsterDifficulty(monster);
		if (difficulty >= 80) return R.string.monster_difficulty_veryeasy;
		if (difficulty >= 60) return R.string.monster_difficulty_easy;
		if (difficulty >= 40) return R.string.monster_difficulty_normal;
		if (difficulty >= 20) return R.string.monster_difficulty_hard;
		if (difficulty == 0) return R.string.monster_difficulty_impossible;
		return R.string.monster_difficulty_veryhard;
	}
}

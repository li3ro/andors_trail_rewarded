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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;

public final class MonsterEncounterActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		if (!app.isInitialized()) { finish(); return; }
		final WorldContext world = app.getWorld();
		final ControllerContext controllers = app.getControllerContext();

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final Monster monster = Dialogs.getMonsterFromIntent(getIntent(), world);
		if (monster == null) {
			finish();
			return;
		}

		setContentView(R.layout.monsterencounter);

		CharSequence difficulty = getText(MonsterInfoActivity.getMonsterDifficultyResource(controllers, monster));

		TextView tv = (TextView) findViewById(R.id.monsterencounter_title);
		tv.setText(monster.getName());
		world.tileManager.setImageViewTile(getResources(), tv, monster);

		tv = (TextView) findViewById(R.id.monsterencounter_description);
		tv.setText(getString(R.string.dialog_monsterencounter_message, difficulty));

		Button b = (Button) findViewById(R.id.monsterencounter_attack);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				MonsterEncounterActivity.this.finish();
			}
			 });
		b = (Button) findViewById(R.id.monsterencounter_cancel);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				MonsterEncounterActivity.this.finish();
			}
		});
		b = (Button) findViewById(R.id.monsterencounter_info);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Dialogs.showMonsterInfo(MonsterEncounterActivity.this, monster);
			}
		});
	}
}

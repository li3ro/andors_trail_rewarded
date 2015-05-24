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
package com.gpl.rpg.AndorsTrail.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.R;

public final class AboutActivity extends Activity implements ImageGetter {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		app.setWindowParameters(this);

		setContentView(R.layout.about);
		final Resources res = getResources();

		final TextView tv = (TextView) findViewById(R.id.about_contents);
		tv.setText(Html.fromHtml(res.getString(R.string.about_contents1)));

		Button b = (Button) findViewById(R.id.about_button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText(Html.fromHtml(res.getString(R.string.about_contents1)));
			}
		});


		b = (Button) findViewById(R.id.about_button2);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText(Html.fromHtml(res.getString(R.string.about_authors)));
			}
		});

		b = (Button) findViewById(R.id.about_button3);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText(Html.fromHtml(res.getString(R.string.about_copyright) + res.getString(R.string.about_contents3)));
			}
		});

		b = (Button) findViewById(R.id.about_button4);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText(Html.fromHtml(res.getString(R.string.about_interface), AboutActivity.this, null));
			}
		});

		tv.setMovementMethod(LinkMovementMethod.getInstance());

		TextView t = (TextView) findViewById(R.id.about_version);
		t.setText('v' + AndorsTrailApplication.CURRENT_VERSION_DISPLAY);
	}

	@Override
	public Drawable getDrawable(String s) {
		Resources res = getResources();
		Drawable d;
		if (s.equals("chest.png")) {
			Drawable r = res.getDrawable(R.drawable.ui_quickslots);
			r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
			return r;
		}
		if (s.equals("char_hero.png")) {
			Drawable r = res.getDrawable(R.drawable.char_hero);
			r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight()*4/5);
			return r;
		}
		if (s.equals("monster.png")) d = res.getDrawable(R.drawable.monsters_eye4);
		else if (s.equals("flee_example.png")) d = res.getDrawable(R.drawable.ui_flee_example);
		else if (s.equals("doubleattackexample.png")) d = res.getDrawable(R.drawable.ui_doubleattackexample);
		else return null;
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		return d;
	}
}

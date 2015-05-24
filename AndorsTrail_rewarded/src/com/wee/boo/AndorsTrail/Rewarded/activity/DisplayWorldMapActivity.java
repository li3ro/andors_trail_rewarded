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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.controller.WorldMapController;
import com.wee.boo.AndorsTrail.Rewarded.model.map.WorldMapSegment;
import com.wee.boo.AndorsTrail.Rewarded.model.map.WorldMapSegment.WorldMapSegmentMap;
import com.wee.boo.AndorsTrail.Rewarded.util.L;

import java.io.File;

public final class DisplayWorldMapActivity extends Activity {
	private WorldContext world;

	private WebView displayworldmap_webview;
	private String worldMapSegmentName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		if (!app.isInitialized()) { finish(); return; }
		this.world = app.getWorld();

		app.setWindowParameters(this);

		setContentView(R.layout.displayworldmap);

		displayworldmap_webview = (WebView) findViewById(R.id.displayworldmap_webview);
		displayworldmap_webview.setBackgroundColor(getResources().getColor(R.color.displayworldmap_background));
		displayworldmap_webview.getSettings().setBuiltInZoomControls(true);
		enableJavascript();

		Button b = (Button) findViewById(R.id.displayworldmap_close);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DisplayWorldMapActivity.this.finish();
			}
		});

		worldMapSegmentName = getIntent().getStringExtra("worldMapSegmentName");
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void enableJavascript() {
		displayworldmap_webview.getSettings().setJavaScriptEnabled(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		update();
	}

	private void update() {
		File worldmap = WorldMapController.getCombinedWorldMapFile(worldMapSegmentName);

		if (!worldmap.exists()) {
			Toast.makeText(this, getResources().getString(R.string.menu_button_worldmap_failed), Toast.LENGTH_LONG).show();
			this.finish();
		}

		WorldMapSegment segment = world.maps.worldMapSegments.get(worldMapSegmentName);
		WorldMapSegmentMap map = segment.maps.get(world.model.currentMap.name);
		if (map == null) {
			this.finish();
			return;
		}

		String url = "file://" + worldmap.getAbsolutePath() + '?'
				+ (world.model.player.position.x + map.worldPosition.x) * WorldMapController.WORLDMAP_DISPLAY_TILESIZE
				+ ','
				+ (world.model.player.position.y + map.worldPosition.y-1) * WorldMapController.WORLDMAP_DISPLAY_TILESIZE;
		L.log("Showing " + url);
		displayworldmap_webview.loadUrl(url);
	}
}

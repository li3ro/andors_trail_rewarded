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
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.util.Range;

public final class RangeBar extends RelativeLayout {
	private final ProgressBar progressBar;
	private final TextView progressBarText;
	private final TextView labelText;

	public RangeBar(Context context, AttributeSet attr) {
		super(context, attr);
		setFocusable(false);
		inflate(context, R.layout.rangebar, this);

		progressBarText = (TextView) findViewById(R.id.rangebar_text);
		progressBar = (ProgressBar) findViewById(R.id.rangebar_progress);
		labelText = (TextView) findViewById(R.id.rangebar_label);
	}

	public void init(int drawableID, int labelTextID) {
		// Wow, you actually need to call this twice (!), or the progressbar won't show the progress image, just the background.
		// TODO: investigate strangeness of setProgressDrawable
		progressBar.setProgressDrawable(getResources().getDrawable(drawableID));
		progressBar.setProgressDrawable(getResources().getDrawable(drawableID));
		labelText.setText(labelTextID);
	}

	public void update(final Range range) { update(range.max, range.current); }
	public void update(final int max, final int current) {
		progressBar.setProgress(0); // http://stackoverflow.com/questions/4348032/android-progressbar-does-not-update-progress-view-drawable

		progressBar.setMax(max);
		progressBar.setProgress(Math.min(current, max));
		progressBarText.setText(current + "/" + max);
		invalidate();
	}
}

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
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorCondition;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionType;

public final class ActorConditionList extends LinearLayout {

	private final WorldContext world;

	public ActorConditionList(Context context, AttributeSet attr) {
		super(context, attr);
		setFocusable(false);
		setOrientation(LinearLayout.VERTICAL);
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivityContext(context);
		this.world = app.getWorld();
	}

	public void update(Iterable<ActorCondition> conditions) {
		removeAllViews();
		if (conditions == null) return;

		final Context context = getContext();
		final Resources res = getResources();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		for (ActorCondition c : conditions) {
			TextView v = (TextView) View.inflate(context, R.layout.inventoryitemview, null);
			world.tileManager.setImageViewTile(res, v, c.conditionType);
			SpannableString content = new SpannableString(describeEffect(res, c));
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			v.setText(content);
			final ActorConditionType conditionType = c.conditionType;
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Dialogs.showActorConditionInfo(context, conditionType);
				}
			});
			this.addView(v, layoutParams);
		}
	}

	private static String describeEffect(Resources res, ActorCondition c) {
		return ActorConditionEffectList.describeEffect(res, c.conditionType, c.magnitude, c.duration);
	}
}

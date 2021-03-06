/*
 * Copyrightę 2015 Yaniv Bokobza
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
import com.wee.boo.AndorsTrail.Rewarded.Dialogs;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorCondition;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionEffect;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionType;

import java.util.Collection;

public final class ActorConditionEffectList extends LinearLayout {

	public ActorConditionEffectList(Context context, AttributeSet attr) {
		super(context, attr);
		setFocusable(false);
		setOrientation(LinearLayout.VERTICAL);
	}

	public void update(Collection<ActorConditionEffect> effects) {
		removeAllViews();
		if (effects == null) return;

		final Context context = getContext();
		final Resources res = getResources();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		for (ActorConditionEffect e : effects) {
			String msg;
			final ActorConditionType conditionType = e.conditionType;
			if (e.isRemovalEffect()) {
				msg = res.getString(R.string.actorcondition_info_removes_all, conditionType.name);
			} else {
				msg = describeEffect(res, e);
			}
			TextView tv = new TextView(context);
			tv.setLayoutParams(layoutParams);

			SpannableString content = new SpannableString(msg);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			tv.setText(content);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Dialogs.showActorConditionInfo(context, conditionType);
				}
			});
			this.addView(tv, layoutParams);
		}
	}

	private static String describeEffect(Resources res, ActorConditionEffect effect) {
		String msg = describeEffect(res, effect.conditionType, effect.magnitude, effect.duration);
		if (effect.chance.isMax()) return msg;

		return res.getString(R.string.iteminfo_effect_chance_of, effect.chance.toPercentString(), msg);
	}

	public static String describeEffect(Resources res, ActorConditionType conditionType, int magnitude, int duration) {
		StringBuilder sb = new StringBuilder(conditionType.name);
		if (magnitude > 1) {
			sb.append(" x");
			sb.append(magnitude);
		}
		if (ActorCondition.isTemporaryEffect(duration)) {
			sb.append(' ');
			sb.append(res.getString(R.string.iteminfo_effect_duration, duration));
		}
		return sb.toString();
	}
}

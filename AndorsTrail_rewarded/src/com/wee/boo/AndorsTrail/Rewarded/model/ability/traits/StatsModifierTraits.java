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
package com.wee.boo.AndorsTrail.Rewarded.model.ability.traits;

import com.wee.boo.AndorsTrail.Rewarded.resource.VisualEffectCollection;
import com.wee.boo.AndorsTrail.Rewarded.util.ConstRange;

public final class StatsModifierTraits {
	public final VisualEffectCollection.VisualEffectID visualEffectID;
	public final ConstRange currentHPBoost;
	public final ConstRange currentAPBoost;

	public StatsModifierTraits(
			VisualEffectCollection.VisualEffectID visualEffectID
			, ConstRange currentHPBoost
			, ConstRange currentAPBoost
	) {
		this.visualEffectID = visualEffectID;
		this.currentHPBoost = currentHPBoost;
		this.currentAPBoost = currentAPBoost;
	}

	public int calculateUseCost() {
		final float averageHPBoost = currentHPBoost == null ? 0 : currentHPBoost.averagef();
		if (averageHPBoost == 0) return 0;

		final int costBoostHP = (int) (0.1*Math.signum(averageHPBoost)*Math.pow(Math.abs(averageHPBoost), 2) + 3*averageHPBoost);
		return costBoostHP;
	}

	public int calculateHitCost() {
		final float averageHPBoost = currentHPBoost == null ? 0 : currentHPBoost.averagef();
		final float averageAPBoost = currentAPBoost == null ? 0 : currentAPBoost.averagef();
		if (averageHPBoost == 0 && averageAPBoost == 0) return 0;

		final int costBoostHP = (int)(2770*Math.pow(Math.max(0,averageHPBoost), 2.5) + 450*averageHPBoost);
		final int costBoostAP = (int)(3100*Math.pow(Math.max(0,averageAPBoost), 2.5) + 300*averageAPBoost);
		return costBoostHP + costBoostAP;
	}

	public int calculateKillCost() {
		final float averageHPBoost = currentHPBoost == null ? 0 : currentHPBoost.averagef();
		final float averageAPBoost = currentAPBoost == null ? 0 : currentAPBoost.averagef();
		if (averageHPBoost == 0 && averageAPBoost == 0) return 0;

		final int costBoostHP = (int)(923*Math.pow(Math.max(0,averageHPBoost), 2.5) + 450*averageHPBoost);
		final int costBoostAP = (int)(1033*Math.pow(Math.max(0,averageAPBoost), 2.5) + 300*averageAPBoost);
		return costBoostHP + costBoostAP;
	}
}

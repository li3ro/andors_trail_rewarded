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
package com.wee.boo.AndorsTrail.Rewarded.util;

import android.util.FloatMath;

public final class ConstRange {
	public final int max;
	public final int current;

	public ConstRange(Range r) {
		this.max = r.max;
		this.current = r.current;
	}
	public ConstRange(ConstRange r) {
		this.max = r.max;
		this.current = r.current;
	}
	public ConstRange(int max, int current) {
		this.max = max;
		this.current = current;
	}

	public String toString() { return current + "/" + max; }
	public String toMinMaxString() {
		if (isMax()) return Integer.toString(max);
		else return current + "-" + max;
	}
	public String toMinMaxAbsString() {
		if (isMax()) return Integer.toString(Math.abs(max));
		else return Math.abs(current) + "-" + Math.abs(max);
	}
	public boolean isMax() { return max == current;	}
	public int average() {
		return (max + current) / 2;
	}
	public float averagef() {
		return ((float) max + current) / 2f;
	}
	public String toPercentString() {
		return Integer.toString((int) FloatMath.ceil((float)current * 100 / max)) + '%';
	}
}

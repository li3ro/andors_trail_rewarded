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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Range {
	public int max;
	public int current;

	public Range() { }
	public Range(Range r) { set(r); }
	public Range(ConstRange r) { set(r); }
	public Range(int max, int current) {
		this.max = max;
		this.current = current;
	}

	public boolean equals(Range r) {
		if (r == null) return false;
		return max == r.max && current == r.current;
	}
	public boolean equals(ConstRange r) {
		if (r == null) return false;
		return max == r.max && current == r.current;
	}

	public void set(Range r) {
		this.max = r.max;
		this.current = r.current;
	}
	public void set(ConstRange r) {
		this.max = r.max;
		this.current = r.current;
	}
	public void set(int max, int current) {
		this.max = max;
		this.current = current;
	}
	public boolean add(int value, boolean mayOverflow) {
		int valueBefore = current;
		this.current += value;
		if (!mayOverflow) capAtMax();
		return (this.current != valueBefore);
	}
	public boolean capAtMax() {
		if (current > max) {
			current = max;
			return true;
		}
		return false;
	}
	public void addToMax(int value) {
		this.max += value;
	}
	public boolean subtract(int value, boolean mayUnderflow) {
		int valueBefore = current;
		this.current -= value;
		if (!mayUnderflow && current < 0) current = 0;
		return (this.current != valueBefore);
	}
	public boolean change(int value, boolean mayUnderflow, boolean mayOverflow) {
		int valueBefore = current;
		if (value < 0) subtract(-value, mayUnderflow);
		else add(value, mayOverflow);
		return current != valueBefore;
	}
	public void add(ConstRange r) {
		this.max += r.max;
		this.current += r.current;
	}

	public String toString() { return current + "/" + max; }
	public String toMinMaxString() {
		if (isMax()) return Integer.toString(max);
		else return current + "-" + max;
	}
	public boolean isMax() { return current >= max;	}
	public void setMax() { current = max; }
	public int average() {
		return (max + current) / 2;
	}
	public float averagef() {
		return ((float) max + current) / 2f;
	}


	// ====== PARCELABLE ===================================================================

	public Range(DataInputStream src, int fileversion) throws IOException {
		this.readFromParcel(src, fileversion);
	}
	public void readFromParcel(DataInputStream src, int fileversion) throws IOException {
		this.max = src.readInt();
		this.current = src.readInt();
	}

	public void writeToParcel(DataOutputStream dest) throws IOException {
		dest.writeInt(max);
		dest.writeInt(current);
	}
}

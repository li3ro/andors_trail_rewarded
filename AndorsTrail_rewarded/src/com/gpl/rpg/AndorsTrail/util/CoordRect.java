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
package com.gpl.rpg.AndorsTrail.util;

public final class CoordRect {
	public final Coord topLeft;
	public final Size size;
	public CoordRect(Size size) {
		topLeft = new Coord();
		this.size = size;
	}
	public CoordRect(Coord topLeft, Size size) {
		this.topLeft = topLeft;
		this.size = size;
	}
	public CoordRect(CoordRect copy) {
		this.topLeft = copy.topLeft;
		this.size = copy.size;
	}
	public boolean contains(Coord p) {
		if (p.x < topLeft.x) return false;
		if (p.y < topLeft.y) return false;
		if (p.x - topLeft.x >= size.width) return false;
		if (p.y - topLeft.y >= size.height) return false;
		return true;
	}
	public boolean contains(final int x, final int y) {
		if (x < topLeft.x) return false;
		if (y < topLeft.y) return false;
		if (x - topLeft.x >= size.width) return false;
		if (y - topLeft.y >= size.height) return false;
		return true;
	}

	/*
	public static boolean contains(final int x, final int y, final Size size, final Coord p) {
		if (p.x < x) return false;
		else if (p.y < y) return false;
		else if (p.x - x >= size.width) return false;
		else if (p.y - y >= size.height) return false;
		else return true;
	}
	*/
	public boolean intersects(final CoordRect a) {
		if (this == a) return true;
		if (a.topLeft.x >= topLeft.x + size.width) return false;
		if (a.topLeft.y >= topLeft.y + size.height) return false;
		if (topLeft.x >= a.topLeft.x + a.size.width) return false;
		if (topLeft.y >= a.topLeft.y + a.size.height) return false;
		return true;
	}

	public boolean isAdjacentTo(Coord p) {
		final int dx = p.x - topLeft.x;
		final int dy = p.y - topLeft.y;
		if (dx < -1) return false;
		if (dy < -1) return false;
		if (dx > size.width) return false;
		if (dy > size.height) return false;
		return true;
	}
	public Coord findPositionAdjacentTo(Coord p) {
		final int dx = Math.min(size.width-1, Math.max(0, p.x - topLeft.x));
		final int dy = Math.min(size.height-1, Math.max(0, p.y - topLeft.y));
		return new Coord(topLeft.x + dx, topLeft.y + dy);
	}
	public Coord getCenter() {
		Coord center = new Coord(topLeft);
		center.x += size.width / 2;
		center.y += size.height / 2;
		return center;
	}

	public String toString() {
		return '{' + topLeft.toString() + ", " + size.toString() + '}';
	}
}

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

public final class ByteUtils {
	public static String toHexString(byte[] bytes) { return toHexString(bytes, bytes.length); }
	public static String toHexString(byte[] bytes, int numBytes) {
		if (bytes == null) return "";
		if (bytes.length == 0) return "";
		final int len = Math.min(numBytes, bytes.length);
		StringBuilder result = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			String h = Integer.toHexString(0xFF & bytes[i]);
			if (h.length() < 2) result.append('0');
			result.append(h);
		}
		return result.toString();
	}

	public static void xorArray(byte[] array, byte[] mask) {
		final int len = Math.min(array.length, mask.length);
		for(int i = 0; i < len; ++i) {
			array[i] ^= mask[i];
		}
	}
}

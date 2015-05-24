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
package com.wee.boo.AndorsTrail.Rewarded.savegames;

import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.MonsterType;
import com.wee.boo.AndorsTrail.Rewarded.util.Coord;

import java.io.DataInputStream;
import java.io.IOException;

public final class LegacySavegameFormatReaderForMonster {
	public static Monster newFromParcel_pre_v25(DataInputStream src, int fileversion, MonsterType monsterType) throws IOException {
		Monster m = new Monster(monsterType);
		m.position.set(new Coord(src, fileversion));
		m.ap.current = src.readInt();
		m.health.current = src.readInt();
		if (fileversion >= 12) {
			if (src.readBoolean()) m.forceAggressive();
		}
		return m;
	}
}

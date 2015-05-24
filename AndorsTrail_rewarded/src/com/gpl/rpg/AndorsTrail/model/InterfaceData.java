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
package com.gpl.rpg.AndorsTrail.model;

import com.gpl.rpg.AndorsTrail.model.actor.Monster;
import com.gpl.rpg.AndorsTrail.util.Coord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class InterfaceData {
	public boolean isMainActivityVisible = false;
	public boolean isInCombat = false;
	public boolean isPlayersCombatTurn = false;
	public Monster selectedMonster;
	public Coord selectedPosition;
	public String selectedTabHeroInfo = "";
	public int selectedQuestFilter = 0; // Should not be parceled

	public InterfaceData() { }


	// ====== PARCELABLE ===================================================================

	public InterfaceData(DataInputStream src, int fileversion) throws IOException {
		this.isMainActivityVisible = src.readBoolean();
		this.isInCombat = src.readBoolean();
		final boolean hasSelectedPosition = src.readBoolean();
		if (hasSelectedPosition) {
			this.selectedPosition = new Coord(src, fileversion);
		} else {
			this.selectedPosition = null;
		}
		this.selectedTabHeroInfo = src.readUTF();
	}

	public void writeToParcel(DataOutputStream dest) throws IOException {
		dest.writeBoolean(isMainActivityVisible);
		dest.writeBoolean(isInCombat);
		if (selectedPosition != null) {
			dest.writeBoolean(true);
			selectedPosition.writeToParcel(dest);
		} else {
			dest.writeBoolean(false);
		}
		dest.writeUTF(selectedTabHeroInfo);
	}
}

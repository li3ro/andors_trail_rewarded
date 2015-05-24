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
package com.wee.boo.AndorsTrail.Rewarded.model;

import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;
import com.wee.boo.AndorsTrail.Rewarded.model.map.LayeredTileMap;
import com.wee.boo.AndorsTrail.Rewarded.model.map.PredefinedMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ModelContainer {

	public final Player player;
	public final InterfaceData uiSelections;
	public final CombatLog combatLog = new CombatLog();
	public final GameStatistics statistics;
	public final WorldData worldData;
	public PredefinedMap currentMap;
	public LayeredTileMap currentTileMap;

	public ModelContainer() {
		player = new Player();
		uiSelections = new InterfaceData();
		statistics = new GameStatistics();
		worldData = new WorldData();
	}

	// ====== PARCELABLE ===================================================================

	public ModelContainer(DataInputStream src, WorldContext world, ControllerContext controllers, int fileversion) throws IOException {
		this.player = Player.newFromParcel(src, world, controllers, fileversion);
		this.currentMap = world.maps.findPredefinedMap(src.readUTF());
		this.uiSelections = new InterfaceData(src, fileversion);
		if (uiSelections.selectedPosition != null) {
			this.uiSelections.selectedMonster = currentMap.getMonsterAt(uiSelections.selectedPosition);
		}
		this.statistics = new GameStatistics(src, world, fileversion);
		this.currentTileMap = null;
		if (fileversion >= 40) {
			this.worldData = new WorldData(src, fileversion);
		} else {
			this.worldData = new WorldData();
		}
	}

	public void writeToParcel(DataOutputStream dest) throws IOException {
		player.writeToParcel(dest);
		dest.writeUTF(currentMap.name);
		uiSelections.writeToParcel(dest);
		statistics.writeToParcel(dest);
		worldData.writeToParcel(dest);
	}
}

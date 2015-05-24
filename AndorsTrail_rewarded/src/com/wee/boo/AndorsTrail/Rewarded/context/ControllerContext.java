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
package com.wee.boo.AndorsTrail.Rewarded.context;

import android.content.res.Resources;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailPreferences;
import com.wee.boo.AndorsTrail.Rewarded.controller.*;

import java.lang.ref.WeakReference;

public final class ControllerContext {
	//Controllers
	public final MapController mapController;
	public final GameRoundController gameRoundController;
	public final CombatController combatController;
	public final ConversationController conversationController;
	public final VisualEffectController effectController;
	public final ItemController itemController;
	public final MonsterMovementController monsterMovementController;
	public final MonsterSpawningController monsterSpawnController;
	public final MovementController movementController;
	public final ActorStatsController actorStatsController;
	public final InputController inputController;
	public final SkillController skillController;

	public final AndorsTrailPreferences preferences;
	private final WeakReference<AndorsTrailApplication> app;

	public ControllerContext(AndorsTrailApplication app, WorldContext world) {
		this.app = new WeakReference<AndorsTrailApplication>(app);
		this.preferences = app.getPreferences();

		this.mapController = new MapController(this, world);
		this.gameRoundController = new GameRoundController(this, world);
		this.combatController = new CombatController(this, world);
		this.conversationController = new ConversationController(this, world);
		this.effectController = new VisualEffectController(this, world);
		this.itemController = new ItemController(this, world);
		this.monsterMovementController = new MonsterMovementController(this, world);
		this.monsterSpawnController = new MonsterSpawningController(this, world);
		this.movementController = new MovementController(this, world);
		this.actorStatsController = new ActorStatsController(this, world);
		this.inputController = new InputController(this, world);
		this.skillController = new SkillController(this, world);
	}

	public Resources getResources() {
		return app.get().getResources();
	}
}

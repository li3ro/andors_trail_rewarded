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
package com.wee.boo.AndorsTrail.Rewarded.controller;

import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.controller.listeners.GameRoundListeners;
import com.wee.boo.AndorsTrail.Rewarded.model.map.MapObject;
import com.wee.boo.AndorsTrail.Rewarded.util.TimedMessageTask;

public final class GameRoundController implements TimedMessageTask.Callback {

	private final ControllerContext controllers;
	private final WorldContext world;
	private final TimedMessageTask roundTimer;
	public final GameRoundListeners gameRoundListeners = new GameRoundListeners();

	public GameRoundController(ControllerContext controllers, WorldContext world) {
		this.controllers = controllers;
		this.world = world;
		this.roundTimer = new TimedMessageTask(this, Constants.TICK_DELAY, true);
	}

	private int ticksUntilNextRound = Constants.TICKS_PER_ROUND;
	private int ticksUntilNextFullRound = Constants.TICKS_PER_FULLROUND;

	@Override
	public boolean onTick(TimedMessageTask task) {
		if (!world.model.uiSelections.isMainActivityVisible) return false;
		if (world.model.uiSelections.isInCombat) return false;

		onNewTick();

		--ticksUntilNextRound;
		if (ticksUntilNextRound <= 0) {
			onNewRound();
			restartWaitForNextRound();
		}

		--ticksUntilNextFullRound;
		if (ticksUntilNextFullRound <= 0) {
			onNewFullRound();
			restartWaitForNextFullRound();
		}

		return true;
	}

	public void resetRoundTimers() {
		restartWaitForNextRound();
		restartWaitForNextFullRound();
	}

	public void resume() {
		world.model.uiSelections.isMainActivityVisible = true;
		roundTimer.start();

		if (world.model.uiSelections.isInCombat) {
			controllers.combatController.setCombatSelection(world.model.uiSelections.selectedMonster, world.model.uiSelections.selectedPosition);
			controllers.combatController.enterCombat(CombatController.BeginTurnAs.continueLastTurn);
		}
	}

	private void restartWaitForNextFullRound() {
		ticksUntilNextFullRound = Constants.TICKS_PER_FULLROUND;
	}

	private void restartWaitForNextRound() {
		ticksUntilNextRound = Constants.TICKS_PER_ROUND;
	}

	public void pause() {
		roundTimer.stop();
		world.model.uiSelections.isMainActivityVisible = false;
	}

	public void onNewFullRound() {
		controllers.mapController.resetMapsNotRecentlyVisited();
		controllers.actorStatsController.applyConditionsToMonsters(world.model.currentMap, true);
		controllers.actorStatsController.applyConditionsToPlayer(world.model.player, true);
		gameRoundListeners.onNewFullRound();
	}

	private void onNewRound() {
		onNewMonsterRound();
		onNewPlayerRound();
		gameRoundListeners.onNewRound();
	}
	public void onNewPlayerRound() {
		world.model.worldData.tickWorldTime();
		controllers.actorStatsController.applyConditionsToPlayer(world.model.player, false);
		controllers.actorStatsController.applySkillEffectsForNewRound(world.model.player, world.model.currentMap);
		controllers.mapController.handleMapEvents(world.model.currentMap, world.model.player.position, MapObject.MapObjectEvaluationType.afterEveryRound);
	}
	public void onNewMonsterRound() {
		controllers.actorStatsController.applyConditionsToMonsters(world.model.currentMap, false);
	}

	private void onNewTick() {
		controllers.monsterMovementController.moveMonsters();
		controllers.monsterSpawnController.maybeSpawn(world.model.currentMap, world.model.currentTileMap);
		controllers.monsterMovementController.attackWithAgressiveMonsters();
		controllers.effectController.updateSplatters(world.model.currentMap);
		controllers.mapController.handleMapEvents(world.model.currentMap, world.model.player.position, MapObject.MapObjectEvaluationType.continuously);
		gameRoundListeners.onNewTick();
	}
}

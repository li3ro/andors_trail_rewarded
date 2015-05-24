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
package com.wee.boo.AndorsTrail.Rewarded.controller.listeners;

import com.wee.boo.AndorsTrail.Rewarded.controller.AttackResult;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class CombatActionListeners extends ListOfListeners<CombatActionListener> implements CombatActionListener {

	private final Function2<CombatActionListener, Monster, AttackResult> onPlayerAttackMissed = new Function2<CombatActionListener, Monster, AttackResult>() {
		@Override public void call(CombatActionListener listener, Monster target, AttackResult attackResult) { listener.onPlayerAttackMissed(target, attackResult); }
	};

	private final Function2<CombatActionListener, Monster, AttackResult> onPlayerAttackSuccess = new Function2<CombatActionListener, Monster, AttackResult>() {
		@Override public void call(CombatActionListener listener, Monster target, AttackResult attackResult) { listener.onPlayerAttackSuccess(target, attackResult); }
	};

	private final Function2<CombatActionListener, Monster, AttackResult> onMonsterAttackMissed = new Function2<CombatActionListener, Monster, AttackResult>() {
		@Override public void call(CombatActionListener listener, Monster attacker, AttackResult attackResult) { listener.onMonsterAttackMissed(attacker, attackResult); }
	};

	private final Function2<CombatActionListener, Monster, AttackResult> onMonsterAttackSuccess = new Function2<CombatActionListener, Monster, AttackResult>() {
		@Override public void call(CombatActionListener listener, Monster attacker, AttackResult attackResult) { listener.onMonsterAttackSuccess(attacker, attackResult); }
	};

	private final Function1<CombatActionListener, Monster> onMonsterMovedDuringCombat = new Function1<CombatActionListener, Monster>() {
		@Override public void call(CombatActionListener listener, Monster m) { listener.onMonsterMovedDuringCombat(m); }
	};

	private final Function1<CombatActionListener, Monster> onPlayerKilledMonster = new Function1<CombatActionListener, Monster>() {
		@Override public void call(CombatActionListener listener, Monster target) { listener.onPlayerKilledMonster(target); }
	};

	private final Function<CombatActionListener> onPlayerStartedFleeing = new Function<CombatActionListener>() {
		@Override public void call(CombatActionListener listener) { listener.onPlayerStartedFleeing(); }
	};

	private final Function<CombatActionListener> onPlayerFailedFleeing = new Function<CombatActionListener>() {
		@Override public void call(CombatActionListener listener) { listener.onPlayerFailedFleeing(); }
	};

	private final Function<CombatActionListener> onPlayerDoesNotHaveEnoughAP = new Function<CombatActionListener>() {
		@Override public void call(CombatActionListener listener) { listener.onPlayerDoesNotHaveEnoughAP(); }
	};

	@Override
	public void onPlayerAttackMissed(Monster target, AttackResult attackResult) {
		callAllListeners(this.onPlayerAttackMissed, target, attackResult);
	}

	@Override
	public void onPlayerAttackSuccess(Monster target, AttackResult attackResult) {
		callAllListeners(this.onPlayerAttackSuccess, target, attackResult);
	}

	@Override
	public void onMonsterAttackMissed(Monster attacker, AttackResult attackResult) {
		callAllListeners(this.onMonsterAttackMissed, attacker, attackResult);
	}

	@Override
	public void onMonsterAttackSuccess(Monster attacker, AttackResult attackResult) {
		callAllListeners(this.onMonsterAttackSuccess, attacker, attackResult);
	}

	@Override
	public void onMonsterMovedDuringCombat(Monster m) {
		callAllListeners(this.onMonsterMovedDuringCombat, m);
	}

	@Override
	public void onPlayerKilledMonster(Monster target) {
		callAllListeners(this.onPlayerKilledMonster, target);
	}

	@Override
	public void onPlayerStartedFleeing() {
		callAllListeners(this.onPlayerStartedFleeing);
	}

	@Override
	public void onPlayerFailedFleeing() {
		callAllListeners(this.onPlayerFailedFleeing);
	}

	@Override
	public void onPlayerDoesNotHaveEnoughAP() {
		callAllListeners(this.onPlayerDoesNotHaveEnoughAP);
	}
}

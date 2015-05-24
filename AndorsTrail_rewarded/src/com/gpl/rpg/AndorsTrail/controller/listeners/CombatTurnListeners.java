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
package com.gpl.rpg.AndorsTrail.controller.listeners;

import com.gpl.rpg.AndorsTrail.model.actor.Monster;
import com.gpl.rpg.AndorsTrail.util.ListOfListeners;

public final class CombatTurnListeners extends ListOfListeners<CombatTurnListener> implements CombatTurnListener {

	private final Function<CombatTurnListener> onCombatStarted = new Function<CombatTurnListener>() {
		@Override public void call(CombatTurnListener listener) { listener.onCombatStarted(); }
	};

	private final Function<CombatTurnListener> onCombatEnded = new Function<CombatTurnListener>() {
		@Override public void call(CombatTurnListener listener) { listener.onCombatEnded(); }
	};

	private final Function<CombatTurnListener> onNewPlayerTurn = new Function<CombatTurnListener>() {
		@Override public void call(CombatTurnListener listener) { listener.onNewPlayerTurn(); }
	};

	private final Function1<CombatTurnListener, Monster> onMonsterIsAttacking = new Function1<CombatTurnListener, Monster>() {
		@Override public void call(CombatTurnListener listener, Monster m) { listener.onMonsterIsAttacking(m); }
	};

	@Override
	public void onCombatStarted() {
		callAllListeners(this.onCombatStarted);
	}

	@Override
	public void onCombatEnded() {
		callAllListeners(this.onCombatEnded);
	}

	@Override
	public void onNewPlayerTurn() {
		callAllListeners(this.onNewPlayerTurn);
	}

	@Override
	public void onMonsterIsAttacking(Monster m) {
		callAllListeners(this.onMonsterIsAttacking, m);
	}
}

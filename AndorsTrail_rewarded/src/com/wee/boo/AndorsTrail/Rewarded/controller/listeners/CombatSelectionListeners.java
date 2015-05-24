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

import com.wee.boo.AndorsTrail.Rewarded.model.actor.Monster;
import com.wee.boo.AndorsTrail.Rewarded.util.Coord;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class CombatSelectionListeners extends ListOfListeners<CombatSelectionListener> implements CombatSelectionListener {

	private final Function3<CombatSelectionListener, Monster, Coord, Coord> onMonsterSelected = new Function3<CombatSelectionListener, Monster, Coord, Coord>() {
		@Override public void call(CombatSelectionListener listener, Monster monster, Coord selectedPosition, Coord previousSelection) { listener.onMonsterSelected(monster, selectedPosition, previousSelection); }
	};

	private final Function2<CombatSelectionListener, Coord, Coord> onMovementDestinationSelected = new Function2<CombatSelectionListener, Coord, Coord>() {
		@Override public void call(CombatSelectionListener listener, Coord selectedPosition, Coord previousSelection) { listener.onMovementDestinationSelected(selectedPosition, previousSelection); }
	};

	private final Function1<CombatSelectionListener, Coord> onCombatSelectionCleared = new Function1<CombatSelectionListener, Coord>() {
		@Override public void call(CombatSelectionListener listener, Coord previousSelection) { listener.onCombatSelectionCleared(previousSelection); }
	};

	@Override
	public void onMonsterSelected(Monster m, Coord selectedPosition, Coord previousSelection) {
		callAllListeners(this.onMonsterSelected, m, selectedPosition, previousSelection);
	}

	@Override
	public void onMovementDestinationSelected(Coord selectedPosition, Coord previousSelection) {
		callAllListeners(this.onMovementDestinationSelected, selectedPosition, previousSelection);
	}

	@Override
	public void onCombatSelectionCleared(Coord previousSelection) {
		callAllListeners(this.onCombatSelectionCleared, previousSelection);
	}
}

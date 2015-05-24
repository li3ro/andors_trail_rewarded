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

import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class GameRoundListeners extends ListOfListeners<GameRoundListener> implements GameRoundListener {

	private final Function<GameRoundListener> onNewTick = new Function<GameRoundListener>() {
		@Override public void call(GameRoundListener listener) { listener.onNewTick(); }
	};

	private final Function<GameRoundListener> onNewRound = new Function<GameRoundListener>() {
		@Override public void call(GameRoundListener listener) { listener.onNewRound(); }
	};

	private final Function<GameRoundListener> onNewFullRound = new Function<GameRoundListener>() {
		@Override public void call(GameRoundListener listener) { listener.onNewFullRound(); }
	};

	@Override
	public void onNewTick() {
		callAllListeners(this.onNewTick);
	}

	@Override
	public void onNewRound() {
		callAllListeners(this.onNewRound);
	}

	@Override
	public void onNewFullRound() {
		callAllListeners(this.onNewFullRound);
	}
}

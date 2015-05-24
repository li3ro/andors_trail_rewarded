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

import com.gpl.rpg.AndorsTrail.model.actor.Player;
import com.gpl.rpg.AndorsTrail.util.ListOfListeners;

public final class PlayerStatsListeners extends ListOfListeners<PlayerStatsListener> implements PlayerStatsListener {

	private final Function1<PlayerStatsListener, Player> onPlayerExperienceChanged = new Function1<PlayerStatsListener, Player>() {
		@Override public void call(PlayerStatsListener listener, Player p) { listener.onPlayerExperienceChanged(p); }
	};

	@Override
	public void onPlayerExperienceChanged(Player p) {
		callAllListeners(this.onPlayerExperienceChanged, p);
	}
}

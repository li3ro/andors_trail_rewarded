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

import com.gpl.rpg.AndorsTrail.util.ListOfListeners;

public final class QuickSlotListeners extends ListOfListeners<QuickSlotListener> implements QuickSlotListener {

	private final Function1<QuickSlotListener, Integer> onQuickSlotChanged = new Function1<QuickSlotListener, Integer>() {
		@Override public void call(QuickSlotListener listener, Integer slotId) { listener.onQuickSlotChanged(slotId); }
	};

	private final Function1<QuickSlotListener, Integer> onQuickSlotUsed = new Function1<QuickSlotListener, Integer>() {
		@Override public void call(QuickSlotListener listener, Integer slotId) { listener.onQuickSlotUsed(slotId); }
	};

	@Override
	public void onQuickSlotChanged(int slotId) {
		callAllListeners(this.onQuickSlotChanged, slotId);
	}

	@Override
	public void onQuickSlotUsed(int slotId) {
		callAllListeners(this.onQuickSlotUsed, slotId);
	}
}

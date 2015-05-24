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

import java.util.LinkedList;
import java.util.ListIterator;

public final class CombatLog {
	private final LinkedList<String> messages = new LinkedList<String>();
	private static final int MAX_COMBAT_LOG_LENGTH = 100;
	private static final String newCombatSession = "--";

	public CombatLog() { }

	public void append(String msg) {
		while (messages.size() >= MAX_COMBAT_LOG_LENGTH) messages.removeFirst();
		messages.addLast(msg);
	}

	public void appendCombatEnded() {
		if (messages.isEmpty()) return;
		if (messages.getLast().equals(newCombatSession)) return;
		append(newCombatSession);
	}

	public String getLastMessages() {
		if (messages.isEmpty()) return "";
		StringBuilder sb = new StringBuilder(100);
		ListIterator<String> it = messages.listIterator(messages.size());
		sb.append(it.previous());
		int i = 1;
		while (it.hasPrevious() && i++ < 3) {
			String s = it.previous();
			if (s.equals(newCombatSession)) break;
			sb.insert(0, '\n').insert(0, s);
		}
		return sb.toString();
	}

	public String[] getAllMessages() {
		return messages.toArray(new String[messages.size()]);
	}
}

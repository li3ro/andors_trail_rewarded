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
package com.wee.boo.AndorsTrail.Rewarded.model.conversation;

import com.wee.boo.AndorsTrail.Rewarded.model.script.Requirement;

public final class Reply {
	public final String text;
	public final String nextPhrase;
	public final Requirement[] requires;

	public boolean hasRequirements() {
		return requires != null;
	}

	public Reply(
			String text
			, String nextPhrase
			, Requirement[] requires
	) {
		this.text = text;
		this.nextPhrase = nextPhrase;
		this.requires = requires;
	}
}

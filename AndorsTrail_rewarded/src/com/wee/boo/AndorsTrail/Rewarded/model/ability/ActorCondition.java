/*
 * Copyrightę 2015 Yaniv Bokobza
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
package com.wee.boo.AndorsTrail.Rewarded.model.ability;

import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ActorCondition {
	public static final int MAGNITUDE_REMOVE_ALL = -99;
	public static final int DURATION_FOREVER = 999;

	public final ActorConditionType conditionType;
	public int magnitude;
	public int duration;

	public ActorCondition(
			ActorConditionType conditionType
			, int magnitude
			, int duration
	) {
		this.conditionType = conditionType;
		this.magnitude = magnitude;
		this.duration = duration;
	}

	public boolean isTemporaryEffect() { return isTemporaryEffect(duration); }
	public static boolean isTemporaryEffect(int duration) {
		return duration != DURATION_FOREVER;
	}


	// ====== PARCELABLE ===================================================================

	public ActorCondition(DataInputStream src, WorldContext world, int fileversion) throws IOException {
		final String conditionTypeID = src.readUTF();
		this.conditionType = world.actorConditionsTypes.getActorConditionType(conditionTypeID);
		this.magnitude = src.readInt();
		this.duration = src.readInt();
	}

	public void writeToParcel(DataOutputStream dest) throws IOException {
		dest.writeUTF(conditionType.conditionTypeID);
		dest.writeInt(magnitude);
		dest.writeInt(duration);
	}
}

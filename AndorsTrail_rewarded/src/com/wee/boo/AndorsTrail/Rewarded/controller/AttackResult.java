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

public final class AttackResult {
	public final boolean isHit;
	public final boolean isCriticalHit;
	public final int damage;
	public final boolean targetDied;
	public AttackResult(boolean isHit, boolean isCriticalHit, int damage, boolean targetDied) {
		this.isHit = isHit;
		this.isCriticalHit = isCriticalHit;
		this.damage = damage;
		this.targetDied = targetDied;
	}
	public static final AttackResult MISS = new AttackResult(false, false, 0, false);
}

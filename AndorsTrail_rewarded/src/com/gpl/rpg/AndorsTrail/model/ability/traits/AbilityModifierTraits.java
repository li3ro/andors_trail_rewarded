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
package com.gpl.rpg.AndorsTrail.model.ability.traits;

public final class AbilityModifierTraits {
	public final int increaseMaxHP;
	public final int increaseMaxAP;
	public final int increaseMoveCost;
	public final int increaseUseItemCost;
	public final int increaseReequipCost;
	public final int increaseAttackCost;

	public final int increaseAttackChance;
	public final int increaseBlockChance;
	public final int increaseMinDamage;
	public final int increaseMaxDamage;
	public final int increaseCriticalSkill;
	public final float setCriticalMultiplier;
	public final int increaseDamageResistance;

	public AbilityModifierTraits(
			int increaseMaxHP
			, int increaseMaxAP
			, int increaseMoveCost
			, int increaseUseItemCost
			, int increaseReequipCost
			, int increaseAttackCost
			, int increaseAttackChance
			, int increaseBlockChance
			, int increaseMinDamage
			, int increaseMaxDamage
			, int increaseCriticalSkill
			, float setCriticalMultiplier
			, int increaseDamageResistance
			) {
		this.increaseMaxHP = increaseMaxHP;
		this.increaseMaxAP = increaseMaxAP;
		this.increaseMoveCost = increaseMoveCost;
		this.increaseUseItemCost = increaseUseItemCost;
		this.increaseReequipCost = increaseReequipCost;
		this.increaseAttackCost = increaseAttackCost;
		this.increaseAttackChance = increaseAttackChance;
		this.increaseBlockChance = increaseBlockChance;
		this.increaseMinDamage = increaseMinDamage;
		this.increaseMaxDamage = increaseMaxDamage;
		this.increaseCriticalSkill = increaseCriticalSkill;
		this.setCriticalMultiplier = setCriticalMultiplier;
		this.increaseDamageResistance = increaseDamageResistance;
	}

	public int calculateCost(boolean isWeapon) {
		final int costBC = (int) (3*Math.pow(Math.max(0, increaseBlockChance), 2.5) + 28*increaseBlockChance);
		final int costAC = (int) (0.4*Math.pow(Math.max(0,increaseAttackChance), 2.5) - 6*Math.pow(Math.abs(Math.min(0,increaseAttackChance)),2.7));
		final int costAP = isWeapon ?
				(int) (0.2*Math.pow(10.0f/increaseAttackCost, 8) - 25*increaseAttackCost)
				:-3125 * increaseAttackCost;
		final int costDR = 1325 * increaseDamageResistance;
		final int costDMG_Min = isWeapon ?
				(int) (10*Math.pow(Math.max(0, increaseMinDamage), 2.5))
				:(int) (10*Math.pow(Math.max(0, increaseMinDamage), 3) + increaseMinDamage*80);
		final int costDMG_Max = isWeapon ?
				(int) (2*Math.pow(Math.max(0, increaseMaxDamage), 2.1))
				:(int) (2*Math.pow(Math.max(0, increaseMaxDamage), 3) + increaseMaxDamage*20);
		final int costCS = (int) (2.2*Math.pow(increaseCriticalSkill, 3));
		final int costCM = (int) (50*Math.pow(Math.max(0, setCriticalMultiplier), 2));

		final int costMaxHP = (int) (30*Math.pow(Math.max(0,increaseMaxHP), 1.2) + 70*increaseMaxHP);
		final int costMaxAP = (int) (50*Math.pow(Math.max(0,increaseMaxAP), 3) + 750*increaseMaxAP);
		final int costMovement = (int) (510*Math.pow(Math.max(0,-increaseMoveCost), 2.5) - 350*increaseMoveCost);
		final int costUseItem = (int)(915*Math.pow(Math.max(0,-increaseUseItemCost), 3) - 430*increaseUseItemCost);
		final int costReequip = (int)(450*Math.pow(Math.max(0,-increaseReequipCost), 2) - 250*increaseReequipCost);

		return costBC + costAC + costAP + costDR + costDMG_Min + costDMG_Max + costCS + costCM
				+ costMaxHP + costMaxAP
				+ costMovement + costUseItem + costReequip;
	}
}

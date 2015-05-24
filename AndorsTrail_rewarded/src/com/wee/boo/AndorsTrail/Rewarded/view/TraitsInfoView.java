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
package com.wee.boo.AndorsTrail.Rewarded.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Actor;
import com.wee.boo.AndorsTrail.Rewarded.util.Range;

public final class TraitsInfoView {

	public static void update(ViewGroup group, Actor actor) {
		TableLayout actorinfo_stats_table = (TableLayout) group.findViewById(R.id.actorinfo_stats_table);

		updateTraitsTable(
			actorinfo_stats_table
			,actor.getMoveCost()
			,actor.getAttackCost()
			,actor.getAttackChance()
			,actor.getDamagePotential()
			,actor.getCriticalSkill()
			,actor.getCriticalMultiplier()
			,actor.getBlockChance()
			,actor.getDamageResistance()
			,actor.isImmuneToCriticalHits());

		TextView actorinfo_currentconditions_title = (TextView) group.findViewById(R.id.actorinfo_currentconditions_title);
		ActorConditionList actorinfo_currentconditions = (ActorConditionList) group.findViewById(R.id.actorinfo_currentconditions);
		if (actor.conditions.isEmpty()) {
			actorinfo_currentconditions_title.setVisibility(View.GONE);
			actorinfo_currentconditions.setVisibility(View.GONE);
		} else {
			actorinfo_currentconditions_title.setVisibility(View.VISIBLE);
			actorinfo_currentconditions.setVisibility(View.VISIBLE);
			actorinfo_currentconditions.update(actor.conditions);
		}
	}

	public static void updateTraitsTable(
			ViewGroup group
			,int moveCost
			,int attackCost
			,int attackChance
			,Range damagePotential
			,int criticalSkill
			,float criticalMultiplier
			,int blockChance
			,int damageResistance
			,boolean isImmuneToCriticalHits
		) {
		TableRow row;
		TextView tv;

		tv = (TextView) group.findViewById(R.id.traitsinfo_move_cost);
		tv.setText(Integer.toString(moveCost));

		tv = (TextView) group.findViewById(R.id.traitsinfo_attack_cost);
		tv.setText(Integer.toString(attackCost));

		row = (TableRow) group.findViewById(R.id.traitsinfo_attack_chance_row);
		if (attackChance == 0) {
			row.setVisibility(View.GONE);
		} else {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_attack_chance);
			tv.setText(Integer.toString(attackChance) + '%');
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_attack_damage_row);
		if (damagePotential != null && damagePotential.max != 0) {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_attack_damage);
			tv.setText(damagePotential.toMinMaxString());
		} else {
			row.setVisibility(View.GONE);
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_criticalhit_skill_row);
		if (criticalSkill == 0) {
			row.setVisibility(View.GONE);
		} else {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_criticalhit_skill);
			tv.setText(Integer.toString(criticalSkill));
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_criticalhit_multiplier_row);
		if (criticalMultiplier != 0 && criticalMultiplier != 1) {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_criticalhit_multiplier);
			tv.setText(Float.toString(criticalMultiplier));
		} else {
			row.setVisibility(View.GONE);
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_criticalhit_effectivechance_row);
		if (criticalSkill != 0 && criticalMultiplier != 0 && criticalMultiplier != 1) {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_criticalhit_effectivechance);
			tv.setText(Integer.toString(Actor.getEffectiveCriticalChance(criticalSkill)) + '%');
		} else {
			row.setVisibility(View.GONE);
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_block_chance_row);
		if (blockChance == 0) {
			row.setVisibility(View.GONE);
		} else {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_block_chance);
			tv.setText(Integer.toString(blockChance) + '%');
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_damageresist_row);
		if (damageResistance == 0) {
			row.setVisibility(View.GONE);
		} else {
			row.setVisibility(View.VISIBLE);
			tv = (TextView) group.findViewById(R.id.traitsinfo_damageresist);
			tv.setText(Integer.toString(damageResistance));
		}

		row = (TableRow) group.findViewById(R.id.traitsinfo_is_immune_to_critical_hits_row);
		row.setVisibility(isImmuneToCriticalHits ? View.VISIBLE : View.GONE);
	}
}

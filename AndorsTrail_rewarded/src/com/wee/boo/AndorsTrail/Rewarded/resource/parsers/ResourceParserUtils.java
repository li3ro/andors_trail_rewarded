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
package com.wee.boo.AndorsTrail.Rewarded.resource.parsers;

import com.wee.boo.AndorsTrail.Rewarded.AndorsTrailApplication;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.traits.AbilityModifierTraits;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.traits.StatsModifierTraits;
import com.wee.boo.AndorsTrail.Rewarded.resource.DynamicTileLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.VisualEffectCollection;
import com.wee.boo.AndorsTrail.Rewarded.resource.parsers.json.JsonFieldNames;
import com.wee.boo.AndorsTrail.Rewarded.util.ConstRange;
import com.wee.boo.AndorsTrail.Rewarded.util.L;
import com.wee.boo.AndorsTrail.Rewarded.util.Size;

import org.json.JSONException;
import org.json.JSONObject;

public final class ResourceParserUtils {

	public static int parseImageID(DynamicTileLoader tileLoader, String s) {
		String[] parts = s.split(":");
		return tileLoader.prepareTileID(parts[0], Integer.parseInt(parts[1]));
	}

	private static final Size size1x1 = new Size(1, 1);
	public static Size parseSize(String s, final Size defaultSize) {
		if (s == null || s.length() <= 0) return defaultSize;
		if (s.equals("1x1")) return size1x1;
		String[] parts = s.split("x");
		if (parts.length < 2) return defaultSize;
		return new Size(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	public static int parseInt(String s, int defaultValue) {
		if (s == null || s.length() <= 0) return defaultValue;
		return Integer.parseInt(s);
	}

	private static final ConstRange zero_or_one = new ConstRange(1, 0);
	private static final ConstRange one = new ConstRange(1, 1);
	private static final ConstRange five = new ConstRange(5, 5);
	private static final ConstRange ten = new ConstRange(10, 10);
	public static ConstRange parseConstRange(JSONObject o) throws JSONException {
		if (o == null) return null;

		return new ConstRange(
				o.getInt(JsonFieldNames.Range.max),
				o.optInt(JsonFieldNames.Range.min)
		);
	}

	public static final ConstRange always = one;
	private static final ConstRange often = new ConstRange(100, 70);
	private static final ConstRange animalpart = new ConstRange(100, 30);
	private static final ConstRange sometimes = new ConstRange(100, 25);
	private static final ConstRange rare_20 = new ConstRange(100, 20);
	private static final ConstRange rare_10 = new ConstRange(100, 10);
	private static final ConstRange seldom = new ConstRange(100, 5);
	private static final ConstRange unique = new ConstRange(100, 1);
	private static final ConstRange extraordinary = new ConstRange(1000, 1);
	private static final ConstRange legendary = new ConstRange(10000, 1);
	public static ConstRange parseChance(String v) {
		if (v.equals("100")) return always;
		else if (v.equals("70")) return often;
		else if (v.equals("30")) return animalpart;
		else if (v.equals("25")) return sometimes;
		else if (v.equals("20")) return rare_20;
		else if (v.equals("10")) return rare_10;
		else if (v.equals("5")) return seldom;
		else if (v.equals("1")) return unique;
		else if (v.equals("1/1000")) return extraordinary;
		else if (v.equals("1/10000")) return legendary;
		else if (v.indexOf('/') >= 0) {
			int c = v.indexOf('/');
			int a = parseInt(v.substring(0, c), 1);
			int b = parseInt(v.substring(c+1), 100);
			return new ConstRange(b, a);
		}
		else return new ConstRange(100, parseInt(v, 10));
	}

	public static StatsModifierTraits parseStatsModifierTraits(JSONObject o) throws JSONException {
		if (o == null) return null;

		ConstRange boostCurrentHP = parseConstRange(o.optJSONObject(JsonFieldNames.StatsModifierTraits.increaseCurrentHP));
		ConstRange boostCurrentAP = parseConstRange(o.optJSONObject(JsonFieldNames.StatsModifierTraits.increaseCurrentAP));
		if (boostCurrentHP == null && boostCurrentAP == null) {
			if (AndorsTrailApplication.DEVELOPMENT_VALIDATEDATA) {
				L.log("OPTIMIZE: Tried to parseStatsModifierTraits , where hasEffect=" + o.toString() + ", but all data was empty.");
			}
			return null;
		} else {
			return new StatsModifierTraits(
					VisualEffectCollection.VisualEffectID.fromString(o.optString(JsonFieldNames.StatsModifierTraits.visualEffectID, null), null)
					,boostCurrentHP
					,boostCurrentAP
			);
		}
	}

	public static AbilityModifierTraits parseAbilityModifierTraits(JSONObject o) throws JSONException {
		if (o == null) return null;

		ConstRange increaseAttackDamage = parseConstRange(o.optJSONObject(JsonFieldNames.AbilityModifierTraits.increaseAttackDamage));
		return new AbilityModifierTraits(
				o.optInt(JsonFieldNames.AbilityModifierTraits.increaseMaxHP, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseMaxAP, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseMoveCost, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseUseItemCost, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseReequipCost, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseAttackCost, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseAttackChance, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseBlockChance, 0)
				,increaseAttackDamage != null ? increaseAttackDamage.current : 0
				,increaseAttackDamage != null ? increaseAttackDamage.max : 0
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseCriticalSkill, 0)
				,(float)o.optDouble(JsonFieldNames.AbilityModifierTraits.setCriticalMultiplier, 0)
				,o.optInt(JsonFieldNames.AbilityModifierTraits.increaseDamageResistance, 0)
		);
	}

	public static ConstRange parseQuantity(JSONObject obj) throws JSONException {
		final int min = obj.getInt(JsonFieldNames.Range.min);
		final int max = obj.getInt(JsonFieldNames.Range.max);
		if (min == 0 && max == 1) return zero_or_one;
		if (min == 1 && max == 1) return one;
		if (min == 5 && max == 5) return five;
		if (min == 10 && max == 10) return ten;
		return parseConstRange(obj);
	}
}

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
package com.wee.boo.AndorsTrail.Rewarded.model.ability;

import com.wee.boo.AndorsTrail.Rewarded.model.actor.Player;

public final class SkillInfo {
	public static final int MAXLEVEL_NONE = -1;
	public static enum LevelUpType {
		alwaysShown
		,onlyByQuests
		,firstLevelRequiresQuest
	}

	public final SkillCollection.SkillID id;
	public final int maxLevel;
	public final LevelUpType levelupVisibility;
	public final SkillLevelRequirement[] levelupRequirements;
	public SkillInfo(
			SkillCollection.SkillID id
			, int maxLevel
			, LevelUpType levelupVisibility
			, SkillLevelRequirement[] levelupRequirements
	) {
		this.id = id;
		this.maxLevel = maxLevel;
		this.levelupVisibility = levelupVisibility;
		this.levelupRequirements = levelupRequirements;
	}

	public boolean hasMaxLevel() {
		if (maxLevel == MAXLEVEL_NONE) return false;
		else return true;
	}

	public boolean hasLevelupRequirements() {
		return levelupRequirements != null;
	}

	public boolean canLevelUpSkillTo(Player player, int requestedSkillLevel) {
		if (!hasLevelupRequirements()) return true;

		for (SkillLevelRequirement requirement : levelupRequirements) {
			if (!requirement.isSatisfiedByPlayer(player, requestedSkillLevel)) return false;
		}
		return true;
	}

	public static final class SkillLevelRequirement {
		public static enum RequirementType {
			skillLevel
			,experienceLevel
			,playerStat
		}
		public final RequirementType requirementType;
		public final String skillOrStatID;
		public final int everySkillLevelRequiresThisAmount;
		public final int initialRequiredAmount;

		private SkillLevelRequirement(RequirementType requirementType, int everySkillLevelRequiresThisAmount, int initialRequiredAmount, String skillOrStatID) {
			this.requirementType = requirementType;
			this.skillOrStatID = skillOrStatID;
			this.everySkillLevelRequiresThisAmount = everySkillLevelRequiresThisAmount;
			this.initialRequiredAmount = initialRequiredAmount;
		}

		public static SkillLevelRequirement requireOtherSkill(SkillCollection.SkillID skillID, int everySkillLevelRequiresThisAmount) {
			return new SkillLevelRequirement(RequirementType.skillLevel, everySkillLevelRequiresThisAmount, 0, skillID.name());
		}
		public static SkillLevelRequirement requireExperienceLevels(int everySkillLevelRequiresThisAmount, int initialRequiredAmount) {
			return new SkillLevelRequirement(RequirementType.experienceLevel, everySkillLevelRequiresThisAmount, initialRequiredAmount, null);
		}
		public static SkillLevelRequirement requirePlayerStats(Player.StatID statID, int everySkillLevelRequiresThisAmount, int initialRequiredAmount) {
			return new SkillLevelRequirement(RequirementType.playerStat, everySkillLevelRequiresThisAmount, initialRequiredAmount, statID.name());
		}

		public boolean isSatisfiedByPlayer(Player player, int requestedSkillLevel) {
			final int minimumValueRequired = getRequiredValue(requestedSkillLevel);
			final int playerValue = getRequirementActualValue(player);
			if (playerValue >= minimumValueRequired) return true;
			return false;
		}

		public int getRequiredValue(int requestedSkillLevel) {
			return requestedSkillLevel * everySkillLevelRequiresThisAmount + initialRequiredAmount;
		}

		private int getRequirementActualValue(Player player) {
			switch (requirementType) {
			case skillLevel: return player.getSkillLevel(SkillCollection.SkillID.valueOf(skillOrStatID));
			case experienceLevel: return player.getLevel();
			case playerStat: return player.getStatValue(Player.StatID.valueOf(skillOrStatID));
			default: return 0;
			}
		}
	}
}

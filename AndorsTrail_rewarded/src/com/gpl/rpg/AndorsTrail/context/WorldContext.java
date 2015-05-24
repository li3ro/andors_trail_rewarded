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
package com.gpl.rpg.AndorsTrail.context;

import com.gpl.rpg.AndorsTrail.model.ModelContainer;
import com.gpl.rpg.AndorsTrail.model.ability.ActorConditionTypeCollection;
import com.gpl.rpg.AndorsTrail.model.ability.SkillCollection;
import com.gpl.rpg.AndorsTrail.model.actor.MonsterTypeCollection;
import com.gpl.rpg.AndorsTrail.model.item.DropListCollection;
import com.gpl.rpg.AndorsTrail.model.item.ItemCategoryCollection;
import com.gpl.rpg.AndorsTrail.model.item.ItemTypeCollection;
import com.gpl.rpg.AndorsTrail.model.map.MapCollection;
import com.gpl.rpg.AndorsTrail.model.quest.QuestCollection;
import com.gpl.rpg.AndorsTrail.resource.ConversationLoader;
import com.gpl.rpg.AndorsTrail.resource.VisualEffectCollection;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileManager;

public final class WorldContext {
	//Objectcollections
	public final ConversationLoader conversationLoader;
	public final ItemTypeCollection itemTypes;
	public final ItemCategoryCollection itemCategories;
	public final MonsterTypeCollection monsterTypes;
	public final VisualEffectCollection visualEffectTypes;
	public final DropListCollection dropLists;
	public final QuestCollection quests;
	public final ActorConditionTypeCollection actorConditionsTypes;
	public final SkillCollection skills;

	//Objectcollections
	public final TileManager tileManager;

	//Model
	public final MapCollection maps;
	public ModelContainer model;

	public WorldContext() {
		this.conversationLoader = new ConversationLoader();
		this.itemTypes = new ItemTypeCollection();
		this.itemCategories = new ItemCategoryCollection();
		this.monsterTypes = new MonsterTypeCollection();
		this.visualEffectTypes = new VisualEffectCollection();
		this.dropLists = new DropListCollection();
		this.tileManager = new TileManager();
		this.maps = new MapCollection();
		this.quests = new QuestCollection();
		this.actorConditionsTypes = new ActorConditionTypeCollection();
		this.skills = new SkillCollection();
	}
	public WorldContext(WorldContext copy) {
		this.conversationLoader = copy.conversationLoader;
		this.itemTypes = copy.itemTypes;
		this.itemCategories = copy.itemCategories;
		this.monsterTypes = copy.monsterTypes;
		this.visualEffectTypes = copy.visualEffectTypes;
		this.dropLists = copy.dropLists;
		this.tileManager = copy.tileManager;
		this.maps = copy.maps;
		this.quests = copy.quests;
		this.model = copy.model;
		this.actorConditionsTypes = copy.actorConditionsTypes;
		this.skills = copy.skills;
	}
	public void resetForNewGame() {
		maps.resetForNewGame();
	}
}

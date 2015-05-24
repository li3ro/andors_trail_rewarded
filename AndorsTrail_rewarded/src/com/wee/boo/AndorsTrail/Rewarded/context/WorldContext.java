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
package com.wee.boo.AndorsTrail.Rewarded.context;

import com.wee.boo.AndorsTrail.Rewarded.model.ModelContainer;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorConditionTypeCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.ability.SkillCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.MonsterTypeCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.item.DropListCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemCategoryCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.item.ItemTypeCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.map.MapCollection;
import com.wee.boo.AndorsTrail.Rewarded.model.quest.QuestCollection;
import com.wee.boo.AndorsTrail.Rewarded.resource.ConversationLoader;
import com.wee.boo.AndorsTrail.Rewarded.resource.VisualEffectCollection;
import com.wee.boo.AndorsTrail.Rewarded.resource.tiles.TileManager;

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

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
package com.wee.boo.AndorsTrail.Rewarded.controller.listeners;

import com.wee.boo.AndorsTrail.Rewarded.model.ability.ActorCondition;
import com.wee.boo.AndorsTrail.Rewarded.model.actor.Actor;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class ActorConditionListeners extends ListOfListeners<ActorConditionListener> implements ActorConditionListener {

	private final Function2<ActorConditionListener, Actor, ActorCondition> onActorConditionAdded = new Function2<ActorConditionListener, Actor, ActorCondition>() {
		@Override public void call(ActorConditionListener listener, Actor actor, ActorCondition condition) { listener.onActorConditionAdded(actor, condition); }
	};
	private final Function2<ActorConditionListener, Actor, ActorCondition> onActorConditionRemoved = new Function2<ActorConditionListener, Actor, ActorCondition>() {
		@Override public void call(ActorConditionListener listener, Actor actor, ActorCondition condition) { listener.onActorConditionRemoved(actor, condition); }
	};
	private final Function2<ActorConditionListener, Actor, ActorCondition> onActorConditionDurationChanged = new Function2<ActorConditionListener, Actor, ActorCondition>() {
		@Override public void call(ActorConditionListener listener, Actor actor, ActorCondition condition) { listener.onActorConditionDurationChanged(actor, condition); }
	};
	private final Function2<ActorConditionListener, Actor, ActorCondition> onActorConditionMagnitudeChanged = new Function2<ActorConditionListener, Actor, ActorCondition>() {
		@Override public void call(ActorConditionListener listener, Actor actor, ActorCondition condition) { listener.onActorConditionMagnitudeChanged(actor, condition); }
	};
	private final Function2<ActorConditionListener, Actor, ActorCondition> onActorConditionRoundEffectApplied = new Function2<ActorConditionListener, Actor, ActorCondition>() {
		@Override public void call(ActorConditionListener listener, Actor actor, ActorCondition condition) { listener.onActorConditionRoundEffectApplied(actor, condition); }
	};

	@Override
	public void onActorConditionAdded(Actor actor, ActorCondition condition) {
		callAllListeners(this.onActorConditionAdded, actor, condition);
	}

	@Override
	public void onActorConditionRemoved(Actor actor, ActorCondition condition) {
		callAllListeners(this.onActorConditionRemoved, actor, condition);
	}

	@Override
	public void onActorConditionDurationChanged(Actor actor, ActorCondition condition) {
		callAllListeners(this.onActorConditionDurationChanged, actor, condition);
	}

	@Override
	public void onActorConditionMagnitudeChanged(Actor actor, ActorCondition condition) {
		callAllListeners(this.onActorConditionMagnitudeChanged, actor, condition);
	}

	@Override
	public void onActorConditionRoundEffectApplied(Actor actor, ActorCondition condition) {
		callAllListeners(this.onActorConditionRoundEffectApplied, actor, condition);
	}
}

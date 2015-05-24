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

import com.wee.boo.AndorsTrail.Rewarded.model.actor.Actor;
import com.wee.boo.AndorsTrail.Rewarded.util.ListOfListeners;

public final class ActorStatsListeners extends ListOfListeners<ActorStatsListener> implements ActorStatsListener {

	private final Function1<ActorStatsListener, Actor> onActorHealthChanged = new Function1<ActorStatsListener, Actor>() {
		@Override public void call(ActorStatsListener listener, Actor actor) { listener.onActorHealthChanged(actor); }
	};

	private final Function1<ActorStatsListener, Actor> onActorAPChanged = new Function1<ActorStatsListener, Actor>() {
		@Override public void call(ActorStatsListener listener, Actor actor) { listener.onActorAPChanged(actor); }
	};

	private final Function2<ActorStatsListener, Actor, Integer> onActorAttackCostChanged = new Function2<ActorStatsListener, Actor, Integer>() {
		@Override public void call(ActorStatsListener listener, Actor actor, Integer newAttackCost) { listener.onActorAttackCostChanged(actor, newAttackCost); }
	};

	private final Function2<ActorStatsListener, Actor, Integer> onActorMoveCostChanged = new Function2<ActorStatsListener, Actor, Integer>() {
		@Override public void call(ActorStatsListener listener, Actor actor, Integer newMoveCost) { listener.onActorMoveCostChanged(actor, newMoveCost); }
	};

	@Override
	public void onActorHealthChanged(Actor actor) {
		callAllListeners(this.onActorHealthChanged, actor);
	}

	@Override
	public void onActorAPChanged(Actor actor) {
		callAllListeners(this.onActorAPChanged, actor);
	}

	@Override
	public void onActorAttackCostChanged(Actor actor, int newAttackCost) {
		callAllListeners(this.onActorAttackCostChanged, actor, newAttackCost);
	}

	@Override
	public void onActorMoveCostChanged(Actor actor, int newMoveCost) {
		callAllListeners(this.onActorMoveCostChanged, actor, newMoveCost);
	}
}

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
package com.wee.boo.AndorsTrail.Rewarded.util;

import android.os.Handler;
import android.os.Message;

public final class TimedMessageTask extends Handler {
	private final long interval;
	private final boolean requireIntervalBeforeFirstTick;
	private final Callback callback;
	private long nextTickTime;
	private boolean hasQueuedTick = false;
	private boolean isAlive = false;

	public TimedMessageTask(Callback callback, long interval, boolean requireIntervalBeforeFirstTick) {
		this.interval = interval;
		this.requireIntervalBeforeFirstTick = requireIntervalBeforeFirstTick;
		this.callback = callback;
		this.nextTickTime = System.currentTimeMillis() + interval;
	}

	@Override
	public void handleMessage(Message msg) {
		if (!isAlive) return;
		if (!hasQueuedTick) return;
		hasQueuedTick = false;
		tick();
	}

	private void tick() {
		nextTickTime = System.currentTimeMillis() + interval;
		boolean continueTicking = callback.onTick(this);
		if (continueTicking) queueAnotherTick();
	}

	private void sleep(long delayMillis) {
		this.removeMessages(0);
		sendMessageDelayed(obtainMessage(0), delayMillis);
	}

	private boolean hasElapsedIntervalTime() {
		return System.currentTimeMillis() >= nextTickTime;
	}

	public void queueAnotherTick() {
		if (hasQueuedTick) return;
		hasQueuedTick = true;
		sleep(interval);
	}

	private boolean shouldCauseTickOnStart() {
		if (requireIntervalBeforeFirstTick) return false;
		if (hasQueuedTick) return false;
		if (!hasElapsedIntervalTime()) return false;
		return true;
	}

	public void start() {
		isAlive = true;
		if (shouldCauseTickOnStart()) tick();
		else queueAnotherTick();
	}

	public void stop() {
		hasQueuedTick = false;
		isAlive = false;
	}

	public interface Callback {
		public boolean onTick(TimedMessageTask task);
	}
}

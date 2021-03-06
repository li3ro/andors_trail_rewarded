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
package com.wee.boo.AndorsTrail.Rewarded;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.wee.boo.AndorsTrail.Rewarded.context.ControllerContext;
import com.wee.boo.AndorsTrail.Rewarded.context.WorldContext;
import com.wee.boo.AndorsTrail.Rewarded.model.ModelContainer;
import com.wee.boo.AndorsTrail.Rewarded.resource.ResourceLoader;
import com.wee.boo.AndorsTrail.Rewarded.savegames.Savegames;

import java.lang.ref.WeakReference;

public final class WorldSetup {

	private final WorldContext world;
	private final ControllerContext controllers;
	private final WeakReference<Context> androidContext;
	private boolean isResourcesInitialized = false;
	private boolean isInitializingResources = false;
	private WeakReference<OnResourcesLoadedListener> onResourcesLoadedListener;
	private WeakReference<OnSceneLoadedListener> onSceneLoadedListener;
	private Object sceneLoaderId;

	public boolean createNewCharacter = false;
	public int loadFromSlot = Savegames.SLOT_QUICKSAVE;
	public boolean isSceneReady = false;
	public String newHeroName;
	private Savegames.LoadSavegameResult loadResult;

	public WorldSetup(WorldContext world, ControllerContext controllers, Context androidContext) {
		this.world = world;
		this.controllers = controllers;
		this.androidContext = new WeakReference<Context>(androidContext);
	}

	public void setOnResourcesLoadedListener(OnResourcesLoadedListener listener) {
		synchronized (this) {
			onResourcesLoadedListener = null;
			if (isResourcesInitialized) {
				if (listener != null) listener.onResourcesLoaded();
				return;
			}
			onResourcesLoadedListener = new WeakReference<WorldSetup.OnResourcesLoadedListener>(listener);
		}
	}

	public void startResourceLoader(final Resources r) {
		if (isResourcesInitialized) return;

		synchronized (this) {
			if (isInitializingResources) return;
			isInitializingResources = true;
		}

		(new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				ResourceLoader.loadResources(world, r);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				synchronized (WorldSetup.this) {
					isResourcesInitialized = true;
					isInitializingResources = false;

					if (onResourcesLoadedListener == null) return;
					WorldSetup.OnResourcesLoadedListener listener = onResourcesLoadedListener.get();
					onResourcesLoadedListener = null;
					if (listener == null) return;
					listener.onResourcesLoaded();
				}
			}
		}).execute();
	}

	public void startCharacterSetup(final OnSceneLoadedListener listener) {
		synchronized (WorldSetup.this) {
			this.onSceneLoadedListener = new WeakReference<OnSceneLoadedListener>(listener);
		}
		startSceneLoader();
	}
	public void removeOnSceneLoadedListener(final OnSceneLoadedListener listener) {
		synchronized (WorldSetup.this) {
			if (this.onSceneLoadedListener == null) return;
			if (this.onSceneLoadedListener.get() == listener) this.onSceneLoadedListener = null;
		}
	}

	private final Object onlyOneThreadAtATimeMayLoadSavegames = new Object();
	private void startSceneLoader() {
		isSceneReady = false;
		final Object thisLoaderId = new Object();
		synchronized (WorldSetup.this) {
			sceneLoaderId = thisLoaderId;
		}

		(new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... arg0) {
				synchronized (onlyOneThreadAtATimeMayLoadSavegames) {
					if (world.model != null) world.resetForNewGame();
					if (createNewCharacter) {
						createNewWorld();
						loadResult = Savegames.LoadSavegameResult.success;
					} else {
						loadResult = continueWorld();
					}
					createNewCharacter = false;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				synchronized (WorldSetup.this) {
					if (sceneLoaderId != thisLoaderId) return; // Some other thread has started after we started.
					isSceneReady = true;

					if (onSceneLoadedListener == null) return;
					OnSceneLoadedListener o = onSceneLoadedListener.get();
					onSceneLoadedListener = null;
					if (o == null) return;

					if (loadResult == Savegames.LoadSavegameResult.success) {
						o.onSceneLoaded();
					} else {
						o.onSceneLoadFailed(loadResult);
					}
				}
			}
		}).execute();
	}

	private Savegames.LoadSavegameResult continueWorld() {
		Context ctx = androidContext.get();
		return Savegames.loadWorld(world, controllers, ctx, loadFromSlot);
	}

	private void createNewWorld() {
		Context ctx = androidContext.get();
		world.model = new ModelContainer();
		world.model.player.initializeNewPlayer(world.dropLists, newHeroName);

		controllers.actorStatsController.recalculatePlayerStats(world.model.player);
		controllers.movementController.respawnPlayer(ctx.getResources());
		controllers.mapController.lotsOfTimePassed();
	}


	public static interface OnSceneLoadedListener {
		void onSceneLoaded();
		void onSceneLoadFailed(Savegames.LoadSavegameResult loadResult);
	}
	public interface OnResourcesLoadedListener {
		void onResourcesLoaded();
	}
}

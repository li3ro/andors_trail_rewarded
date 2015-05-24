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
package com.wee.boo.AndorsTrail.Rewarded.resource;

import android.graphics.Color;

import com.wee.boo.AndorsTrail.Rewarded.R;
import com.wee.boo.AndorsTrail.Rewarded.util.ConstRange;

public final class VisualEffectCollection {

	public static enum VisualEffectID {
		redSplash
		,blueSwirl
		,greenSplash;

		public static VisualEffectID fromString(String s, VisualEffectID default_) {
			if (s == null) return default_;
			return valueOf(s);
		}
	}

	private final VisualEffect[] effects = new VisualEffect[VisualEffectID.values().length];

	public void initialize(DynamicTileLoader loader) {
		effects[VisualEffectID.redSplash.ordinal()] = createEffect(loader, R.drawable.effect_blood4, new ConstRange(14, 0), 400, Color.RED);
		effects[VisualEffectID.blueSwirl.ordinal()] = createEffect(loader, R.drawable.effect_heal2, new ConstRange(16, 0), 400, Color.rgb(150, 150, 255));
		effects[VisualEffectID.greenSplash.ordinal()] = createEffect(loader, R.drawable.effect_poison1, new ConstRange(16, 0), 400, Color.GREEN);
	}

	public VisualEffect getVisualEffect(VisualEffectID effectID) {
		return effects[effectID.ordinal()];
	}

	private static VisualEffect createEffect(DynamicTileLoader loader, int drawableID, ConstRange frameRange, int duration, int textColor) {
		int[] frameIconIDs = new int[frameRange.max - frameRange.current];
		for(int i = 0; i < frameIconIDs.length; ++i) {
			frameIconIDs[i] = loader.prepareTileID(drawableID, frameRange.current + i);
		}
		return new VisualEffect(frameIconIDs, duration, textColor);
	}

	public static final class VisualEffect {
		public final int[] frameIconIDs;
		public final int duration; // milliseconds
		public final int textColor;
		//public final int fps = ModelContainer.attackAnimationFPS;
		//public final int millisecondPerFrame = 1000 / fps;
		//public final int totalFrames = duration / millisecondPerFrame;
		public final int fps;
		public final int millisecondPerFrame;
		public final int totalFrames;
		public final int lastFrame;

		public VisualEffect(int[] frameIconIDs, int duration, int textColor) {
			this.frameIconIDs = frameIconIDs;
			this.duration = duration;
			this.textColor = textColor;
			totalFrames = frameIconIDs.length;
			lastFrame = totalFrames - 1;
			millisecondPerFrame = duration / totalFrames;
			fps = 1000 / millisecondPerFrame;
		}
	}
}

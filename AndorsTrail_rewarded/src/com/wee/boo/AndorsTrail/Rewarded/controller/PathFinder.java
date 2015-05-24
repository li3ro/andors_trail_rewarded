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
package com.wee.boo.AndorsTrail.Rewarded.controller;

import com.wee.boo.AndorsTrail.Rewarded.util.Coord;
import com.wee.boo.AndorsTrail.Rewarded.util.CoordRect;

import java.util.Arrays;

public class PathFinder {
	private final int maxWidth;
	private final int maxHeight;
	private final boolean visited[];
	private final ListOfCoords visitQueue;
	private final EvaluateWalkable map;

	public PathFinder(int maxWidth, int maxHeight, EvaluateWalkable map) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.map = map;
		this.visited = new boolean[maxWidth*maxHeight];
		this.visitQueue = new ListOfCoords(maxWidth*maxHeight);
	}

	public interface EvaluateWalkable {
		public boolean isWalkable(CoordRect r);
	}

	public boolean findPathBetween(final CoordRect from, final Coord to, CoordRect nextStep) {
		int iterations = 0;
		if (from.contains(to)) return false;

		Coord measureDistanceTo = from.topLeft;
		Coord p = nextStep.topLeft;
		Arrays.fill(visited, false);
		visitQueue.reset();

		visitQueue.push(to.x, to.y, 0);
		visited[(to.y * maxWidth) + to.x] = true;

		while (!visitQueue.isEmpty()) {
			visitQueue.popFirst(p);
			++iterations;

			if (iterations > 100) return false;

			if (from.isAdjacentTo(p)) return true;

			p.x -= 1; visit(nextStep, measureDistanceTo);
			p.x += 2; visit(nextStep, measureDistanceTo);
			p.x -= 1; p.y -= 1; visit(nextStep, measureDistanceTo);
			p.y += 2; visit(nextStep, measureDistanceTo);
			p.x -= 1; visit(nextStep, measureDistanceTo);
			p.x += 2; visit(nextStep, measureDistanceTo);
			p.y -= 2; visit(nextStep, measureDistanceTo);
			p.x -= 2; visit(nextStep, measureDistanceTo);
		}
		return false;
	}

	private void visit(CoordRect r, Coord measureDistanceTo) {
		final int x = r.topLeft.x;
		final int y = r.topLeft.y;

		if (x < 0) return;
		if (y < 0) return;
		if (x >= maxWidth) return;
		if (y >= maxHeight) return;

		final int i = (y * maxWidth) + x;
		if (visited[i]) return;
		visited[i] = true;
		if (!map.isWalkable(r)) return;

		int dx = (measureDistanceTo.x - x);
		int dy = (measureDistanceTo.y - y);
		visitQueue.push(x, y, dx * dx + dy * dy);
	}

	private static final class ListOfCoords {
		private final int xCoords[];
		private final int yCoords[];
		private final int weights[];
		private final int maxIndex;
		private int lastIndex; // Index of the last coord that was inserted
		private int frontIndex; // Index to the first coord that is not discarded
		private static final int DISCARDED = -1;

		public ListOfCoords(int maxSize) {
			this.maxIndex = maxSize-1;
			this.xCoords = new int[maxSize];
			this.yCoords = new int[maxSize];
			this.weights = new int[maxSize];
		}

		public void reset() {
			lastIndex = -1;
			frontIndex = 0;
		}

		public void push(int x, int y, int weight) {
			if (lastIndex == maxIndex) return;
			++lastIndex;
			xCoords[lastIndex] = x;
			yCoords[lastIndex] = y;
			weights[lastIndex] = weight;
		}

		public int popFirst(Coord dest) {
			int i = frontIndex;
			int lowestWeightIndex = i;
			int lowestWeight = weights[i];
			++i;
			for(;i <= lastIndex; ++i) {
				if (weights[i] == DISCARDED) continue;
				if (weights[i] < lowestWeight) {
					lowestWeightIndex = i;
					lowestWeight = weights[i];
				}
			}
			dest.x = xCoords[lowestWeightIndex];
			dest.y = yCoords[lowestWeightIndex];
			weights[lowestWeightIndex] = DISCARDED;

			// Increase frontIndex to the first index that is not discarded.
			while (frontIndex <= lastIndex) {
				if (weights[frontIndex] == DISCARDED) ++frontIndex;
				else break;
			}

			return lowestWeight;
		}

		public boolean isEmpty() {
			return frontIndex > lastIndex;
		}
	}
}

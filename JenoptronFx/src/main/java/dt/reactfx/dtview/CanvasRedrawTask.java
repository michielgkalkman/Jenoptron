/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package dt.reactfx.dtview;

import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

// From: http://stackoverflow.com/questions/26082732/what-approach-should-i-use-for-javafx-canvas-multithreading
// generic task that redraws the canvas when new data arrives
// (but not more often than 60 times per second).
public abstract class CanvasRedrawTask<T> extends AnimationTimer {
	private final AtomicReference<T> data = new AtomicReference<>(null);
	protected final Canvas canvas;

	public CanvasRedrawTask(final Canvas canvas) {
		this.canvas = canvas;
	}

	public void requestRedraw(final T dataToDraw) {
		data.set(dataToDraw);
		start(); // in case, not already started
	}

	@Override
	public void handle(final long now) {
		// check if new data is available
		final T dataToDraw = data.getAndSet(null);
		if (dataToDraw != null) {
			redraw(canvas.getGraphicsContext2D(), dataToDraw);
		}
	}

	protected abstract void redraw(GraphicsContext context, T data);
}
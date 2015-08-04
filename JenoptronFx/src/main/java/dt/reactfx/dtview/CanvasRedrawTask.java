package dt.reactfx.dtview;

import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

// From: http://stackoverflow.com/questions/26082732/what-approach-should-i-use-for-javafx-canvas-multithreading
// generic task that redraws the canvas when new data arrives
// (but not more often than 60 times per second).
public abstract class CanvasRedrawTask<T> extends AnimationTimer {
	private final AtomicReference<T> data = new AtomicReference<T>(null);
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
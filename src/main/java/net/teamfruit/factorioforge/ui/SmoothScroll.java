package net.teamfruit.factorioforge.ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

public class SmoothScroll {
	private final Node node;
	private final DoubleProperty property;

	private SmoothScroll(final Node node, final DoubleProperty property) {
		this.node = node;
		this.property = property;
	}

	private final Timeline timeline = new Timeline();
	private double scrollAmount = 0;

	private static double clampIfNotChange(final double t, final double c) {
		if (t+c<0&&c>0)
			return 0;
		if (t+c>1&&c<0)
			return 1;
		return t+c;
	}

	private static final Interpolator easeOutCubic = Interpolator.SPLINE(0.215, 0.61, 0.355, 1);

	private void onScroll(final ScrollEvent event) {
		this.scrollAmount = clampIfNotChange(this.scrollAmount, -event.getDeltaY()*1/200);
		this.timeline.stop();
		this.timeline.getKeyFrames().clear();
		this.timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(this.property, this.scrollAmount, easeOutCubic)));
		this.timeline.playFromStart();
		event.consume();
	}

	private void applyScroll() {
		this.node.setOnScroll((final ScrollEvent event) -> {
			onScroll(event);
		});
	}

	public static void apply(final ScrollPane target) {
		new SmoothScroll(target.getContent(), target.vvalueProperty()).applyScroll();
	}

	public static void apply(final ScrollBar target) {
		new SmoothScroll(target, target.valueProperty()).applyScroll();
	}
}

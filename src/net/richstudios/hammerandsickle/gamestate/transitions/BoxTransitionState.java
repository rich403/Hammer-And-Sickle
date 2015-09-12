package net.richstudios.hammerandsickle.gamestate.transitions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import net.richstudios.hammerandsickle.gamestate.GameState;
import net.richstudios.hammerandsickle.gamestate.GameStateManager;
import net.richstudios.hammerandsickle.reference.References;

public class BoxTransitionState extends TransitionState {

	protected ArrayList<Rectangle> rectangles;

	private double widthInterval, heightInterval;

	private static final int EXPANDING = 0;
	private static final int DONE_EXPANDING = 1;
	private static final int COLLAPSING = 2;
	private static final int DONE_COLLAPSING = 3;
	private int status = EXPANDING;

	public BoxTransitionState(GameStateManager gsm, GameState prev,
			GameState next) {
		super(gsm, prev, next);
		this.totalTime = 2 * References.FPS;
		rectangles = new ArrayList<Rectangle>();
		widthInterval = Math.round((double) References.WIDTH
				/ (double) this.totalTime);
		heightInterval = Math.round((double) References.HEIGHT
				/ (double) this.totalTime);
	}

	public void update() {
		super.update();
		if (status == EXPANDING) {
			if (timer == 1) {
				rectangles.add(0, new Rectangle(References.WIDTH / 2,
						References.HEIGHT / 2, 0, 0));
			} else if (timer < totalTime) {
				rectangles.get(0).x -= widthInterval;
				rectangles.get(0).y -= heightInterval;
				rectangles.get(0).width += widthInterval * 2;
				rectangles.get(0).height += heightInterval * 2;
				if (timer == totalTime / 2) {
					status = DONE_EXPANDING;
					rectangles.clear();
				}
			}
		} else if (status == DONE_EXPANDING) {
			rectangles.add(new Rectangle(0, 0, References.WIDTH,
					References.HEIGHT / 2));
			rectangles.add(new Rectangle(0, 0, References.WIDTH / 2,
					References.HEIGHT));
			rectangles.add(new Rectangle(0, References.HEIGHT / 2,
					References.WIDTH, References.HEIGHT / 2));
			rectangles.add(new Rectangle(References.WIDTH / 2, 0,
					References.WIDTH / 2, References.HEIGHT));
			status = COLLAPSING;
		} else if (status == COLLAPSING) {
			if (timer < totalTime) {
				rectangles.get(0).height -= heightInterval;
				rectangles.get(1).width -= widthInterval;
				rectangles.get(2).y += heightInterval;
				rectangles.get(3).x += widthInterval;
			} else if (timer == totalTime) {
				status = DONE_COLLAPSING;
				finish();
			}
		}
	}

	public void draw(Graphics2D g) {
		if (status == EXPANDING) {
			prevState.draw(g);
		} else if (status == COLLAPSING) {
			nextState.draw(g);
		}
		g.setColor(Color.BLACK);
		for (int i = 0; i < rectangles.size(); i++) {
			g.fill(rectangles.get(i));
		}
	}
}

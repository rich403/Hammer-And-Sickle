package net.richstudios.hammerandsickle.gamestate.transitions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.gamestate.GameState;
import net.richstudios.hammerandsickle.gamestate.GameStateManager;
import net.richstudios.hammerandsickle.reference.References;

public class CloseTransitionState extends TransitionState {

	protected ArrayList<Rectangle> rectangles;

	private double heightInterval;

	private static final int CLOSING = 0;
	private static final int CLOSED = 1;
	private int status = CLOSING;

	public CloseTransitionState(GameStateManager gsm, GameState prevState) {
		super(gsm, prevState, null);
		this.totalTime = (float) (0.5 * References.FPS);
		rectangles = new ArrayList<Rectangle>();
		this.heightInterval = Math.round(((double) References.HEIGHT / 2D) / (double) this.totalTime);
		Sound.stopAll();
	}

	public void update() {
		super.update();
		if (status == CLOSING) {
			if (timer == 1) {
				rectangles.add(0, new Rectangle(0, 0, References.WIDTH, 0));
				rectangles.add(1, new Rectangle(0, References.HEIGHT, References.WIDTH, 0));
			} else if (timer <= totalTime) {
				rectangles.get(0).height += heightInterval;
				rectangles.get(1).height += heightInterval;
				rectangles.get(1).y -= heightInterval;
			} else if (timer > totalTime) {
				status = CLOSED;
				rectangles.clear();
			}
		} else if (status == CLOSED) {
			try {
				Thread.sleep(500);
				System.exit(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void draw(Graphics2D g) {
		prevState.draw(g);
		g.setColor(Color.BLACK);
		if (status == CLOSING) {
			for (int i = 0; i < rectangles.size(); i++) {
				g.fill(rectangles.get(i));
			}
		} else {
			g.fillRect(0, 0, References.WIDTH, References.HEIGHT);
		}

	}

}

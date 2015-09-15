package net.richstudios.hammerandsickle.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.graphics.hud.Box;
import net.richstudios.hammerandsickle.graphics.hud.Hud;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class NewGameState extends GameState {

	private static final int FRAME_WIDTH = 50, FRAME_HEIGHT = 50, FRAME_SIZE = 1, MAX_OVERLAY_APLHA = 120;
	private static final int FRAME_X = References.WIDTH / 2 - (FRAME_WIDTH * Box.SIZE * FRAME_SIZE) / 2, FRAME_Y = References.HEIGHT -(FRAME_HEIGHT * Box.SIZE * FRAME_SIZE);
	private static final float CHANGE_TIME = 0.7f;
	
	private GameState lastState;
	private Color overlayColor;
	private int overlayAlpha = 0;
	private float alphaStep = (float) MAX_OVERLAY_APLHA / (CHANGE_TIME * (float) References.FPS);

	private Hud frame;
	private Box bgbox;

	private int x, y;
	private float yStep = (References.HEIGHT - (float) FRAME_Y) / (CHANGE_TIME * (float) References.FPS);

	private static final int ENTERING = 1, DONE_ENTERING = 2, WAITING = 5, CANCELING = 6, CANCELED = 7;
	private int status = ENTERING;

	public NewGameState(GameStateManager gsm, GameState lastState) {
		super(gsm);
		this.lastState = lastState;
		frame = new Hud();
		overlayColor = new Color(0, 0, 0, 0);
		x = FRAME_X;
		y = References.HEIGHT;
		bgbox = new Box(0, 0, FRAME_WIDTH, FRAME_HEIGHT + 1, FRAME_SIZE);
		frame.add(bgbox);
	}

	public void init() {

	}

	public void update() {
		if (status == ENTERING) {
			overlayAlpha += (int) alphaStep;
			y -= (int) yStep;
			if (overlayAlpha >= MAX_OVERLAY_APLHA || y <= FRAME_Y) {
				overlayAlpha = MAX_OVERLAY_APLHA;
				y = FRAME_Y;
				status = DONE_ENTERING;
			}
			overlayColor = new Color(0, 0, 0, overlayAlpha);
		} else if (status == DONE_ENTERING) {
			status = WAITING;
		}
		frame.setPosition(x, y);
		frame.update();
	}

	public void draw(Graphics2D g) {
		lastState.draw(g);
		g.setColor(overlayColor);
		g.fillRect(0, 0, References.WIDTH, References.HEIGHT);
		frame.draw(g);
	}

	public void handleInput(InputHandler input) {
		frame.handleInput(input);
	}

}

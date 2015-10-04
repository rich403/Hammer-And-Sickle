package net.richstudios.hammerandsickle.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.graphics.hud.HudBox;
import net.richstudios.hammerandsickle.graphics.hud.HudButton;
import net.richstudios.hammerandsickle.graphics.hud.Hud;
import net.richstudios.hammerandsickle.graphics.hud.HudAction;
import net.richstudios.hammerandsickle.graphics.hud.HudComponent;
import net.richstudios.hammerandsickle.graphics.hud.HudLabel;
import net.richstudios.hammerandsickle.graphics.hud.HudScrollBar;
import net.richstudios.hammerandsickle.graphics.hud.HudTextBox;
import net.richstudios.hammerandsickle.graphics.hud.HudTextInput;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class NewGameState extends GameState {

	private static final int FRAME_WIDTH = 50, FRAME_HEIGHT = 50, FRAME_SIZE = 1, MAX_OVERLAY_APLHA = 120;
	private static final int FRAME_X = References.WIDTH / 2 - (FRAME_WIDTH * HudBox.SIZE * FRAME_SIZE) / 2, FRAME_Y = References.HEIGHT -(FRAME_HEIGHT * HudBox.SIZE * FRAME_SIZE);
	private static final float CHANGE_TIME = 0.7f;
	
	private GameState lastState;
	private Color overlayColor;
	private int overlayAlpha = 0;
	private float alphaStep = (float) MAX_OVERLAY_APLHA / (CHANGE_TIME * (float) References.FPS);

	private Hud frame;
	private HudBox bgBox;
	private HudButton btnCancel, btnGo;
	private HudTextInput txtInName;
	private HudLabel lblTitle;

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
		bgBox = new HudBox(0, 0, FRAME_WIDTH, FRAME_HEIGHT + 1, FRAME_SIZE);
		frame.add(bgBox);
		lblTitle = new HudLabel("NEW GAME", 2, 3, 1);
		frame.add(lblTitle);
		addButtons(frame);
		txtInName = new HudTextInput(2, 12, 20, "", FRAME_SIZE);
		frame.add(txtInName);
	}
	
	private void addButtons(Hud frame) {
		int buttonWidth = 8;
		int buttonHeight = 4;
		int buttonY = FRAME_HEIGHT * 4 - (buttonHeight + 3) * 4;
		int buttonX = FRAME_WIDTH * 4 - (buttonWidth + 1) * 4;
		btnCancel = new HudButton(buttonX, buttonY, buttonWidth, buttonHeight, "Back", FRAME_SIZE);
		btnCancel.setAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
				status = CANCELING;
			}
		});
		frame.add(btnCancel);
		btnGo = new HudButton(buttonX - buttonWidth * 4 - 1 * 4, buttonY, buttonWidth, buttonHeight, "Go", FRAME_SIZE);
		btnGo.setAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
			}
		});
		frame.add(btnGo);
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
		} else if (status == CANCELING) {
			overlayAlpha -= (int) alphaStep;
			y += (int) yStep;
			if (overlayAlpha <= 0 || y >= References.HEIGHT) {
				overlayAlpha = 0;
				y = References.HEIGHT;
				status = CANCELED;
			}
			overlayColor = new Color(0, 0, 0, overlayAlpha);
		} else if (status == CANCELED) {
			gsm.set(lastState);
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

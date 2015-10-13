package net.richstudios.hammerandsickle.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.gamestate.transitions.CheckeredTransitonState;
import net.richstudios.hammerandsickle.gamestate.transitions.CloseTransitionState;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.graphics.hud.Hud;
import net.richstudios.hammerandsickle.graphics.hud.HudAction;
import net.richstudios.hammerandsickle.graphics.hud.HudComponent;
import net.richstudios.hammerandsickle.graphics.hud.HudSpecialButton;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class MenuState extends GameState {

	private Hud hud;

	private final String[] btnNames = new String[] { "NEW GAME", "LOAD GAME", "HELP", "EXIT" };
	private final HudAction[] btnActions = new HudAction[] { new HudAction() {
		public void actionPerformed(HudComponent comp) {
			newGame();
		}
	}, new HudAction() {
		public void actionPerformed(HudComponent comp) {
			loadGame();
		}
	}, new HudAction() {
		public void actionPerformed(HudComponent comp) {
			help();
		}
	}, new HudAction() {
		public void actionPerformed(HudComponent comp) {
			exit();
		}
	} };

	public MenuState(GameStateManager gsm) {
		super(gsm);
		Sound.loop("revolt");
		
		hud = new Hud();
		HudSpecialButton[] buttons = new HudSpecialButton[btnNames.length];
		final int btnSize = 2;
		final int btnWidth = HudSpecialButton.DEFAULT_WIDTH * btnSize;
		final int btnHeight = HudSpecialButton.DEFAULT_HEIGHT * btnSize;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new HudSpecialButton(References.WIDTH / 2 - btnWidth / 2, References.HEIGHT / 2 + (btnHeight + 2 * btnSize) * i, btnSize, btnNames[i]);
			buttons[i].setAction(btnActions[i]);
			hud.add(buttons[i]);
		}
	}

	private void newGame() {
		gsm.set(new NewGameState(gsm, this));
	}

	private void loadGame() {
		CheckeredTransitonState cts = new CheckeredTransitonState(gsm, this, this);
		gsm.set(cts);
	}

	private void help() {
		CheckeredTransitonState cts = new CheckeredTransitonState(gsm, this, this);
		gsm.set(cts);
	}

	private void exit() {
		CloseTransitionState cts = new CloseTransitionState(gsm, this);
		gsm.set(cts);
	}

	public void init() {

	}

	public void update() {
		hud.update();
	}

	public void draw(Graphics2D g) {
		g.drawImage(Textures.getTexture("menubg"), 0, 0, References.WIDTH, References.HEIGHT, null);
		BufferedImage title = Textures.getTexture("title");
		int titleWidth = title.getWidth() * 2;
		int titleHeight = title.getHeight() * 2;
		g.drawImage(title, References.WIDTH / 2 - titleWidth / 2, References.HEIGHT / 20, titleWidth, titleHeight, null);
		hud.draw(g);
	}

	public void handleInput(InputHandler input) {
		hud.handleInput(input);
	}

}

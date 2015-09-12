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
import net.richstudios.hammerandsickle.graphics.hud.MainMenuButton;
import net.richstudios.hammerandsickle.graphics.hud.ScrollBar;
import net.richstudios.hammerandsickle.graphics.hud.TextBox;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class MenuState extends GameState {
	
	private Hud hud;
	private static int menuX = References.WIDTH - (References.WIDTH / 20);
	private TextBox box;
	private ScrollBar sb;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		Sound.loop("revolt");
		MainMenuButton[] buttons = new MainMenuButton[4];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new MainMenuButton(menuX - MainMenuButton.DEFAULT_WIDTH * 2, References.HEIGHT / 2 - MainMenuButton.DEFAULT_HEIGHT + (MainMenuButton.DEFAULT_HEIGHT * 2 + 2) * i, 2, i);
		}
		buttons[0].addAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
				newGame();
			}
		});
		buttons[1].addAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
				loadGame();
			}
		});
		buttons[2].addAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
				help();
			}
		});
		buttons[3].addAction(new HudAction() {
			public void actionPerformed(HudComponent comp) {
				exit();
			}
		});
		
		hud = new Hud();
		for (int i = 0; i < buttons.length; i++) {
			hud.add(buttons[i]);
		}
		box = new TextBox(0, 0, 20, 20, 2);
		box.setText("This is a textBox\n"
				+ "1\n"
				+ "2\n"
				+ "3");
		sb = new ScrollBar(20 * 4 * 2, 0, 20, 20, 2);
		hud.add(box);
		hud.add(sb);
	}

	private void newGame() {
		CheckeredTransitonState cts = new CheckeredTransitonState(gsm, this, this);
		gsm.set(cts);
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
		box.setTextOffset(0, sb.getValue());
	}

	public void draw(Graphics2D g) {
		g.drawImage(Textures.getTexture("menubg"), 0, 0, null);
		BufferedImage title = Textures.getTexture("title");
		g.drawImage(title, menuX - title.getWidth(), References.HEIGHT / 20, null);
		hud.draw(g);
	}

	public void handleInput(InputHandler input) {
		hud.handleInput(input);
	}

}

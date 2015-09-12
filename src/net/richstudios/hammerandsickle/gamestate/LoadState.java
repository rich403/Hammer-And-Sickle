package net.richstudios.hammerandsickle.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.gamestate.transitions.CheckeredTransitonState;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;
import net.richstudios.hammerandsickle.utilites.StringUtils;

public class LoadState extends GameState {

	private Thread loaderThread;

	private ResourceLoader loader;

	public LoadState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		loader = new ResourceLoader();
		loaderThread = new Thread(loader);
		loaderThread.start();
	}

	int count = 0;

	public void update() {
		if (loader.isDone()) {
			try {
				if (loaderThread.isAlive())
					loaderThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		if (count >= References.FPS * 2) {
			CheckeredTransitonState cts = new CheckeredTransitonState(gsm,
					this, new MenuState(gsm));
			gsm.set(cts);
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED.darker());
		g.fillRect(0, 0, References.WIDTH, References.HEIGHT);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.setColor(Color.YELLOW);
		String percentageDone = (int) (loader.getPercentageDone() * 100D) + "%";
		int percentageX = (References.WIDTH / 2)
				- (StringUtils.getStringWidth(g.getFont(), percentageDone) / 2);
		int percentageY = (References.HEIGHT / 2)
				- (StringUtils.getStringHeight(g.getFont(), percentageDone) / 2);
		g.drawString(percentageDone, percentageX, percentageY);
		String loadText = loader.getLoadText();
		int loadTextX = References.WIDTH
				- StringUtils.getStringWidth(g.getFont(), loadText) - 3;
		int loadTextY = References.HEIGHT
				- StringUtils.getStringHeight(g.getFont(), loadText) + 2;
		g.drawString(loadText, loadTextX, loadTextY);
	}

	public void handleInput(InputHandler keys) {
	}

	private class ResourceLoader implements Runnable {

		private boolean done = false;

		private String loadText = "";

		public void run() {
			loadText = "Loading Textures";
			Textures.loadTextures();
			loadText = "Loading Sounds";
			Sound.loadSound();
			loadText = "Done";
			done = true;
		}

		public boolean isDone() {
			return done;
		}

		public String getLoadText() {
			return loadText;
		}

		public double getPercentageDone() {
			return (Textures.getPercentageLoaded() + Sound
					.getPercentageLoaded()) / 2D;
		}

	}

}

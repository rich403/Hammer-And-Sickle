package net.richstudios.hammerandsickle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.gamestate.GameStateManager;
import net.richstudios.hammerandsickle.gamestate.LoadState;
import net.richstudios.hammerandsickle.graphics.GameFont;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.reference.Settings;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private Thread thread;
	private boolean running;
	private long targetTime = 1000 / References.FPS;

	private BufferedImage image;
	private Graphics2D g;

	private GameStateManager gsm;

	private InputHandler input;

	private JFrame frame;

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		super();
		frame = new JFrame(References.NAME + " | " + References.VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
		icons.add(Textures.icon16x);
		icons.add(Textures.icon32x);
		icons.add(Textures.icon64x);
		frame.setIconImages(icons);
		frame.setSize(References.WIDTH * References.SCALE, References.HEIGHT * References.SCALE);
		frame.setContentPane(this);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(References.WIDTH, References.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		input = new InputHandler(this);
		Sound.init();
		Textures.init();
		gsm = new GameStateManager(input);
		gsm.push(new LoadState(gsm));
		running = true;
	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		while (running) {

			start = System.nanoTime();

			update();
			draw();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			try {
				if (wait > 0)
					Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		if (input.isKeyTyped(InputHandler.F12)) {
			Settings.debug = !Settings.debug;
		}
		gsm.update();
		input.update();
	}

	private void draw() {
		gsm.draw(g);
		if (Settings.debug) {
			int tx = input.mouseX / References.SCALE;
			int ty = input.mouseY / References.SCALE;
			g.setColor(Color.PINK);
			g.fillRect(tx, ty, 1, 1);
		}
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, References.WIDTH * References.SCALE, References.HEIGHT * References.SCALE, null);
		g2.setColor(Color.blue);
		g2.dispose();
	}

}

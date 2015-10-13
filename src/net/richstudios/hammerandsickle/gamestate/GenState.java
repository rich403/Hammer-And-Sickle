package net.richstudios.hammerandsickle.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;
import net.richstudios.hammerandsickle.world.Camera;
import net.richstudios.hammerandsickle.world.Map;

public class GenState extends GameState {

	private Map map;
	private Camera camera;

	public GenState(GameStateManager gsm) {
		super(gsm);
		camera = new Camera(0, 0);
		map = new Map(camera, 1151);
	}

	public void init() {

	}

	public void update() {
		map.update();
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, References.WIDTH, References.HEIGHT);
		map.draw(g);
	}

	private double x = 0, y = 0;
	private final double moveSpeed = 0.1;

	public void handleInput(InputHandler input) {
		if (input.isKeyPressed(KeyEvent.VK_UP)) {
			y -= moveSpeed;
		} else if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
			y += moveSpeed;
		}
		if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
			x -= moveSpeed;
		} else if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
			x += moveSpeed;
		}
		camera.setX((int) x);
		camera.setY((int) y);
	}

}

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
		map = new Map(camera);
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

	public void handleInput(InputHandler input) {
		if (input.isKeyPressed(KeyEvent.VK_UP)) {
			camera.setY(camera.getY() - 1);
		} else if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
			camera.setY(camera.getY() + 1);
		} if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
			camera.setX(camera.getX() - 1);
		} else if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
			camera.setX(camera.getX() + 1);
		}
	}

}

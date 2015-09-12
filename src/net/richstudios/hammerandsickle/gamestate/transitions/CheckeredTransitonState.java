package net.richstudios.hammerandsickle.gamestate.transitions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richstudios.hammerandsickle.gamestate.GameState;
import net.richstudios.hammerandsickle.gamestate.GameStateManager;
import net.richstudios.hammerandsickle.reference.References;

public class CheckeredTransitonState extends TransitionState {

	private int size;
	private int numRows;
	private int numCols;
	private float expandSpeed = 2;
	private float delay = 10;
	private int finishCount;

	private static final int EXPANDING = 0;
	private static final int DONE_EXPANDING = 1;
	private static final int COLLAPSING = 2;
	private static final int DONE_COLLAPSING = 3;
	private int status = EXPANDING;

	private float[][] sizes;

	public CheckeredTransitonState(GameStateManager gsm, GameState prevState, GameState nextState) {
		super(gsm, prevState, nextState);
		
		size = 20;
		numRows = References.HEIGHT / size + 1;
		numCols = References.WIDTH / size + 1;
		sizes = new float[numRows][numCols];
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				sizes[row][col] = ((numRows - row) + col) * -delay;
			}
		}
	}

	public void update() {

		finishCount = 0;

		if (status == EXPANDING) {
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					sizes[row][col] += expandSpeed;
					if (sizes[row][col] >= size) {
						finishCount++;
						sizes[row][col] = size;
					}
				}
			}
			if (finishCount == numRows * numCols) {
				status = DONE_EXPANDING;
				for (int row = 0; row < numRows; row++) {
					for (int col = 0; col < numCols; col++) {
						sizes[row][col] = ((numRows - row) + col) * delay + size;
					}
				}
				status = COLLAPSING;
			}
		}

		finishCount = 0;

		if (status == COLLAPSING) {
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					sizes[row][col] -= expandSpeed;
					if (sizes[row][col] <= 0) {
						finishCount++;
						sizes[row][col] = 0;
					}
				}
			}

			if (finishCount == numRows * numCols) {
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

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (sizes[row][col] < 0) {
					continue;
				}
				g.setColor(Color.BLACK);
				g.fill(new Rectangle((int) (col * size + size / 2 - sizes[row][col] / 2), (int) (row * size + size / 2 - sizes[row][col] / 2), (int)sizes[row][col], (int)sizes[row][col]));
			}
		}
	}

}

package com.dvdfu.gij;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dvdfu.gij.components.SpriteComponent;

public class Cell {
	enum State {
		EMPTY, SPROUT, TREE
	}
	State state;
	Level level;
	int x;
	Player owner;
	
	SpriteComponent soil;
	SpriteComponent soilTop;
	SpriteComponent sprout;
	SpriteComponent tree;
	SpriteComponent slash;
	boolean drawSlash;
	
	public Cell(Level level, int x) {
		this.level = level;
		this.x = x;
		soil = new SpriteComponent(Consts.atlas.findRegion("soil"));
		soilTop = new SpriteComponent(Consts.atlas.findRegion("soil_top"));
		sprout = new SpriteComponent(Consts.atlas.findRegion("sprout"));
		tree = new SpriteComponent(Consts.atlas.findRegion("tree"));
		slash = new SpriteComponent(Consts.atlas.findRegion("slash"), 48);
		slash.setOrigin(8, 0);
		slash.setFrameRate(1);
		state = State.EMPTY;
	}
	
	public void draw(SpriteBatch batch) {
		float drawX = Gdx.graphics.getWidth() / 4 + (x - level.width / 2f) * 32;
		for (int i = 0; i < 3; i++) {
			if (i == 2) {
				soilTop.draw(batch, drawX, i * 32);
			} else {
				soil.draw(batch, drawX, i * 32);
			}
		}
		switch (state) {
		case EMPTY:
			break;
		case SPROUT:
			sprout.draw(batch, drawX, 96);
			break;
		case TREE:
			tree.drawOrigin(batch, drawX, 96);
			break;
		default:
			break;
		}
		if (drawSlash) {
			slash.update();
			slash.drawOrigin(batch, drawX, 96);
			if (slash.getFrame() == 7) {
				drawSlash = false;
			}
		}
	}
	
	public void slash() {
		drawSlash = true;
		slash.setFrame(0);
	}
	
	public void setState(Player owner, Cell.State state) {
		this.state = state;
		this.owner = owner;
		switch (state) {
		case EMPTY:
			break;
		case SPROUT:
			if (owner.equals(level.p1)) {
				sprout.setImage(Consts.atlas.findRegion("sprout"));
			} else {
				sprout.setImage(Consts.atlas.findRegion("sprout_red"));
			}
			break;
		case TREE:
			if (owner.equals(level.p1)) {
				tree.setImage(Consts.atlas.findRegion("tree"));
			} else {
				tree.setImage(Consts.atlas.findRegion("tree_red"));
			}
			tree.setOrigin(8, 0);
			break;
		default:
			break;
		}
	}
}

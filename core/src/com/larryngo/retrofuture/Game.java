package com.larryngo.retrofuture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.larryngo.retrofuture.states.GameStateManager;
import com.larryngo.retrofuture.states.MenuState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 1500;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Retro Future";
	private GameStateManager gsm;
	private SpriteBatch batch;
	public static AssetManager manager;
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1,0,0,1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {

	}
}

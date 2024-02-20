package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.pantallas.ScreenLoad;
import com.mygdx.game.pantallas.ScreenMenu;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class MyGame extends Game {

	// JUEGO EN NUEVA VERSION

	@Override
	public void create() {
		Gdx.input.setInputProcessor(Recursos.mux);
		System.out.println("Bienvenido");
		Render.app = this;
		Render.batch = new SpriteBatch();
		Render.app.setScreen(new ScreenMenu()); // Muestro pantalla de carga.
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();

	}

	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {
		if(UtilesRed.hc != null) {			
		UtilesRed.hc.enviarMensaje("desconectar");
		UtilesRed.hc.fin();
		}
	}
}

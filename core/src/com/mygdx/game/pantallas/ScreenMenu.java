package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.recursos.Imagen;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.EstiloFuente;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class ScreenMenu implements Screen, Hud {

	private ScreenViewport screenViewPort;
	private Stage stage;
	private Table tabla, contenedor;
	private Label textoJugar;
	private Label textoMultiJugador;
	private Label textoSalir;
	private Label.LabelStyle estiloLabel;

	public ScreenMenu() {

		crearFuente();
		crearActores();
		poblarStage();

	}

	@Override
	public void crearFuente() {
		estiloLabel = EstiloFuente.generarFuente(32, "#ffffff");

	}

	@Override
	public void crearActores() {

		screenViewPort = new ScreenViewport();
		stage = new Stage(screenViewPort);

		Recursos.mux.addProcessor(stage);

		tabla = new Table();
		contenedor = new Table();
		textoJugar = new Label("Jugar", estiloLabel);
		textoMultiJugador = new Label("Multijugador", estiloLabel);
		textoSalir = new Label("Salir", estiloLabel);

		textoJugar.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Label jugar", "Click");
				// resultadosHUD.cerrar = !mostrarResultadosBatalla;
				// System.out.println(HelpDebug.debub(getClass())+"click");
				Render.app.setScreen(new ScreenGame(false));
				ScreenGame.numeroEscenario = 1;
				stage.dispose();
			}

		});

		textoMultiJugador.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Label Multijugador", "Click");
				// resultadosHUD.cerrar = !mostrarResultadosBatalla;
				// System.out.println(HelpDebug.debub(getClass())+"click");
				Render.limpiarPantalla(0,0,0);
				Render.app.setScreen(new EsperaConexion());
				
			}
			
			

		});

	}

	@Override
	public void poblarStage() {

		tabla.setFillParent(true);
		stage.setDebugAll(true);

		contenedor.add(textoJugar).pad(20);
		contenedor.row();
		contenedor.add(textoMultiJugador).pad(20);
		contenedor.row();
		contenedor.add(textoSalir).pad(20);
		contenedor.row();
		tabla.add(contenedor);
		stage.addActor(tabla);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		stage.draw();
		stage.act();
	}

	@Override
	public void resize(int width, int height) {
		screenViewPort.update(width, height, true);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		
	}
	

}

package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	private Label textoTitulo;
	private Label textoJugar;
	private Label textoMultiJugador;
	private Label textoSalir;
	private Label.LabelStyle estiloLabel;
	private Label.LabelStyle estiloLabelTitulo;
	private Music select;
	public Music backgroundMusic;
	Imagen background;

	public ScreenMenu() {

		crearFuente();
		crearActores();
		poblarStage();

	}

	@Override
	public void crearFuente() {
		//Crep las diferentes fuentes que se usa en el menu
		estiloLabel = EstiloFuente.generarFuente(32, "#ffffff");
		estiloLabelTitulo = EstiloFuente.generarFuente(64, "#ffffff");

	}

	@Override
	public void crearActores() {

		screenViewPort = new ScreenViewport();
		stage = new Stage(screenViewPort); 	// se crea el stage donde va el contenedor con los textos

		Recursos.mux.addProcessor(stage);

		tabla = new Table(); 
		contenedor = new Table();
		
		//Se crean los diferentes textos del menu
		textoTitulo = new Label("Java Souls", estiloLabelTitulo);
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

		textoJugar.addListener(new ClickListener() {

			//Diferentes funciones para las interacciones del mouse con los textos del menu.
			@Override
			public void clicked(InputEvent event, float x, float y) {

				Gdx.app.log("Label jugar", "Click");
				// resultadosHUD.cerrar = !mostrarResultadosBatalla;
				// System.out.println(HelpDebug.debub(getClass())+"click");
				Render.app.setScreen(new ScreenGame(false));
				ScreenGame.numeroEscenario = 1;
				stage.dispose();
				backgroundMusic.stop();
				backgroundMusic.dispose();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
				select.play();
				estiloLabel = EstiloFuente.generarFuente(32, "#D4BD13");

			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			}

		});

		textoMultiJugador.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Label Multijugador", "Click");
				// resultadosHUD.cerrar = !mostrarResultadosBatalla;
				// System.out.println(HelpDebug.debub(getClass())+"click");
				Render.limpiarPantalla(0, 0, 0);
				Render.app.setScreen(new EsperaConexion());
				backgroundMusic.pause();
				backgroundMusic.dispose();

			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
				select.play();
				estiloLabel = EstiloFuente.generarFuente(32, "#D4BD13");

			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			}

		});

		textoSalir.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Label Salir", "Click");
				// resultadosHUD.cerrar = !mostrarResultadosBatalla;
				// System.out.println(HelpDebug.debub(getClass())+"click");
				Render.limpiarPantalla(0, 0, 0);
				Gdx.app.exit();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
				select.play();
				estiloLabel = EstiloFuente.generarFuente(32, "#D4BD13");

			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			}

		});

	}

	@Override
	public void poblarStage() {

		tabla.setFillParent(true);
		// stage.setDebugAll(true); //Dibuja los limites de las cosas
		
		//Agrego todo al contenedor
		contenedor.add(textoTitulo).pad(50);
		contenedor.row();
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
		
		
		// Carga la música de fondo desde el archivo "menu_music.mp3"
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Music/Menumusic.mp3"));
		// Configura la música para que se repita en bucle
		backgroundMusic.setLooping(true);
		// Reproduce la música de fondo
		backgroundMusic.play();

		// Carga la música de fondo desde el archivo "menu_music.mp3"
		select = Gdx.audio.newMusic(Gdx.files.internal("Sounds/FX/click.mp3"));
		// Carga el fondo del menú
		background = new Imagen(Recursos.FONDOMENU);
		background.setSize(Config.ANCHO, Config.ALTO);
	}

	@Override
	public void render(float delta) {
		
		//Renderizo primero el fondo para que no tape el stage
		Render.batch.begin();
			background.dibujar(); 
		Render.batch.end();
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

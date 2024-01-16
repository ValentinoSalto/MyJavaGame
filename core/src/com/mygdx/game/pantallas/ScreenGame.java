package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.recursos.Boss1;
import com.mygdx.game.recursos.Fondo;
import com.mygdx.game.recursos.Ghost;
import com.mygdx.game.recursos.Knight3;
import com.mygdx.game.recursos.Knight3.EstadoPersonaje;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Render;

public class ScreenGame implements Screen {

	Image personaje;
	SpriteBatch b;
	SpriteBatch h;
	private OrthographicCamera hudCamera; // creo la camara del hud
	private BitmapFont font;
	private int muertes = 0; // Ejemplo: Inicializa el contador de muertes a 0
	private Knight3 knight;
	private Ghost ghost1;
	private Ghost ghost2;
	private Ghost ghost3;
	private Boss1 boss;
	boolean bloqueoActivo;
	ShapeRenderer sr; // Agrega un objeto ShapeRenderer
	public static int numeroEscenario = 0;

	private Fondo fondo1;
	private Fondo fondo2;
	private Fondo fondo3;
	private OrthographicCamera cam; // creo la camara
	private TiledMap mapa; // info del mapa
	private TiledMapRenderer mapaRenderer; // render del mapa

	private void chequearLimites() {
		float knightX = knight.getX();
		float knightY = knight.getY();
		float knightWidth = knight.getWidth();
		float knightHeight = knight.getHeight();

		// Definir límites de transición
		float limiteIzquierdo = 0;
		float limiteDerecho = Config.ANCHO;
		float limiteInferior = 0;
		float limiteSuperior = Config.ALTO;

		// Verificar si el personaje ha salido de los límites
		if (knightX + knightWidth < limiteIzquierdo) {
			// Personaje salió por la izquierda

		} else if (knightX > limiteDerecho) {
			// Personaje salió por la derecha
			cambiarEscenario();
		} else if (knightY + knightHeight < limiteInferior) {
			// Personaje salió por abajo

		} else if (knightY > limiteSuperior) {
			// Personaje salió por arriba

		}
	}

	private void cambiarEscenario() {

		if (numeroEscenario == 1) {
			// Cargar el nuevo escenario y ajustar la posición del personaje
			// Por ejemplo, puedes cargar otro mapa con TiledMapLoader
			mapa = new TmxMapLoader().load("Mapas/Mapa1/Mapa 2.tmx");
			mapaRenderer = new OrthogonalTiledMapRenderer(mapa); // crea el render

			numeroEscenario = 2;

			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(100, 145);

		} else if (numeroEscenario == 2) {
			// Cargar el nuevo escenario y ajustar la posición del personaje
			// Por ejemplo, puedes cargar otro mapa con TiledMapLoader
			mapa = new TmxMapLoader().load("Mapas/Mapa1/Mapa 3.tmx");
			mapaRenderer = new OrthogonalTiledMapRenderer(mapa); // crea el render

			numeroEscenario = 3;

			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(100, 145);

		}
	}

	@Override
	public void show() {

		// Restringe el cursor del mouse a no ser visible
		Gdx.input.setCursorCatched(true);
		cam = new OrthographicCamera(Config.ANCHO, Config.ALTO); // creo la camara
		cam.setToOrtho(false, Config.ANCHO, Config.ALTO);

		// Inicializa la cámara del HUD
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Crear el fondo
		fondo1 = new Fondo("Mapas/Mapa1/background_layer_1.png");
		fondo2 = new Fondo("Mapas/Mapa1/background_layer_2.png");
		fondo3 = new Fondo("Mapas/Mapa1/background_layer_3.png");
		// Carga el mapa desde Tiled
		mapa = new TmxMapLoader().load("Mapas/Mapa1/Mapa 1.tmx");
		mapaRenderer = new OrthogonalTiledMapRenderer(mapa); // crea el render

		// Ajusta la cámara del mapa para que ocupe toda la pantalla

		// Inicializa la fuente (ajusta los parámetros según tus preferencias)
		font = new BitmapFont();
		font.setColor(Color.WHITE); // Configura el color de la fuente
		font.getData().setScale(1.5f); // Escala el tamaño de la fuente

		b = Render.batch;
		h = Render.batch;
		sr = new ShapeRenderer(); // Inicializa el ShapeRenderer

		knight = new Knight3(200, 145, 100, 100);
		ghost1 = new Ghost(500, 145, 200, 200);
		ghost2 = new Ghost(500, 145, 200, 200);
		ghost3 = new Ghost(800, 145, 200, 200);
		boss = new Boss1(900, 117, 300, 300);

	}

	@Override
	public void render(float delta) {

		// Limpio la pantalla (solamente para ver bien el caballero despues va a tener
		// un fondo)

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);

		// Ajusta la vista del mapa
		float mapWidth = mapa.getProperties().get("width", Integer.class)
				* mapa.getProperties().get("tilewidth", Integer.class);
		float mapHeight = mapa.getProperties().get("height", Integer.class)
				* mapa.getProperties().get("tileheight", Integer.class);

		float viewportWidth = cam.viewportWidth * cam.zoom;
		float viewportHeight = cam.viewportHeight * cam.zoom;

		float minX = viewportWidth / 2;
		float maxX = mapWidth - viewportWidth / 2;
		float minY = viewportHeight / 2;
		float maxY = mapHeight - viewportHeight / 2;

		float cameraX = MathUtils.clamp(cam.position.x, minX, maxX);
		float cameraY = MathUtils.clamp(cam.position.y, minY, maxY);

		cam.position.set(cameraX, cameraY, 0);
		cam.update();

		// Actualiza la posición de la cámara para que siga al personaje
		/*
		 * cam.position.set(knight.getX() + knight.getWidth() / 2,360, 0); cam.update();
		 */

		// Manejar el cambio de escenario
		chequearLimites();

		// Maneja las entradas del teclado para cambiar el estado del personaje
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			knight.cambiarEstado(Knight3.EstadoPersonaje.WALKING_LEFT);

		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			knight.cambiarEstado(Knight3.EstadoPersonaje.WALKING_RIGHT);

		}

		else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

			knight.cambiarEstado(Knight3.EstadoPersonaje.JUMP);

		} else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

			knight.cambiarEstado(Knight3.EstadoPersonaje.ATTACK);
			System.out.println("ataca");

		} else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				// Inicia la acción de bloqueo
				bloqueoActivo = true;
				// Cambia el estado del personaje a COVER
				knight.cambiarEstado(Knight3.EstadoPersonaje.COVER);

			} else {
				// Si el clic derecho no está presionado, detiene la acción de bloqueo
				bloqueoActivo = false;
				// Cambia el estado del personaje a IDLE (o cualquier otro estado apropiado)
				knight.cambiarEstado(Knight3.EstadoPersonaje.IDLE);
			}

		} else {
			knight.cambiarEstado(Knight3.EstadoPersonaje.IDLE);
		}

		knight.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual

		b.begin();

		// Dibuja el fondo
		fondo1.render(b);
		fondo2.render(b);
		fondo3.render(b);

		b.end();

		b.begin();

		// Establece la vista del mapa
		mapaRenderer.setView(cam);
		mapaRenderer.render();

		Render.batch.setProjectionMatrix(cam.combined);
		knight.render(b);

		if (numeroEscenario == 1) {

			ghost1.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			ghost1.render(b);
			ghost1.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
			ghost1.atacarKnight(knight);
		}
		if (numeroEscenario == 2) {
			ghost1.dispose();
			ghost2.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			ghost2.render(b);
			ghost2.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
			ghost2.atacarKnight(knight);

			ghost3.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			ghost3.render(b);
			ghost3.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
			ghost3.atacarKnight(knight);

		} else if (numeroEscenario == 3) {
			boss.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			boss.render(b);
			boss.seguirKnight(knight, delta);
			ghost3.dispose();
			ghost2.dispose();

		}

		// Actualiza el temporizador
		Ghost.tiempoDesdeUltimoAtaque += delta;
		// Actualiza el temporizador
		Boss1.tiempoDesdeUltimoAtaque += delta;

		Render.batch.setProjectionMatrix(hudCamera.combined); // Configura el SpriteBatch para la cámara del HUD
		// Configura el color de fuente y dibuja la información de "Vida"
		font.setColor(Color.WHITE); // Configura el color de fuente (blanco en este ejemplo)
		font.draw(b, "Vida: " + knight.vida, 10, 700); // Dibuja la vida en la esquina superior izquierda
		font.draw(b, "Muertes: " + muertes, 10, 670); // Dibuja las muertes en la esquina superior derecha

		if (knight.vida == 0) {
			Render.app.setScreen(new ScreenMenu());
		}

		b.end();

		/*
		 * // Inicia el dibujado de líneas sr.begin(ShapeType.Line); sr.setColor(255, 0,
		 * 0, 1); // Establece el color de la línea (blanco en este caso)
		 * 
		 * // Dibuja la línea del piso desde (x1, y1) a (x2, y2) sr.line(0, 100,
		 * Gdx.graphics.getWidth(), 100);
		 * 
		 * // Finaliza el dibujado de líneas sr.end();
		 */

	}

	@Override
	public void resize(int width, int height) {
		// El resize de la cam con la ventana
		cam.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

		// Restaura la visibilidad del cursor del mouse cuando se oculta la pantalla
		Gdx.input.setCursorCatched(false);
		// ... otros limpiezas y cambios de pantalla

	}

	@Override
	public void dispose() {
		mapa.dispose();

	}

}
package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.recursos.Boss1;
import com.mygdx.game.recursos.Enemigo;
import com.mygdx.game.recursos.EstadosKnight;
import com.mygdx.game.recursos.Fondo;
import com.mygdx.game.recursos.Ghost;
import com.mygdx.game.recursos.Herrero;
import com.mygdx.game.recursos.Hoguera;
import com.mygdx.game.recursos.Knight3;
import com.mygdx.game.recursos.KnightBlue;
import com.mygdx.game.recursos.Npc;
import com.mygdx.game.recursos.Texturas;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Render;

import hud.BarraVida;
import hud.Choza;

public class ScreenGame implements Screen {
	// Camara y HUD
	private OrthographicCamera hudCamera; // creo la camara del hud
	private BitmapFont font;
	private int muertes = 0; // Ejemplo: Inicializa el contador de muertes a 0
	private BarraVida barravida;
	private BarraVida barravida2;
	private Choza choza;
	public ShapeRenderer vida;
	public ShapeRenderer vida2;
	// Personajes
	private Knight3 knight;
	private Knight3 knight2;

	// Enemigos
	private Enemigo ghost1;
	private Enemigo ghost2;
	private Enemigo ghost3;

	private Enemigo ghost4;
	private Enemigo ghost5;
	private Enemigo ghost6;
	private Enemigo ghost7;
	private Enemigo ghost8;

	private Boss1 boss;
	private Boss1 boss2;

	// NPC
	private Npc herrero;
	// Hoguera checkpoints
	private Hoguera hoguera1;

	// Plataformas
	public Rectangle plataforma1;
	public Rectangle plataforma2;
	public Rectangle plataforma3;
	public Rectangle plataforma4;
	public Rectangle plataforma5;
	public Rectangle plataforma6;
	public Rectangle plataforma7;
	public Rectangle plataforma8;

	ShapeRenderer sr; // Agrega un objeto ShapeRenderer
	public static int numeroEscenario = 0;

	// Fondos map1
	private Fondo fondo1;
	private Fondo fondo2;
	private Fondo fondo3;
	// Fondos map2
	private Fondo fondo4;
	private Fondo fondo5;
	private Fondo fondo6;
	private Fondo fondo7;
	private OrthographicCamera cam, camHud; // creo la camara

	ScreenViewport vw;

	// Mapas
	private TiledMap mapa1; // info del mapa
	private TiledMapRenderer mapaRenderer1; // render del mapa
	private TiledMap mapa2; // info del mapa
	private TiledMapRenderer mapaRenderer2; // render del mapa
	private TiledMap mapa3; // info del mapa
	private TiledMapRenderer mapaRenderer3; // render del mapa
	private TiledMap mapa4; // info del mapa
	private TiledMapRenderer mapaRenderer4; // render del mapa
	private TiledMap mapa5; // info del mapa
	private TiledMapRenderer mapaRenderer5; // render del mapa
	private TiledMap mapa6; // info del mapa
	private TiledMapRenderer mapaRenderer6; // render del mapa
	private TiledMap mapa7; // info del mapa
	private TiledMapRenderer mapaRenderer7; // render del mapa
	private TiledMap mapa8; // info del mapa
	private TiledMapRenderer mapaRenderer8; // render del mapa

	// red
	public boolean enRed = true;

	public ScreenGame(boolean red) {
		enRed = red;

		if (enRed) {
			UtilesRed.hc.setGame(this);
		}
	}

	@Override
	public void show() {
		numeroEscenario = 2;

		if (enRed) {
			barravida = new BarraVida(Texturas.barraVida, 10, 640, 300, 100);
			vida = new ShapeRenderer();
			barravida2 = new BarraVida(Texturas.barraVida, 980, 640, 300, 100);
			vida2 = new ShapeRenderer();
			choza = new Choza(Texturas.herreroChoza, 500, 69, 250, 300);
		} else {

			barravida = new BarraVida(Texturas.barraVida, 10, 640, 300, 100);
			vida = new ShapeRenderer();
			choza = new Choza(Texturas.herreroChoza, 500, 69, 250, 300);
		}

		// Restringe el cursor del mouse a no ser visible
//		Gdx.input.setCursorCatched(true);

		// Camara y viewport
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // creo la camara
		vw = new ScreenViewport(cam);
		cam.zoom = 1;
		// cam.setToOrtho(false, Config.ANCHO/2, Config.ALTO/2);

		/*
		 * camKnight = new OrthographicCamera(Config.ANCHO, Config.ALTO);
		 * camKnight.setToOrtho(false, Config.ANCHO, Config.ALTO); camKnight.position.x
		 * = Config.ANCHO / 2; camKnight.position.y = Config.ALTO / 2;
		 */

		// Inicializa la cámara del HUD y Hud
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.zoom = 6;

		// Crear el fondo 1
		fondo1 = new Fondo("Mapas/Mapa1/background_layer_1.png");
		fondo2 = new Fondo("Mapas/Mapa1/background_layer_2.png");
		fondo3 = new Fondo("Mapas/Mapa1/background_layer_3.png");

		// Crear el fondo 2
		fondo4 = new Fondo("Mapas/Mapa2/Sky.png");
		fondo5 = new Fondo("Mapas/Mapa2/Clouds.png");
		fondo6 = new Fondo("Mapas/Mapa2/Rock Mountains.png");
		fondo7 = new Fondo("Mapas/Mapa2/Grass Mountains.png");

		// Carga el mapa desde Tiled
		mapa1 = new TmxMapLoader().load("Mapas/Mapa1/Mapa 1.tmx");
		mapaRenderer1 = new OrthogonalTiledMapRenderer(mapa1); // crea el render

		mapa2 = new TmxMapLoader().load("Mapas/Mapa1/Mapa 2.tmx");
		mapaRenderer2 = new OrthogonalTiledMapRenderer(mapa2); // crea el render

		mapa3 = new TmxMapLoader().load("Mapas/Mapa1/Mapa 3.tmx");
		mapaRenderer3 = new OrthogonalTiledMapRenderer(mapa3); // crea el render

		// Carga el mapa desde Tiled
		mapa4 = new TmxMapLoader().load("Mapas/Mapa1/Mapalobby.tmx");
		mapaRenderer4 = new OrthogonalTiledMapRenderer(mapa4); // crea el render

		mapa5 = new TmxMapLoader().load("Mapas/Mapa2/Mapa 1.tmx");
		mapaRenderer5 = new OrthogonalTiledMapRenderer(mapa5); // crea el render

		mapa6 = new TmxMapLoader().load("Mapas/Mapa2/Mapa 2.tmx");
		mapaRenderer6 = new OrthogonalTiledMapRenderer(mapa6); // crea el render

		mapa7 = new TmxMapLoader().load("Mapas/Mapa2/Mapa 3.tmx");
		mapaRenderer7 = new OrthogonalTiledMapRenderer(mapa7); // crea el render

		// Inicializa la fuente (ajusta los parámetros según tus preferencias)
		font = new BitmapFont();
		font.setColor(Color.WHITE); // Configura el color de la fuente
		font.getData().setScale(1.5f); // Escala el tamaño de la fuente

		sr = new ShapeRenderer(); // Inicializa el ShapeRenderer

		// Inicializar personajes

		if (enRed) {// Siempre chequear que el juego esta en red para hacer cualquier cosa con el
					// knigth2
			if (UtilesRed.jugador) {
				knight = new Knight3(20, 168, 100, 100, true, Texturas.KnightSpriteSheet, true);
				knight2 = new Knight3(50, 168, 100, 100, true, Texturas.KnightSpriteSheet2, false);
			} else {
				knight = new Knight3(20, 168, 100, 100, true, Texturas.KnightSpriteSheet, false);
				knight2 = new Knight3(50, 168, 100, 100, true, Texturas.KnightSpriteSheet2, true);
			}

			System.out.println("creado");

		} else {
			knight = new Knight3(20, 168, 100, 100, false, Texturas.KnightSpriteSheet, true);
		}

		herrero = new Herrero(600, 168);

		ghost1 = new Ghost(500, 168);
		ghost2 = new Ghost(900, 168);
		ghost3 = new Ghost(500, 168);
		ghost4 = new Ghost(900, 168);

		ghost5 = new Ghost(500, 168);
		ghost6 = new Ghost(900, 168);
		ghost7 = new Ghost(500, 168);
		ghost8 = new Ghost(900, 168);

		boss = new Boss1(500, 168);
		boss2 = new Boss1(700, 168);
		hoguera1 = new Hoguera(600, 145, 20, 100);

		// Escalo boss
		boss.escalar(140f * 4f, (744f / 8f) * 4f);
		boss2.escalar(140f * 4f, (744f / 8f) * 4f);

	}

	@Override
	public void render(float delta) {

		// System.out.println(knight.getX());

		// Limpio la pantalla (solamente para ver bien el caballero despues va a tener
		// un fondo
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Actualiza la posición de la cámara para que siga al personaje
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		cam.zoom = 1f;

		// Actualiza la posición de la cámara camKnight para que siga al personaje y a
		// los enemigos
		/*
		 * camKnight.position.set(Config.ANCHO / 2, Config.ALTO / 2, 0);
		 * camKnight.update();
		 */

		// Renderiza el mapa con la cámara cam
		Render.batch.setProjectionMatrix(cam.combined);

		Render.batch.begin();
		cam.zoom = 1f;

		// Ajusta la vista del mapa
		float mapWidth = mapa1.getProperties().get("width", Integer.class)
				* mapa1.getProperties().get("tilewidth", Integer.class);
		float mapHeight = mapa1.getProperties().get("height", Integer.class)
				* mapa1.getProperties().get("tileheight", Integer.class);

		float viewportWidth = cam.viewportWidth * cam.zoom;
		float viewportHeight = cam.viewportHeight * cam.zoom;

		float minX = viewportWidth / 2;
		float maxX = mapWidth - viewportWidth / 2;
		float minY = viewportHeight / 2;
		float maxY = mapHeight - viewportHeight / 2;

		float cameraX = MathUtils.clamp(cam.position.x, minX, maxX);
		float cameraY = MathUtils.clamp(cam.position.y, minY, maxY);

		// Renderizo los fondos.

		if (numeroEscenario <= 4) {
			fondo1.render();
			fondo2.render();
			fondo3.render();
		} else {
			fondo1.dispose();
			fondo2.dispose();
			fondo3.dispose();

			fondo4.render();
			fondo5.render();
			fondo6.render();
			fondo7.render();
		}

		Render.batch.end();

		// Renderiza los personajes y enemigos con la cámara camKnight
		// Render.batch.setProjectionMatrix(hudCamera);

		Render.batch.begin();

		if (enRed) {
			// Renderizo la barra de vida HUD, por algun motivo el shape renderer no puede
			// ser llamdo en la instacion de barravida.render por que sino la textura se ven
			// blancas
			vida.begin(ShapeRenderer.ShapeType.Filled);
			vida.setColor(Color.RED);
			vida.rect(25, 675, knight.vida, 25); // Adjust x, y, width, height as needed
			vida.end();

			vida2.begin(ShapeRenderer.ShapeType.Filled);
			vida2.setColor(Color.BLUE);
			vida2.rect(995, 675, knight2.vida, 25); // Adjust x, y, width, height as needed
			vida2.end();
		} else {
			// Renderizo la barra de vida HUD, por algun motivo el shape renderer no puede
			// ser llamdo en la instacion de barravida.render por que sino la textura se ven
			// blancas
			vida.begin(ShapeRenderer.ShapeType.Filled);
			vida.setColor(Color.RED);
			vida.rect(25, 675, knight.vida, 25); // Adjust x, y, width, height as needed
			vida.end();
			dibujarAreaInteraccion(knight.areaJugador);
		}

		Render.batch.end();

		Render.batch.begin();

		// Renderiza el marco de la barra de vida HUD
		if (enRed) {
			barravida.render();
			barravida2.render();

		} else {
			barravida.render();

		}

		// Manejar el cambio de escenario
		chequearLimites();

		// Maneja la interaccion de los jugadores con el npc
		if (enRed) {
			knight.interactuarNpc(herrero);
			knight2.interactuarNpc(herrero);
		} else {
			knight.interactuarNpc(herrero);
		}

		if (numeroEscenario == 1) {
			// setea la vista del mpaa y lo renderiza

			mapaRenderer1.setView(cam);
			mapaRenderer1.render();
			// renderiza los ghost

			ghost1.render(Render.batch);
			ghost2.render(Render.batch);

			if (plataforma1 == null && plataforma2 == null) {
				plataforma1 = new Rectangle(9 * 24, 10 * 24, 7 * 24, 24);
				plataforma2 = new Rectangle(33 * 24, 10 * 24, 7 * 24, 24);

				if (enRed) {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost1, ghost2);
					knight2.getEnemigosMapa(ghost1, ghost2);

				} else {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost1, ghost2);
				}

				/*
				 * if (enRed) { knight.verificarEstadoEnemigo();
				 * knight2.verificarEstadoEnemigo(); } else { knight.verificarEstadoEnemigo(); }
				 */

			}
			if (enRed) {
				// Cada ghost sigue a un personaje y atacarlo
				ghost1.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost1.atacarKnight(knight);
				ghost2.seguirKnight(knight2, delta); // Actualiza posición para seguir al Knight
				ghost2.atacarKnight(knight2);

				// Chequea colisiones en el mapa
				knight.chequearColisionesMapa(plataforma1);
				knight.chequearColisionesMapa(plataforma2);

				knight2.chequearColisionesMapa(plataforma1);
				knight2.chequearColisionesMapa(plataforma2);

			} else {
				// Cada ghost sigue al knight y atacarlo
				ghost1.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost1.atacarKnight(knight);
				ghost2.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost2.atacarKnight(knight);

				// Chequea colisiones en el mapa
				knight.chequearColisionesMapa(plataforma1);
				knight.chequearColisionesMapa(plataforma2);

			}

		} else if (numeroEscenario == 2 || (Hoguera.numHoguera == 2 && knight.vida <= 0)) {

			mapaRenderer2.setView(cam);
			mapaRenderer2.render();
			hoguera1.render(Render.batch);
			// renderiza los ghost
			ghost3.render(Render.batch);
			ghost4.render(Render.batch);
			if (plataforma3 == null) {
				plataforma3 = new Rectangle(18 * 24, 10 * 24, 16 * 24, 24);

				if (enRed) {
					// chequear si esta en red y usar al segundo jugador tambien
					knight.getEnemigosMapa(ghost3, ghost4);
					knight2.getEnemigosMapa(ghost3, ghost4);

				} else {
					knight.getEnemigosMapa(ghost3, ghost4);
				}

			}

			if (enRed) {
				// Cada ghost sigue a un personaje y atacarlo
				ghost3.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost3.atacarKnight(knight);
				ghost4.seguirKnight(knight2, delta); // Actualiza posición para seguir al Knight
				ghost4.atacarKnight(knight2);

				knight.chequearColisionesMapa(plataforma3);
				knight2.chequearColisionesMapa(plataforma3);

			} else {
				// Cada ghost sigue al knight y atacarlo
				ghost3.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost3.atacarKnight(knight);
				ghost4.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost4.atacarKnight(knight);

				knight.chequearColisionesMapa(plataforma3);

			}

		} else if (numeroEscenario == 3 || Hoguera.numHoguera == 3) {
			mapaRenderer3.setView(cam);
			mapaRenderer3.render();

			boss.render(Render.batch);
			boss.seguirKnight(knight, delta);

			// chequear si esta en red y usar al segundo jugador tambien
			knight.getEnemigosMapa(boss);
			if (enRed) {
				knight.getEnemigosMapa(boss);
				knight2.getEnemigosMapa(boss);
			}

		} else if (numeroEscenario == 4) {
			// Establece la vista del mapa
			mapaRenderer4.setView(cam);
			mapaRenderer4.render();
			// System.out.println("Lobby");
//			Gdx.app.log("Boton inicar", "Click");

			choza.render();
			herrero.render(Render.batch);

		} else if (numeroEscenario == 5) {

			// Establece la vista del mapa
			mapaRenderer5.setView(cam);
			mapaRenderer5.render();

			ghost5.render(Render.batch);
			ghost6.render(Render.batch);

			if (plataforma4 == null && plataforma5 == null && plataforma6 == null) {

				plataforma4 = new Rectangle(11 * 16, 20 * 16, 9 * 16, 16);
				plataforma5 = new Rectangle(31 * 16, 28 * 16, 19 * 16, 16);
				plataforma6 = new Rectangle(62 * 16, 28 * 16, 9 * 16, 16);

				if (enRed) {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost5, ghost6);
					knight2.getEnemigosMapa(ghost5, ghost6);

				} else {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost5, ghost6);
				}
			}

			if (enRed) {
				// Cada ghost sigue a un personaje y atacarlo
				ghost5.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost5.atacarKnight(knight);
				ghost6.seguirKnight(knight2, delta); // Actualiza posición para seguir al Knight
				ghost6.atacarKnight(knight2);

				knight.chequearColisionesMapa(plataforma4);
				knight.chequearColisionesMapa(plataforma5);
				knight.chequearColisionesMapa(plataforma6);
				knight2.chequearColisionesMapa(plataforma4);
				knight2.chequearColisionesMapa(plataforma5);
				knight2.chequearColisionesMapa(plataforma6);

			} else {
				// Cada ghost sigue al knight y atacarlo
				ghost5.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost5.atacarKnight(knight);
				ghost6.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost6.atacarKnight(knight);

				knight.chequearColisionesMapa(plataforma4);
				knight.chequearColisionesMapa(plataforma5);
				knight.chequearColisionesMapa(plataforma6);

			}

		} else if (numeroEscenario == 6) {
			// Establece la vista del mapa
			mapaRenderer6.setView(cam);
			mapaRenderer6.render();
			ghost7.render(Render.batch);
			ghost8.render(Render.batch);

			if (plataforma7 == null && plataforma8 == null) {

				plataforma7 = new Rectangle(10 * 29, 28 * 16, 20 * 16, 16);
				plataforma8 = new Rectangle(50 * 16, 29 * 16, 20 * 16, 16);

				if (enRed) {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost7, ghost6);
					knight2.getEnemigosMapa(ghost7, ghost8);

				} else {
					// Pone los ghost en el array para poder atacarlos
					knight.getEnemigosMapa(ghost7, ghost8);
				}

			}

			if (enRed) {
				// Cada ghost sigue a un personaje y atacarlo
				ghost7.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost7.atacarKnight(knight);
				ghost8.seguirKnight(knight2, delta); // Actualiza posición para seguir al Knight
				ghost8.atacarKnight(knight2);

				knight.chequearColisionesMapa(plataforma7);
				knight.chequearColisionesMapa(plataforma8);
				knight2.chequearColisionesMapa(plataforma7);
				knight2.chequearColisionesMapa(plataforma8);

			} else {
				// Cada ghost sigue al knight y atacarlo
				ghost7.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost7.atacarKnight(knight);
				ghost8.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost8.atacarKnight(knight);

				knight.chequearColisionesMapa(plataforma7);
				knight.chequearColisionesMapa(plataforma8);

			}

		} else if (numeroEscenario == 7) {
			// Establece la vista del mapa
			mapaRenderer7.setView(cam);
			mapaRenderer7.render();

			boss2.render(Render.batch);
			boss2.seguirKnight(knight, delta);
			// chequear si esta en red y usar al segundo jugador tambien

			knight.getEnemigosMapa(boss);

			if (enRed) {
				knight.getEnemigosMapa(boss2);
				knight2.getEnemigosMapa(boss2);
				boss2.atacarKnight(knight2);
				boss2.seguirKnight(knight2, delta);
				boss2.bossFinal();
			}
		}

		// Actualiza el temporizador
		Ghost.tiempoDesdeUltimoAtaque += delta;
		// Actualiza el temporizador
		Boss1.tiempoDesdeUltimoAtaque += delta;

		Render.batch.setProjectionMatrix(hudCamera.combined); // Configura el SpriteBatch para la cámara del HUD
		// Configura el color de fuente y dibuja la información de "Vida"
		font.setColor(Color.WHITE); // Configura el color de fuente (blanco en este ejemplo)

		if (enRed) {
			font.draw(Render.batch, "Knight 1 :" + knight.vida, 40, 695); // Dibuja la vida en la esquina superior
			font.draw(Render.batch, "Almas :" + knight.almas, 40, 655); // Dibuja la vida en la esquina superior
			// izquierda
			font.draw(Render.batch, "Knight 2 : " + knight2.vida, 1010, 695);
			font.draw(Render.batch, "Almas : " + knight2.almas, 1010, 655); // Dibuja la vida en la esquina superior

		} else {
			font.draw(Render.batch, "Knight 1 :" + knight.vida, 40, 695); // Dibuja la vida en la esquina superior
			font.draw(Render.batch, "Almas : " + knight.almas, 40, 655); // Dibuja la vida en la esquina superior //
																			// izquierda
		}

		if (knight.vida == 0) {

			Render.app.setScreen(new ScreenDeath());
			if (Hoguera.encendida) {
				numeroEscenario = 2;
			} else {
				numeroEscenario = 0;
			}
		}

		Render.batch.end();

		/*
		 * // Inicia el dibujado de líneas sr.begin(ShapeType.Line); sr.setColor(255, 0,
		 * 0, 1); // Establece el color de la línea (blanco en este caso)
		 * 
		 * // Dibuja la línea del piso desde (x1, y1) a (x2, y2) sr.line(0, 100,
		 * Gdx.graphics.getWidth(), 100);
		 * 
		 * // Finaliza el dibujado de líneas sr.end();
		 */

		Render.batch.begin();
		if (enRed) {

			knight.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			knight.alternarSprites();
			knight.moverPersonaje();

			knight2.updateAnimation(delta);
			knight2.moverPersonaje();
			knight2.alternarSprites();

		} else {

			knight.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
			knight.alternarSprites(); // Renderiza los sprites
			knight.moverPersonaje(); // Cambia los estados dependiendo de la tecla que tocas

		}

		Render.batch.end();

		if (enRed) {
			if (UtilesRed.hc.salirDelJuego) {
				Render.app.setScreen(new ScreenMenu());
			}
		}
	}

	public void cambiarEscenario() {

		if (numeroEscenario == 1) {
			numeroEscenario = 2;

			// Establece la vista del mapa
			mapaRenderer2.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 168);
			if (enRed) {
				knight2.setPosition(50, 168);
			}
		} else if (numeroEscenario == 2) {
			numeroEscenario = 3;

			// Establece la vista del mapa
			mapaRenderer3.setView(cam);

			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 168);
			if (enRed) {
				knight2.setPosition(50, 168);
			}
		} else if (numeroEscenario == 3) {
			numeroEscenario = 4;

			// Establece la vista del mapa
			mapaRenderer4.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 168);
			if (enRed) {
				knight2.setPosition(50, 168);
			}
		} else if (numeroEscenario == 4) {
			numeroEscenario = 5;

			// Establece la vista del mapa
			mapaRenderer5.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 168);
			if (enRed) {
				knight2.setPosition(50, 168);
			}
		} else if (numeroEscenario == 5) {
			numeroEscenario = 6;

			// Establece la vista del mapa
			mapaRenderer6.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 168);
			if (enRed) {
				knight2.setPosition(50, 168);
			}
		} else if (numeroEscenario == 6) {
			numeroEscenario = 7;

			// Establece la vista del mapa
			mapaRenderer7.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(300, 168);
			if (enRed) {
				knight2.setPosition(320, 168);
			}
		}
	}

	private void chequearLimites() {
		float knightX = knight.getX();
		float knightY = knight.getY();
		float knight2X = 0;
		float knight2Y = 0;
		//knight2X = knight2.getX();
		//knight2Y = knight2.getY();
		

		// Definir límites de transición
		float limiteIzquierdo = 0;
		float limiteDerecho = Config.ANCHO;
		float limiteInferior = 0;
		float limiteSuperior = Config.ALTO;

		if(enRed) {
			if (knightX > limiteDerecho && knight2X > limiteDerecho) {
				// Personajes salierón por la derecha
				cambiarEscenario();
			}else {
				if (knightX > limiteDerecho) {
					// Personajes salierón por la derecha
					cambiarEscenario();
				}
			}
		}

		

	}

	public void dibujarAreaInteraccion(Rectangle rectangulo) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(Render.batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(rectangulo.x, rectangulo.y, rectangulo.width, rectangulo.height);
		shapeRenderer.end();
	}

	public Knight3 getJugador1() {

		return knight;

	}

	public Knight3 getJugador2() {

		return knight2;

	}

	@Override
	public void resize(int width, int height) {
		// El resize de la cam con la ventana
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
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
		mapa1.dispose();

	}

}
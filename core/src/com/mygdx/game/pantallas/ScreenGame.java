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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.recursos.Boss1;
import com.mygdx.game.recursos.Enemigo;
import com.mygdx.game.recursos.EstadosKnight;
import com.mygdx.game.recursos.Fondo;
import com.mygdx.game.recursos.Ghost;
import com.mygdx.game.recursos.Hoguera;
import com.mygdx.game.recursos.Knight3;
import com.mygdx.game.recursos.KnightBlue;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Render;

public class ScreenGame implements Screen {
	// Camara y HUD
	Image personaje;
	private OrthographicCamera hudCamera; // creo la camara del hud
	private BitmapFont font;
	private int muertes = 0; // Ejemplo: Inicializa el contador de muertes a 0
	private Knight3 knight;
	private Knight3 knight2;

	// Enemigos
	private Enemigo ghost1;
	private Ghost ghost2;
	private Ghost ghost3;
	private Boss1 boss;

	// Hoguera checkpoints
	private Hoguera hoguera1;
	private Hoguera hoguera2;

	// Plataformas
	public Rectangle plataforma1;
	public Rectangle plataforma2;
	public Rectangle plataforma3;

	ShapeRenderer sr; // Agrega un objeto ShapeRenderer
	public static int numeroEscenario = 0;

	// Fondos
	private Fondo fondo1;
	private Fondo fondo2;
	private Fondo fondo3;
	private OrthographicCamera cam, camKnight; // creo la camara

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

		if(enRed) {
		UtilesRed.hc.setGame(this);
		}
	}

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
		if (enRed) {
			if (knightX + knightWidth < limiteIzquierdo) {
				// Personaje salió por la izquierda

			} else if (knightX > limiteDerecho ) {
				// Personaje salió por la derecha
				cambiarEscenario();
			} else if (knightY + knightHeight < limiteInferior) {
				// Personaje salió por abajo

			} else if (knightY > limiteSuperior) {
				// Personaje salió por arriba

			}
		} else {
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

	}

	private void cambiarEscenario() {

		if (numeroEscenario == 1) {
			numeroEscenario = 2;

			// Establece la vista del mapa
			mapaRenderer2.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		} else if (numeroEscenario == 2) {
			numeroEscenario = 3;

			// Establece la vista del mapa
			mapaRenderer3.setView(cam);

			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		} else if (numeroEscenario == 3) {
			numeroEscenario = 4;

			// Establece la vista del mapa
			mapaRenderer4.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		} else if (numeroEscenario == 4) {
			numeroEscenario = 5;

			// Establece la vista del mapa
			mapaRenderer5.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		} else if (numeroEscenario == 5) {
			numeroEscenario = 6;

			// Establece la vista del mapa
			mapaRenderer6.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		} else if (numeroEscenario == 6) {
			numeroEscenario = 7;

			// Establece la vista del mapa
			mapaRenderer7.setView(cam);
			// Restablecer la posición inicial del los personajes en el nuevo escenario
			knight.setPosition(20, 145);
			if(enRed) {
				knight2.setPosition(50, 145);				
			}
		}
		/*
		 * else if (numeroEscenario == 1) { // Cargar el nuevo escenario y ajustar la
		 * posición del personaje // Por ejemplo, puedes cargar otro mapa con
		 * TiledMapLoader mapa = new TmxMapLoader().load("Mapas/Mapa1/Mapa 2.tmx");
		 * mapaRenderer = new OrthogonalTiledMapRenderer(mapa); // crea el render
		 * 
		 * numeroEscenario = 2;
		 * 
		 * // Restablecer la posición inicial del los personajes en el nuevo escenario
		 * knight.setPosition(20, 145);
		 * 
		 * } else if (numeroEscenario == 2) { // Cargar el nuevo escenario y ajustar la
		 * posición del personaje // Por ejemplo, puedes cargar otro mapa con
		 * TiledMapLoader mapa = new TmxMapLoader().load("Mapas/Mapa1/Mapa 3.tmx");
		 * mapaRenderer = new OrthogonalTiledMapRenderer(mapa); // crea el render
		 * 
		 * numeroEscenario = 3;
		 * 
		 * // Restablecer la posición inicial del los personajes en el nuevo escenario
		 * knight.setPosition(100, 145);
		 * 
		 * }
		 */
	}

	@Override
	public void show() {

		numeroEscenario = 1;

		// Restringe el cursor del mouse a no ser visible
//		Gdx.input.setCursorCatched(true);
		cam = new OrthographicCamera(Config.ANCHO, Config.ALTO); // creo la camara
		cam.setToOrtho(false, Config.ANCHO, Config.ALTO);
		cam.position.x = Config.ANCHO/2;
		cam.position.y = Config.ALTO/2;
		
		camKnight = new OrthographicCamera(Config.ANCHO, Config.ALTO);
		camKnight.setToOrtho(false, Config.ANCHO, Config.ALTO);
		camKnight.position.x = Config.ANCHO/2;
		camKnight.position.y = Config.ALTO/2;
		
		
		
		cam.zoom = 1;

		// Inicializa la cámara del HUD
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.zoom = 2;

		// Crear el fondo
		fondo1 = new Fondo("Mapas/Mapa1/background_layer_1.png");
		fondo2 = new Fondo("Mapas/Mapa1/background_layer_2.png");
		fondo3 = new Fondo("Mapas/Mapa1/background_layer_3.png");

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

		// Plataformas
		plataforma1 = new Rectangle(9 * 24, 10 * 24, 7 * 24, 24);
		plataforma2 = new Rectangle(33 * 24, 10 * 24, 7 * 24, 24);
		plataforma3 = new Rectangle(18 * 24, 10 * 24, 16 * 24, 24);

		// Ajusta la cámara del mapa para que ocupe toda la pantalla

		// Inicializa la fuente (ajusta los parámetros según tus preferencias)
		font = new BitmapFont();
		font.setColor(Color.WHITE); // Configura el color de la fuente
		font.getData().setScale(1.5f); // Escala el tamaño de la fuente

		sr = new ShapeRenderer(); // Inicializa el ShapeRenderer

		// Inicializar personajes

		if (enRed) {//Siempre chequear que el juego esta en red para hacer cualquier cosa con el knigth2
			knight = new Knight3(20, 168, 100, 100, true);
			knight2 = new Knight3(50, 168, 100, 100, true);
			System.out.println("creado");
		} else {
			knight = new Knight3(20, 168, 100, 100, false);
		}

		ghost1 = new Ghost(500, 145);
		ghost2 = new Ghost(500, 145);
		ghost3 = new Ghost(900, 145);
		boss = new Boss1(900, 90);
		hoguera1 = new Hoguera(140, 145, 20, 100);
		hoguera2 = new Hoguera(600, 145, 20, 100);

	}

	@Override
	public void render(float delta) {

		    // Limpio la pantalla (solamente para ver bien el caballero despues va a tener un fondo)
		    Gdx.gl.glClearColor(0, 0, 0, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		    // Actualiza la posición de la cámara para que siga al personaje
		    cam.position.set(Config.ANCHO/2, Config.ALTO/2, 0);
		    cam.update();

		    // Actualiza la posición de la cámara camKnight para que siga al personaje y a los enemigos
		    camKnight.position.set(Config.ANCHO/2, Config.ALTO/2, 0);
		    camKnight.update();

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

					fondo1.render();
					fondo2.render();
					fondo3.render();
					
		    Render.batch.end();

		    // Renderiza los personajes y enemigos con la cámara camKnight
		    Render.batch.setProjectionMatrix(camKnight.combined);


		    // Dibujar aquí los personajes y enemigos
		    // ...
			// Limpio la pantalla (solamente para ver bien el caballero despues va a tener
			// un fondo)


			// Actualiza la posición de la cámara para que siga al personaje
			/*
			 * cam.position.set(knight.getX() + knight.getWidth() / 2,360, 0); cam.update();
			 */

			// Manejar el cambio de escenario
			chequearLimites();

			/*
			 * //Maneja las colisiones knight.chequearColisiones(ghost1);
			 * knight.chequearColisiones(ghost2); knight.chequearColisiones(ghost3);
			 */
			Render.batch.begin();
		    camKnight.zoom = .1f;
			
			
			//knight2.updateAnimation(delta);

			// Dibuja el fondo


			
			

			


			camKnight.update();

			//knight.render(Render.batch);

			
			if (numeroEscenario == 1) {

				mapaRenderer1.setView(cam);
				mapaRenderer1.render();
				// ghost1.updateAnimation(delta); // Actualiza la animación del personaje según
				// el estado actual
				ghost1.render(Render.batch);
				ghost1.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost1.atacarKnight(knight);

				// dibujarAreaInteraccion(plataforma1);
				// dibujarAreaInteraccion(plataforma2);
				knight.chequearColisionesMapa(plataforma1);
				knight.chequearColisionesMapa(plataforma2);

			} else if (numeroEscenario == 2 || (Hoguera.numHoguera == 2 && knight.vida <= 0)) {

				mapaRenderer2.setView(cam);
				mapaRenderer2.render();
				hoguera2.render(Render.batch);
				ghost1.dispose();
				// ghost2.updateAnimation(delta); // Actualiza la animación del personaje según
				// el estado actual
				ghost2.render(Render.batch);
				ghost2.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost2.atacarKnight(knight);

				// ghost3.updateAnimation(delta); // Actualiza la animación del personaje según
				// el estado actual
				ghost3.render(Render.batch);
				ghost3.seguirKnight(knight, delta); // Actualiza posición para seguir al Knight
				ghost3.atacarKnight(knight);
//			dibujarAreaInteraccion(plataforma3);
				knight.chequearColisionesMapa(plataforma3);

			} else if (numeroEscenario == 3 || Hoguera.numHoguera == 3) {
				mapaRenderer3.setView(cam);
				mapaRenderer3.render();
				// boss.updateAnimation(delta); // Actualiza la animación del personaje según el
				// estado actual
				boss.render(Render.batch);
				boss.seguirKnight(knight, delta);
				ghost3.dispose();
				ghost2.dispose();

			} else if (numeroEscenario == 4) {
				// Establece la vista del mapa
				mapaRenderer4.setView(cam);
				mapaRenderer4.render();
				// System.out.println("Lobby");
//			Gdx.app.log("Boton inicar", "Click");
			}else if (numeroEscenario == 5) {
				// Establece la vista del mapa
				mapaRenderer5.setView(cam);
				mapaRenderer5.render();

			}else if (numeroEscenario == 6) {
				// Establece la vista del mapa
				mapaRenderer6.setView(cam);
				mapaRenderer6.render();

			}else if (numeroEscenario == 7) {
				// Establece la vista del mapa
				mapaRenderer7.setView(cam);
				mapaRenderer7.render();

			}
			if (enRed) {
				
				
				knight2.moverPersonaje();
				knight2.updateAnimation(delta);	
				knight2.alternarSprites();
				//knight.render(Render.batch);
				knight.alternarSprites();
				knight.moverPersonaje();
				knight.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
				
				// System.out.println("renderizando");
				//knight2.render(Render.batch);
			}else {
			
				knight.moverPersonaje();
				knight.updateAnimation(delta); // Actualiza la animación del personaje según el estado actual
				knight.moverPersonaje();
				knight.alternarSprites();
			
				//knight.render(Render.batch);
			}
			
		
			if(enRed) {
				
			}
			
			
			// Actualiza el temporizador
			Ghost.tiempoDesdeUltimoAtaque += delta;
			// Actualiza el temporizador
			Boss1.tiempoDesdeUltimoAtaque += delta;

			Render.batch.setProjectionMatrix(hudCamera.combined); // Configura el SpriteBatch para la cámara del HUD
			// Configura el color de fuente y dibuja la información de "Vida"
			font.setColor(Color.WHITE); // Configura el color de fuente (blanco en este ejemplo)
			font.draw(Render.batch, "Vida: " + knight.vida, 10, 700); // Dibuja la vida en la esquina superior izquierda
			font.draw(Render.batch, "Muertes: " + muertes, 10, 670); // Dibuja las muertes en la esquina superior
																		// derecha

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


			if(enRed) {
			if(UtilesRed.hc.salirDelJuego) {
				Render.app.setScreen(new ScreenMenu());				
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
		mapa1.dispose();

	}

}
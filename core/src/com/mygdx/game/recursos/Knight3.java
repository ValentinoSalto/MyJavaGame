package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Animador;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;
import java.util.ArrayList;

public class Knight3 {

	private float alto, ancho;

	private String textura;

	private Animador idleAnimation;
	private Animador walkingLeftAnimation;
	private Animador walkingRightAnimation;
	private Animador runAnimation;
	private Animador runLeftAnimation;
	private Animador attackAnimation;
	private Animador coverAnimation;
	private Animador coverWalkRightAnimation;
	private Animador coverWalkLeftAnimation;
	private Animador jumpAnimation;
	private Animador fallAnimation;

	/*
	 * private TextureRegion[] regionsMovement_idle; private TextureRegion[]
	 * regionsMovement_walking_left; private TextureRegion[]
	 * regionsMovement_walking_right; private TextureRegion[] regionsMovement_run;
	 * private TextureRegion[] regionsMovement_runLeft; private TextureRegion[]
	 * regionsMovement_attack; private TextureRegion[] regionsMovement_cover;
	 * private TextureRegion[] regionsMovement_coverWalkRight; private
	 * TextureRegion[] regionsMovement_coverWalkLeft; private TextureRegion[]
	 * regionsMovement_jump; private TextureRegion[] regionsMovement_fall; private
	 * Texture idleTexture;
	 * 
	 * private TextureRegion currentFrame;
	 */
	private float time;
	private boolean ataqueIniciado;
	private float tiempoAtaque;
	private EstadosKnight estadoActual;
	public static int vida = 260;
	public boolean bloqueando = false;
	public boolean jumping = false;
	public boolean terminoSalto = true;
	public boolean cayendo = false;
	private boolean atacando = false;
	private final float DISTANCIA_ATAQUE = 48f;
	private float GROUND_LEVEL = 168f;
	public final float GRAVITY = -300; // Ajusta según la gravedad deseada
	public final float JUMP_SPEED = 300; // Ajusta según la velocidad de salto deseada
	public final float RANGO_ATAQUE = 50; // Ajusta según el rango de ataque deseado
	private final float ALTURA_SALTO = 300f;
	public float ySpeed = 0;
	public boolean lastimable = false;
	boolean bloqueoActivo;
	boolean moverse = true;
	float delta;
	boolean personaje = false;
	public int almas = 0;
	public ArrayList<Enemigo> enemigosEnElMapa;
	private String rutaTextura;
	// colisiones
	public Rectangle areaJugador;
	private Vector2 posicion;
	boolean pasoPlataforma = false;
	private boolean enRed = false;

	public Knight3(float x, float y, float ancho, float alto, boolean enRed, String textura, boolean personaje) {

		posicion = new Vector2();
		posicion.x = x;
		posicion.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.enRed = enRed;
		this.textura = textura;
		this.personaje = personaje;
		enemigosEnElMapa = new ArrayList<Enemigo>();
		estadoActual = EstadosKnight.IDLE;

		areaJugador = new Rectangle(this.posicion.x, this.posicion.y, this.ancho, this.alto / 2);
		crearAnimacion();

		dibujarAreaInteraccion();
		delta = Gdx.graphics.getDeltaTime();
		moverPersonaje();
	}

	public void moverPersonaje() {

		if (!enRed) {

			// Maneja las entradas del teclado para cambiar el estado del personaje
			if (Gdx.input.isKeyPressed(Keys.A) && !bloqueando
					&& moverse /* && direccion != EstadosKnight.WALKING_LEFT */) {

				cambiarEstado(EstadosKnight.WALKING_LEFT);

				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					cambiarEstado(EstadosKnight.RUN_LEFT);

				}

			} else if (Gdx.input.isKeyPressed(Keys.D) && !bloqueando
					&& moverse /* && direccion != EstadosKnight.WALKING_RIGHT */) {
				cambiarEstado(EstadosKnight.WALKING_RIGHT);
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					cambiarEstado(EstadosKnight.RUN_RIGHT);

				}
			} else if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !bloqueando) {

				cambiarEstado(EstadosKnight.JUMP);

			} else if (Gdx.input.isKeyJustPressed(Keys.E)) {
				encenderHoguera();

			} else if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {

				atacando = true;
				cambiarEstado(EstadosKnight.ATTACK);
				System.out.println("ataca");

			} else if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
					// Inicia la acción de bloqueo
					bloqueoActivo = true;
					// Cambia el estado del personaje a COVER
					cambiarEstado(EstadosKnight.COVER);
					if (Gdx.input.isKeyPressed(Keys.A)) {
						// spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
						cambiarEstado(EstadosKnight.COVER_LEFT);
						bloqueando = true;

					} else if (Gdx.input.isKeyPressed(Keys.D)) {
						cambiarEstado(EstadosKnight.COVER_RIGHT);
						bloqueando = true;

					}

				} else {
					// Si el clic derecho no está presionado, detiene la acción de bloqueo
					bloqueoActivo = false;
					// Cambia el estado del personaje a IDLE (o cualquier otro estado apropiado)
				}

			} else {
				if (!atacando) {
					estadoActual = EstadosKnight.IDLE;
				}

			}

			if (!terminoSalto) {

				if (jumping) {
					moverse = false;
					estadoActual = EstadosKnight.JUMP;
					saltar();
				}

				if (cayendo) {
					estadoActual = EstadosKnight.FALL;
					caer();
				}
			}

		} else {
			// Maneja las entradas del teclado para cambiar el estado del personaje
			if (Gdx.input.isKeyPressed(Keys.A) && personaje) {
				System.out.println("moverse en red izquierda");
				cambiarEstado(EstadosKnight.WALKING_LEFT);
				UtilesRed.hc.enviarMensaje("moverse#izquierda" + "#" + UtilesRed.hc.IdCliente);

				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					cambiarEstado(EstadosKnight.RUN_LEFT);
					UtilesRed.hc.enviarMensaje("moverse#correrizquierda" + "#" + UtilesRed.hc.IdCliente);
				}

			} else if (Gdx.input.isKeyPressed(Keys.D) && personaje) {
				// System.out.println("moverse en red derecha");
				UtilesRed.hc.enviarMensaje("moverse#derecha" + "#" + UtilesRed.hc.IdCliente);
				cambiarEstado(EstadosKnight.WALKING_RIGHT);

				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && personaje) {
					cambiarEstado(EstadosKnight.RUN_RIGHT);
					UtilesRed.hc.enviarMensaje("moverse#correrderecha" + "#" + UtilesRed.hc.IdCliente);
				}

			} else if (Gdx.input.isKeyPressed(Keys.SPACE) && personaje) {
				UtilesRed.hc.enviarMensaje("moverse#arriba" + "#" + UtilesRed.hc.IdCliente);
				cambiarEstado(EstadosKnight.JUMP);

			} else if (Gdx.input.isButtonPressed(Buttons.LEFT) && personaje) {
				System.out.println("ataca");
				UtilesRed.hc.enviarMensaje("moverse#atacar" + "#" + UtilesRed.hc.IdCliente);
				atacando = true;
				cambiarEstado(EstadosKnight.ATTACK);
			} else if (Gdx.input.isButtonPressed(Buttons.RIGHT) && personaje) {
				System.out.println("cubrirse");
				UtilesRed.hc.enviarMensaje("moverse#cubrirse" + "#" + UtilesRed.hc.IdCliente);
				cambiarEstado(EstadosKnight.COVER);
				/*
				 * if (Gdx.input.isKeyPressed(Keys.A)&&personaje) {
				 * 
				 * UtilesRed.hc.enviarMensaje("moverse#cubrirseizquierda"+"#"+
				 * UtilesRed.hc.IdCliente); cambiarEstado(EstadosKnight.COVER_LEFT); bloqueando
				 * = true;
				 * 
				 * } else if (Gdx.input.isKeyPressed(Keys.D)&&personaje) {
				 * UtilesRed.hc.enviarMensaje("moverse#cubrirsederecha"+"#"+
				 * UtilesRed.hc.IdCliente); cambiarEstado(EstadosKnight.COVER_RIGHT); bloqueando
				 * = true;
				 * 
				 * }
				 */
			} else {
				estadoActual = EstadosKnight.IDLE;
			}

		}

	}

	public void inciarSalto() {
		// salto

		jumping = true;
		moverse = false;
		terminoSalto = false;

	}

	public void saltar() {
		if (!cayendo) {

			posicion.y -= GRAVITY * delta;

			if (posicion.y < ALTURA_SALTO) {// la altura deseada
				posicion.y += JUMP_SPEED * delta; // esto se sube para arriba

			}

			if (posicion.y >= ALTURA_SALTO) {// aca ya estas cayendo
				cayendo = true;

			}
		}

	}

	public void caer() {
		if (!terminoSalto && cayendo) {
			estadoActual = EstadosKnight.FALL;
			posicion.y += GRAVITY * delta * 2;
			System.out.println(posicion.y);

			if (!pasoPlataforma) {

				if (posicion.y <= GROUND_LEVEL) {// la altura deseada
					posicion.y = GROUND_LEVEL; // aca cuando termino de caer
					terminoSalto = true;
					moverse = true;
					jumping = false;
					cayendo = false;
					estadoActual = EstadosKnight.IDLE;
				}
			} else {
				System.out.println("me pare en una plataforma");
				posicion.y = 285; // aca cuando termino de caer
				terminoSalto = true;
				moverse = true;
				jumping = false;
				cayendo = false;
				estadoActual = EstadosKnight.IDLE;
			}
		}

	}

	public void actualizarPosicionRed(float x, float y, String estado) {
		posicion.x = x;
		posicion.y = y;

		System.out.println(estado + " ");
		if (estado.equals(String.valueOf(EstadosKnight.WALKING_LEFT))) {
			walkingLeftAnimation.render();

		} else if (estado.equals(String.valueOf(EstadosKnight.WALKING_RIGHT))) {
			walkingLeftAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.RUN_LEFT))) {
			runLeftAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.RUN_RIGHT))) {
			runAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.RUN_RIGHT))) {
			runAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.JUMP))) {
			jumpAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.ATTACK))) {
			attackAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.COVER))) {
			coverAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.COVER_LEFT))) {
			coverWalkLeftAnimation.render();
		} else if (estado.equals(String.valueOf(EstadosKnight.COVER_RIGHT))) {
			coverWalkRightAnimation.render();
		}

		// spr.setPosition(posicion.x, posicion.y);
	}

	public void update() {

	}

	public void updateAnimation(float delta) {

		switch (estadoActual) {
		case IDLE:
			// spr.setRegion(idleAnimation.getKeyFrame(time, true));
			bloqueando = false;
			terminoSalto = true;
			moverse = true;
//			posicion.y = 145;
			atacando = false;
			break;

		case WALKING_LEFT:
			// spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			atacando = false;
			posicion.x -= 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_LEFT);
				posicion.x -= 3;

			}
			break;

		case WALKING_RIGHT:
			// spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));

			atacando = false;
			posicion.x += 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {

				// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_RIGHT);
				posicion.x += 6;

			}
			break;

		case JUMP:

			atacando = false;
			inciarSalto();

			break;

		case FALL:
//
//			if (!pasoPlataforma) {
//
//				if (posicion.y > GROUND_LEVEL) {
//					posicion.y -= JUMP_SPEED * delta;
//
//				}
//
//				if (posicion.y <= GROUND_LEVEL) {
//					jumping = false;
//					terminoSalto = true;
//					estadoActual = EstadosKnight.IDLE;
//					moverse = true;
//				} else {
//					spr.setRegion(fallAnimation.getKeyFrame(time, true));
//				}
//
//			} else {
//				posicion.y = 260;
//				estadoActual = EstadosKnight.IDLE;
//				terminoSalto = true;
//			}
			break;

		case RUN_RIGHT:
			atacando = false;
			posicion.x += 6;

			break;

		case RUN_LEFT:
			atacando = false;
			posicion.x -= 6;
			break;

		case ATTACK:
			iniciarAtaque();

			for (int i = 0; i < enemigosEnElMapa.size(); i++) {
				if (DISTANCIA_ATAQUE > enemigosEnElMapa.get(i).getX() - posicion.x) {
					enemigosEnElMapa.get(i).restarVidaEnemigo(0.5f);

					if (enemigosEnElMapa.get(i).vida <= 0) {
						enemigosEnElMapa.get(i).morir();
						enemigosEnElMapa.remove(i);
						almas += 30;

						atacando = false;
					}
				} else {

				}

			}

			break;

		case COVER:

			bloqueando = true;

			break;
		case COVER_RIGHT:

			bloqueando = true;
			posicion.x += 3;

			break;
		case COVER_LEFT:
			bloqueando = true;
			posicion.x -= 3;

			break;

		}

		// spr.setPosition(posicion.x, posicion.y);
		areaJugador.setPosition(posicion.x, posicion.y);
	}

	public void cambiarEstado(EstadosKnight nuevoEstado) {
		estadoActual = nuevoEstado;
		// spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

	}

	private void iniciarAtaque() {
		System.out.println("atacando");

	}

	public void restarVida(int cantidad) {

		if (!bloqueando) {

			vida -= cantidad;

		}

	}

	public void encenderHoguera() {

		if (!Hoguera.encendida) {
			if (posicion.x >= Hoguera.x - Hoguera.distancia || posicion.x <= Hoguera.x + Hoguera.distancia) {
				Hoguera.encendida = true;
			} else {
				System.out.println("No hay hoguera cerca");
			}
		}
	}

	/*
	 * public void chequearColisiones(Ghost area) {
	 * 
	 * if (areaJugador.overlaps(area.areaJugador)) { lastimable = true;
	 * //System.out.println("contacto"); area.atacarKnight(this);
	 * 
	 * }else { lastimable = false; }
	 * 
	 * }
	 */
	public void interactuarNpc(Npc npc) {

		if (posicion.x > npc.posicion.x - npc.distancia && posicion.x < npc.posicion.x + npc.distancia) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				if (almas >= 100) {
					vida += 100;
					almas -= 100;
				}
			}
		}
	}

	/*
	 * private void mostrarMenu() { // Aquí mostrarías el menú en la pantalla //
	 * Puedes utilizar una librería de UI como Scene2D para crear el menú // El
	 * siguiente código es solo un ejemplo de cómo podrías mostrar el menú: Menu
	 * menu = new Menu(); menu.mostrar(); // Método ficticio para mostrar el menú //
	 * Espera la selección del jugador OpcionMenu opcionSeleccionada =
	 * menu.obtenerSeleccion(); // Realiza la acción correspondiente
	 * ejecutarAccion(opcionSeleccionada); } private void ejecutarAccion(OpcionMenu
	 * opcion) { // Dependiendo de la opción seleccionada, realiza la acción
	 * correspondiente if (opcion == OpcionMenu.SUBIR_VIDA) { // Lógica para subir
	 * la vida del jugador vida += 50; // Por ejemplo, incrementa la vida en 50
	 * puntos } else if (opcion == OpcionMenu.AUMENTAR_DAÑO) { // Lógica para
	 * aumentar el daño del jugador // ... } }
	 */

	public void getEnemigosMapa(Enemigo... enemigos) {// var args, cantidad de parametros indeterminada
		enemigosEnElMapa.clear();

		for (int i = 0; i < enemigos.length; i++) {
			enemigosEnElMapa.add(enemigos[i]);
		}
	}

	public void chequearColisionesMapa(Rectangle plataformas) {

		if (areaJugador.overlaps(plataformas)) {
			lastimable = true;
			System.out.println("contacto");
			pasoPlataforma = true;

		} else {
			lastimable = false;
			pasoPlataforma = false;

		}

	}

	public void alternarSprites() {
		switch (estadoActual) {
		case IDLE:
			idleAnimation.render();
			break;
		case WALKING_LEFT:
			walkingLeftAnimation.render();
			break;

		case WALKING_RIGHT:
			walkingRightAnimation.render();
			break;
		case RUN_RIGHT:
			runAnimation.render();
			break;
		case RUN_LEFT:
			runLeftAnimation.render();
			break;
		case ATTACK:
			attackAnimation.render();
			break;
		case COVER:
			coverAnimation.render();
			break;
		case COVER_RIGHT:
			coverWalkRightAnimation.render();
			break;
		case COVER_LEFT:
			coverWalkLeftAnimation.render();
			break;
		case JUMP:
			jumpAnimation.render();
			break;
		case FALL:
			fallAnimation.render();
			break;
		default:
			idleAnimation.render();
		}
	}

	public void dibujarAreaInteraccion() {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(Render.batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(areaJugador.x, areaJugador.y, areaJugador.width, areaJugador.height);
		shapeRenderer.end();
	}

	private void crearAnimacion() {
		// Crea animaciones
		idleAnimation = new Animador(textura, posicion, 0, 6, 11);
		walkingLeftAnimation = new Animador(textura, posicion, 2, 6, 11);
		walkingRightAnimation = new Animador(textura, posicion, 1, 6, 11);
		runAnimation = new Animador(textura, posicion, 3, 6, 11);
		runLeftAnimation = new Animador(textura, posicion, 4, 6, 11);
		attackAnimation = new Animador(textura, posicion, 10, 6, 11);
		coverAnimation = new Animador(textura, posicion, 7, 6, 11);
		coverWalkRightAnimation = new Animador(textura, posicion, 8, 6, 11);
		coverWalkLeftAnimation = new Animador(textura, posicion, 9, 6, 11);
		jumpAnimation = new Animador(textura, posicion, 5, 6, 11);
		fallAnimation = new Animador(textura, posicion, 6, 6, 11);

	}

	public void verificarEstadoEnemigo() {
		for (Enemigo enemigo : enemigosEnElMapa) {
			UtilesRed.hc.enviarMensaje("enemigoMuerto#" + String.valueOf(enemigo.muerto) + "#"
					+ String.valueOf(enemigosEnElMapa.indexOf(enemigo)) + "#" + UtilesRed.hc.IdCliente);
		}
	}

	public void setPosition(float newX, float newY) {
		posicion.x = newX;
		posicion.y = newY;
	}

	public float getX() {
		return posicion.x;
	}

	public float getY() {
		return posicion.y;
	}

	public float getWidth() {
		return alto;
	}

	public float getHeight() {
		return ancho;
	}

	public Vector2 getPosicion() {
		return posicion;
	}

}
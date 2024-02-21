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

public class Knight3 {

	private float alto, ancho;

	private Texture textura;

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
	public int vida = 100;
	public boolean bloqueando = false;
	public boolean jumping = false;
	public boolean terminoSalto = true;
	public boolean cayendo = false;
	private float GROUND_LEVEL = 168f;
	public final float GRAVITY = -100; // Ajusta según la gravedad deseada
	public final float JUMP_SPEED = 100; // Ajusta según la velocidad de salto deseada
	public final float RANGO_ATAQUE = 50; // Ajusta según el rango de ataque deseado
	private final float ALTURA_SALTO = 300f;
	public float ySpeed = 0;
	public boolean lastimable = false;
	boolean bloqueoActivo;
	boolean moverse = true;
	float delta;

	private String rutaTextura;
	// colisiones
	public Rectangle areaJugador;
	private Vector2 posicion;
	boolean pasoPlataforma = false;
	private boolean enRed = false;

	public Knight3(float x, float y, float ancho, float alto, boolean enRed) {

		posicion = new Vector2();
		posicion.x = x;
		posicion.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.enRed = enRed;
		this.rutaTextura = rutaTextura;

		

		areaJugador = new Rectangle(this.posicion.x, this.posicion.y, this.ancho, this.alto / 2);
		crearAnimacion();

		/*
		 * // CARGA LAS TEXTURAS idleTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Combat Ready Idle.png"));
		 * Texture walkingRightTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Walk.png")); Texture
		 * walkingLeftTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Walk2.png")); Texture
		 * runTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Run.png"));
		 * Texture runLeftTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Run2.png")); Texture
		 * attackTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Attack 1.png")); Texture
		 * coverTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise.png")); Texture
		 * coverWalkRightTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise Walk.png"));
		 * Texture coverWalkLeftTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise Walk2.png"));
		 * Texture jumpTexture = new
		 * Texture(Gdx.files.internal("Personajes/Hero/1/jump.png")); Texture
		 * fallTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/fall.png"));
		 * 
		 * // LOGICA DE LAS ANIMACIONES
		 * 
		 * // IDLE
		 * 
		 * TextureRegion[][] idleFrames = TextureRegion.split(idleTexture,
		 * idleTexture.getWidth() / 5, idleTexture.getHeight()); regionsMovement_idle =
		 * new TextureRegion[5];
		 * 
		 * for (int i = 0; i < 5; i++) { regionsMovement_idle[i] = idleFrames[0][i];
		 * idleAnimation = new Animation<>(1 / 5f, idleFrames[0]); time = 0f; }
		 * 
		 * // CAMINAR HACIA LA IZQUIERDA TextureRegion[][] walkingLeftFrames =
		 * TextureRegion.split(walkingLeftTexture, walkingLeftTexture.getWidth() / 6,
		 * walkingLeftTexture.getHeight()); regionsMovement_walking_left = new
		 * TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_walking_left[i] =
		 * walkingLeftFrames[0][i]; walkingLeftAnimation = new Animation<>(1 / 10f,
		 * walkingLeftFrames[0]); time = 0f; }
		 * 
		 * // CAMINAR HACIA LA DERECHA
		 * 
		 * TextureRegion[][] walkingRightFrames =
		 * TextureRegion.split(walkingRightTexture, walkingRightTexture.getWidth() / 6,
		 * walkingRightTexture.getHeight()); regionsMovement_walking_right = new
		 * TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_walking_right[i] =
		 * walkingRightFrames[0][i]; walkingRightAnimation = new Animation<>(1 / 10f,
		 * walkingRightFrames[0]); time = 0f; }
		 * 
		 * // CORRER HACIA LA DERECHA
		 * 
		 * TextureRegion[][] runFrames = TextureRegion.split(runTexture,
		 * runTexture.getWidth() / 6, runTexture.getHeight()); regionsMovement_run = new
		 * TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_run[i] = runFrames[0][i];
		 * runAnimation = new Animation<>(1 / 10f, runFrames[0]); time = 0f; }
		 * 
		 * // CORRER HACIA LA IZQUIERDA
		 * 
		 * TextureRegion[][] runLeftFrames = TextureRegion.split(runLeftTexture,
		 * runLeftTexture.getWidth() / 6, runLeftTexture.getHeight());
		 * regionsMovement_runLeft = new TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_runLeft[i] =
		 * runLeftFrames[0][i]; runLeftAnimation = new Animation<>(1 / 10f,
		 * runLeftFrames[0]); time = 0f; }
		 * 
		 * // ATACAR
		 * 
		 * TextureRegion[][] attackFrames = TextureRegion.split(attackTexture,
		 * attackTexture.getWidth() / 10, attackTexture.getHeight());
		 * regionsMovement_attack = new TextureRegion[10];
		 * 
		 * for (int i = 0; i < 10; i++) { regionsMovement_attack[i] =
		 * attackFrames[0][i]; attackAnimation = new Animation<>(1 / 20f,
		 * attackFrames[0]); time = 0f; }
		 * 
		 * // CUBRIR
		 * 
		 * TextureRegion[][] coverFrames = TextureRegion.split(coverTexture,
		 * coverTexture.getWidth() / 5, coverTexture.getHeight()); regionsMovement_cover
		 * = new TextureRegion[5];
		 * 
		 * for (int i = 0; i < 5; i++) { regionsMovement_cover[i] = coverFrames[0][i];
		 * coverAnimation = new Animation<>(1 / 10f, coverFrames[0]); time = 0f; }
		 * 
		 * // CUBRIR CAMINANDO A LA DERECHA
		 * 
		 * TextureRegion[][] coverWalkRightFrames =
		 * TextureRegion.split(coverWalkRightTexture, coverWalkRightTexture.getWidth() /
		 * 6, coverWalkRightTexture.getHeight()); regionsMovement_coverWalkRight = new
		 * TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_coverWalkRight[i] =
		 * coverWalkRightFrames[0][i]; coverWalkRightAnimation = new Animation<>(1 /
		 * 10f, coverWalkRightFrames[0]); time = 0f; }
		 * 
		 * // CUBRIR CAMINANDO A LA IZQUIERDA
		 * 
		 * TextureRegion[][] coverWalkLeftFrames =
		 * TextureRegion.split(coverWalkLeftTexture, coverWalkLeftTexture.getWidth() /
		 * 6, coverWalkLeftTexture.getHeight()); regionsMovement_coverWalkLeft = new
		 * TextureRegion[6];
		 * 
		 * for (int i = 0; i < 6; i++) { regionsMovement_coverWalkLeft[i] =
		 * coverWalkLeftFrames[0][i]; coverWalkLeftAnimation = new Animation<>(1 / 10f,
		 * coverWalkLeftFrames[0]); time = 0f; }
		 * 
		 * // SALTAR
		 * 
		 * TextureRegion[][] jumpFrames = TextureRegion.split(jumpTexture,
		 * jumpTexture.getWidth() / 4, jumpTexture.getHeight()); regionsMovement_jump =
		 * new TextureRegion[4];
		 * 
		 * for (int i = 0; i < 4; i++) { regionsMovement_jump[i] = jumpFrames[0][i];
		 * jumpAnimation = new Animation<>(1 / 10f, jumpFrames[0]); time = 0f; }
		 * 
		 * // CAER
		 * 
		 * TextureRegion[][] fallFrames = TextureRegion.split(fallTexture,
		 * fallTexture.getWidth() / 4, fallTexture.getHeight()); regionsMovement_fall =
		 * new TextureRegion[4];
		 * 
		 * for (int i = 0; i < 4; i++) { regionsMovement_fall[i] = fallFrames[0][i];
		 * fallAnimation = new Animation<>(1 / 10f, fallFrames[0]); time = 0f; }
		 * 
		 * estadoActual = EstadosKnight.IDLE;
		 * 
		 * spr = new Sprite(idleAnimation.getKeyFrame(0, true)); spr.setPosition(x, y);
		 * spr.setSize(ancho, alto); }
		 * 
		 * public void dispose() { // Libera los recursos asociados al sprite, texturas,
		 * etc. // Aquí deberías realizar cualquier limpieza necesaria. spr = spr2; //
		 * Por ejemplo, para la textura idleTexture idleTexture.dispose();
		 * 
		 * }
		 * 
		 * public void render(SpriteBatch batch) { time += Gdx.graphics.getDeltaTime();
		 * currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);
		 * 
		 * spr.draw(batch); float x = spr.getX(); float ANCHO = spr.getWidth(); //
		 */
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
				
				if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					cambiarEstado(EstadosKnight.RUN_LEFT);
					posicion.x -= 6;

				}

			} else if (Gdx.input.isKeyPressed(Keys.D) && !bloqueando
					&& moverse /* && direccion != EstadosKnight.WALKING_RIGHT */) {
				cambiarEstado(EstadosKnight.WALKING_RIGHT);
				if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					cambiarEstado(EstadosKnight.RUN_RIGHT);
					posicion.x += 6;

				}
			} else if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !bloqueando) {

				inciarSalto();
				cambiarEstado(EstadosKnight.JUMP);
				

			} else if (Gdx.input.isKeyJustPressed(Keys.E)) {
				encenderHoguera();

			} else if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {

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
						posicion.x -= 3;
						
					} else if(Gdx.input.isKeyPressed(Keys.D)) {
						cambiarEstado(EstadosKnight.COVER_RIGHT);
						bloqueando = true;
						posicion.x += 3;
						
					}
					
				} else {
					// Si el clic derecho no está presionado, detiene la acción de bloqueo
					bloqueoActivo = false;
					// Cambia el estado del personaje a IDLE (o cualquier otro estado apropiado)
				}

			} else {
				estadoActual = EstadosKnight.IDLE;
				
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
			if (Gdx.input.isKeyPressed(Keys.A)) {
				System.out.println("moverse en red izquierda");
				UtilesRed.hc.enviarMensaje("moverse#izquierda#" + UtilesRed.hc.IdCliente);

			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				System.out.println("moverse en red derecha");
				UtilesRed.hc.enviarMensaje("moverse#derecha#" + UtilesRed.hc.IdCliente);

			} else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				UtilesRed.hc.enviarMensaje("moverse#arriba#" + UtilesRed.hc.IdCliente);
			}

		}

	}

	public void inciarSalto() {
		// salto
		estadoActual = EstadosKnight.JUMP;
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
				posicion.y = 285; // aca cuando termino de caer
				terminoSalto = true;
				moverse = true;
				jumping = false;
				cayendo = false;
				estadoActual = EstadosKnight.IDLE;
			}
		}

	}

	public void actualizarPosicionRed(float x, float y) {
		posicion.x = x;
		posicion.y = y;
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
			break;

		case WALKING_LEFT:
			// spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			posicion.x -= 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_LEFT);
				posicion.x -= 6;

			}
			break;

		case WALKING_RIGHT:
			// spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
			posicion.x += 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {

				// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_RIGHT);
				posicion.x += 6;

			}
			break;

		case JUMP:
//			moverse = false;
//			jumping = true;
//			terminoSalto = false;
//
//			if (posicion.y < ALTURA_SALTO) {// la altura deseada
//				posicion.y += JUMP_SPEED * delta;
//
//				if (posicion.y >= ALTURA_SALTO) {
//					estadoActual = EstadosKnight.FALL;
//				}
//
//			}

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
		  
		  break;
		  
		 case RUN_LEFT:
		
		 break;
		 
		case ATTACK:

			
			break;

		case COVER:
			
			bloqueando = true;

			
			break;
		case COVER_RIGHT:
			
			bloqueando = true;
			posicion.x += 3;
			
			break;
		case COVER_LEFT:
			// spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));			
			bloqueando = true;
			posicion.x -= 3;
			break;

		}

		// spr.setPosition(posicion.x, posicion.y);

		/*
		 * if (ataqueIniciado) { tiempoAtaque += delta; if (tiempoAtaque <
		 * attackAnimation.getAnimationDuration()) {
		 * 
		 * // Guarda las dimensiones originales float tempWidth = spr.getWidth(); float
		 * tempHeight = spr.getHeight();
		 * 
		 * // Establece la región de ataque
		 * spr.setRegion(attackAnimation.getKeyFrame(tiempoAtaque, false));
		 * 
		 * // Restaura las dimensiones originales spr.setSize(tempWidth, tempHeight);
		 * 
		 * } else {
		 * 
		 * ataqueIniciado = false; tiempoAtaque = 0f; cambiarEstado(EstadosKnight.IDLE);
		 * 
		 * } } else {
		 * 
		 * // Si no está atacando, actualiza la animación normal
		 * Animation<TextureRegion> currentAnimation = getAnimationForCurrentState();
		 * spr.setRegion(currentAnimation.getKeyFrame(time, true));
		 * 
		 * }
		 */

		areaJugador.setPosition(posicion.x, posicion.y);
	}

	public void cambiarEstado(EstadosKnight nuevoEstado) {
		estadoActual = nuevoEstado;
		// spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

		if (nuevoEstado == EstadosKnight.ATTACK) {
			iniciarAtaque();
		}

	}


	private void iniciarAtaque() {
		ataqueIniciado = true;
		tiempoAtaque = 0f;

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
		idleAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 0, 6, 11);
		walkingLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 2, 6, 11);
		walkingRightAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 1, 6, 11);
		runAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 3, 6, 11);
		runLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 4, 6, 11);
		attackAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 10, 6, 11);
		coverAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 7, 6, 11);
		coverWalkRightAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 8, 6, 11);
		coverWalkLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 9, 6, 11);
		jumpAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 5, 6, 11);
		fallAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 6, 6, 11);

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
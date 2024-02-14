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
import com.mygdx.game.utiles.Animador;
import com.mygdx.game.utiles.Render;

public class Knight3 {

	private Sprite spr;
	private Sprite spr2;
	private float alto, ancho;
	
	
	private Texture textura;
	private Animador animacionQuieto;
	private Animador animacionIzquierda;
	private Animador animacionDerecha;

	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> walkingLeftAnimation;
	private Animation<TextureRegion> walkingRightAnimation;
	private Animation<TextureRegion> runAnimation;
	private Animation<TextureRegion> runLeftAnimation;
	private Animation<TextureRegion> attackAnimation;
	private Animation<TextureRegion> coverAnimation;
	private Animation<TextureRegion> coverWalkRightAnimation;
	private Animation<TextureRegion> coverWalkLeftAnimation;
	private Animation<TextureRegion> jumpAnimation;
	private Animation<TextureRegion> fallAnimation;

	private TextureRegion[] regionsMovement_idle;
	private TextureRegion[] regionsMovement_walking_left;
	private TextureRegion[] regionsMovement_walking_right;
	private TextureRegion[] regionsMovement_run;
	private TextureRegion[] regionsMovement_runLeft;
	private TextureRegion[] regionsMovement_attack;
	private TextureRegion[] regionsMovement_cover;
	private TextureRegion[] regionsMovement_coverWalkRight;
	private TextureRegion[] regionsMovement_coverWalkLeft;
	private TextureRegion[] regionsMovement_jump;
	private TextureRegion[] regionsMovement_fall;
	private Texture idleTexture;
	private float time;
	private TextureRegion currentFrame;

	private boolean ataqueIniciado;
	private float tiempoAtaque;
	private EstadosKnight estadoActual;
	private EstadosKnight direccion;
	public int vida = 100;
	public boolean bloqueando = false;
	public boolean jumping = false;
	public boolean terminoSalto = true;
	private boolean spacePressed = false;
	private float GROUND_LEVEL = 145f;
	public final float GRAVITY = -800; // Ajusta según la gravedad deseada
	public final float JUMP_SPEED = 500; // Ajusta según la velocidad de salto deseada
	public final float RANGO_ATAQUE = 50; // Ajusta según el rango de ataque deseado
	private final float ALTURA_SALTO = 300f;
	public float ySpeed = 0;
	public boolean lastimable = false;
	boolean bloqueoActivo;
	boolean moverse = true;
	// colisiones
	public Rectangle areaJugador;
	private Vector2 posicion;
	boolean pasoPlataforma = false;

	public Knight3(float x, float y, float ancho, float alto) {
		posicion = new Vector2();
		posicion.x = x;
		posicion.y = y;
		this.alto = alto;
		this.ancho = ancho;

		posicion.y = 145;

		areaJugador = new Rectangle(this.posicion.x, this.posicion.y, this.ancho, this.alto / 2);

		// CARGA LAS TEXTURAS
		idleTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Combat Ready Idle.png"));
		Texture walkingRightTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Walk.png"));
		Texture walkingLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Walk2.png"));
		Texture runTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Run.png"));
		Texture runLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Run2.png"));
		Texture attackTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Attack 1.png"));
		Texture coverTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise.png"));
		Texture coverWalkRightTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise Walk.png"));
		Texture coverWalkLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise Walk2.png"));
		Texture jumpTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/jump.png"));
		Texture fallTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/fall.png"));

		// LOGICA DE LAS ANIMACIONES

		// IDLE

		TextureRegion[][] idleFrames = TextureRegion.split(idleTexture, idleTexture.getWidth() / 5,
				idleTexture.getHeight());
		regionsMovement_idle = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			regionsMovement_idle[i] = idleFrames[0][i];
			idleAnimation = new Animation<>(1 / 5f, idleFrames[0]);
			time = 0f;
		}

		// CAMINAR HACIA LA IZQUIERDA
		TextureRegion[][] walkingLeftFrames = TextureRegion.split(walkingLeftTexture, walkingLeftTexture.getWidth() / 6,
				walkingLeftTexture.getHeight());
		regionsMovement_walking_left = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_walking_left[i] = walkingLeftFrames[0][i];
			walkingLeftAnimation = new Animation<>(1 / 10f, walkingLeftFrames[0]);
			time = 0f;
		}

		// CAMINAR HACIA LA DERECHA

		TextureRegion[][] walkingRightFrames = TextureRegion.split(walkingRightTexture,
				walkingRightTexture.getWidth() / 6, walkingRightTexture.getHeight());
		regionsMovement_walking_right = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_walking_right[i] = walkingRightFrames[0][i];
			walkingRightAnimation = new Animation<>(1 / 10f, walkingRightFrames[0]);
			time = 0f;
		}

		// CORRER HACIA LA DERECHA

		TextureRegion[][] runFrames = TextureRegion.split(runTexture, runTexture.getWidth() / 6,
				runTexture.getHeight());
		regionsMovement_run = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_run[i] = runFrames[0][i];
			runAnimation = new Animation<>(1 / 10f, runFrames[0]);
			time = 0f;
		}

		// CORRER HACIA LA IZQUIERDA

		TextureRegion[][] runLeftFrames = TextureRegion.split(runLeftTexture, runLeftTexture.getWidth() / 6,
				runLeftTexture.getHeight());
		regionsMovement_runLeft = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_runLeft[i] = runLeftFrames[0][i];
			runLeftAnimation = new Animation<>(1 / 10f, runLeftFrames[0]);
			time = 0f;
		}

		// ATACAR

		TextureRegion[][] attackFrames = TextureRegion.split(attackTexture, attackTexture.getWidth() / 10,
				attackTexture.getHeight());
		regionsMovement_attack = new TextureRegion[10];

		for (int i = 0; i < 10; i++) {
			regionsMovement_attack[i] = attackFrames[0][i];
			attackAnimation = new Animation<>(1 / 20f, attackFrames[0]);
			time = 0f;
		}

		// CUBRIR

		TextureRegion[][] coverFrames = TextureRegion.split(coverTexture, coverTexture.getWidth() / 5,
				coverTexture.getHeight());
		regionsMovement_cover = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			regionsMovement_cover[i] = coverFrames[0][i];
			coverAnimation = new Animation<>(1 / 10f, coverFrames[0]);
			time = 0f;
		}

		// CUBRIR CAMINANDO A LA DERECHA

		TextureRegion[][] coverWalkRightFrames = TextureRegion.split(coverWalkRightTexture,
				coverWalkRightTexture.getWidth() / 6, coverWalkRightTexture.getHeight());
		regionsMovement_coverWalkRight = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_coverWalkRight[i] = coverWalkRightFrames[0][i];
			coverWalkRightAnimation = new Animation<>(1 / 10f, coverWalkRightFrames[0]);
			time = 0f;
		}

		// CUBRIR CAMINANDO A LA IZQUIERDA

		TextureRegion[][] coverWalkLeftFrames = TextureRegion.split(coverWalkLeftTexture,
				coverWalkLeftTexture.getWidth() / 6, coverWalkLeftTexture.getHeight());
		regionsMovement_coverWalkLeft = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_coverWalkLeft[i] = coverWalkLeftFrames[0][i];
			coverWalkLeftAnimation = new Animation<>(1 / 10f, coverWalkLeftFrames[0]);
			time = 0f;
		}

		// SALTAR

		TextureRegion[][] jumpFrames = TextureRegion.split(jumpTexture, jumpTexture.getWidth() / 4,
				jumpTexture.getHeight());
		regionsMovement_jump = new TextureRegion[4];

		for (int i = 0; i < 4; i++) {
			regionsMovement_jump[i] = jumpFrames[0][i];
			jumpAnimation = new Animation<>(1 / 10f, jumpFrames[0]);
			time = 0f;
		}

		// CAER

		TextureRegion[][] fallFrames = TextureRegion.split(fallTexture, fallTexture.getWidth() / 4,
				fallTexture.getHeight());
		regionsMovement_fall = new TextureRegion[4];

		for (int i = 0; i < 4; i++) {
			regionsMovement_fall[i] = fallFrames[0][i];
			fallAnimation = new Animation<>(1 / 10f, fallFrames[0]);
			time = 0f;
		}

		estadoActual = EstadosKnight.IDLE;

		spr = new Sprite(idleAnimation.getKeyFrame(0, true));
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
	}

	public void dispose() {
		// Libera los recursos asociados al sprite, texturas, etc.
		// Aquí deberías realizar cualquier limpieza necesaria.
		spr = spr2;
		// Por ejemplo, para la textura idleTexture
		idleTexture.dispose();

	}

	public void render(SpriteBatch batch) {
		time += Gdx.graphics.getDeltaTime();
		currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);

		spr.draw(batch);
		float x = spr.getX();
		float ANCHO = spr.getWidth();
		dibujarAreaInteraccion();

		moverPersonaje();
	}

	public void moverPersonaje() {
		// Maneja las entradas del teclado para cambiar el estado del personaje
		if (Gdx.input.isKeyPressed(Keys.A) && !bloqueando && moverse /* && direccion != EstadosKnight.WALKING_LEFT */) {
			cambiarEstado(EstadosKnight.WALKING_LEFT);

		} else if (Gdx.input.isKeyPressed(Keys.D) && !bloqueando
				&& moverse /* && direccion != EstadosKnight.WALKING_RIGHT */) {
			cambiarEstado(EstadosKnight.WALKING_RIGHT);

		} else if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !bloqueando) {

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

			} else {
				// Si el clic derecho no está presionado, detiene la acción de bloqueo
				bloqueoActivo = false;
				// Cambia el estado del personaje a IDLE (o cualquier otro estado apropiado)
				if (terminoSalto) {
					cambiarEstado(EstadosKnight.IDLE);
				}
			}
		} else {
			if (terminoSalto) {
				cambiarEstado(EstadosKnight.IDLE);

			}
		}
	}

	public void update() {

	}

	public void updateAnimation(float delta) {

		switch (estadoActual) {
		case IDLE:
			spr.setRegion(idleAnimation.getKeyFrame(time, true));
			bloqueando = false;
			terminoSalto = true;
			moverse = true;
//			posicion.y = 145;
			break;

		case WALKING_LEFT:
			spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			posicion.x -= 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_LEFT);
				posicion.x -= 6;

			}
			break;

		case WALKING_RIGHT:
			spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
			posicion.x += 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {

				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.RUN_RIGHT);
				posicion.x += 6;

			}
			break;

		case JUMP:
			moverse = false;
			jumping = true;
			terminoSalto = false;

			if (posicion.y < ALTURA_SALTO) {// la altura deseada
				posicion.y += JUMP_SPEED * delta;

				if (posicion.y >= ALTURA_SALTO) {
					estadoActual = EstadosKnight.FALL;
				}

			}

			break;

		case FALL:

			if (!pasoPlataforma) {

				if (posicion.y > GROUND_LEVEL) {
					posicion.y -= JUMP_SPEED * delta;

				}

				if (posicion.y <= GROUND_LEVEL) {
					jumping = false;
					terminoSalto = true;
					estadoActual = EstadosKnight.IDLE;
					moverse = true;
				} else {
					spr.setRegion(fallAnimation.getKeyFrame(time, true));
				}

			} else {
				posicion.y = 260;
				estadoActual = EstadosKnight.IDLE;
				terminoSalto = true;
			}
			break;

		/*
		 * case RUN_RIGHT: spr.setRegion(runAnimation.getKeyFrame(time, true));
		 * posicion.x += 6; break;
		 * 
		 * case RUN_LEFT: spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
		 * posicion.x -= 6; break;
		 */
		case ATTACK:

			spr.setRegion(attackAnimation.getKeyFrame(time, true));
			break;

		case COVER:
			spr.setRegion(coverAnimation.getKeyFrame(time, true));
			bloqueando = true;

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {

				spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.COVER_LEFT);
				bloqueando = true;
				posicion.x -= 3;

			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {

				spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.COVER_RIGHT);
				bloqueando = true;
				posicion.x += 3;
			}
			break;
		case COVER_RIGHT:
			spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));

			break;
		case COVER_LEFT:
			spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));

			break;

		}

		spr.setPosition(posicion.x, posicion.y);

		if (ataqueIniciado) {
			tiempoAtaque += delta;
			if (tiempoAtaque < attackAnimation.getAnimationDuration()) {

				// Guarda las dimensiones originales
				float tempWidth = spr.getWidth();
				float tempHeight = spr.getHeight();

				// Establece la región de ataque
				spr.setRegion(attackAnimation.getKeyFrame(tiempoAtaque, false));

				// Restaura las dimensiones originales
				spr.setSize(tempWidth, tempHeight);

			} else {

				ataqueIniciado = false;
				tiempoAtaque = 0f;
				cambiarEstado(EstadosKnight.IDLE);

			}
		} else {

			// Si no está atacando, actualiza la animación normal
			Animation<TextureRegion> currentAnimation = getAnimationForCurrentState();
			spr.setRegion(currentAnimation.getKeyFrame(time, true));

		}

		areaJugador.setPosition(posicion.x, posicion.y);
	}

	public void cambiarEstado(EstadosKnight nuevoEstado) {
		estadoActual = nuevoEstado;
		spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

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
			direccion = estadoActual;
			pasoPlataforma = true;

		} else {
			lastimable = false;

			pasoPlataforma = false;

		}

	}

	private Animation<TextureRegion> getAnimationForCurrentState() {
		switch (estadoActual) {
		case IDLE:
			return idleAnimation;
		case WALKING_LEFT:
			return walkingLeftAnimation;
		case WALKING_RIGHT:
			return walkingRightAnimation;
		case RUN_RIGHT:
			return runAnimation;
		case RUN_LEFT:
			return runLeftAnimation;
		case ATTACK:
			return attackAnimation;
		case COVER:
			return coverAnimation;
		case COVER_RIGHT:
			return coverWalkRightAnimation;
		case COVER_LEFT:
			return coverWalkLeftAnimation;
		case JUMP:
			return jumpAnimation;
		case FALL:
			return fallAnimation;
		default:
			return idleAnimation;
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
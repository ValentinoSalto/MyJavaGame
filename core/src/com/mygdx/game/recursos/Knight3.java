package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.recursos.Knight3.EstadoPersonaje;

public class Knight3 {

	public enum EstadoPersonaje {
		IDLE, WALKING_LEFT, WALKING_RIGHT, JUMP, FALL, RUN_RIGHT, RUN_LEFT, ATTACK, COVER, COVER_RIGHT, COVER_LEFT, HURT
	}

	private Sprite spr;
	private Sprite spr2;
	private float alto, ancho;
	public float x;
	public float y;
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
	private EstadoPersonaje estadoActual;
	public int vida = 100;
	public boolean bloqueando = false;
	public boolean jumping;
	private boolean spacePressed = false;
	private static final float GROUND_LEVEL = 145;
	public static final float GRAVITY = -800; // Ajusta según la gravedad deseada
	public static final float JUMP_SPEED = 500; // Ajusta según la velocidad de salto deseada

	public float ySpeed = 0;

	public Knight3(float x, float y, float ancho, float alto) {

		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;

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

		estadoActual = EstadoPersonaje.IDLE;

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
		float X = spr.getX();
		float ANCHO = spr.getWidth();

	}

	public void update() {

	}

	public void updateAnimation(float delta) {

		switch (estadoActual) {
		case IDLE:
			spr.setRegion(idleAnimation.getKeyFrame(time, true));
			bloqueando = false;
			y = 145;
			break;

		case WALKING_LEFT:
			spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			x -= 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(Knight3.EstadoPersonaje.RUN_LEFT);
				x -= 6;

			}
			break;

		case WALKING_RIGHT:
			spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
			x += 3;
			bloqueando = false;
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(Knight3.EstadoPersonaje.RUN_RIGHT);
				x += 6;

			}
			break;

		case JUMP:
			spacePressed = true;

			if (!jumping && spacePressed && y == GROUND_LEVEL) {
				ySpeed = JUMP_SPEED;
				jumping = true;
				spacePressed = false; // Reinicia la variable después de iniciar el salto
			}

			if (jumping) {
				// Aplica la velocidad vertical durante el salto
				y += ySpeed * delta;

				// Reduce la velocidad vertical debido a la gravedad
				ySpeed += GRAVITY * delta;

				if (y <= GROUND_LEVEL) {
					y = GROUND_LEVEL;
					ySpeed = 0;
					jumping = false;
					cambiarEstado(EstadoPersonaje.FALL); // Cambia al estado de caída
				} else {
					spr.setRegion(jumpAnimation.getKeyFrame(time, true));
				}
			}
			break;

		case FALL:
			// Simula la caída
			ySpeed += GRAVITY * delta;
			y += ySpeed * delta;

			if (y < GROUND_LEVEL) {
				y = GROUND_LEVEL;
				ySpeed = 0;
				jumping = false;
				cambiarEstado(EstadoPersonaje.IDLE); // Puedes cambiar el estado al aterrizar
			} else {
				spr.setRegion(fallAnimation.getKeyFrame(time, true));
			}

			break;

		/*
		 * case RUN_RIGHT: spr.setRegion(runAnimation.getKeyFrame(time, true)); x += 6;
		 * break;
		 * 
		 * case RUN_LEFT: spr.setRegion(runLeftAnimation.getKeyFrame(time, true)); x -=
		 * 6; break;
		 */
		case ATTACK:

			spr.setRegion(attackAnimation.getKeyFrame(time, true));
			break;

		case COVER:
			spr.setRegion(coverAnimation.getKeyFrame(time, true));
			bloqueando = true;

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {

				spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadoPersonaje.COVER_LEFT);
				bloqueando = true;
				x -= 3;
				
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {

				spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadoPersonaje.COVER_RIGHT);
				bloqueando = true;
				x += 3;
			}
			break;
		case COVER_RIGHT:
			spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));
			
			break;
		case COVER_LEFT:
			spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
			
			break;

		}

		spr.setPosition(x, y);

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
				cambiarEstado(EstadoPersonaje.IDLE);
			}
		} else {
			// Si no está atacando, actualiza la animación normal
			Animation<TextureRegion> currentAnimation = getAnimationForCurrentState();
			spr.setRegion(currentAnimation.getKeyFrame(time, true));
		}
	}

	public void cambiarEstado(EstadoPersonaje nuevoEstado) {
		estadoActual = nuevoEstado;
		spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

		if (nuevoEstado == EstadoPersonaje.ATTACK) {
			iniciarAtaque();
		}

	}

	private void iniciarAtaque() {
		ataqueIniciado = true;
		tiempoAtaque = 0f;

	}

	public void restarVida(int cantidad) {

		if (!bloqueando)
			for (int i = 1; i == 1; i++) {
				vida -= cantidad;
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

	public void setPosition(float newX, float newY) {
		x = newX;
		y = newY;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return alto;
	}

	public float getHeight() {
		return ancho;
	}

}
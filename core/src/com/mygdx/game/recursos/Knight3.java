package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight3 {

	public enum EstadoPersonaje {
		IDLE, WALKING_LEFT, WALKING_RIGHT, JUMP, FALL, RUN_RIGHT, RUN_LEFT, ATTACK, COVER, HURT
	}

	private Sprite spr;
	private Sprite spr2;
	private float alto, ancho;
	private float x, y;
	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> walkingLeftAnimation;
	private Animation<TextureRegion> walkingRightAnimation;
	private Animation<TextureRegion> runAnimation;
	private Animation<TextureRegion> runLeftAnimation;
	private Animation<TextureRegion> attackAnimation;
	private Animation<TextureRegion> coverAnimation;
	private Animation<TextureRegion> jumpAnimation;
	private Animation<TextureRegion> fallAnimation;

	private TextureRegion[] regionsMovement_idle;
	private TextureRegion[] regionsMovement_walking_left;
	private TextureRegion[] regionsMovement_walking_right;
	private TextureRegion[] regionsMovement_run;
	private TextureRegion[] regionsMovement_runLeft;
	private TextureRegion[] regionsMovement_attack;
	private TextureRegion[] regionsMovement_cover;
	private TextureRegion[] regionsMovement_jump;
	private TextureRegion[] regionsMovement_fall;
	private Texture idleTexture;
	private float time;
	private TextureRegion currentFrame;

	private boolean ataqueIniciado;
	private float tiempoAtaque;
	private EstadoPersonaje estadoActual;
	public int vida =100;
	
	private static final float GRAVITY = -1000f;
	private static final float JUMP_SPEED = 400f;

	private float velocityY;
	public boolean jumping;
	
	public Knight3(float x, float y, float ancho, float alto) {
		
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		
		//CARGA LAS TEXTURAS
		
		idleTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Combat Ready Idle.png"));
		Texture walkingRightTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Walk.png"));
		Texture walkingLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Walk2.png"));
		Texture runTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Run.png"));
		Texture runLeftTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Run2.png"));
		Texture attackTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Attack 1.png"));
		Texture coverTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/Shield Raise.png"));
		Texture jumpTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/jump.png"));
		Texture fallTexture = new Texture(Gdx.files.internal("Personajes/Hero/1/fall.png"));
		
		
		//LOGICA DE LAS ANIMACIONES

		TextureRegion[][] idleFrames = TextureRegion.split(idleTexture, idleTexture.getWidth() / 5,
				idleTexture.getHeight());
		regionsMovement_idle = new TextureRegion[5];
		
		for (int i = 0; i < 5; i++) {
			regionsMovement_idle[i] = idleFrames[0][i];
			idleAnimation = new Animation<>(1 / 5f, idleFrames[0]);
			time = 0f;
		}


		TextureRegion[][] walkingLeftFrames = TextureRegion.split(walkingLeftTexture, walkingLeftTexture.getWidth() / 6,
				walkingLeftTexture.getHeight());
		regionsMovement_walking_left = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_walking_left[i] = walkingLeftFrames[0][i];
			walkingLeftAnimation = new Animation<>(1 / 10f, walkingLeftFrames[0]);
			time = 0f;
		}
		

		TextureRegion[][] walkingRightFrames = TextureRegion.split(walkingRightTexture,
				walkingRightTexture.getWidth() / 6, walkingRightTexture.getHeight());
		regionsMovement_walking_right = new TextureRegion[6];
		
		for (int i = 0; i < 6; i++) {
			regionsMovement_walking_right[i] = walkingRightFrames[0][i];
			walkingRightAnimation = new Animation<>(1 / 10f, walkingRightFrames[0]);
			time = 0f;
		}

		TextureRegion[][] runFrames = TextureRegion.split(runTexture, runTexture.getWidth() / 6,
				runTexture.getHeight());
		regionsMovement_run = new TextureRegion[6];

		for (int i = 0; i < 6; i++) {
			regionsMovement_run[i] = runFrames[0][i];
			runAnimation = new Animation<>(1 / 10f, runFrames[0]);
			time = 0f;
		}

		TextureRegion[][] runLeftFrames = TextureRegion.split(runLeftTexture, runLeftTexture.getWidth() / 6,
				runLeftTexture.getHeight());
		regionsMovement_runLeft = new TextureRegion[6];
		
		for (int i = 0; i < 6; i++) {
			regionsMovement_runLeft[i] = runLeftFrames[0][i];
			runLeftAnimation = new Animation<>(1 / 10f, runLeftFrames[0]);
			time = 0f;
		}


		TextureRegion[][] attackFrames = TextureRegion.split(attackTexture, attackTexture.getWidth() / 10,
				attackTexture.getHeight());
		regionsMovement_attack = new TextureRegion[10];
		
		for (int i = 0; i < 10; i++) {
			regionsMovement_attack[i] = attackFrames[0][i];
			attackAnimation = new Animation<>(1 / 20f, attackFrames[0]);
			time = 0f;
		}

		TextureRegion[][] coverFrames = TextureRegion.split(coverTexture, coverTexture.getWidth() / 5,
				coverTexture.getHeight());
		regionsMovement_cover = new TextureRegion[5];
		
		for (int i = 0; i < 5; i++) {
			regionsMovement_cover[i] = coverFrames[0][i];
			coverAnimation = new Animation<>(1 / 10f, coverFrames[0]);
			time = 0f;
		}

		TextureRegion[][] jumpFrames = TextureRegion.split(jumpTexture, jumpTexture.getWidth() / 5,
				jumpTexture.getHeight());
		regionsMovement_jump = new TextureRegion[5];
		
		for (int i = 0; i < 5; i++) {
			regionsMovement_jump[i] = jumpFrames[0][i];
			jumpAnimation = new Animation<>(1 / 10f, jumpFrames[0]);
			time = 0f;
		}

		TextureRegion[][] fallFrames = TextureRegion.split(fallTexture, fallTexture.getWidth() / 5,
				fallTexture.getHeight());
		regionsMovement_fall = new TextureRegion[5];
		
		for (int i = 0; i < 5; i++) {
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

	public void updateAnimation(float delta) {
		switch (estadoActual) {
		case IDLE:
			spr.setRegion(idleAnimation.getKeyFrame(time, true));
			break;

		case WALKING_LEFT:
			spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			x -= 3;
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(Knight3.EstadoPersonaje.RUN_LEFT);
				x -= 6;

			}
			break;

		case WALKING_RIGHT:
			spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
			x += 3;
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

				spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(Knight3.EstadoPersonaje.RUN_RIGHT);
				x += 6;

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
			break;
		case JUMP:
            velocityY += GRAVITY * delta;
            y += velocityY * delta;
            if (y <= 0) {
                y = 0;
                cambiarEstado(EstadoPersonaje.IDLE);
            }
            break;
        case FALL:
            velocityY += GRAVITY * delta;
            y += velocityY * delta;
            if (y <= 0) {
                y = 0;
                cambiarEstado(EstadoPersonaje.IDLE);
            }   
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
		if (nuevoEstado == EstadoPersonaje.JUMP) {
	        iniciarSalto();
	    }
	}
	
	private void iniciarSalto() {
	    if (!jumping) {
	        jumping = true;
	        velocityY = JUMP_SPEED;
	    }
	}

	public void caer() {
	    if (estadoActual != EstadoPersonaje.JUMP) {
	        cambiarEstado(EstadoPersonaje.FALL);
	    }
	}

	private void iniciarAtaque() {
		ataqueIniciado = true;
		tiempoAtaque = 0f;

	}

	public void restarVida(int cantidad) {
		
		for(int i=1; i==1;i++) {
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
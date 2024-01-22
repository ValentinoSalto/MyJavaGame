package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.recursos.Knight3.EstadoPersonaje;

public class Ghost {

	public enum EstadoPersonaje {
		IDLE, WALKING_LEFT, WALKING_RIGHT, JUMP, RUN, ATTACK, COVER
	}

	private Sprite spr;
	private float alto, ancho;
	private float x, y;
	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> walkingLeftAnimation;
	private Animation<TextureRegion> walkingRightAnimation;
	private Animation<TextureRegion> attackAnimation;
	private TextureRegion[] regionsMovement_idle;
	private TextureRegion[] regionsMovement_walking_left;
	private TextureRegion[] regionsMovement_walking_right;
	private TextureRegion[] regionsMovement_attack;
	private float time;
	private TextureRegion currentFrame;
	private Texture idleTexture;
	private boolean ataqueIniciado;
	private float tiempoAtaque;
	private boolean disposed;
	private float tiempoEntreAtaques = 1f; // 1 segundo de espera entre ataques
	public static float tiempoDesdeUltimoAtaque = 0f;
	public int vida = 20;
	public boolean detectado = false;
	// Define aquí las demás animaciones para los otros estados (JUMP, RUN, ATTACK,
	// COVER)
	// ...

	private EstadoPersonaje estadoActual;

	public Ghost(float x, float y, float ancho, float alto) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;

		// Carga las texturas para las animaciones
		idleTexture = new Texture(Gdx.files.internal("Personajes/Ghost-Files/PNG/ghost-idle.png"));
		Texture walkingLeftTexture = new Texture(Gdx.files.internal("Personajes/Ghost-Files/PNG/ghost-idle2.png"));
		Texture attackTexture = new Texture(Gdx.files.internal("Personajes/Ghost-Files/PNG/ghost-shriek.png"));

		// Divide las texturas en regiones para las animaciones

		// IDLE
		TextureRegion[][] idleFrames = TextureRegion.split(idleTexture, idleTexture.getWidth() / 7,
				idleTexture.getHeight());
		regionsMovement_idle = new TextureRegion[7];

		for (int i = 0; i < 7; i++) {
			regionsMovement_idle[i] = idleFrames[0][i];
			idleAnimation = new Animation<>(1 / 6f, idleFrames[0]);
			time = 0f;
		}

		// WALKIN LEFT
		TextureRegion[][] walkingLeftFrames = TextureRegion.split(walkingLeftTexture, walkingLeftTexture.getWidth() / 7,
				walkingLeftTexture.getHeight());
		regionsMovement_walking_left = new TextureRegion[7];

		for (int i = 0; i < 7; i++) {
			regionsMovement_walking_left[i] = walkingLeftFrames[0][i];
			walkingLeftAnimation = new Animation<>(1 / 10f, walkingLeftFrames[0]);
			time = 0f;
		}

		/*
		 * TextureRegion[][] attackFrames = TextureRegion.split(attackTexture,
		 * attackTexture.getWidth() / 4, attackTexture.getHeight());
		 * regionsMovement_attack = new TextureRegion[4];
		 * 
		 * for (int i = 0; i < 4; i++) { regionsMovement_attack[i] = attackFrames[0][i];
		 * attackAnimation = new Animation<>(1 / 20f, attackFrames[0]); time = 0f; }
		 */
		// Establece el estado inicial del personaje
		estadoActual = EstadoPersonaje.IDLE;

		// Inicializa la sprite con la animación idle
		spr = new Sprite(idleAnimation.getKeyFrame(0, true));
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
	}

	public void render(SpriteBatch batch) {

		time += Gdx.graphics.getDeltaTime();
		currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);
		// Dibuja el sprite correspondiente a la animación del estado actual
		spr.draw(batch);

	}

	public void updateAnimation(float delta) {
		// Actualiza la animación según el estado actual del personaje
		switch (estadoActual) {
		case IDLE:
			spr.setRegion(idleAnimation.getKeyFrame(time, true));
			break;
		case WALKING_LEFT:
			spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
			// Mueve al personaje hacia la izquierda

			break;
		case WALKING_RIGHT:
			spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
			// Mueve al personaje hacia la derecha

			break;
		case ATTACK:
			spr.setRegion(attackAnimation.getKeyFrame(time, true));
			break;

		// Agrega las animaciones para los otros estados (JUMP, RUN, ATTACK, COVER)
		// ...
		}
		// Actualiza la posición del sprite
		spr.setPosition(x, y);
	}

	public void setPosition(float newX, float newY) {
		x = newX;
		y = newY;
	}

	public void cambiarEstado(EstadoPersonaje nuevoEstado) {
		// Cambia el estado del personaje y actualiza la animación
		estadoActual = nuevoEstado;

		// Reinicia la animación para que comience desde el inicio al cambiar de estado
		spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

	}

	public void seguirKnight(Knight3 knight, float delta) {
		// Lógica para seguir al Knight
		float knightX = knight.getX();
		float knightY = knight.getY();

		// Calcula las diferencias en las coordenadas X e Y
		float deltaX = knightX - getX();
		float deltaY = knightY - getY();

		// Calcula el ángulo hacia el Knight
		float angleToKnight = MathUtils.atan2(deltaY, deltaX);

		// Calcula la nueva posición del Ghost
		float speed = 100; // Ajusta la velocidad según sea necesario
		float newX = getX() + speed * MathUtils.cos(angleToKnight) * delta;
		float newY = getY() + speed * MathUtils.sin(angleToKnight) * delta;

		if (knight.x >= 150) {
			detectado = true;
		}

		// Actualiza la posición del Ghost
		if (detectado) {
			setPosition(newX, newY);

		}

		if (newX < knight.getX()) {
			cambiarEstado(EstadoPersonaje.WALKING_LEFT);
		} else {
			cambiarEstado(EstadoPersonaje.IDLE);
		}
	}

	public void atacarKnight(Knight3 knight) {
		float distanciaAtaque = 10; // Ajusta la distancia de ataque según sea necesario

		float distancia = Math.abs(knight.getX() - x);

		if (distancia < distanciaAtaque) {
			// Verifica el tiempo desde el último ataque
			if (tiempoDesdeUltimoAtaque >= tiempoEntreAtaques) {
				// Resta vida al Knight
				knight.restarVida(0); // Ajusta la cantidad de daño según sea necesario

				// Reinicia el temporizador
				tiempoDesdeUltimoAtaque = 0f;
			}
		}

	}

	private Animation<TextureRegion> getAnimationForCurrentState() {
		// Devuelve la animación correspondiente al estado actual
		switch (estadoActual) {
		case IDLE:
			return idleAnimation;
		case WALKING_LEFT:
			return walkingLeftAnimation;
		case WALKING_RIGHT:
			return walkingRightAnimation;
		// Devuelve las animaciones para los otros estados (JUMP, RUN, ATTACK, COVER)
		// ...
		default:
			return idleAnimation;
		}
	}

	// Método para obtener la posición X del personaje
	public float getX() {
		return x;
	}

	// Método para obtener la posición Y del personaje
	public float getY() {
		return y;
	}

	// Método para obtener el ancho del personaje
	public float getWidth() {
		return alto;
	}

	// Método para obtener la altura del personaje
	public float getHeight() {
		return ancho;
	}

	public void dispose() {
		// Libera los recursos asociados al sprite, texturas, etc.
		// Aquí deberías realizar cualquier limpieza necesaria.
		detectado=false;
		// Dispose de la textura, ajusta según tus necesidades
		if (idleTexture != null) {
			idleTexture.dispose();
			
		}

		// Establece el estado "disposed"
		disposed = true;
	}

	// Método para verificar si el Ghost ha sido eliminado
	public boolean isDisposed() {
		return disposed;
	}
}

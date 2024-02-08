package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.pantallas.ScreenGame;

public class Hoguera {

	public enum EstadoPersonaje {
		OFF, ON
	}

	private Sprite spr;
	private float alto, ancho;
	public static float x;
	private float y;
	
	private Animation<TextureRegion> offAnimation;
	private Animation<TextureRegion> onAnimation;
	
	private TextureRegion[] regionsMovement_off;
	private TextureRegion[] regionsMovement_on;
	
	public boolean disposed;
	public static boolean encendida = false;
	public static int distancia = 10;
	public static int numHoguera = 1;
	private float time;
	private TextureRegion currentFrame;
	private Texture offTexture;
	private EstadoPersonaje estadoActual;

	public Hoguera(float x, float y, float ancho, float alto) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;

		// Carga las texturas para las animaciones
		offTexture = new Texture(Gdx.files.internal("Personajes/Torch-Sprites/bigtorchOFF.png"));
		Texture onTexture = new Texture(Gdx.files.internal("Personajes/Torch-Sprites/bigtorchlit2.png"));
		
		// Divide las texturas en regiones para las animaciones
		
		//OFF
		TextureRegion[][] offFrames = TextureRegion.split(offTexture, offTexture.getWidth() / 1,
				offTexture.getHeight());
		regionsMovement_off = new TextureRegion[1];

		for (int i = 0; i < 1; i++) {
			regionsMovement_off[i] = offFrames[0][i];
			offAnimation = new Animation<>(1 / 10f, offFrames[0]);
			time = 0f;
		}
		
		// ON
		TextureRegion[][] onFrames = TextureRegion.split(onTexture, onTexture.getWidth() / 1,
				onTexture.getHeight());
		regionsMovement_on = new TextureRegion[1];

		for (int i = 0; i < 1; i++) {
			regionsMovement_on[i] = onFrames[0][i];
			onAnimation = new Animation<>(1 / 10f, onFrames[0]);
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
		estadoActual = EstadoPersonaje.OFF;

		// Inicializa la sprite con la animación off
		spr = new Sprite(offAnimation.getKeyFrame(0, true));
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
		
		if(encendida) {
			numHoguera = ScreenGame.numeroEscenario;
			System.out.println("El respawn esta en la hoguera num: "+ numHoguera);
		}
		
	}

	public void render(SpriteBatch batch) {

		time += Gdx.graphics.getDeltaTime();
		currentFrame = (TextureRegion) offAnimation.getKeyFrame(time, true);
		// Dibuja el sprite correspondiente a la animación del estado actual
		spr.draw(batch);
		
		if(encendida) {
			cambiarEstado(Hoguera.EstadoPersonaje.ON);
		} else {
			cambiarEstado(Hoguera.EstadoPersonaje.OFF);
		}
		
		
	}

	public void updateAnimation(float delta) {
		// Actualiza la animación según el estado actual del personaje
		switch (estadoActual) {
		case OFF:
			spr.setRegion(offAnimation.getKeyFrame(time, true));
			break;
		case ON:
			spr.setRegion(onAnimation.getKeyFrame(time, true));
			// Mueve al personaje hacia la izquierda
			
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


	private Animation<TextureRegion> getAnimationForCurrentState() {
		// Devuelve la animación correspondiente al estado actual
		switch (estadoActual) {
		case OFF:
			return offAnimation;
		case ON:
			return onAnimation;
		
		default:
			return offAnimation;
		}
	}

	// Método para obtener la posición X del personaje
	public static float getX() {
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

		// Dispose de la textura, ajusta según tus necesidades
		if (offTexture != null) {
			offTexture.dispose();

		}

		// Establece el estado "disposed"
		disposed = true;
	}

	// Método para verificar si el Ghost ha sido eliminado
	public boolean isDisposed() {
		return disposed;
	}
}

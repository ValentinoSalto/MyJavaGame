package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utiles.Animador;

public class Npc {
	private float alto, ancho;
	public Vector2 posicion;
	private EstadosNpc estadoActual;
	private boolean disposed;
	private Texture textura;
	private Animador idleAnimation;
	private Animador workAnimation;
	private Sprite spr;
	private float time;
	public final int distancia = 20 ;

	public Npc(String textura, int vida, float x, float y) {

		posicion = new Vector2();
		this.textura = new Texture(textura);
		this.posicion.y = y;
		this.posicion.x = x;
		this.ancho = ancho;
		this.alto = alto;
		estadoActual = EstadosNpc.IDLE;
		spr = new Sprite(this.textura);
		crearAnimaciones(textura);
	}

	public void render(SpriteBatch batch) {

		time += Gdx.graphics.getDeltaTime();
		// currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);
		// Dibuja el sprite correspondiente a la animación del estado actual
		// spr.draw(batch);

		// dibujarAreaInteraccion();

		switch (estadoActual) {

		case IDLE:
			idleAnimation.render();
			break;

		case WORK:
			workAnimation.render();
			break;

		}
	}

	public void cambiarEstado(EstadosNpc nuevoEstado) {
		// Cambia el estado del personaje y actualiza la animación
		estadoActual = nuevoEstado;

		// Reinicia la animación para que comience desde el inicio al cambiar de estado
		// spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

	}

	// Método para obtener la posición X del personaje
	public float getX() {
		return posicion.x;
	}

	// Método para obtener la posición Y del personaje
	public float getY() {
		return posicion.y;
	}

	// Método para obtener el ancho del personaje
	public float getWidth() {
		return alto;
	}

	// Método para obtener la altura del personaje
	public float getHeight() {
		return ancho;
	}

	public void setPosition(float newX, float newY) {
		posicion.x = newX;
		posicion.y = newY;
	}

	private void crearAnimaciones(String textura) {

		// Animaciones
		idleAnimation = new Animador(textura, posicion, 2, 7, 6);
		workAnimation = new Animador(textura, posicion, 4, 7, 6);

	}

	public void dispose() {
		// Libera los recursos asociados al sprite, texturas, etc.
		// Aquí deberías realizar cualquier limpieza necesaria.

		// Dispose de la textura, ajusta según tus necesidades
		/*
		 * if (idleTexture != null) { idleTexture.dispose();
		 * 
		 * }
		 */
		// Establece el estado "disposed"
		disposed = true;
	}

	// Método para verificar si el Ghost ha sido eliminado
	public boolean isDisposed() {
		return disposed;
	}
}

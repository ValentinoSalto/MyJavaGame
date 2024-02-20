package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utiles.Animador;
import com.mygdx.game.utiles.Render;

public class Enemigo {
	private Sprite spr;
	private float alto, ancho;
	private Vector2 posicion;
	private float tiempoEntreAtaques = 1f; // 1 segundo de espera entre ataques
	public int vida = 20;
	public boolean detectado = false;
	public static float tiempoDesdeUltimoAtaque = 0f;
	private float time;
	private EstadosEnemigos estadoActual;
	private boolean disposed;
	private Texture textura;
	private Animador animacionIzquierda;
	private Animador animacionDerecha;

	// colisiones
	public Rectangle areaJugador;

	public Enemigo(String textura, int vida, float x, float y) {
		posicion = new Vector2();
		this.textura = new Texture(textura);
		this.vida = vida;
		this.posicion.y = y;
		this.posicion.x = x;
		this.ancho = ancho;
		this.alto = alto;
		estadoActual = EstadosEnemigos.IDLE;
		spr = new Sprite(this.textura);
		crearAnimaciones(textura);

	}

	public void render(SpriteBatch batch) {

		time += Gdx.graphics.getDeltaTime();
		// currentFrame = (TextureRegion) idleAnimation.getKeyFrame(time, true);
		// Dibuja el sprite correspondiente a la animación del estado actual
		// spr.draw(batch);
		

		//dibujarAreaInteraccion();

		switch (estadoActual) {

		case IDLE:
			animacionIzquierda.render();
			break;

		case WALKING_LEFT:
			animacionIzquierda.render();
			break;
		case WALKING_RIGHT:
			animacionDerecha.render();
			break;
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

		if (knight.getPosicion().x >= 150) {
			detectado = true;
		}

		// Actualiza la posición del Ghost
		if (detectado) {
			setPosition(newX, newY);

		}

		if (newX < knight.getX()) {
			cambiarEstado(EstadosEnemigos.WALKING_RIGHT);
		} else {
			cambiarEstado(EstadosEnemigos.IDLE);
		}
	}

	public void atacarKnight(Knight3 knight) {
		float distanciaAtaque = 10; // Ajusta la distancia de ataque según sea necesario

		float distancia = Math.abs(knight.getX() - posicion.x);

		if (distancia < distanciaAtaque) {

			// Verifica el tiempo desde el último ataque
			if (tiempoDesdeUltimoAtaque >= tiempoEntreAtaques) {
				System.out.println("entro 1");
				if (tiempoDesdeUltimoAtaque >= tiempoEntreAtaques) {
					// Resta vida al Knight
					knight.restarVida(0);
					System.out.println("restando vida");
				}
				// Reinicia el temporizador
				tiempoDesdeUltimoAtaque = 0f;
			}
		}

	}

	public void restarVida(int cantidad) {

		vida -= cantidad;

	}

	public void cambiarEstado(EstadosEnemigos nuevoEstado) {
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
		
		//Animaciones ghost
		animacionIzquierda = new Animador(textura, posicion, 0,1,6);
		animacionDerecha = new Animador(textura, posicion, 1,1,6);

		animacionIzquierda.create();
		animacionDerecha.create();
		
		//Animacion Boss
		

	}

	public void dispose() {
		// Libera los recursos asociados al sprite, texturas, etc.
		// Aquí deberías realizar cualquier limpieza necesaria.
		detectado = false;
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

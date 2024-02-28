package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.utiles.Render;
import com.mygdx.game.recursos.EstadosEnemigos;

public class Ghost extends Enemigo {

	

	

	// Define aquí las demás animaciones para los otros estados (JUMP, RUN, ATTACK,
	// COVER)
	// ...

	
	public Ghost(float x, float y) {
		
		super(Texturas.ghostSpriteSheet,20,x,y,7,3);
		
		/*// Carga las texturas para las animaciones
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
		 
		// Establece el estado inicial del personaje
		estadoActual = EstadosGhost.IDLE;
		
		// Inicializa la sprite con la animación idle
		spr = new Sprite(idleAnimation.getKeyFrame(0, true));
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
		
		*/
	}
	


	
	

	

	

	
}
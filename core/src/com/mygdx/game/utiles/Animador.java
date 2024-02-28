package com.mygdx.game.utiles;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animador implements ApplicationListener {

	//https://libgdx.com/wiki/graphics/2d/2d-animation
	// Constant rows and columns of the sprite sheet
	private int FRAME_COLS , FRAME_ROWS ;
	private int filaDelSpriteSheet;

	// Objects used
	private Animation<TextureRegion> animacion; // Must declare frame type (TextureRegion)
	private Texture spriteSheet;
	private String rutaSpriteSheet;
	
	private Vector2 posicion;
	
	// A variable for tracking elapsed time for the animation
	float stateTime;
	public float escalaX = 24*4, escalaY = 24*4;
	
	
	
	public Animador(String rutaSpriteSheet, Vector2 posicion, int filaDelSpriteSheet, int columna, int fila) {
		this.rutaSpriteSheet = rutaSpriteSheet;
		this.filaDelSpriteSheet = filaDelSpriteSheet;
		this.posicion = posicion;
		FRAME_COLS = columna;
		FRAME_ROWS = fila;
		
		
		create();
	}

	@Override
	public void create() {
		
		// Load the sprite sheet as a Texture
		spriteSheet = new Texture(rutaSpriteSheet);
		int frameWidth = spriteSheet.getWidth() / FRAME_COLS;
		int frameHeight = spriteSheet.getHeight() / FRAME_ROWS;

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		 TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] frames = new TextureRegion[FRAME_COLS];

			for(int j = 0; j < FRAME_COLS; j++) {
				frames[j] = tmp[filaDelSpriteSheet][j];//Con filaDelSpriteSheet puedo elegir por constructor que fila es la que se va a utilizar
			}
		

		// Initialize the Animation with the frame interval and array of frames
		animacion = new Animation<TextureRegion>(.1f, frames);


		// time to 0
		stateTime = 0f;
	}

	@Override
	public void render() {
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // El problema que tenia de las animaciones era que en render limpiaba toda la pantalla
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = animacion.getKeyFrame(stateTime, true);
		
		Render.batch.draw(currentFrame, posicion.x-24, posicion.y-24,escalaX,escalaY);
		
		
	}
	
	public void setearEscala(float escalaX, float escalaY) {
		this.escalaX = escalaX;
		this.escalaY = escalaY;
		
	}
	
	
	public void reset() {
		stateTime = 0;
	}

	@Override
	public void dispose() {
		spriteSheet.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}

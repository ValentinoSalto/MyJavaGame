package com.mygdx.game.recursos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight {
	
	
	public Sprite spr;
	public float x, y;
	public float height,width;
	private Animation animation;
	private float time;
	private TextureRegion [] regionsMovement;
	private TextureRegion currentFrame;
	private Texture idle;
	boolean movement;
	
	
	public Knight(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		movement = false;
		//spr = new Sprite( new Texture(Gdx.files.internal("Personajes/Knight_1/idle.png")),71,86);
		idle = new Texture(Gdx.files.internal("Personajes/Knight_1/Idle.png"));
		TextureRegion[][] temp = TextureRegion.split(idle, idle.getWidth() / 4, idle.getHeight());
		regionsMovement = new TextureRegion[4];

		for (int i = 0; i < 4; i++)
		regionsMovement[i] = temp[0][i];
		animation = new Animation(1 / 6f, regionsMovement);
		time = 0f;
	
		
			
			
			

			
		
		
	}
	
	public void walk(final SpriteBatch batch) {
		
		
		

	}

	

	public void render(final SpriteBatch batch) {
		/*batch.draw(idle,x,y,height,width);*/
		
		//Tiempo q paso desde el ultimo render
		time += Gdx.graphics.getDeltaTime();
		currentFrame = (TextureRegion) animation.getKeyFrame(time,true);
		batch.draw(currentFrame,x,y,height,width);
		//Se mueve para la derecha
	/*	if((Gdx.input.isKeyPressed(Input.Keys.D))) {
			x+=3;
		}
		//Se mueve para la izquierda
		if((Gdx.input.isKeyPressed(Input.Keys.A))) {
			
			idle = new Texture(Gdx.files.internal("Personajes/Knight_1/Idleinvertido.png"));	
			time += Gdx.graphics.getDeltaTime();
			currentFrame = (TextureRegion) animation.getKeyFrame(time,true);
			batch.draw(currentFrame,x,y,height,width);
			x-=3;
		}
	*/
	}
}
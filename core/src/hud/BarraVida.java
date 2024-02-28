package hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.utiles.Render;

public class BarraVida {
	private Sprite spr;
	private float alto, ancho;
	public float x;
	private float y;
	String textura;
	Texture barraVida;
	

	public BarraVida(String textura, float x, float y, float ancho, float alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.textura = textura;

		barraVida = new Texture(textura);
		spr = new Sprite(barraVida);
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
		
	
		
	}
	
	public void render() {
		
		spr.draw(Render.batch);

	}

}

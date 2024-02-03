package com.mygdx.game.pantallas;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.recursos.Imagen;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class ScreenDeath implements Screen {
	
	Imagen fondo;
	SpriteBatch b;
	boolean fadeInTerminado = false, termina = false;
	float a = 0;
	float contTiempo = 0, tiempoEspera = 5;
	float contTiempoTermina = 0, tiempoTermina = 5;
	
	@Override
	public void show() {
		
        
		System.out.println("show");
		fondo = new Imagen(Recursos.PANTALLAMUERTE);
		b = Render.batch;
		fondo.setTransparencia(0);
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0,0,0);
		b.begin();
		fondo.dibujar();
		b.end();
		procesarFade();
	}

	private void procesarFade() {
		
		if (!fadeInTerminado) {  //Mientras que faseInTerminado sea falso se le suma la trasparencia hasta q sea la misma sea 1 y termina el fade in.
			a += 0.01f;

			if (a > 1) {
				a = 1;
				fadeInTerminado = true;
				System.out.println("Fade in terminado");
			}
		}else {
			contTiempo+=0.05f;			 //Se le empieza a sumar al contador del timepo para arrancar el fadee out . Y cuando alcanza el valor 5 comienza a bajar la trasparencia para el fade out.
			if(contTiempo>tiempoEspera) {
				a -= 0.01f;
				if (a < 0) {
					a = 0;
					termina = true;
					
				}
			}
		}
		
		
		fondo.setTransparencia(a);	
		
		if(termina) {
			contTiempoTermina+=0.04f;
			if(contTiempoTermina>tiempoTermina) {
				System.out.println("Cambio de pantalla");
				Render.app.setScreen(new ScreenGame());
			}
		}
		 
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}
	

}

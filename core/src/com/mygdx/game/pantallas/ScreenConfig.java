package com.mygdx.game.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.recursos.Imagen;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class ScreenConfig implements Screen {
	
	Imagen fondo;
	SpriteBatch b;
	private Skin skin;
	private Stage stage;
	private Music select;
	
	
	@Override
	public void show() {
		
		// Carga la música de fondo desde el archivo "menu_music.mp3"
        select = Gdx.audio.newMusic(Gdx.files.internal("Sounds/FX/click.mp3"));
		
		// Crea e inicializa el Stage
	    stage = new Stage();
		
		fondo = new Imagen(Recursos.FONDOCONFIG);		
		fondo.setSize(Config.ANCHO, Config.ALTO);
		b = Render.batch;
		
		Gdx.input.setInputProcessor(stage);
		
		// Crea un Pixmap blanco para el drawable "white_pixel"
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pixmap));
		
		// Crea un nuevo Skin para los botones
        skin = new Skin();
        // Registra el drawable "black_pixel" en el Skin
        skin.add("white_pixel", textureRegion);
		// Crea una instancia de BitmapFont para la fuente del TextButton
        final BitmapFont font = new BitmapFont();
        
        // Configura las propiedades de la fuente según tus preferencias
        font.setColor(Color.WHITE); // Establece el color de la fuente
        font.getData().setScale(1.5f); // Escala el tamaño de la fuente
        
        // Crea el estilo para el TextButton utilizando la fuente y el drawable
        final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font; // Asigna la fuente al estilo del botón
        // buttonStyle.up = skin.getDrawable("white_pixel"); // Establece el drawable para el estado normal del botón
        skin.add("default", buttonStyle); // Agrega el estilo al skin
        
        // Crea el estilo para el TextButton cuando el cursor está sobre él (Hand y fondo blanco)
        final TextButton.TextButtonStyle buttonStyleHover = new TextButton.TextButtonStyle();
        buttonStyleHover.font = font; // Asigna la fuente al estilo del botón
        //buttonStyleHover.fontColor = Color.YELLOW; // Establece el color de la fuente cuando el cursor está sobre el botón
        //buttonStyleHover.over = skin.getDrawable("white_pixel"); // Establece un drawable blanco para el fondo del botón cuando el cursor está sobre él
        
		// Crea el boton y agrega acciones a los ClickListeners
		final TextButton backButton = new TextButton("BACK", skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				//Llamo al sonido
				select.play();
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand); // Cambia el cursor a la mano (Hand) cuando el mouse entra en el botón																	
				backButton.setStyle(buttonStyleHover); // Cambia el estilo del botón al estilo con fondo blanco cuando el cursor está sobre él															
				backButton.setText("< BACK >");
				System.out.println("BACK");
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				
				select.dispose();
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); // Restaura el cursor predeterminado (flecha)
																			// cuando el mouse sale del botón
				backButton.setStyle(buttonStyle); // Restaura el estilo normal del botón cuando el cursor sale de él
				backButton.setText("BACK");
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Realiza las acciones del botón cuando es clickeado
				Render.app.setScreen(new ScreenMenu());
			}

		});
		
		backButton.setPosition(Config.BOTONX, Config.BOTONY);
		
		stage.addActor(backButton);
		
	}

	@Override
	public void render(float delta) {
		
		b.begin();
			fondo.dibujar();
		b.end();
		
		// Actualiza la lógica y el dibujado de los elementos de la escena
		stage.act(delta);
		
		stage.draw();

		
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

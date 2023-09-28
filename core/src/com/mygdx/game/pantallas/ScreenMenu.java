package com.mygdx.game.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.recursos.Imagen;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class ScreenMenu implements Screen{
	
	Imagen background;
	SpriteBatch b;
	BitmapFont fuente;
	private Skin skin;
	private Stage stage;
	private Music select;
	private Music backgroundMusic;
	private OrthographicCamera cam;
	
	
	public void show() { // Aquí puedes inicializar elementos del menú, como botones o texturas.
		// Carga la música de fondo desde el archivo "menu_music.mp3"
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Music/Menumusic.mp3"));
        // Configura la música para que se repita en bucle
        backgroundMusic.setLooping(true);
        // Reproduce la música de fondo
        backgroundMusic.play();
		
        // Carga la música de fondo desde el archivo "menu_music.mp3"
        select = Gdx.audio.newMusic(Gdx.files.internal("Sounds/FX/click.mp3"));
        
		// Crea e inicializa el Stage
	    stage = new Stage();  
	    
	    /*// Configura el procesador de entrada para el Stage
	    Gdx.input.setInputProcessor(new Entradas());
	    */
	    // Carga el fondo del menú
		background = new Imagen(Recursos.FONDOMENU);		
		background.setSize(Config.ANCHO, Config.ALTO);

		// creo la camara
		cam = new OrthographicCamera(Config.ANCHO, Config.ALTO);

		//Esto es para ahorrarse de poner Render.batch
		b = Render.batch;
		
		
		
		// Configura el procesador de entrada para el Stage
		Gdx.input.setInputProcessor(stage);
		 			
		// Crea un nuevo Skin para los botones
        skin = new Skin();
        
        // Crea un Pixmap blanco para el drawable "white_pixel"
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pixmap));

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
        
        
        // Crea los botones y agrega acciones a los ClickListeners
        final TextButton playButton = new TextButton("START", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	select.play();
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand); // Cambia el cursor a la mano (Hand) cuando el mouse entra en el botón
                playButton.setStyle(buttonStyleHover); // Cambia el estilo del botón al estilo con fondo blanco cuando el cursor está sobre él
                System.out.println("START");
                playButton.setText("< START >");               
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow); // Restaura el cursor predeterminado (flecha) cuando el mouse sale del botón
                playButton.setStyle(buttonStyle); // Restaura el estilo normal del botón cuando el cursor sale de él
                playButton.setText("START");
                select.dispose();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Realiza las acciones del botón cuando es clickeado
            	Render.app.setScreen(new ScreenGame());
            	backgroundMusic.dispose();
            	stage.dispose();
            	
                
            }
        });
        
        final TextButton configButton = new TextButton("CONFIG", skin);
        

        configButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	select.play();
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                configButton.setStyle(buttonStyleHover);
                System.out.println("CONFIG");
                configButton.setText("< CONFIG >");  
                
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                configButton.setStyle(buttonStyle);
                configButton.setText("CONFIG");  
                select.dispose();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Realiza las acciones del botón cuando es clickeado
            	 Render.app.setScreen(new ScreenConfig());
            	 
            }
        });
        
        final TextButton exitButton = new TextButton("QUIT", skin);
       

        exitButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	select.play();
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                exitButton.setStyle(buttonStyleHover);
                System.out.println("QUIT");
                exitButton.setText("< QUIT >");  
               
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                exitButton.setStyle(buttonStyle);
                exitButton.setText("QUIT");  
                select.dispose();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Realiza las acciones del botón cuando es clickeado
                Gdx.app.exit();
            }
        });
         
        // Crea una tabla para organizar los botones
		Table table = new Table();	
		table.setFillParent(true);
		table.center();	
		

		// Agrega los botones a la tabla
        table.add(playButton).uniform().padBottom(40).row();
        table.add(configButton).uniform().padBottom(40).row();
        table.add(exitButton).uniform();
      /* table.add(startButtonLabel).uniform().padBottom(40).row();
        table.add(configButtonLabel).uniform().padBottom(40).row();
        table.add(exitButtonLabel).uniform();
       */ 
        
		// Agrega los botones al Stage para que se puedan procesar y dibujar
		stage.addActor(table);
		
		
		
	    
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
		
	}
	
	@Override
	public void render(float delta) {
		
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);
		
		// Aca dibujas los elementos del menú.

		
		b.begin();
			background.dibujar();
		b.end();
		
		// Actualiza la lógica y el dibujado de los elementos de la escena
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// El resize de la cam con la ventana
		cam.setToOrtho(false, width, height);
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
		
		backgroundMusic.dispose();
		
	}

}

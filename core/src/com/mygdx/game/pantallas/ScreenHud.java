package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenHud extends ScreenAdapter {

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    

    public ScreenHud() {
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(800, 600, hudCamera); // Ajusta el tamaño según tus necesidades
        hudViewport.apply();

        batch = new SpriteBatch();
        font = new BitmapFont(); // Puedes personalizar la fuente según tus preferencias
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla del HUD
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Configura la proyección de la cámara del HUD
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        // Dibuja elementos del HUD aquí (texto, imágenes, barras, etc.)
        font.draw(batch, "Puntuación: 100", 20, hudViewport.getWorldHeight() - 20);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {
    private Texture texturaFondo;

    public Fondo(String rutaImagen) {
        texturaFondo = new Texture(Gdx.files.internal(rutaImagen));
    }

    public void render(SpriteBatch batch) {
        batch.draw(texturaFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose() {
        texturaFondo.dispose();
    }
}
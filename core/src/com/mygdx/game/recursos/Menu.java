package com.mygdx.game.recursos;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;

public class Menu {

    private ShapeRenderer shapeRenderer;

    public Menu() {
        shapeRenderer = new ShapeRenderer();
    }

    public void mostrar() {
        // Configurar el ShapeRenderer para dibujar rectángulos rellenos
        shapeRenderer.begin(ShapeType.Filled);

        // Dibujar el fondo del menú (rectángulo)
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(100, 100, 200, 150); // Posición (100, 100) y tamaño (200x150)

        // Dibujar las opciones del menú (por ejemplo, dos rectángulos para representar las opciones)
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(120, 200, 160, 40); // Posición y tamaño de la primera opción
        shapeRenderer.rect(120, 150, 160, 40); // Posición y tamaño de la segunda opción

        shapeRenderer.end();
    }

    // Método ficticio para obtener la opción seleccionada
    public OpcionMenu obtenerSeleccion() {
        // Aquí deberías implementar la lógica para detectar la opción seleccionada por el jugador
        // Por simplicidad, siempre devolvemos la primera opción
        return OpcionMenu.SUBIR_VIDA;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

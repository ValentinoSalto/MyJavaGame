package com.mygdx.game.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.red.Cliente;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.EstiloFuente;
import com.mygdx.game.utiles.Render;

public class EsperaConexion implements Screen, Hud{

	private ScreenViewport screenViewPort;
	private Stage stage;
	private Table tabla, contenedor;
	private Label textoEsperando;
	private Label.LabelStyle estiloLabel;
	private Cliente cliente;

	
	public EsperaConexion() {
		crearFuente();
		crearActores();
		poblarStage();
		cliente = new Cliente();
	}



	@Override
	public void crearFuente() {
		estiloLabel = EstiloFuente.generarFuente(32, "#ffffff");
		
	}



	@Override
	public void crearActores() {
		screenViewPort = new ScreenViewport();
		stage = new Stage(screenViewPort);
		
		tabla = new Table();
		contenedor = new Table();
		textoEsperando = new Label("Esperando conexion", estiloLabel);
	}



	@Override
	public void poblarStage() {
		
		tabla.setFillParent(true);
		//stage.setDebugAll(true);
		
		
		contenedor.add(textoEsperando);
		tabla.add(contenedor);
		stage.addActor(tabla);
		
	}



	public void resize(int width, int height) {
		screenViewPort.update(width, height, true);	
		
	}



	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void render(float delta) {
		stage.draw();
		
		if(UtilesRed.hc.empezar) {
			Render.app.setScreen(new ScreenGame(true));
			
		}
	}



	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dispose() {
		
	
		
	}
	
	
	
	
}

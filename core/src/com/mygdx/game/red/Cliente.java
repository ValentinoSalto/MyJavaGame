package com.mygdx.game.red;

public class Cliente {


	private boolean empiezaChat=false;

	public Cliente() {
		UtilesRed.hc = new HiloCliente(UtilesRed.game);
		UtilesRed.hc.start();			
		UtilesRed.hc.enviarMensaje("conectar");		
	}

	public void empezarChat() {
		this.empiezaChat = true;
	}

	public void cerrarCliente() {

		UtilesRed.hc.fin();
		UtilesRed.hc.interrupt();
	}

	
}

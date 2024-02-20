package com.mygdx.game.red;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.mygdx.game.pantallas.ScreenGame;
import com.mygdx.game.pantallas.ScreenMenu;
import com.mygdx.game.utiles.Render;

public class HiloCliente extends Thread {

	private DatagramSocket socket;
	private boolean fin = false;
	private InetAddress ipServer;
	private int puerto = 56366;// El puerto del servidor siempre va a estar fijo
	private ScreenGame game;

	public boolean empezar = false;
	public boolean salirDelJuego = false;

	// El serviodor asigna id
	public int IdCliente;

	public HiloCliente(ScreenGame game) {
		this.game = game;

		try {
			socket = new DatagramSocket();
			// socket.setReuseAddress(true); // Permite reutilizar el puerto
			ipServer = InetAddress.getByName("255.255.255.255");// Broadcast
			// enviarMensaje("conectar");
		} catch (SocketException | UnknownHostException e) {
			fin = true;
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		do {
			byte[] datos = new byte[1024];
			DatagramPacket dp = new DatagramPacket(datos, datos.length);
			try {
				socket.receive(dp);
				procesarMensaje(dp);
			} catch (IOException e) {
				// e.printStackTrace();

			}
		} while (!fin);
		socket.close(); // Cierra el socket al salir del bucle
	}

	private void procesarMensaje(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim();// trim() lo que hace es sacar los espacios
		String[] mensajeCompuesto = msg.split("#");

		switch (mensajeCompuesto[0]) {
		case "salir_del_juego":
			salirDelJuego = true;
			break;

		default:
			break;

		case "empezo":
			// game.enRed = true;
			System.out.println("Que empieze el juego!!!");
			empezar = true;
		
			break;
			
		case "Exito":
			if (mensajeCompuesto[1].equals("0")) {
				IdCliente = 0;
			}else {
				IdCliente = 1;
			}
				break;
		case "seMovio":
				
				if(mensajeCompuesto[3].equals("0")) {
					game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),Float.valueOf(mensajeCompuesto[2]));
					//System.out.println("se movio 0");
				}else {
					game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),Float.valueOf(mensajeCompuesto[2]));
					//System.out.println("se movio 1");
				}
			
			break;
			
		case "saltando":
			if(mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),Float.valueOf(mensajeCompuesto[2]));
				
			}else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),Float.valueOf(mensajeCompuesto[2]));
			}
			System.out.println(IdCliente +  " Estoy Saltandooooo");
			break;
			
		
		}

	}
 
	public void enviarMensaje(String msg) {
		byte[] mensaje = msg.getBytes();

		try {
			DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipServer, puerto);
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getIdCliente() {
		return IdCliente;
	}

	public void fin() {
		fin = true;
		socket.close();
	}

	public void setGame(ScreenGame game) {// Sirve para pasarle un Juego, porque en el constructor del static le estoy
											// pasando uno nulo, entonces llamo a esta funcion desde la clase del juego
											// y fue.
		this.game = game;
	}
}
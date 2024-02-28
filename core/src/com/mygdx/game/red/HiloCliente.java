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
import com.mygdx.game.recursos.Enemigo;
import com.mygdx.game.recursos.EstadosKnight;
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
		// System.out.println("llego mensaje "+msg);
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
			if (IdCliente == 0) {

				UtilesRed.jugador = true;
			} else {

				UtilesRed.jugador = false;
			}
			break;

		case "Exito":
			if (mensajeCompuesto[1].equals("0")) {
				IdCliente = 0;

			} else {
				IdCliente = 1;

			}
			break;
		case "seMovioIzquierda":
			// System.out.println("llego mensajee");

			/*for (int i = 0; i < mensajeCompuesto.length; i++) {
				System.out.println(i + " " + mensajeCompuesto[i]);
			}
			/*
			 * switch(mensajeCompuesto[4]) { case "izquierda":
			 * if(mensajeCompuesto[3].equals("0")) {
			 * game.getJugador1().cambiarEstado(EstadosKnight.WALKING_LEFT); }else {
			 * game.getJugador2().cambiarEstado(EstadosKnight.WALKING_LEFT); }
			 * 
			 * break; }
			 */
			if (mensajeCompuesto[3].equals("0")) {
				//Renderiza las animaciones en red
				game.getJugador1().cambiarEstado(EstadosKnight.WALKING_LEFT);
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				System.out.println("se movio 0");
				
			
			} else {
				game.getJugador2().cambiarEstado(EstadosKnight.WALKING_LEFT);
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				System.out.println("se movio 1");
			}

			break;
		case "seMovioDerecha":
			// System.out.println("llego mensajee");

			/*for (int i = 0; i < mensajeCompuesto.length; i++) {
				System.out.println(i + " " + mensajeCompuesto[i]);
			}
			/*
			 * switch(mensajeCompuesto[4]) { case "izquierda":
			 * if(mensajeCompuesto[3].equals("0")) {
			 * game.getJugador1().cambiarEstado(EstadosKnight.WALKING_LEFT); }else {
			 * game.getJugador2().cambiarEstado(EstadosKnight.WALKING_LEFT); }
			 * 
			 * break; }
			 */
			if (mensajeCompuesto[3].equals("0")) {
				//Renderiza las animaciones en red
				game.getJugador1().cambiarEstado(EstadosKnight.WALKING_RIGHT);
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				System.out.println("se movio 0");
				
			
			} else {
				game.getJugador2().cambiarEstado(EstadosKnight.WALKING_RIGHT);
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				System.out.println("se movio 1");
			}

			break;

		case "saltando":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				
			} else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}
			// System.out.println(IdCliente + " Estoy Saltandooooo");
			break;
		case "cayendo":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);

			} else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}
			// System.out.println(IdCliente + " Estoy Saltandooooo");
			break;
		case "atacando":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().cambiarEstado(EstadosKnight.ATTACK);
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
				
			} else {
				game.getJugador2().cambiarEstado(EstadosKnight.ATTACK);
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}	
			// System.out.println(IdCliente + " Estoy Saltandooooo");
			break;
		case "cubriendo":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);

			} else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}
			break;
			
		case"cambioEscenario":
			game.cambiarEscenario();
			break;
		/*case "restarVida":
			if(mensajeCompuesto[1].equals("0")) {
				game.getJugador1().restarVida(20);
			}else {
				game.getJugador2().restarVida(20);
			}
			break;
			
		
		/*case "cubriendoizquierda":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);

			} else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}
			break;
		case "cubriendoderecha":
			if (mensajeCompuesto[3].equals("0")) {
				game.getJugador1().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);

			} else {
				game.getJugador2().actualizarPosicionRed(Float.valueOf(mensajeCompuesto[1]),
						Float.valueOf(mensajeCompuesto[2]), mensajeCompuesto[3]);
			}
			break;
			
		/*case "estadoenemigo":
			boolean estadoEnemigo = Boolean.parseBoolean(mensajeCompuesto[1]);
			int index = Integer.parseInt(mensajeCompuesto[2]);
			
			if(game.getJugador1().enemigosEnElMapa.size()>index) {
				Enemigo enemigo = game.getJugador1().enemigosEnElMapa.get(index);
				
				if(enemigo!=null) {
					enemigo.muerto = estadoEnemigo;
				}
			
			}if(game.getJugador2().enemigosEnElMapa.size()>index) {
				Enemigo enemigo = game.getJugador2().enemigosEnElMapa.get(index);
				
				if(enemigo!=null) {
					enemigo.muerto = estadoEnemigo;
				}
			
			}
			
			
			break;*/

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
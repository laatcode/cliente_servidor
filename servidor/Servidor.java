package cliente_servidor.servidor;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Luis Angel Avila Torres
 * @version 1.0
 * 
 * Permite crear un servidor para recibir clientes a trav�s de una conexi�n de sockets.
 * Solo necesita el n�mero dle puerto para ser creada
 * 
 */

public class Servidor{
	
	ServerSocket servidor;
	static ArrayList<Socket> clientes;
	Socket cliente;
	SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	
	public ArrayList<Socket> getClientes(){
		return clientes;
		/*Si se desea mostrar clientes conectados se debe agregar un c�digo como el siguiente:
		 for(int i = 0; i<clientes.size();i++){
		 		cliente = clientes.get(i);
		 		System.out.println(cliente.getInetAddress()+":"+cliente.getPort());
		 }*/
	}
	
	public static void eliminarCliente(Socket socket){
		clientes.remove(socket);
	}
	 
	public Servidor(int puerto){
		try{
			//Se crea una instancia de la clase ServerSocket a trav�s del puerto indicado
			servidor = new ServerSocket(puerto);
			//Mensajes con informaci�n del servidor, como el puerto y la direcci�n local
			System.out.println(formateador.format(new Date())+" Servidor iniciado en puerto "+puerto );
			System.out.println(formateador.format(new Date())+" "+InetAddress.getLocalHost().toString());
			//Se crea una instancia de ArrayList para almacenar todos lso clientes que se conecten
			clientes = new ArrayList<Socket>();
			//Se crea un ciclo infinito con el fin de que el servidor se mantenga esperando conexiones de clientes todo el tiempo
			while(true){
				//Espera que el cliente env�e una petici�n para conectarse y se captura
				cliente = servidor.accept();
				//Se agrega el cliente al ArrayList
				clientes.add(cliente);
				//Se crea un nuevo hilo en ejecutando la clase Gesti�nCliente y se le env�a el cliente
				new GestionCliente(cliente).start();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] arg){
		new Servidor(5000);
	}
}
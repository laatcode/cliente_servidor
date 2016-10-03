package cliente_servidor.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionCliente extends Thread{
	
	Socket cliente;
	InputStream is;
	DataInputStream flujoEntrada;
	OutputStream os;
	DataOutputStream flujoSalida;
	SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:MM:S");
	boolean conexion = true;
	String texto="";
	
	public void run(){
		try{
			//Muestra la dirección del cliente y puerto de conexión
			System.out.println(formateador.format(new Date())+" "+cliente.getInetAddress()+":"+cliente.getPort()+" conectado");
			//Se instancias las clases para los flujos de entrada y de salida de datos
			is = cliente.getInputStream();
			flujoEntrada = new DataInputStream(is);
			os = cliente.getOutputStream();
			flujoSalida= new DataOutputStream(os);
			
			//Ciclo infinito mientras se mantenga la conexión
			while(conexion){
				//Compara si el flujo estimado de Bytes es diferente de 0 
				if(is.available()!=0){
					//Si es diferente, se ejecutar el método leer
					leer();
				}
				//Compara si la variable texto no está vacía
				if(texto!=""){
					//Si no está vacía ejecuta el método escribir
					escribir(texto);
				}
			}
		}catch(Exception e){
			System.out.println("Error: "+e);
		}
	}
	
	public void desconectar(){
		try{
			//Cierra las conexiones con el socket del cliente y los flujos de entrada
			cliente.close();
			is.close();
			os.close();
			flujoEntrada.close();
			flujoSalida.close();
			//Cambia el valor de la varibale conexión a false para controlar el ciclo
			conexion = false;
			//Elimina el cliente de la lista de clientes conectados al servidor
			Servidor.eliminarCliente(cliente);
			System.out.println(formateador.format(new Date())+" "+cliente.getInetAddress()+":"+cliente.getPort()+" desconectado");
		}catch(Exception e){
			System.out.println(formateador.format(new Date())+" Error al desconectar cliente: "+e);
		}
	}
	
	public void setTexto(String t){
		texto = t;
	}
	
	public void escribir(String texto){
		try{
			//Coloca el valor de la variable texto en el flujo de salida del socket
			flujoSalida.writeUTF(texto);
			//Cambia el valor de la variable texto a vacío
			this.texto = "";
		}catch(Exception e){
			System.out.println(formateador.format(new Date())+" Error al enviar: "+e);
		}
	}
	
	public void leer(){
		String text;
		try{
			//Lee el flujo de entrada y lo almacena en al variable text, luego compara si es igual a <salir>
			if((text=flujoEntrada.readUTF()).equals("<salir>")){
				//Si es igual asigna a la variable conexión el valor false
				conexion = false;
				//Ejecuta el método desconectar
				desconectar();
			}else{
				//Si no es igual a <salir> ejecuta el método de la clase acciones con el valor de text
				Acciones.ejecutar(text);
			}
		}catch(Exception e){
			System.out.println(formateador.format(new Date())+" Error al leer "+e);
		}
	}
	
	//Contructor de la clase, el cual toma el cliente enviado por Servidor y lo asigna al cliente de la propia clase
	public GestionCliente(Socket cliente){
		this.cliente = cliente;
	}
}
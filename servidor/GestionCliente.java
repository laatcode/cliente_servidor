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
			//Muestra la direcci�n del cliente y puerto de conexi�n
			System.out.println(formateador.format(new Date())+" "+cliente.getInetAddress()+":"+cliente.getPort()+" conectado");
			//Se instancias las clases para los flujos de entrada y de salida de datos
			is = cliente.getInputStream();
			flujoEntrada = new DataInputStream(is);
			os = cliente.getOutputStream();
			flujoSalida= new DataOutputStream(os);
			
			//Ciclo infinito mientras se mantenga la conexi�n
			while(conexion){
				//Compara si el flujo estimado de Bytes es diferente de 0 
				if(is.available()!=0){
					//Si es diferente, se ejecutar el m�todo leer
					leer();
				}
				//Compara si la variable texto no est� vac�a
				if(texto!=""){
					//Si no est� vac�a ejecuta el m�todo escribir
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
			//Cambia el valor de la varibale conexi�n a false para controlar el ciclo
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
			//Cambia el valor de la variable texto a vac�o
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
				//Si es igual asigna a la variable conexi�n el valor false
				conexion = false;
				//Ejecuta el m�todo desconectar
				desconectar();
			}else{
				//Si no es igual a <salir> ejecuta el m�todo de la clase acciones con el valor de text
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
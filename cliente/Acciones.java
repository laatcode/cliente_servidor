package cliente_servidor.cliente;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Acciones {
	
	Cliente conexion;
	Properties propiedades;
	InputStream entrada;
	String host;
	int puerto;
	boolean conectado=false;
	Component padre;
	
	public Acciones(Component padre){
		this.padre = padre;
		propiedades = new Properties();
	}
	
	//Acciones Vista Cliente
	public boolean cargarConfiguracion(Acciones acciones){
		boolean estado=false;
		try{
			entrada = new FileInputStream("configuracion.properties");
			propiedades.load(entrada);
			host=propiedades.getProperty("Host");
			puerto = Integer.parseInt(propiedades.getProperty("Puerto"));
			entrada.close();
			estado = true;
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(padre, "Diligencie los datos de conexión", "Conexión", 1);
			new Configuracion(padre, true, acciones).setVisible(true);
		}catch(IOException e){
			JOptionPane.showMessageDialog(padre, "Error al leer el archivo de configuración "+e, "Error", 0);
		}
		return estado;
	}
	
	public boolean conectar(){
		conexion = new Cliente();
		conectado = conexion.conectar(host, puerto); 
		return conectado;
	}
	
	public boolean desconectar(){
		conectado = conexion.desconectar(); 
		return conectado;
	}
	
	public void comunicacion(){
		conexion.start();
	}
	public void enviar(String texto){
		conexion.setTexto(texto);
	}
	public static void recibir(String t){
		VentanaCliente.setArea(t);
	}
}
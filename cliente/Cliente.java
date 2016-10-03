package cliente_servidor.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Cliente extends Thread{
	Socket socket;
	InputStream is;
	DataInputStream flujoEntrada;
	OutputStream os;
	DataOutputStream flujoSalida;
	SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	String texto = "";
	boolean conectado = false;
	
	public void run(){
		try{
			while(conectado){
				if(is.available()!=0){
					leer();
				}
				if(texto!=""){
					escribir(texto);
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, formateador.format(new Date())+" Error en la comunicación: "+e.getMessage(), "Error", 0);
		}
	}
	
	public boolean conectar(String host, int puerto){
		boolean estado;
		try{
			socket = new Socket(host, puerto);
			os = socket.getOutputStream();
			flujoSalida = new DataOutputStream(os);
			is = socket.getInputStream();
			flujoEntrada = new DataInputStream(is);
			conectado = true;
			estado = true;
		}catch(ConnectException ce){
			estado = false;
			JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor:\n\n- Revise los datos de conexion al servidor\n- Asegurese que el servidor ha iniciado\n- Asegurese que el servidor está esperando clientes");
		}catch(Exception e){
			estado = false;
			JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor: "+e.getMessage());
		}
		return estado;
	}
	
	public boolean desconectar(){
		boolean estado;
		try{
			escribir("<salir>");
			JOptionPane.showMessageDialog(null, "Desconectado del servidor","Información",1);
			conectado = false;
			socket.close();
			estado = conectado;
		}catch(Exception e){
			estado = conectado;
			JOptionPane.showMessageDialog(null, formateador.format(new Date())+" Error al desconectar "+e, "Error", 0);
		}
		return estado;
	}
	
	public void setTexto(String t){
		if(!t.trim().equals("")){
			texto = t;
		}
	}
	
	public void leer(){
		try{
			Acciones.recibir(formateador.format(new Date())+" "+flujoEntrada.readUTF()+"\n");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al leer "+e+"\n","Error",0);
		}
	}
	
	public void escribir(String t){
		try{
			flujoSalida.writeUTF(t);
			texto = "";
		}catch(SocketException se){
			texto = "";
			JOptionPane.showMessageDialog(null, "No se encuentra conectado con el servidor:\n\n- Intente volver a realizar la conexión con el servidor\n ", "Error", 0);
		}catch(Exception e){
			texto = "";
			JOptionPane.showMessageDialog(null, formateador.format(new Date())+" Error al escribir "+e, "Error", 0);
		}
	}
}
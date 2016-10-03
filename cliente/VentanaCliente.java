package cliente_servidor.cliente;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaCliente extends JFrame implements ActionListener{
	JPanel pDatos, pArea, pTexto;
	JMenuBar barra;
	JMenu archivo, informacion;
	JMenuItem iConectar, iConfiguracion, iSalir, iDetalles;
	JSeparator separador;
	JLabel lDatos;
	static JTextArea area;
	JScrollPane scr;
	static JTextField tDatos;
	JTextField tMensaje;
	JButton bConexion, bEnviar;
	boolean conexion = false;
	Acciones acciones;
	
	@Override
	public void actionPerformed(ActionEvent ae){
		String str=ae.getActionCommand();
		if(str.equals("Conectar")){
			conexion = acciones.conectar();
			if(conexion){
				tMensaje.setEditable(true);
				bConexion.setText("Desconectar");
				iConectar.setText("Desconectar");
				acciones.comunicacion();
			}
		}
		if(str.equals("Desconectar")){
			conexion = acciones.desconectar();
			if(conexion==false){
				tMensaje.setEditable(false);
				bConexion.setText("Conectar");
				iConectar.setText("Conectar");
			}
		}
		if(str.equals("Enviar")){
			acciones.enviar(tMensaje.getText());
			limpiar();
		}
		if(str.equals("Salir")){
			if(conexion){
				if (JOptionPane.showConfirmDialog(this, "¿Desea realmente salir del sistema?","Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					conexion = acciones.desconectar();
	        		System.exit(0);
	        	}
			}else{
        		System.exit(0);
        	}
		}
		if(str.equals("Configuracion")){
			new Configuracion(this, true, acciones).setVisible(true);
		}
	}
	
	public VentanaCliente(){
		acciones = new Acciones(this);
		//Paneles
		pDatos = new JPanel();
		pDatos.setLayout(new GridBagLayout());
		pArea = new JPanel();
		pArea.setLayout(new GridBagLayout());
		pTexto = new JPanel();
		pTexto.setLayout(new GridBagLayout());
		
		//Barra
		iConectar = new JMenuItem("Conectar");
		iConectar.addActionListener(this);
		iConfiguracion = new JMenuItem("Configuración");
		iConfiguracion.addActionListener(this);
		iConfiguracion.setActionCommand("Configuracion");
		separador = new JSeparator();
		iSalir = new JMenuItem("Salir");
		iSalir.addActionListener(this);
		
		archivo = new JMenu("Archivo");
		archivo.add(iConectar);
		archivo.add(iConfiguracion);
		archivo.add(separador);
		archivo.add(iSalir);
		
		iDetalles = new JMenuItem("Detalles");
		iDetalles.addActionListener(this);
		
		informacion = new JMenu("Información");
		informacion.add(iDetalles);
		
		barra = new JMenuBar();
		barra.add(archivo);
		barra.add(informacion);
		
		GridBagConstraints constraints=new GridBagConstraints();
		
		lDatos = new JLabel("Servidor:");
		constraints.gridx = 0; // Ubicacion en x.
		constraints.gridy = 0; // Ubicacion en Y
		constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
		constraints.insets = new Insets( 20,20,0,0 );//Distancia top, left, bottom, right
		pDatos.add (lDatos, constraints);
		
		tDatos = new JTextField();
		tDatos.setEditable(false);
		constraints.gridx = 1; // Ubicacion en x.
		constraints.gridy = 0; // Ubicacion en Y
		constraints.gridwidth = 6; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 1.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.HORIZONTAL; //Hacia donde crece
		constraints.insets = new Insets( 20,20,0,0 );//Distancia top, left, bottom, right
		pDatos.add (tDatos, constraints);
		
		bConexion = new JButton("Conectar");
		bConexion.addActionListener(this);
		constraints.gridx = 7; // Ubicacion en x.
		constraints.gridy = 0; // Ubicacion en Y
		constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
		constraints.insets = new Insets( 20,10,0,20 );//Distancia top, left, bottom, right
		pDatos.add (bConexion, constraints);
		
		area = new JTextArea();
		area.setEditable(false);
		scr = new JScrollPane(area);
		constraints.gridx = 0; // Ubicacion en x.
		constraints.gridy = 0; // Ubicacion en Y
		constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 1.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 1.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.BOTH; //Hacia donde crece
		constraints.insets = new Insets( 10,20,0,20 );//Distancia top, left, bottom, right
		pArea.add(scr, constraints);
		
		tMensaje = new JTextField();
		tMensaje.setEditable(false);
		tMensaje.addActionListener(this);
		tMensaje.setActionCommand("Enviar");
		constraints.gridx = 0; // Ubicacion en x.
		constraints.gridy = 1; // Ubicacion en Y
		constraints.gridwidth = 8; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 1.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.HORIZONTAL; //Hacia donde crece
		constraints.insets = new Insets( 10,20,10,10 );//Distancia top, left, bottom, right
		pTexto.add (tMensaje, constraints);
		
		bEnviar = new JButton("Enviar");
		constraints.gridx = 9; // Ubicacion en x.
		constraints.gridy = 1; // Ubicacion en Y
		constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
		constraints.gridheight = 1; // Cantidad de filas que ocupa.
		constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
		constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
		constraints.fill = GridBagConstraints.HORIZONTAL; //Hacia donde crece
		constraints.insets = new Insets( 10,0,10,20 );//Distancia top, left, bottom, right
		pTexto.add (bEnviar, constraints);
		bEnviar.addActionListener(this);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent evt) {
		    	if(conexion){
		    		if(JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente salir del sistema?","Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
		    			conexion = acciones.desconectar();
			    		System.exit(0);
			    	}
		    	}else{
		    		System.exit(0);
		    	}
		    }
		});
		
		if(acciones.cargarConfiguracion(acciones)){
			setTDatos(acciones.host+" : "+acciones.puerto);
		}
		
		this.setTitle("Cliente");
		this.setSize(500,400);
		this.setJMenuBar(barra);
		this.setLocationRelativeTo(null);
		this.add(pDatos, BorderLayout.NORTH);
		this.add(pArea, BorderLayout.CENTER);
		this.add(pTexto, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void setTDatos(String t){
		tDatos.setText(t);
	}
	
	public void limpiar(){
		tMensaje.setText("");
	}
	
	public static void setArea(String t){
		area.append(t);
	}
	public static void main(String[] args) {
		new VentanaCliente();
	}
}

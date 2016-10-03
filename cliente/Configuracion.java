package cliente_servidor.cliente;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Configuracion extends JDialog implements ActionListener{
	Properties propiedades;
	InputStream entrada;
	FileOutputStream salida;
	JPanel pConfiguracion, pBotones;
	JLabel lIP, lPuerto;
	JTextField tIP, tPuerto;
	JButton bAceptar, bCancelar;
	Acciones acciones;
	
	public void actionPerformed(ActionEvent ae){
		String str=ae.getActionCommand();
		if(str.equals("Aceptar")){
			//Crear Configuración
			try{
				propiedades.setProperty("Host", tIP.getText().trim());
				propiedades.setProperty("Puerto", tPuerto.getText().trim());
				propiedades.store(new FileWriter("configuracion.properties"), "Modificado");
				entrada.close();
				if(acciones.cargarConfiguracion(acciones)){
					VentanaCliente.setTDatos(acciones.host+" : "+acciones.puerto);
				}
				this.dispose();
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "Error al modificar el archivo de configuración "+e,"Error", 0);
			}
		}
		if(str.equals("Cancelar")){
			this.dispose();
		}
	}
	
	public Configuracion(Component padre, boolean modal, Acciones acciones){
		super((JFrame)padre, modal);
		propiedades = new Properties();
		this.acciones = acciones;
		
		pConfiguracion = new JPanel();
		pConfiguracion.setLayout(new GridBagLayout());
		pBotones = new JPanel();
		pBotones.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints=new GridBagConstraints();
		
		lIP = new JLabel("IP:");
		constraints.gridx = 0; // Ubicacion en x.
        constraints.gridy = 0; // Ubicacion en Y
        constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
        constraints.insets = new Insets( 20,20,0,0 );//Distancia top, left, bottom, right
        pConfiguracion.add(lIP, constraints);
        
        tIP = new JTextField();
        tIP.addActionListener(this);
        tIP.setActionCommand("Aceptar");
        constraints.gridx = 1; // Ubicacion en x.
        constraints.gridy = 0; // Ubicacion en Y
        constraints.gridwidth = 3; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 1.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.HORIZONTAL; //Hacia donde crece
        constraints.insets = new Insets( 20,10,0,20 );//Distancia top, left, bottom, right
        pConfiguracion.add(tIP, constraints);
        
        lPuerto = new JLabel("Puerto:");
        constraints.gridx = 0; // Ubicacion en x.
        constraints.gridy = 1; // Ubicacion en Y
        constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
        constraints.insets = new Insets( 10,20,0,0 );//Distancia top, left, bottom, right
        pConfiguracion.add(lPuerto, constraints);
        
        tPuerto = new JTextField();
        tPuerto.addActionListener(this);
        tPuerto.setActionCommand("Aceptar");
        constraints.gridx = 1; // Ubicacion en x.
        constraints.gridy = 1; // Ubicacion en Y
        constraints.gridwidth = 3; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 1.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.HORIZONTAL; //Hacia donde crece
        constraints.insets = new Insets( 10,10,0,20 );//Distancia top, left, bottom, right
        pConfiguracion.add(tPuerto, constraints);
        
        bAceptar = new JButton("Aceptar");
        bAceptar.addActionListener(this);
        constraints.gridx = 1; // Ubicacion en x.
        constraints.gridy = 0; // Ubicacion en Y
        constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
        constraints.insets = new Insets( 10,0,20,0 );//Distancia top, left, bottom, right
        pBotones.add(bAceptar, constraints);
        
        bCancelar = new JButton("Cancelar");
        bCancelar.addActionListener(this);
        constraints.gridx = 2; // Ubicacion en x.
        constraints.gridy = 0; // Ubicacion en Y
        constraints.gridwidth = 1; // Cantidad de columnas que ocupa.
        constraints.gridheight = 1; // Cantidad de filas que ocupa.
        constraints.weightx = 0.0; //Crecimiento exponencial del componente en x
        constraints.weighty = 0.0; //Crecimiento exponencial del componente en y
        constraints.fill = GridBagConstraints.NONE; //Hacia donde crece
        constraints.insets = new Insets( 10,20,20,20 );//Distancia top, left, bottom, right
        pBotones.add(bCancelar, constraints);
        
        try{
			File archivo = new File("configuracion.properties");
			if(archivo.exists()){
				entrada = new FileInputStream("configuracion.properties");
				propiedades.load(entrada);
				tIP.setText(propiedades.getProperty("Host").toString());
				tPuerto.setText(propiedades.getProperty("Puerto").toString());
			}else{
				salida = new FileOutputStream("configuracion.properties");
				entrada = new FileInputStream("configuracion.properties");
			}
		}catch(Exception e){
			System.out.println("Error: "+e);
		}
        
        this.setTitle("Configuración");
        this.setSize(300,170);
        this.add(pConfiguracion, BorderLayout.CENTER);
        this.add(pBotones, BorderLayout.SOUTH);
        this.setLocationRelativeTo(padre);
	}
}
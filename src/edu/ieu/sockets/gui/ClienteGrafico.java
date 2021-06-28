package edu.ieu.sockets.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;

import edu.ieu.sockets.tcp.EchoClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteGrafico {

	private JFrame frame;
	private JTextField textMensaje;
	private JTextPane textRespuesta;
	private JButton btnEnviar;
	
	
	private EchoClient echoClient = new EchoClient();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteGrafico window = new ClienteGrafico();
					window.frame.setVisible(true);
					window.conectar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void conectar() {
		echoClient.startConnection("localhost", 6000);
		textRespuesta.setText("Conectado al servidor localhost:6000");		
	}
	
	public String agregarTextoRespuesta(String nuevoTexto) {
		StringBuilder builder = new StringBuilder();
		builder.append( textRespuesta.getText()  );
		builder.append(nuevoTexto + "\n");
		return builder.toString();
	}

	/**
	 * Create the application.
	 */
	public ClienteGrafico() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textMensaje = new JTextField();
		textMensaje.setToolTipText("Escriba el mensaje al servidor\r\n");
		frame.getContentPane().add(textMensaje, BorderLayout.NORTH);
		textMensaje.setColumns(10);
		
		btnEnviar = new JButton("Enviar");
		
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mensaje = textMensaje.getText();
				System.out.println("Mensaje para el servidor " + mensaje);
				
				String respuesta = echoClient.sendMessage(mensaje);
				System.out.println("Respuesta : " + respuesta);
				String historialRespuesta = agregarTextoRespuesta(respuesta);
				textRespuesta.setText(historialRespuesta);
				
				if(respuesta.equals("good bye")) {
					System.out.println("Conexion finalizada");
					textRespuesta.setText("Conexion finalizada... \n "
							+ "Reinice el programa");
					echoClient.stopConnection();
					btnEnviar.setEnabled(false);
				}
			}
		});
		frame.getContentPane().add(btnEnviar, BorderLayout.EAST);
		
		textRespuesta = new JTextPane();
		frame.getContentPane().add(textRespuesta, BorderLayout.CENTER);
	}

}

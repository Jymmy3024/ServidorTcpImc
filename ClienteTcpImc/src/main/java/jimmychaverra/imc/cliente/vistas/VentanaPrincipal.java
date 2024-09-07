package jimmychaverra.imc.cliente.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VentanaPrincipal extends JFrame{
    private JPanel panelFondo;
    private JTabbedPane tabbedPane1;
    private JTextField campoIpServidor;
    private JLabel txtEstado;
    private JButton btnIniciar;
    private JTextField campoPuertoServidor;
    private JTextField txtMensaje;
    private JTextField campoPeso;
    private JTextField campoAltura;
    private JLabel txtResultado;
    private JButton btnCalcular;

    Socket servidor;
    DataOutputStream out;
    DataInputStream in;

    public VentanaPrincipal() {
        iniciarForma();
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleIniciar();
            }


        });

        //formopened();
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularImc();
            }
        });
    }

   /* private void formopened() {
        String ip;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
            campoIpServidor.setText(ip);
        }catch(UnknownHostException e){
            JOptionPane.showMessageDialog(panelFondo, "Falla en la conexion   ");
        }
    }*/

    private void iniciarForma(){
        setContentPane(panelFondo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);

        //setLocationRelativeTo(null);
    }

    private void toggleIniciar() {
        int puerto = Integer.parseInt(campoPuertoServidor.getText());
        String ip = campoIpServidor.getText();
        try{
            if(btnIniciar.getText().equalsIgnoreCase("Conectar")){
                servidor = new Socket(ip, puerto);
                out = new DataOutputStream(servidor.getOutputStream());
                in = new DataInputStream(servidor.getInputStream());
                btnIniciar.setText("Desconectar");
                btnIniciar.setForeground(Color.RED);
                txtEstado.setText("Conectado");
                txtEstado.setForeground(Color.GREEN);
            }else if(btnIniciar.getText().equalsIgnoreCase("Desconectar")){
                if(servidor.isConnected()){
                    servidor.close();
                }
                btnIniciar.setText("Conectar");
                txtEstado.setText("Desconectado");
                btnIniciar.setForeground(Color.GREEN);
                txtEstado.setForeground(Color.RED);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(panelFondo, "Error al conectar al servidor  ");
            e.printStackTrace();
        }

    }

    private void calcularImc() {
        if(!servidor.isConnected()){
            JOptionPane.showMessageDialog(panelFondo, "Cliente offline, Conecte con el Servidor");
            return;
        }

        float peso = Float.parseFloat(campoPeso.getText());
        float altura = Float.parseFloat(campoAltura.getText());
        Thread hilo = new Thread(){
            @Override
            public void run() {
                try{
                    System.out.println("Peso: " + peso);
                    System.out.println("Altura: " + altura);
                    out.writeFloat(peso);
                    out.writeFloat(altura);
                    out.flush();

                    System.out.println("Enviados los datos\nEsperando respuesta");

                    float imc = in.readFloat();
                    String msj = in.readUTF();
                    System.out.println("IMC: " + imc+"\nMensaje: " + msj);
                    txtResultado.setText(imc+"");
                    txtMensaje.setText(msj);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(panelFondo, "Error con el cliente"+e.getMessage());
                    System.out.println("Error con el cliente"+e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        hilo.start();
    }

    public JLabel getTxtEstado() {
        return txtEstado;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }
}

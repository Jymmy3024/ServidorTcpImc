package jimmychaverra.imc.vistas;

import jimmychaverra.imc.servidor.ServidorTcp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class VentanaPrincipal extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTextField campoIp;
    private JButton btnIniciar;
    private JLabel txtEstado;
    private JTextField campoPuerto;
    private JTextArea cajaLog;
    private JButton btnLimpiar;
    private JPanel panelFondo;

    ServidorTcp servidorTcp;


    public VentanaPrincipal() {
        iniciarForma();
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleIniciar();
            }


        });

        formopened();
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }

    private void limpiar() {
        this.cajaLog.setText("");
    }

    private void formopened() {
        String ip;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
            campoIp.setText(ip);
        }catch(UnknownHostException e){
            JOptionPane.showMessageDialog(panelFondo, "Falla en la conexion   ");
        }
    }

    private void iniciarForma(){
        setContentPane(panelFondo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);

        //setLocationRelativeTo(null);
    }

    private void toggleIniciar() {
        if(btnIniciar.getText().equalsIgnoreCase("INICIAR")){
            int puerto = Integer.parseInt(campoPuerto.getText());
            servidorTcp = new ServidorTcp(puerto, this);
            servidorTcp.start();
            btnIniciar.setText("Detener");
            txtEstado.setText("ONLINE");
            txtEstado.setForeground(Color.green);
        }else if(btnIniciar.getText().equalsIgnoreCase("DETENER")){
            servidorTcp.detenerServicio();
            btnIniciar.setText("Iniciar");
            txtEstado.setText("OFFLINE");
            txtEstado.setForeground(Color.RED);
        }
    }


    public ServidorTcp getServidorTcp() {
        return servidorTcp;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTextArea getCajaLog() {
        return cajaLog;
    }

    public JTextField getCampoPuerto() {
        return campoPuerto;
    }

    public JLabel getTxtEstado() {
        return txtEstado;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JTextField getCampoIp() {
        return campoIp;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }
}

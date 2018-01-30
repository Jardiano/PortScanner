/*
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 314115512
 */
/*
public class ThreadTeste extends javax.swing.JFrame {

    public static void main(String[] args) {
        
        final String ip= JOptionPane.showInputDialog("Digite o IP da máquina que será escaneada");
        
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                Port port = new Port();
                try {
                    port.ScannerPortas(ip,port.ArmazPortConhe(),);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                Port port = new Port();
                try {
                    port.ScannerPortas(ip,port.ArmazPortReg());
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadTeste.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        new Thread(run1).start();
        new Thread(run2).start();

    }
}    
*/
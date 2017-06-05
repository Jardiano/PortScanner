
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 314115512
 */
public class ThreadTeste {

    public static void main(String[] args) {

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                Port port = new Port();
                try {
                    port.ScannerPortas(port.ArmazPortConhe());
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
                    port.ScannerPortas(port.ArmazPortReg());
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

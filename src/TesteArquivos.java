
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class TesteArquivos {

    public static void main(String[] args) {
        Port porta = new Port();
        try {
            System.out.println(porta.convertePortasLista(porta.ArmazPortConhe()));
            System.out.println(porta.convertePortasLista(porta.ArmazPortReg()));
        } catch (IOException ex) {
            Logger.getLogger(TesteArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

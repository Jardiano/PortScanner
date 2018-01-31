

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author natan
 */
public class ScanResultado{

    private final String ip;
    private List<PortData> listaPortas = new ArrayList<>();
    private int openPorts = 0;

    public ScanResultado(String ip) {
        this.ip = ip;
    }

    public List<PortData> getListaPortas() {
        return listaPortas;
    }

    public void setListaPortas(List<PortData> listaPortas) {
        this.listaPortas = listaPortas;
    }

    public void LerDados() {
        Workbook planilha;
        Sheet aba;
        File arquivo;
        Cell[] linha;
        try {
            String s = System.getProperty("file.separator"); //file path separator
            String path = System.getProperty("user.dir") + s + "src" + s + "arquivos" + s + "portas.xls";
            arquivo = new File(path); //novo arquivo
            // instancia a planilha
            planilha = Workbook.getWorkbook(arquivo);

            Sheet[] abas = planilha.getSheets(); //abas da planilha
            aba = planilha.getSheet(0); // pega a primeira aba, ou seja, aba de indice 0.

            int i = 0;
            linha = aba.getRow(i); // pega a primeira linha, ou seja, linhas de indice 0.

            int port;
            boolean isOpen;
            String status;
            String description;
            String[] aux;

            int lowerPort;
            int upperPort;
            while (linha != null) {
                try {
                    port = Integer.parseInt(linha[0].getContents());
                    status = linha[1].getContents();
                    description = linha[2].getContents();
                    listaPortas.add(new PortData(port, false, status, description));
                } catch (Exception e) {
                    aux = linha[0].getContents().split("-");
                    lowerPort = Integer.parseInt(aux[0]);
                    upperPort = Integer.parseInt(aux[1]);

                    status = linha[1].getContents();
                    description = linha[2].getContents();
                    for (int j = lowerPort; j <= upperPort; j++) {
                        listaPortas.add(new PortData(j, false, status, description));
                    }
                } finally {
                    linha = aba.getRow(++i);
 
                }
            }

// mostra os dados da linha
        } catch (IOException | BiffException | IndexOutOfBoundsException e) {
        }

    }

    public Future<PortData> portIsOpen(ExecutorService es, final int port, final int timeout) {

        return es.submit(new Callable<PortData>() {
            @Override
            public PortData call() {
                PortData porta = new PortData();
                porta.setIsOpen(false);
                porta.setPort(port);

                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    porta.setIsOpen(true); //esta aberta
                    return porta;
                } catch (Exception ex) {
                    return porta;
                }

            }
        });
    }

    public void ScanearPortas(JTextArea resultadoArea) throws InterruptedException, ExecutionException {
        final ExecutorService es = Executors.newFixedThreadPool(20);
        final int timeout = 200;
        final List<Future<PortData>> todasAsPortas = new ArrayList<>();
        for (int port = 1; port <= 65535; port++) { //testa todas as portas se estao ou nao abertas, gerando uma lista desses resultados
             todasAsPortas.add(portIsOpen(es, port, timeout));
        }
        
        es.awaitTermination(200L, TimeUnit.MILLISECONDS);
  
        String resultadoParcial;
        String resultadoAreaAux;
        for (final Future<PortData> portaAtual : todasAsPortas) {
            // System.out.println(portaAtual.get().getPort() + " " + portaAtual.get().IsOpen()+" "+this.listaPortas.contains(portaAtual.get()));

            if (portaAtual.get().IsOpen()) {
                
                this.openPorts++;
                resultadoParcial = "Porta: " + portaAtual.get().getPort();
                if(this.listaPortas.contains(portaAtual.get())){
                    PortData aux = this.listaPortas.get(this.listaPortas.indexOf(portaAtual.get()));
                    aux.setIsOpen(true);
                    resultadoParcial+=" "+"\nRegistro: "+ aux.getRegistro() + "\nDescrição: "+aux.getDescription();
                }else{
                    resultadoParcial+=" "+"\nRegistro: -- \nDescrição: --";
                }
                
                resultadoAreaAux = resultadoArea.getText()+"\n" + resultadoParcial+"\n------------------------------------------------------\n";
                resultadoArea.setText(resultadoAreaAux);
            }
        }
        System.out.println(this.openPorts);
    }
    
}

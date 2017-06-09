
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 314115512
 */
public class Port {

    public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
            final int timeout) {
        return es.submit(new Callable<ScanResult>() {
            @Override
            public ScanResult call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return new ScanResult(port, true);
                } catch (Exception ex) {
                    return new ScanResult(port, false);
                }
            }
        });
    }

    /**
     *
     * @param ip Endereço de ip da máquina que sera escaneada.
     * @param listaPortas Lista de portas que devem ser verificadas
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public void ScannerPortas(final String ip,int[] listaPortas) throws InterruptedException, ExecutionException, IOException {
        final ExecutorService es = Executors.newFixedThreadPool(20);
        //final String ip = "127.0.0.1";
        final int timeout = 200;
        final List<Future<ScanResult>> futures = new ArrayList<>();
        for (int port = 1; port <= 65535; port++) {
            futures.add(portIsOpen(es, ip, port, timeout));
        }

        es.awaitTermination(200L, TimeUnit.MILLISECONDS);

        int openPorts = 0;
        List<Integer> lista = convertePortasLista(listaPortas);
        for (final Future<ScanResult> f : futures) {
            if (f.get().isOpen() && lista.contains(f.get().getPort())) {
                openPorts++;
                int index = lista.indexOf(f.get().getPort());
                if (armazPortReg[index] == null) {
                    System.out.println("Porta: "+f.get().getPort() + " Serviço: " + armazPortConhe[index]);
                    } else {
                    System.out.println("Porta: "+f.get().getPort() + " Serviço: " + armazPortReg[index]);
                }

                //System.out.println("Quantidade de portas abertas "+openPorts);
            }
        }
        System.out.println("Timeout: " + timeout);
        //System.out.println("There are " + openPorts + " open ports on host " + ip + " (probed with a timeout of "
        //      + timeout + "ms)");
    }

    final String PORTAS_CONHECIDAS = System.getProperty("user.dir") + "\\src\\arquivos\\PortasConhecidas.txt";
    final String PORTAS_REGISTRADAS = System.getProperty("user.dir") + "\\src\\arquivos\\PortasRegistradas.txt";
    String[] armazPortConhe = new String[30];
    String[] armazPortReg = new String[30];

    /**
     * Método para leitura do arquivo de portas conhecidas.
     *
     * @return Lista de portas registradas num vetor de inteiros.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[] ArmazPortConhe() throws FileNotFoundException, IOException {
        FileReader arquivo = new FileReader(PORTAS_CONHECIDAS);
        BufferedReader buffer = new BufferedReader(arquivo);
        int[] portasConhecidas = new int[30];
        int i = 0;
        while (buffer.ready()) {
            String linha = buffer.readLine();
            if (linha.contains("#")) {
                //System.out.println(linha);
                linha = linha.substring(1, linha.length());
                armazPortConhe[i] = linha;
                continue;
            } else {
                if (!linha.trim().isEmpty()) {
                    portasConhecidas[i] = Integer.parseInt(linha);
                    //System.out.println(linha);
                }
                i++;
            }
        }
        buffer.close();
        return portasConhecidas;
    }

    /**
     * Método para leitura do arquivo de portas registradas.
     *
     * @return Lista de portas registradas num vetor de inteiros.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[] ArmazPortReg() throws FileNotFoundException, IOException {
        FileReader arquivo = new FileReader(PORTAS_REGISTRADAS);
        BufferedReader buffer = new BufferedReader(arquivo);
        int[] portasRegistradas = new int[30];
        int i = 0;
        while (buffer.ready()) {
            String linha = buffer.readLine();
            if (linha.contains("#")) {
                //System.out.println(linha);
                linha = linha.substring(1, linha.length());
                armazPortReg[i] = linha;
                continue;
            } else {
                if (!linha.trim().isEmpty()) {
                    portasRegistradas[i] = Integer.parseInt(linha);
                    //System.out.println(linha);
                }
                i++;
            }
        }
        buffer.close();
        return portasRegistradas;
    }

    /**
     * Metodo para conversão do vetor de portas em listas
     *
     * @param listaPortas Vetor de portas scaneadas
     * @return Lista de portas
     */
    public List<Integer> convertePortasLista(int[] listaPortas) {
        List<Integer> listaInt = new ArrayList<>();
        for (int index = 0; index < listaPortas.length; index++) {
            listaInt.add(listaPortas[index]);
        }
        return listaInt;
    }

}

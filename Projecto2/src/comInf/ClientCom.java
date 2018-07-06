package comInf;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 * Este tipo de dados implementa o canal de comunicação, lado do cliente, para uma comunicação baseada em passagem de
 * mensagens sobre sockets usando o protocolo TCP.
 * A transferência de dados é baseada em objectos, um objecto de cada vez.
 */

public class ClientCom {

    /**
     * Socket de comunicação
     *
     * @serialField commSocket
     */
    private Socket commSocket = null;

    /**
     * Nome do sistema computacional onde está localizado o servidor
     *
     * @serialField serverHostName
     */

    private String serverHostName = null;

    /**
     * Número do port de escuta do servidor
     *
     * @serialField serverPortNumb
     */

    private int serverPortNumb;

    /**
     * Stream de entrada do canal de comunicação
     *
     * @serialField in
     */

    private ObjectInputStream in = null;

    /**
     * Stream de saída do canal de comunicação
     *
     * @serialField out
     */

    private ObjectOutputStream out = null;

    /**
     * Instanciação de um canal de comunicação.
     *
     * @param hostName nome do sistema computacional onde está localizado o servidor
     * @param portNumb número do port de escuta do servidor
     */

    public ClientCom(String hostName, int portNumb) {
        serverHostName = hostName;
        serverPortNumb = portNumb;
    }

    /**
     * Abertura do canal de comunicação.
     * Instanciação de um socket de comunicação e sua associação ao endereço do servidor.
     * Abertura dos streams de entrada e de saída do socket.
     *
     * @return <li>true, se o canal de comunicação foi aberto
     * <li>false, em caso contrário
     */

    public boolean open() {
        boolean success = true;
        SocketAddress serverAddress = new InetSocketAddress(serverHostName, serverPortNumb);

        try {
            commSocket = new Socket();
            commSocket.connect(serverAddress);
        } catch (UnknownHostException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - name of the machine is not known: " +
                    serverHostName + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (NoRouteToHostException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - the machine cannot be reached: " +
                    serverHostName + "!");
            e.printStackTrace();
            System.exit(1);
        } catch (ConnectException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - server does not respond in: " + serverHostName + "." + serverPortNumb + "!");
            if (e.getMessage().equals("Connection refused"))
                success = false;
            else {
                GenericIO.writelnString(e.getMessage() + "!");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (SocketTimeoutException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Timeout on: " +
                    serverHostName + "." + serverPortNumb + "!");
            success = false;
        } catch (IOException e)                           // erro fatal --- outras causas
        {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Unexpected on: " +
                    serverHostName + "." + serverPortNumb + "!");
            e.printStackTrace();
            System.exit(1);
        }

        if (!success) return false;

        try {
            out = new ObjectOutputStream(commSocket.getOutputStream());
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - not possible to open the exit way!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            in = new ObjectInputStream(commSocket.getInputStream());
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - not possible to open the enter way!");
            e.printStackTrace();
            System.exit(1);
        }

        return true;
    }

    /**
     * Fecho do canal de comunicação.
     * Fecho dos streams de entrada e de saída do socket.
     * Fecho do socket de comunicação.
     */

    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - not possible to close the socket!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            out.close();
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - not possible to close the socket!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - not possible to close the channel!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Leitura de um objecto do canal de comunicação.
     *
     * @return objecto lido
     */

    public Object readObject() {
        Object fromServer = null;                            // objecto

        try {
            fromServer = in.readObject();
        } catch (InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Deserialization not possible!");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Error in reading from socket!");
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Type of object unknown!");
            e.printStackTrace();
            System.exit(1);
        }

        return fromServer;
    }

    /**
     * Escrita de um objecto no canal de comunicação.
     *
     * @param toServer objecto a ser escrito
     */

    public void writeObject(Object toServer) {
        try {
            out.writeObject(toServer);
            out.flush();
        } catch (InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Object cannot be serializable!");
            e.printStackTrace();
            System.exit(1);
        } catch (NotSerializableException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Data type not serializable!");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() +
                    " - Error in writing object!");
            e.printStackTrace();
            System.exit(1);
        }
    }

}

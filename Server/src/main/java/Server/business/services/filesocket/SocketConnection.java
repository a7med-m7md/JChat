package Server.business.services.filesocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.Constants.SOCKET_SERVER_PORT;

public class SocketConnection {
    ServerSocket serverSocket;
    ServerThreadHandled serverThreadHandled;


    private static SocketConnection socketConnection;

    private SocketConnection() {
    }

    public static synchronized SocketConnection getInstance() {
        if (socketConnection == null)
            socketConnection = new SocketConnection();
        return socketConnection;
    }
    //TODO -> Used when server is initialized
    public void startConnection() {
        //use thread with waiting operation here to avoid freezing the current thread
        try {
            serverSocket
                    = new ServerSocket(SOCKET_SERVER_PORT);
            System.out.println(
                    "Server is Starting in Port "+ SOCKET_SERVER_PORT);
            new Thread(
                    () -> {
                        while (!serverSocket.isClosed()) {
                            try {
                                Socket clientSocket = serverSocket.accept();
                                serverThreadHandled = new ServerThreadHandled(clientSocket);
                                Thread th = new Thread(serverThreadHandled);
                                th.start();
                            } catch (IOException e) {
                                //e.printStackTrace();
                                closeResources();
                            }
                        }
                    }
            ).start();

        } catch (IOException e) {
            e.printStackTrace();
            //closeResources();
        }
    }

   /* public void receiveFile(Socket clientSocket) {
        Thread th = new Thread(new FileTransferHandled(clientSocket));
        th.start();
    }*/


    public void closeResources() {
        try {
            if (serverSocket != null)
                this.serverSocket.close();
            /*if (clientSocket != null)
                this.clientSocket.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        ServerThreadHandled.closeServer();
    }
}

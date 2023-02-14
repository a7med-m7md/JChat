package Server.network.services.fileservice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.Constants.SOCKET_SERVER_PORT;

public class SocketConnection {
    Socket clientSocket;
    ServerSocket serverSocket;
    FileTransferHandled fileTransferHandled;

/*
    public SocketConnection() {
        startConnection();

    }
*/

    private static SocketConnection socketConnection;

    private SocketConnection(){ }

    public static synchronized SocketConnection getInstance( ) {
        if (socketConnection == null)
            socketConnection=new SocketConnection();
        return socketConnection;
    }

    public void startConnection() {
        //use thread with waiting operation here to avoid freezing the current thread
        try {
            serverSocket
                    = new ServerSocket(SOCKET_SERVER_PORT);
            System.out.println(
                    "Server is Starting in Port 1200");

            new Thread(
                    () -> {
                        while (!serverSocket.isClosed()) {
                            try {
                                clientSocket = serverSocket.accept();
                                fileTransferHandled = new FileTransferHandled(clientSocket);
                                Thread th = new Thread(fileTransferHandled);
                                th.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                                closeResources();
                                break;
                            }
                        }
                    }
            ).start();

        } catch (IOException e) {
            e.printStackTrace();
            //closeResources();
        }
    }


    public void closeResources() {
        try {
            if (serverSocket != null)
                this.serverSocket.close();
            if (clientSocket != null)
                this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

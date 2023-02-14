package Server.network.services.fileservice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.Constants.SOCKET_SERVER_PORT;

public class SocketConnection {
    private DataOutputStream dataOutputStream = null;
    private  DataInputStream dataInputStream = null;
    Socket clientSocket;
    ServerSocket serverSocket;
    FileTransferHandled fileTransferHandled;


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
            while (!serverSocket.isClosed()){
                new Thread(
                        () -> {
                            try {
                                clientSocket = serverSocket.accept();
                                fileTransferHandled = new FileTransferHandled(clientSocket);
                                Thread th = new Thread(fileTransferHandled);
                                th.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //closeResources();
        }
        /* new Thread(() -> {
                while (true){
                    try {
                        serverSocket
                                = new ServerSocket(SOCKET_SERVER_PORT);
                        System.out.println(
                                "Server is Starting in Port 1200");
                        clientSocket = serverSocket.accept();
                        receiveFile(clientSocket);
                        System.out.println("Connected");
                        dataInputStream = new DataInputStream(
                                clientSocket.getInputStream());
                        dataOutputStream = new DataOutputStream(
                                clientSocket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                        //closeResources();
                    }
                }
            }).start();*/
    }
    public void receiveFile(Socket clientSocket){
        Thread th = new Thread(new FileTransferHandled(clientSocket));
        th.start();
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

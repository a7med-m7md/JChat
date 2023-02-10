package Server.network;

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
    public SocketConnection(){
        startConnection();
    }

    private void startConnection() {
        // Here we define Server Socket running on port 900
        try {
            serverSocket
                    = new ServerSocket(SOCKET_SERVER_PORT);
            System.out.println(
                    "Server is Starting in Port 900");
            clientSocket = serverSocket.accept();
            System.out.println("Connected");
            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());
            closeResources();
        }
        catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }
    }

    private void closeResources(){
        try {
            this.clientSocket.close();
            this.serverSocket.close();
            this.dataOutputStream.close();
            this.dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

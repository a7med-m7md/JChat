package Client.network;

import java.io.*;
import java.net.Socket;

public class FileThreadHandled implements Runnable{
    Socket clientSocket;
    int userId;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;
    public FileThreadHandled(Socket clientSocket,int userId){
        this.clientSocket = clientSocket;
        this.userId = userId;
        try {
            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());
            dataOutputStream.writeInt(userId);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }
    @Override
    public void run() {
        while (clientSocket.isConnected()){
            //TODO -> read file from server as a receiver client.
        }
    }

    public void sendFile(File fileToSend) {

        try {
            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            // Get the name of the file you want to send and store it in filename.
            String fileName = fileToSend.getName();
            // Convert the name of the file into an array of bytes to be sent to the server.
            byte[] fileNameBytes = fileName.getBytes();
            // Create a byte array the size of the file so don't send too little or too much data to the server.
            byte[] fileBytes = new byte[(int) fileToSend.length()];
            // Put the contents of the file into the array of bytes to be sent so these bytes can be sent to the server.
            fileInputStream.read(fileBytes);
            // Send the length of the name of the file so server knows when to stop reading.
            dataOutputStream.writeInt(fileNameBytes.length);
            // Send the file name.
            dataOutputStream.write(fileNameBytes);
            // Send the length of the byte array so the server knows when to stop reading.
            dataOutputStream.writeInt(fileBytes.length);
            // Send the actual file.
            dataOutputStream.write(fileBytes);
            closeResources();
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }

    private void closeResources() {
        try {
            //this.socket.close();
            if (clientSocket != null)
                clientSocket.close();
            if (dataInputStream != null)
                this.dataInputStream.close();
            if (dataOutputStream != null)
                this.dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

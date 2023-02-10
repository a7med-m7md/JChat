package Client.network;

import utils.Constants;

import java.io.*;
import java.net.Socket;

public class FileService {
    String filePath;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;
    Socket socket;
    public FileService(String filePath){
        this.filePath = filePath;
        //startConnection();
    }

    private void startConnection() {
        try {
            socket = new Socket("localhost", Constants.SOCKET_SERVER_PORT);
            dataInputStream = new DataInputStream(
                    socket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            System.out.println(
                    "Sending the File to the Server");
            //closeResources();
            //TODO -> set up file chooser to select the specific file, file chooser returns file object that can be used to retreive the absolute path of that file.

            sendFile("F:\\Adel\\ITI 9 Months\\Projects\\JChat\\Client\\src\\main\\resources\\files\\clienttest.txt");
        }
        catch (Exception e) {
            e.printStackTrace();
            closeResources();
        }
    }

    private void sendFile(String filePath) {
        int bytes = 0;
        //TODO-> test that method to take the file object as a parameter from controller
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            // Here we send the File to Server
            dataOutputStream.writeLong(file.length());
            // Here we  break file into chunks
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer))
                    != -1) {
                // Send the file to Server Socket
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }
            closeResources();
            // close the file here
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }

    private void closeResources(){
        try {
            //this.socket.close();
            this.dataOutputStream.close();
            this.dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

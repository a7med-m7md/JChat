package Client.network;

import utils.Constants;

import java.io.*;
import java.net.Socket;

public class FileService {
    String filePath;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;
    Socket socket;

    public FileService(String filePath) {
        this.filePath = filePath;
        startConnection();
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
            //sendFile(new File("F:\\Adel\\ITI 9 Months\\Projects\\JChat\\Client\\src\\main\\resources\\files\\clienttest.txt"));
            File file = new File("F:\\Adel\\ITI 9 Months\\Projects\\JChat\\Client\\src\\main\\resources\\files\\clienttest.txt");
            /*System.out.println(file.getName());
            byte[] fileNameBytes = file.getName().getBytes();
            System.out.println(fileNameBytes.length);*/
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            //closeResources();
        }
    }

    private void sendFile(File fileToSend) {

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


        /*int bytes = 0;
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
        }*/

    }

    private void closeResources() {
        try {
            //this.socket.close();
            if (dataInputStream != null)
                this.dataInputStream.close();
            if (dataOutputStream != null)
                this.dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

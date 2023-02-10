package Server.network;

import java.io.*;
import java.net.Socket;

public class FileTransferHandled implements Runnable{
    Socket clientSocket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    public FileTransferHandled(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        }catch (IOException exc){
            exc.printStackTrace();
            closeResources();
        }
    }

    private void closeResources() {
        try{
            if (clientSocket != null)
                clientSocket.close();
            if (dataOutputStream != null)
                dataOutputStream.close();
            if (dataInputStream != null)
                dataInputStream.close();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public static String getFileExtension(String fileName) {
        // Get the file type by using the last occurence of . (for example aboutMe.txt returns txt).
        // Will have issues with files like myFile.tar.gz.
        int i = fileName.lastIndexOf('.');
        // If there is an extension.
        if (i > 0) {
            // Set the extension to the extension of the filename.
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

}

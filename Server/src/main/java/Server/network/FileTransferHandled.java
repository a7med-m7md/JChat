package Server.network;

import model.FileEntity;

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
            //closeResources();
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

            try {
                // Wait for a client to connect and when they do create a socket to communicate with them.
                // Stream to receive data from the client through the socket.
                // Read the size of the file name so know when to stop reading.
                int fileNameLength = dataInputStream.readInt();
                System.out.println("aedl ->"+fileNameLength);
                if (fileNameLength > 0) {
                    // Byte array to hold name of file.
                    byte[] fileNameBytes = new byte[fileNameLength];
                    // Read from the input stream into the byte array.
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                    // Create the file name from the byte array.
                    String fileName = new String(fileNameBytes);
                    // Read how much data to expect for the actual content of the file.
                    int fileContentLength = dataInputStream.readInt();
                    // If the file exists.
                    if (fileContentLength > 0) {
                        // Array to hold the file data.
                        byte[] fileContentBytes = new byte[fileContentLength];
                        // Read from the input stream into the fileContentBytes array.
                        dataInputStream.readFully(fileContentBytes, 0, fileContentBytes.length);
                        //TODO -> then we need to create logic here which is used to store file in server or db then send it to the receiver client
                        //FileEntity newFileEntity = new FileEntity(id,fileName,fileContentBytes,getFileExtension(fileName));
                        File fileToDownload = new File(fileName);
                        fileToDownload.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                        fileOutputStream.write(fileContentBytes);
                        System.out.println("file needs to be stored in the server -> "+fileToDownload.getName());
                        fileOutputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                //System.out.println(e.getMessage());
            }

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

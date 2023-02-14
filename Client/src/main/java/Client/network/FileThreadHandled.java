package Client.network;

import model.FileEntity;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileThreadHandled implements Runnable {
    Socket clientSocket;
    int userId;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;

    public FileThreadHandled(Socket clientSocket, int userId) {
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
        while (clientSocket.isConnected()) {
            //TODO -> read file from server as a receiver client.
            try {
                //TODO -> read flag string or int to stop client working here
                //TODO -> handle in server that client is removed from hashmap
                boolean isStop = false;
                if (isStop){
                    closeResources();
                    break;
                }
                int fileNameLength = dataInputStream.readInt();
                if (fileNameLength > 0) {
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName = new String(fileNameBytes);
                    long fileContentLength = dataInputStream.readLong();
                    if (fileContentLength > 0) {
                        int fileId = 10;
                        //TODO -> create path for the received file in client
                        String path = "F:\\testt"+"\\"+fileName+"."+getFileExtension(fileName);
                        File receivedLocalFile = new File(path);
                        receivedLocalFile.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(receivedLocalFile);
                        //TODO -> with unlimited size
                        int readBytes = 0;
                        byte[] buffer = new byte[4 * 1024];
                        while (fileContentLength > 0
                                && (readBytes = dataInputStream.read(
                                buffer, 0,
                                (int) Math.min(buffer.length, fileContentLength)))
                                != -1) {
                            // Here we write the file using write method
                            fileOutputStream.write(buffer, 0, readBytes);
                            fileContentLength -= readBytes;
                            //fileOutputStream.flush();
                        }
                        fileOutputStream.close();
                        System.out.println("success save local file");
/*
                        FileEntity fileEntity = new FileEntity(fileId, fileName, fileContentBytes, getFileExtension(fileName));
                        System.out.println("the received file name -> " + fileEntity.getName());*/
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                closeResources();
                break;
            }
        }
    }
    public void stopClient(){
        try {
            dataOutputStream.writeBoolean(true);
            dataOutputStream.flush();
            //closeResources();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void sendFile(File fileToSend) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            // Get the name of the file you want to send and store it in filename.
            String fileName = fileToSend.getName();
            // Convert the name of the file into an array of bytes to be sent to the server.
            byte[] fileNameBytes = fileName.getBytes();
            // Create a byte array the size of the file so don't send too little or too much data to the server.
            //byte[] fileBytes = new byte[(int) fileToSend.length()];
            // Put the contents of the file into the array of bytes to be sent so these bytes can be sent to the server.
            //fileInputStream.read(fileBytes);
            // Send the length of the name of the file so server knows when to stop reading.
            dataOutputStream.writeBoolean(false);
            dataOutputStream.writeInt(fileNameBytes.length);
            // Send the file name.
            dataOutputStream.write(fileNameBytes);
            // Send the length of the byte array so the server knows when to stop reading.
            dataOutputStream.writeLong(fileToSend.length());
            System.out.println("send operation in client side with fileName ->"+fileName);
            // Send the actual file.
            int readBytes = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((readBytes = fileInputStream.read(buffer))
                    != -1) {
                // Send the file to Server Socket//
                dataOutputStream.write(buffer, 0, readBytes);
                dataOutputStream.flush();
            }
            System.out.println("in client -> finish send file to server fileName ->"+fileName);
            //dataOutputStream.flush();
            //fileInputStream.close();
            //closeResources();
        } catch (IOException e) {
            e.printStackTrace();
            closeResources();
        }

    }

    public void closeResources() {
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

package Server.network.services.fileservice;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FileTransferHandled implements Runnable{
    Socket clientSocket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Map<Integer,Socket> clientsWithId = new HashMap<>();
    Socket userReceiverSocket;
    DataOutputStream receiverOutputStream;
    DataInputStream receiverInputStream;
    public FileTransferHandled(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            //get the user id and then store it in the map with the socket object
            int userId = dataInputStream.readInt();
            clientsWithId.put(userId,clientSocket);
        }catch (IOException exc){
            exc.printStackTrace();
            //closeResources();
        }
    }


    @Override
    public void run() {
            while (clientSocket.isConnected()){
                try {
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
                            /*File fileToDownload = new File(fileName);
                            fileToDownload.createNewFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                            fileOutputStream.write(fileContentBytes);
                            System.out.println("file needs to be stored in the server -> "+fileToDownload.getName());
                            fileOutputStream.close();*/
                            sendDownloadedFileToReceiver(fileNameLength,fileNameBytes,fileContentLength,fileContentBytes);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    closeResources(clientSocket,dataOutputStream,dataInputStream);
                    break;
                }
            }

    }

    private void sendDownloadedFileToReceiver(int fileNameLength, byte[] fileNameBytes, int fileContentLength, byte[] fileContentBytes) {
        try {
            //TODO -> get the user receiver id here and then iterate through the map to get the socket of that user
            //TODO -> we can get user receiver id from stream of the fileToSend
            int userReceiverId = 20;
            userReceiverSocket = clientsWithId.get(userReceiverId);
            receiverOutputStream = new DataOutputStream(userReceiverSocket.getOutputStream());
            receiverInputStream = new DataInputStream(userReceiverSocket.getInputStream());
            if (userReceiverSocket.isConnected()){
                receiverOutputStream.writeInt(fileNameLength);
                receiverOutputStream.write(fileNameBytes);
                receiverOutputStream.writeInt(fileContentLength);
                receiverOutputStream.write(fileContentBytes);
            }else{
                System.out.println("the receiver user is not connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeResources(userReceiverSocket,receiverOutputStream,receiverInputStream);
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

    public void closeResources(Socket socket,DataOutputStream doutStream, DataInputStream dinStream){
        try {
            if (socket != null)
                socket.close();
            if (doutStream != null)
                doutStream.close();
            if (dinStream != null)
                dinStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

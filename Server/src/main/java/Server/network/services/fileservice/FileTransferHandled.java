package Server.network.services.fileservice;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FileTransferHandled implements Runnable{
    Socket clientSocket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    static Map<Integer,Socket> clientsWithId = new HashMap<>();
    Socket userReceiverSocket;
    DataOutputStream receiverOutputStream;
    DataInputStream receiverInputStream;
    int userId ;
    public FileTransferHandled(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            //get the user id and then store it in the map with the socket object
            this.userId = dataInputStream.readInt();
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
                    boolean isStop = dataInputStream.readBoolean();
                    if (isStop)
                    {
                        unRegister(userId);
                        closeResources(clientSocket,dataOutputStream,dataInputStream);
                        break;
                    }
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
                            /*int readBytes = 0;
                            long size
                                    = dataInputStream.readLong(); // read file size
                            byte[] buffer = new byte[4 * 1024];
                            while (size > 0
                                    && (readBytes = dataInputStream.read(
                                    buffer, 0,
                                    (int)Math.min(buffer.length, size)))
                                    != -1) {
                                // Here we write the file using write method
                                fileOutputStream.write(buffer, 0, bytes);
                                size -= bytes; // read upto file size
                            }*/
                            System.out.println("received file in server successfully...");
                            sendDownloadedFileToReceiver(fileName,fileNameLength,fileNameBytes,fileContentLength,dataInputStream);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    closeResources(clientSocket,dataOutputStream,dataInputStream);
                    break;
                }
            }

    }

    private void sendDownloadedFileToReceiver(String fileName,int fileNameLength, byte[] fileNameBytes, long fileContentLength, DataInputStream senderDataInputStream) {
        try {
            //TODO -> get the user receiver id here and then iterate through the map to get the socket of that user
            //TODO -> we can get user receiver id from stream of the fileToSend
            System.out.println("start send file from server to client... filename ->"+fileName);
            int userReceiverId = 11;
            userReceiverSocket = clientsWithId.get(userReceiverId);
            receiverOutputStream = new DataOutputStream(userReceiverSocket.getOutputStream());
            receiverInputStream = new DataInputStream(userReceiverSocket.getInputStream());
            if (userReceiverSocket.isConnected()){
                receiverOutputStream.writeInt(fileNameLength);
                receiverOutputStream.write(fileNameBytes);
                receiverOutputStream.writeLong(fileContentLength);
                int readBytes = 0;
                byte[] buffer = new byte[4 * 1024];
                int cntr =0;
                while (fileContentLength > 0 &&(readBytes = senderDataInputStream.read(buffer))
                        != -1) {
                    // Send the file to Server Socket
                    receiverOutputStream.write(buffer, 0, readBytes);
                    System.out.println("wrong loop in sending back ->"+cntr++);
                    fileContentLength -= readBytes;
                    //receiverOutputStream.flush();
                }
                //TODO -> not close
                //receiverOutputStream.close();
                //senderDataInputStream.close();
                //closeResources();
            }else{
                System.out.println("the receiver user is not connected");
            }
            System.out.println("in server -> succes send file back to the receiver fileName ->"+fileName);
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
    private void unRegister(int userId){
        clientsWithId.remove(userId);
    }

    public void closeResources(Socket socket,DataOutputStream doutStream, DataInputStream dinStream){
        try {
            //TODO -> unregister client
            //TODO -> handle when close the SERVER
            //unRegisterClient();
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

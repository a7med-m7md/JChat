package Server.business.services.filesocket;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThreadHandled implements Runnable {
    Socket clientSocket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    static Map<Long, Socket> clientsWithId = new HashMap<>();
    Socket userReceiverSocket;
    DataOutputStream receiverOutputStream;
    DataInputStream receiverInputStream;
    long userId;

    public ServerThreadHandled(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            //get the user id and then store it in the map with the socket object
            this.userId = dataInputStream.readLong();
            clientsWithId.put(userId, clientSocket);
        } catch (IOException exc) {
            exc.printStackTrace();
            //closeResources();
        }
    }


    @Override
    public void run() {
        while (!clientSocket.isClosed()) {
            try {
                if (dataInputStream == null)
                    System.out.println("data inout stream is null in server");
                boolean isStop = dataInputStream.readBoolean();
                System.out.println("isStop in receiver side -> "+isStop);
                if (isStop) {
                    unRegister(this.userId);
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.flush();
                    closeResources(clientSocket, dataOutputStream, dataInputStream);
                    break;
                }
                long receiverId = dataInputStream.readLong();
                System.out.println("receiver id in receiver side -> "+receiverId);
                int fileNameLength = dataInputStream.readInt();
                if (fileNameLength > 0) {
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName = new String(fileNameBytes);
                    long fileContentLength = dataInputStream.readLong();
                    // If the file exists.
                    if (fileContentLength > 0) {
                        System.out.println("received file in server successfully...");
                        sendDownloadedFileToReceiver(receiverId, fileName, fileNameLength, fileNameBytes, fileContentLength, dataInputStream);
                    }
                }
            }
            catch (IOException e) {
                closeResources(clientSocket, dataOutputStream, dataInputStream);
                break;
            }
        }

    }

    private void sendDownloadedFileToReceiver(long receiverId, String fileName, int fileNameLength, byte[] fileNameBytes, long fileContentLength, DataInputStream senderDataInputStream) {
        try {
            System.out.println("start send file from server to client... filename ->" + fileName);
            userReceiverSocket = clientsWithId.get(receiverId);
            receiverOutputStream = new DataOutputStream(userReceiverSocket.getOutputStream());
            receiverInputStream = new DataInputStream(userReceiverSocket.getInputStream());
            if (userReceiverSocket.isConnected()) {
                receiverOutputStream.writeBoolean(false);
                receiverOutputStream.writeInt(fileNameLength);
                receiverOutputStream.write(fileNameBytes);
                receiverOutputStream.writeLong(fileContentLength);
                int readBytes = 0;
                byte[] buffer = new byte[4 * 1024];
                while (fileContentLength > 0 && (readBytes = senderDataInputStream.read(buffer))
                        != -1) {
                    receiverOutputStream.write(buffer, 0, readBytes);
                    fileContentLength -= readBytes;
                }
            } else {
                System.out.println("the receiver user is not connected");
            }
            receiverOutputStream.flush();
            System.out.println("in server -> succes send file back to the receiver fileName ->" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            closeResources(userReceiverSocket, receiverOutputStream, receiverInputStream);
        }
    }

    public static void closeServer(){
        DataOutputStream currentDataOutStream;
        for (Socket currentSocket :
                clientsWithId.values()) {
            try {
                currentDataOutStream = new DataOutputStream(currentSocket.getOutputStream());
                currentDataOutStream.writeBoolean(true);
                currentDataOutStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clientsWithId = new HashMap<>();
        //TODO -> then close the server;
    }

    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        // If there is an extension.
        if (i > 0) {
            // Set the extension to the extension of the filename.
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

    private void unRegister(long userId) {
        clientsWithId.remove(userId);
        //closeResources(clientSocket,dataOutputStream,dataInputStream);
    }

    public void closeResources(Socket socket, DataOutputStream doutStream, DataInputStream dinStream) {
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

    /*public void disconnect() {
        for (Long tempUserId :
                clientsWithId.keySet()) {
            try{
                Socket currentUserSocket = clientsWithId.get(tempUserId);
                DataOutputStream currentDataOutStream = new DataOutputStream(currentUserSocket.getOutputStream());
                currentDataOutStream.writeBoolean(true);
                currentDataOutStream.flush();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
    }*/
}

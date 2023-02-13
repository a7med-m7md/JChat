package Client.network;

import utils.Constants;

import java.io.*;
import java.net.Socket;

public class FileService {
    String filePath;

    Socket clientSocket;
    int userId;
    File file;
    FileThreadHandled fileThreadHandled;
    public FileService(File file, int userId) {
        //startConnection();
        this.userId = userId;
        this.file = file;
    }

    public FileService(int userId){
        this.userId = userId;
    }
    public void startConnection() {
        try {
            clientSocket = new Socket("localhost", Constants.SOCKET_SERVER_PORT);
            //TODO -> get user id from the controller to send it to the server.
            //int currentUserId = 10;
            fileThreadHandled = new FileThreadHandled(clientSocket,userId);
            Thread th = new Thread(fileThreadHandled);
            th.start();
            System.out.println(
                    "Sending the File to the Server");
            //TODO -> set up file chooser to select the specific file, file chooser returns file object that can be used to retreive the absolute path of that file.
            //File file = new File("F:\\Adel\\ITI 9 Months\\Projects\\JChat\\Client\\src\\main\\resources\\files\\clienttest.txt");
        } catch (Exception e) {
            e.printStackTrace();
            //closeResources();
        }
    }

    public void sendFile(File fileToSend){
        fileThreadHandled.sendFile(fileToSend);
    }
}

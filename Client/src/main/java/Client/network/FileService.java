package Client.network;

import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileService {
    static FileService fileService;
    static List<ClientThreadHandled> listHandlers = new ArrayList<>();
    private static long userId;
    //FileThreadHandled fileThreadHandled;
    ExecutorService executor;
    private FileService(){
        //executor = Executors.newFixedThreadPool(10);
    }
    //TODO -> Use this factory method.
    public static synchronized FileService getInstance() {
        if (fileService == null)
            fileService = new FileService();
        return fileService;
    }
    //TODO -> used when user sign in
    public void startConnection(long userId) {
        try {
            Socket clientSocket = new Socket("localhost", Constants.SOCKET_SERVER_PORT);
            ClientThreadHandled clientThreadHandled = new ClientThreadHandled(clientSocket,userId);
            listHandlers.add(clientThreadHandled);
//            Future<ClientThreadHandled> future = (Future<ClientThreadHandled>) executor.submit(clientThreadHandled);
            this.userId = userId;
            Thread th = new Thread(clientThreadHandled);
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
            //closeResources();
        }
    }

    public void stopClient() {
        System.out.println("start close the client from stop client innnnn file service");
        //this.fileThreadHandled.stopClient();
        ClientThreadHandled targerHandler = null;
        for (ClientThreadHandled temp :
                listHandlers) {
            if (temp.userId == this.userId)
                targerHandler = temp;
        }
        listHandlers.remove(targerHandler);
        targerHandler.stopClient();
    }
    //TODO -> used in chat when user click on file choose
    public void sendFile(File fileToSend, long receiverId) {
        ClientThreadHandled targerHandler = null;
        for (ClientThreadHandled temp :
                listHandlers) {
            if (temp.userId == this.userId)
                targerHandler = temp;
        }
        targerHandler.sendFile(fileToSend,receiverId);
    }
}

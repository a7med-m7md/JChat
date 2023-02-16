package Client.network.services.filesocket;

import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class FileService {
    static FileService fileService;
    static Map<Long, ClientThreadHandled> listHandlers = new HashMap<>();
    private static long userId;
    ExecutorService executor;

    private FileService() {
    }

    //TODO -> Use this factory method.
    public static synchronized FileService getInstance() {
        if (fileService == null) fileService = new FileService();
        return fileService;
    }

    //TODO -> used when user sign in
    public void startConnection(long userId) {
        try {
            System.out.println("Client starts on file socket server");
            Socket clientSocket = new Socket("localhost", Constants.SOCKET_SERVER_PORT);
            ClientThreadHandled clientThreadHandled = new ClientThreadHandled(clientSocket, userId);
            listHandlers.put(userId, clientThreadHandled);
            this.userId = userId;
            Thread th = new Thread(clientThreadHandled);
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopClient(long userId) {
        System.out.println("start close the client from stop client innnnn file service");
        ClientThreadHandled targerHandler = listHandlers.get(userId);
        listHandlers.remove(targerHandler);
        targerHandler.stopClient();
    }

    public void sendFile(File fileToSend, long senderId, long receiverId) {
        if (listHandlers.containsKey(receiverId)) {
            ClientThreadHandled targerHandler = listHandlers.get(senderId);
            targerHandler.sendFile(fileToSend, receiverId);
        }
    }

    public void sendFileToGroup(File fileToSend, long senderId, List<Long> groupIds) {
        for (Long checkId : groupIds) {
            if (!listHandlers.containsKey(checkId)) {
                listHandlers.remove(checkId);
            }
            ClientThreadHandled targetHandler = listHandlers.get(senderId);
            targetHandler.sendFileToGroup(fileToSend, groupIds);
        }
    }
}

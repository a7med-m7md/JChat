package services;

import model.MessageEntity;
import model.MessageGroupEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessagingService extends Remote {
    void sendMessage(MessageEntity msg) throws RemoteException;
    void sendGroupMessage(MessageGroupEntity msg) throws RemoteException;
}

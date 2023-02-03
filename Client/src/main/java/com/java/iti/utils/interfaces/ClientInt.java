package com.java.iti.utils.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {
    String receiveMSG(String mobNum, String msg) throws RemoteException;
}

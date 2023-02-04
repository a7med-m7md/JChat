package com.java.iti.network.interfaces;

import java.rmi.Remote;

public interface ClientInt extends Remote {
    String receiveMSG(String mobNum, String msg);
}
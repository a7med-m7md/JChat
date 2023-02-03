package com.java.iti.utils.interfaces;

import java.rmi.Remote;

public interface ClientInt extends Remote {
    String receiveMSG(String mobNum, String msg);
}
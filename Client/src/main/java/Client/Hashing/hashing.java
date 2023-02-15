package Client.Hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.*;
import java.math.*;
import java.net.*;
import java.io.*;

public class hashing {
    public String encryptString(String input)throws NoSuchAlgorithmException{

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger bigInt = new BigInteger(1,messageDigest);

        return bigInt.toString(16);
    }

    public static String Decrypt(String md5_hash) throws Exception {

        String api_key = "YOUR_VIP_KEY";
        URL md5online = new URL("https://www.md5online.org/api.php?d=1&p="+api_key+"&h="+md5_hash);
        BufferedReader in = new BufferedReader(new InputStreamReader(md5online.openStream()));

        String result = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result = result+inputLine;
        in.close();

        return result;
    }

    public static void main(String[] args) throws Exception {

        hashing encryptor = new hashing();

        String password = "monkey1234";

        String encrpt= encryptor.encryptString(password);
        System.out.println(encrpt);
        String pass= encryptor.Decrypt(encrpt);

       // System.out.println(pass);

    }
}

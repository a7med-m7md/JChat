package Client.Hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashing {
    public String encryptString(String input)throws NoSuchAlgorithmException{

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger bigInt = new BigInteger(1,messageDigest);

        return bigInt.toString(16);
    }

    public static void main(String[] args)throws NoSuchAlgorithmException{

        hashing encryptor = new hashing();

        String password = "monkey1234";

        System.out.println(encryptor.encryptString(password));

    }
}

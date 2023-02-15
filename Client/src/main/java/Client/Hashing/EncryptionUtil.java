package Client.Hashing;


import Client.Main;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class EncryptionUtil {

    private static Properties props = loadProps();
    private static final String SECRET_KEY = props.getProperty("ENCRYPTION_KEY");
    private static final String TRANSFORMATION = props.getProperty("TRANSFORMATION");
    private static final String ENCRYPTION_ALGORITHM = props.getProperty("ENCRYPTION_ALGORITHM");


    public static String encrypt (String data) {
        byte[] encryptedValue = null;

        try {
            Cipher encryptionCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPTION_ALGORITHM);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = encryptionCipher.doFinal(data.getBytes());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public static String decrypt(String data) {
        byte[] decryptedValue = null;

        try {
            Cipher decryptionCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPTION_ALGORITHM);
            decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = decryptionCipher.doFinal(Base64.getDecoder().decode(data));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
        }

        return new String(decryptedValue);
    }

    private static Properties loadProps(){
        Properties properties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("security.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
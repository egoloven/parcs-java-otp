import java.security.SecureRandom;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import java.util.ArrayList;
import parcs.*;

public class OTP implements AM {
    public void run(AMInfo info) {

        var data = (List<DataEnc>) info.parent.readObject();

        byte[][] encryptedMessages = new byte[data.size()][];
        var res = new LinkedList<String>();
        for (int i = 0; i < data.size(); ++i) {
            var message = data.get(i).plaintext;
            var key = data.get(i).key;
            encryptedMessages[i] = encrypt(message, key);
            System.out.println("Encrypted message " + (i + 1) + ": " + Base64.getEncoder().encodeToString(encryptedMessages[i]));
            res.add(Base64.getEncoder().encodeToString(encryptedMessages[i]));
        }

        // Decrypt messages
//        startTime = System.nanoTime();
//        for (int i = 0; i < messages.size(); i++) {
//            String decryptedMessage = decrypt(encryptedMessages[i], keys[i]);
//            System.out.println("Decrypted message " + (i + 1) + ": " + decryptedMessage);
//        }
//        endTime = System.nanoTime();
//        System.out.println("Time taken for decryption: " + (endTime - startTime) + " nanoseconds");

        info.parent.write(res);
    }

    public static List<String> readMessagesFromFile(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] encrypt(String message, byte[] key) {
        byte[] messageBytes = message.getBytes();
        byte[] encryptedMessage = new byte[messageBytes.length];

        for (int i = 0; i < messageBytes.length; i++) {
            encryptedMessage[i] = (byte) (messageBytes[i] ^ key[i]);
        }

        return encryptedMessage;
    }

    public static String decrypt(byte[] encryptedMessage, byte[] key) {
        byte[] decryptedMessage = new byte[encryptedMessage.length];

        for (int i = 0; i < encryptedMessage.length; i++) {
            decryptedMessage[i] = (byte) (encryptedMessage[i] ^ key[i]);
        }

        return new String(decryptedMessage);
    }
}

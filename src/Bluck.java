import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.*;
import parcs.*;

public class Bluck {

    private final static int WORKERS = 2;

    private final static String MESSAGES_SPATH = "messages.txt";

    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("OTP.jar");

//        Import data
        List<String> messages = readMessagesFromFile(MESSAGES_SPATH);
        List<List<DataEnc>> data = prepareData(messages);


        AMInfo info = new AMInfo(curtask, null);
        var channels = new LinkedList<channel>();
        System.out.println("Waiting for result...");
        for (var data_item : data) {
            var p = info.createPoint();
            var c = p.createChannel();
            p.execute("OTP");
            c.write((Serializable) data_item);
            channels.add(c);
        }

        List<List<String>> result = new LinkedList<List<String>>();
        long startTime = System.nanoTime();
        for (var c: channels) {
            var res = (List<String>) c.readObject();
            result.add(res);
        }
        for (var l: result) {
            for (var s: l) {
                System.out.println(s);
            }
        }
        long endTime = System.nanoTime();
        //System.out.println("Time taken for encryption: " + (endTime - startTime) + " nanoseconds");
        curtask.end();
    }

    public static List<List<DataEnc>> prepareData(List<String> messages) {
        var result = new LinkedList<List<DataEnc>>();
        for (int j = 0; j < WORKERS; ++j) {
            var temp_result = new LinkedList<DataEnc>();
            for (int i = j * (messages.size() / WORKERS); i < (messages.size() / WORKERS) * (j + 1); ++i) {
                var data = new DataEnc(messages.get(i), generateKey(messages.get(i).length()));
                temp_result.add(data);
            }
            result.add(temp_result);
        }

        return result;
    }

    public static byte[] generateKey(int length) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[length];
        random.nextBytes(key);
        return key;
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
}

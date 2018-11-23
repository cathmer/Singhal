package mutual_exclusion;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Reader {

    public HashMap<Integer, String> readConfiguration() {
        HashMap<Integer, String> componentsMap = new HashMap<>();

        File file = new File("./processes.conf");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                int processID = sc.nextInt();
                String host = sc.next();
                componentsMap.put(processID, host);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return componentsMap;
    }
}

package client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileManager {

    private static FileManager instance = null;

    private FileManager() {

    }

    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

  public static FileManager getInstance() {
    if (instance == null)
      instance = new FileManager();
      return instance;
  }
}

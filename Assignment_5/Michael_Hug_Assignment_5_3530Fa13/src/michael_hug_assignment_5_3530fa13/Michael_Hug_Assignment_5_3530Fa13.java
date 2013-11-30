package michael_hug_assignment_5_3530fa13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Michael_Hug_Assignment_5_3530Fa13 {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(
    "http://java.com/applet/JreCurrentVersion2.txt").openStream()))) {
  String fullVersion = br.readLine();
  String version = fullVersion.split("_")[0];
  String revision = fullVersion.split("_")[1];
  System.out.println("Version " + version + " revision " + revision);
} catch (IOException e) {
  // handle properly
}
    }
    
}

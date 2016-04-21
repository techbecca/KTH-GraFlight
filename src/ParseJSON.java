/**
 * Created by Aiman on 21/04/16.
 */
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ParseJSON {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("json/fact.ce.cc.be.f.json");
        FileReader fileRead = new FileReader(file);
        Scanner scan = new Scanner(fileRead);
        String jsonString = "";
        while(scan.hasNext()){
            jsonString += scan.nextLine();
        }
        JSONObject jObj = new JSONObject(jsonString);
        System.out.println(jObj.get("inputs"));
    }

}

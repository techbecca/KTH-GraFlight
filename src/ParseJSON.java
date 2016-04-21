/**
 * Created by Aiman on 21/04/16.
 */
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseJSON {
    public static void main(String[] args) throws FileNotFoundException {
        //Läser in en specifik JSON-fil och sparar i en File-klass
        //I framtiden borde file ta en inparameter istället för hårdkodad path
        File file = new File("json/fact.ce.cc.be.f.json");
        FileReader fileRead = new FileReader(file);
        Scanner scan = new Scanner(fileRead);
        //Läser in JSON-filen till en enda lång sträng
        String jsonString = "";
        while(scan.hasNext()){
            jsonString += scan.nextLine();
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");

        //Omvandlar från JSON-formatet till Edge-klassen
        ArrayList<Edge> edgeList = new ArrayList<>();
        for(int i = 0; i < edges.length(); i++){
            JSONArray jsonEdge = edges.getJSONArray(i);
            int source = jsonEdge.getInt(0);
            int target = jsonEdge.getInt(1);
            String etype = jsonEdge.getJSONObject(2).getString("etype");
            edgeList.add(new Edge(source, target, etype));
        }

        for(Edge e : edgeList){
            System.out.println(e);
        }
    }

}

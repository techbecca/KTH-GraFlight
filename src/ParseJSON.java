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
        File file = new File("json/fact.ce.cc.be.f.json");
        FileReader fileRead = new FileReader(file);
        Scanner scan = new Scanner(fileRead);
        String jsonString = "";
        while(scan.hasNext()){
            jsonString += scan.nextLine();
        }
        JSONObject jObj = new JSONObject(jsonString);
        JSONObject graph = jObj.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");
        System.out.println(edges.getJSONArray(0));

        ArrayList<Edge> edgeList = new ArrayList<>();
        for(int i = 0; i < edges.length(); i++){
            JSONArray JSONedge = edges.getJSONArray(i);
            int source = JSONedge.getInt(0);
            int target = JSONedge.getInt(1);
            String etype = JSONedge.getJSONObject(2).getString("etype");
            edgeList.add(new Edge(source, target, etype));
        }

        for(Edge e : edgeList){
            System.out.println(e);
        }
    }

}

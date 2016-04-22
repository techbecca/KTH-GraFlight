/**
 * Created by Aiman on 21/04/16.
 */
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseJSONf {

    //Den här koden är bara här i testningssyfte
    public static void main(String args[]) throws FileNotFoundException {
        parse("json/fact.ce.cc.be.f.json");
    }

    public static RawGraphDataF parse(String path) throws FileNotFoundException {
        //Läser in en specifik JSON-fil och sparar i en File-klass
        //I framtiden borde file ta en inparameter istället för hårdkodad path
        File file = new File(path);
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
        JSONArray nodes = graph.getJSONArray("nodes");

        JSONArray inputArray = jsonObject.getJSONArray("inputs");
        int[] inputs = new int[inputArray.length()];
        for(int i = 0; i < inputArray.length(); i++){
            inputs[i] = inputArray.getInt(i);
        }
        String functionName = jsonObject.getString("name");

        //Omvandlar från JSON-formatet till Edge-klassen
        ArrayList<Edge> edgeList = new ArrayList<>();
        for(int i = 0; i < edges.length(); i++){
            JSONArray jsonEdge = edges.getJSONArray(i);
            int source = jsonEdge.getInt(0);
            int target = jsonEdge.getInt(1);
            String etype = jsonEdge.getJSONObject(2).getString("etype");
            edgeList.add(new Edge(source, target, etype));
        }

        ArrayList<Node> nodeList = new ArrayList<>();
        for(int i = 0; i < nodes.length(); i++){
            JSONArray jsonNode = nodes.getJSONArray(i);
            int id = jsonNode.getInt(0);
            String ntype = jsonNode.getJSONObject(1).getJSONObject("type").getString("ntype");
            nodeList.add(new Node(id,ntype));
        }

        return new RawGraphDataF(functionName, inputs, nodeList, edgeList);

    }

}

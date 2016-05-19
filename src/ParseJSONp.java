import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.*;

/**
 * This class parses the pattern JSON file and includes the matches from it in the "Match" class
 * @since 2016-05-19
 */
public class ParseJSONp {

	/**
	 * This method parses the pattern JSON file, includes the matches and includes them in the "Match" class.
	 * @param file This is the JSON file to be parsed  
	 * @return The list of matches
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Match> parsep(File file) throws FileNotFoundException {

		//Read an entire JSON file
		String json = new Scanner(file).useDelimiter("\\A").next();
		JSONObject jObj = new JSONObject(json);				

		JSONArray Pattern = jObj.getJSONArray("match-data");

		ArrayList<Match> matchList = new ArrayList<>();

		for(int i = 0; i < Pattern.length(); i++){

			JSONArray NodeToNodeMatches = Pattern.getJSONObject(i).getJSONArray("match");

			int[] GraphNodes = new int[NodeToNodeMatches.length()];

			int InstrId = Pattern.getJSONObject(i).getInt("instr-id");
			int PatternId = Pattern.getJSONObject(i).getInt("pattern-id");
			int MatchId = Pattern.getJSONObject(i).getInt("match-id");
			
			for(int k = 0; k < NodeToNodeMatches.length(); k++){

				JSONArray NodeToNodeMatch = NodeToNodeMatches.getJSONArray(k);
				GraphNodes[k] = NodeToNodeMatch.getInt(0);
			}
			matchList.add(new Match(GraphNodes, PatternId, InstrId, MatchId));
		}
		return matchList;
	}
}

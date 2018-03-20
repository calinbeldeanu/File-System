package Date;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ReadData {
	public static int lines;
	public static String[] fileLines;
	public static String[] tokens;
	public static int nTokens;

	public static void Read(String input) throws IOException {
		BufferedReader linesReader = new BufferedReader(new FileReader(input));
		while (linesReader.readLine() != null) {
			lines++;
		}
		linesReader.close();
		fileLines = new String[lines];

		BufferedReader reader = new BufferedReader(new FileReader(input));

		String CurrentLine;
		int i = 0;
		while ((CurrentLine = reader.readLine()) != null) {
			fileLines[i] = CurrentLine;
			i++;
		}
		reader.close();
	}

	public static String[] InputArray(String something) {
		StringTokenizer st = new StringTokenizer(something);
		nTokens = st.countTokens();
		tokens = new String[nTokens];
		for (int i = 0; i < nTokens; i++) {
			tokens[i] = st.nextToken();
		}
		return tokens;

	}
}

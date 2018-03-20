
import java.io.IOException;
import java.util.StringTokenizer;
import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;
import commands.Command;
import commands.CommandFactory;

public class Test {

	public static void main(String[] args) throws IOException {
		CommandFactory commandFactory = new CommandFactory();
		Information system = new Information();
		ReadData.Read(args[0]);
		String[] duplicat = new String[ReadData.lines];
		int nTokens;
		String[][] dataIn = new String[100][100];
		String token;
		Command[] command = new Command[ReadData.lines];
		int i;
		for (i = 0; i < ReadData.lines; i++) {
			int j = 0;
			duplicat[i] = ReadData.fileLines[i];
			StringTokenizer st = new StringTokenizer(ReadData.fileLines[i]);
			nTokens = st.countTokens();
			token = String.valueOf(nTokens);
			dataIn[i][j] = token;
			for (j = 0; j < nTokens; j++) {
				dataIn[i][j + 1] = st.nextToken();
			}

		}
		for (i = 0; i < ReadData.lines; i++) {

			int j = 0;
			command[i] = commandFactory.getCommand(dataIn[i][j + 1]);
			command[i].run(ReadData.fileLines[i], duplicat[i], system);

		}
		System.out.println(system.root.name + " " + system.root.rights + " "
				+ system.root.owner);

		for (Entity ceva : system.root.getSubordinates()) {
			System.out.print("\t");
			System.out
					.println(ceva.name + " " + ceva.rights + " " + ceva.owner);
			if (!ceva.subordinates.isEmpty()) {
				for (Entity ceva1 : ceva.getSubordinates()) {
					System.out.print("\t");
					System.out.print("\t");
					System.out.println(ceva1.name + " " + ceva1.rights + " "
							+ ceva1.owner);
					if (!ceva1.subordinates.isEmpty()) {
						for (Entity ceva2 : ceva1.getSubordinates()) {
							System.out.print("\t");
							System.out.print("\t");
							System.out.print("\t");
							System.out.println(ceva2.name + " " + ceva2.rights
									+ " " + ceva2.owner);
						}
					}
				}
			}
		}
	}
}
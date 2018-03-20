/**
 * Clasa in care implementez comanda rmdir
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Rmdir implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (!array[1].contains("/")) {
			if (system.curUser.equals("root")
					|| system.curLocation.owner.equals(system.curUser)) {
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println("-2: " + helper + ": No such directory");
					return;
				} else if (array[1].equals("..") || array[1].equals(".")) {
					System.out.println("-13: " + helper + ": Cannot delete parent or current directory");
					return;
				}
				int i = 0;
				for (Entity ent : system.curLocation.getSubordinates()) {
					if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")) {
						System.out.println("-3: " + helper + ": Not a directory");
						return;
					} else if (ent.name.equals(array[1])
							&& ent.getSubordinates().isEmpty()) {
						ent.parent.getSubordinates().remove(i);
						return;

					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size() - 1).name) {
						System.out.println("-2: " + helper + ": No such directory");
						return;

					} else if (ent.name.equals(array[1])
							&& !ent.getSubordinates().isEmpty()) {
						System.out.println("-14: " + helper + ": Non empty directory");
						return;
					}
					i++;

				}
			} else {
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println("-2: " + helper + ": No such directory");
					return;
				} else if (array[1].equals("..") || array[1].equals(".")) {
					System.out.println("-13: " + helper + ": Cannot delete parent or current directory");
					return;
				}
				int i;
				for (Entity ent : system.curLocation.getSubordinates()) {
					i = 0;
					if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")) {
						System.out.println("-3: " + helper + ": Not a directory");
						return;
					} else if (ent.name.equals(array[1])
							&& ent.getSubordinates().isEmpty()
							&& system.curLocation.rights.substring(5, 6).equals("w")) {
						ent.parent.getSubordinates().remove(i);
						return;

					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()- 1).name) {
						System.out.println("-2: " + helper + ": No such directory");
						return;

					} else if (ent.name.equals(array[1])
							&& !ent.getSubordinates().isEmpty()) {
						System.out.println("-14: " + helper + ": Non empty directory");
						return;
					} else if (ent.name.equals(array[1])
							&& !system.curLocation.rights.substring(5, 6).equals("w")) {
						System.out.println("-5: " + helper + ": No rights to write");
						return;
					}
					i++;
				}

			}

		}
		if (array[1].contains("/")) {
			StringTokenizer st = new StringTokenizer(array[1], "/");
			CommandFactory comm = new CommandFactory();
			Entity retinere = new Entity(true, input, input, input);
			Entity verificare1 = new Entity(true, input, input, input);
			retinere = system.curLocation;
			int tok = st.countTokens();
			Command[] repeta = new Command[tok];
			String[] tokens = new String[tok];
			String[][] data = new String[tok][tok];
			int nTokens;
			for (int i = 0; i < tok; i++) {
				tokens[i] = "rmdir " + st.nextToken();
			}
			if (array[1].startsWith("/")) {
				system.curLocation = system.root;
			}
			if (tok == 1) {
				repeta[0] = comm.getCommand("rmdir");
				repeta[0].run(tokens[0], helper, system);
				system.curLocation = retinere;
				return;
			} else {
				for (int i = 0; i < tok; i++) {
					int j = 0;
					StringTokenizer bau = new StringTokenizer(tokens[i]);
					nTokens = bau.countTokens();
					for (j = 0; j < nTokens; j++) {
						data[i][j] = bau.nextToken();
					}
				}
				if (array[1].startsWith("/")) {
					system.curLocation = system.root;
				}
				int i;
				for (i = 0; i < tok - 1; i++) {
					verificare1 = system.curLocation;
					repeta[i] = comm.getCommand("cd");
					repeta[i].run(tokens[i], helper, system);
					if ((system.curLocation == verificare1
							&& !data[i][1].equals(".."))) {
						if ((system.curLocation == verificare1 && !data[i][1].equals(".")))
							break;
					}

				}

				if (system.curLocation.name.equals(data[tok - 2][tok - 1])) {
					repeta[tok - 1] = comm.getCommand("rmdir");
					repeta[tok - 1].run(tokens[tok - 1], helper, system);
					system.curLocation = retinere;
					return;
				} else if (system.curLocation.name
						.equals(data[tok - 2][tok - 3])) {

					repeta[tok - 1] = comm.getCommand("rmdir");
					repeta[tok - 1].run(tokens[tok - 1], helper, system);
					system.curLocation = retinere;
					return;
				} else {
					return;
				}

			}
		}
	}

}

/**
 * Clasa in care implementez comanda cd
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Cd implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (!array[1].contains("/")) {

			if (array[1].equals("..")) {
				system.curLocation = system.curLocation.parent;
				return;
			}
			if (array[1].equals(".")) {
				system.curLocation = system.curLocation;
				return;
			}
			if (system.curLocation.subordinates.isEmpty()) {
				System.out.println("-2: " + helper + ": No such directory");
			} else {
				if (system.curUser.equals("root")) {

					for (Entity ent : system.curLocation.subordinates) {
						if (ent.name.equals(array[1])) {
							system.curLocation = ent;
							return;
						} else if (ent.name.equals(array[1])
								&& ent.rights.startsWith("f")) {
							System.out.println(
									"-3: " + helper + ": Not a directory");
							return;

						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates
												.size() - 1).name) {

							System.out.println(
									"-2: " + helper + ": No such directory");
							return;
						}
					}
				} else if (system.curLocation.owner.equals(system.curUser)) {

					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println(
								"-2: " + helper + ": No such directory");
					} else {

						for (Entity ent : system.curLocation.subordinates) {
							if (ent.name.equals(array[1])
									&& ent.rights.startsWith("d")) {
								system.curLocation = ent;
								return;
							}
							if (ent.name.equals(array[1])
									&& ent.rights.startsWith("f")) {
								System.out.println(
										"-3: " + helper + ": Not a directory");
								return;
							} else if (!ent.name.equals(array[1])
									&& ent.name == system.curLocation.subordinates
											.get(system.curLocation.subordinates
													.size() - 1).name) {
								System.out.println("-2: " + helper
										+ ": No such directory");
								return;
							}
						}
					}
				} else {
					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println(
								"-2: " + helper + ": No such directory");
					} else {

						for (Entity ent : system.curLocation.subordinates) {
							if (ent.name.equals(array[1])
									&& ent.rights.endsWith("x")) {
								system.curLocation = ent;
								return;
							} else if (ent.name.equals(array[1])
									&& system.curUser.equals(ent.owner)) {
								system.curLocation = ent;
								return;

							} else if (ent.name.equals(array[1])
									&& ent.rights.startsWith("f")) {
								System.out.println(
										"-3: " + helper + ": Not a directory");
								return;
							} else if (ent.name.equals(array[1])
									&& !ent.rights.endsWith("x")) {
								System.out.println("-6: " + helper
										+ ": No rights to execute");
								return;
							} else if (!ent.name.equals(array[1])
									&& ent.name == system.curLocation.subordinates
											.get(system.curLocation.subordinates
													.size() - 1).name) {
								System.out.println("-2: " + helper
										+ ": No such directory");
								return;
							}
						}
					}
				}
			}

		} else {
			Entity verificare = new Entity(true, input, input, input);
			Entity verificare1 = new Entity(true, input, input, input);
			CommandFactory comm = new CommandFactory();
			StringTokenizer st1 = new StringTokenizer(array[1], "/");
			int tok = st1.countTokens();
			Command[] doit = new Command[tok];
			String[] tokens = new String[tok];
			String[][] data = new String[tok][tok];
			int nTokens;
			String token;
			for (int i = 0; i < tok; i++) {
				tokens[i] = "cd " + st1.nextToken();
			}
			for (int i = 0; i < tok; i++) {
				int j = 0;
				StringTokenizer st = new StringTokenizer(tokens[i]);
				nTokens = st.countTokens();
				token = String.valueOf(nTokens);
				data[i][j] = token;
				for (j = 0; j < nTokens; j++) {
					data[i][j] = st.nextToken();
				}
			}
			verificare1 = system.curLocation;
			if (array[1].startsWith("/")) {
				system.curLocation = system.root;
			}
			int i;
			for (i = 0; i < tok; i++) {
				verificare = system.curLocation;
				doit[i] = comm.getCommand("cd");
				doit[i].run(tokens[i], helper, system);
				if ((system.curLocation == verificare
						&& !data[i][1].equals(".."))) {
					if ((system.curLocation == verificare
							&& !data[i][1].equals("."))) {
						system.curLocation = verificare1;
						break;
					}
				}

			}

			return;

		}
	}
}

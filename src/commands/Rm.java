/**
 * Clasa in care implementez comanda rm si rm -r
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Rm implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (array.length == 2) {
			if (!array[1].contains("/")) {

				if (system.curUser.equals("root")
						|| system.curLocation.owner.equals(system.curUser)) {
					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println("-11: " + helper + ": No such file");
						return;
					}
					int i = 0;
					for (Entity ent : system.curLocation.getSubordinates()) {

						if (ent.name.equals(array[1])
								&& ent.rights.startsWith("d")) {
							System.out.println("-1:" + " " + helper + ": Is a directory");
							return;
						} else if (ent.name.equals(array[1])
								&& ent.rights.startsWith("f")) {

							system.curLocation.subordinates.remove(i);
							return;
						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name) {
							System.out.println("-11: " + helper + ": No such file");
							return;

						}
						i++;

					}
				} else {
					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println("-11: " + helper + ": No such file");
						return;
					}
					int i;
					for (Entity ent : system.curLocation.getSubordinates()) {
						i = 0;
						if (ent.name.equals(array[1])
								&& ent.rights.startsWith("d")) {
							System.out.println("-1:" + " " + helper + ": Is a directory");
							return;
						} else if (ent.name.equals(array[1])
								&& ent.rights.startsWith("f")
								&& system.curLocation.rights.substring(5, 6).equals("w")) {
							system.curLocation.subordinates.remove(i);
							return;
						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name
								&& system.curLocation.rights.substring(5, 6).equals("w")) {
							System.out.println("-11: " + helper + ": No such file");
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
				Command[] doit = new Command[tok];
				String[] tokens = new String[tok];
				String[][] data = new String[tok][tok];
				int nTokens;
				for (int i = 0; i < tok; i++) {
					tokens[i] = "rm " + st.nextToken();
				}
				if (array[1].startsWith("/")) {
					system.curLocation = system.root;
				}
				if (tok == 1) {
					doit[0] = comm.getCommand("rm");
					doit[0].run(tokens[0], helper, system);
					system.curLocation = retinere;
					return;
				} else {
					for (int i = 0; i < tok; i++) {
						int j = 0;
						StringTokenizer st1 = new StringTokenizer(tokens[i]);
						nTokens = st1.countTokens();
						for (j = 0; j < nTokens; j++) {
							data[i][j] = st1.nextToken();
						}
					}
					if (array[1].startsWith("/")) {
						system.curLocation = system.root;
					}
					int i;
					for (i = 0; i < tok - 1; i++) {
						verificare1 = system.curLocation;
						doit[i] = comm.getCommand("cd");
						doit[i].run(tokens[i], helper, system);
						if ((system.curLocation == verificare1
								&& !data[i][1].equals(".."))) {
							if ((system.curLocation == verificare1
									&& !data[i][1].equals(".")))
								break;
						}
					}
					if (system.curLocation.name
							.equals(data[tok - 2][tok - 2])) {

						doit[tok - 1] = comm.getCommand("rm");
						doit[tok - 1].run(tokens[tok - 1], helper, system);
						system.curLocation = retinere;
						return;
					} else {
						return;
					}

				}

			}
		} else if (array.length == 3) {
			if (!array[2].contains("/")) {
				if (system.curUser.equals("root")
						|| system.curLocation.owner.equals(system.curUser)) {
					if (array[2].equals("..") || array[2].equals(".")) {
						System.out.println("-13: " + helper
								+ ": Cannot delete parent or current directory");
						return;
					}
					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println("-12: " + helper
								+ ": No such file or directory");
						return;
					}

					int i = 0;
					for (Entity ent : system.curLocation.getSubordinates()) {
						if (array[2].equals("..") || array[2].equals(".")) {
							System.out.println("-13: " + helper + ": Cannot delete parent or current directory");
							return;
						} else if (ent.name.equals(array[2])) {
							system.curLocation.subordinates.remove(i);
							return;
						} else if (!ent.name.equals(array[2])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates	.size() - 1).name) {
							System.out.println("-12: " + helper + ": No such file or directory");
							return;

						}
						i++;

					}
				} else {
					if (system.curLocation.subordinates.isEmpty()) {
						System.out.println("-12: " + helper + ": No such file or directory");
						return;
					}
					if (array[2].equals("..") || array[2].equals(".")) {
						System.out.println("-13: " + helper + ": Cannot delete parent or current directory");
						return;
					}

					int i = 0;
					for (Entity ent : system.curLocation.getSubordinates()) {
						if (ent.name.equals(array[2])
								&& system.curLocation.rights.substring(5, 6).equals("w")) {
							system.curLocation.subordinates.remove(i);
							return;
						} else if (!ent.name.equals(array[2]) && ent.name
								.equals(system.curLocation.subordinates.get(
										system.curLocation.subordinates.size()- 1).name)) {
							System.out.println("-12: " + helper	+ ": No such file or directory");
							return;

						} else if (ent.name.equals(array[2])
								&& !system.curLocation.rights.substring(5, 6).equals("w")) {
							System.out.println("-5: " + helper + ": No rights to write");
							return;
						}

						i++;
					}

				}

			}

			if (array[2].equals("/")) {
				System.out.println("-13: " + helper + ": Cannot delete parent or current directory");
				return;
			}
			if (array[2].contains("/")) {
				StringTokenizer st = new StringTokenizer(array[2], "/");
				CommandFactory comm = new CommandFactory();
				Entity retinere = new Entity(true, input, input, input);
				Entity verificare1 = new Entity(true, input, input, input);
				retinere = system.curLocation;
				int tok = st.countTokens();
				Command[] doit = new Command[tok];
				String[] tokens = new String[tok];
				String[][] data = new String[tok + 1][tok + 1];
				int nTokens;
				if (array[2].startsWith("/")) {
					system.curLocation = system.root;
				}
				if (tok == 1) {
					for (int i = 0; i < tok; i++) {
						tokens[i] = "rm " + "-r " + st.nextToken();
					}
					doit[0] = comm.getCommand("rm");
					doit[0].run(tokens[0], helper, system);
					system.curLocation = retinere;
					return;
				} else {
					for (int i = 0; i < tok - 1; i++) {
						tokens[i] = "cd " + st.nextToken();
					}
					tokens[tok - 1] = "rm " + "-r " + st.nextToken();
					for (int i = 0; i < tok; i++) {
						int j = 0;
						StringTokenizer st1 = new StringTokenizer(tokens[i]);
						nTokens = st1.countTokens();
						for (j = 0; j < nTokens; j++) {
							data[i][j] = st1.nextToken();
						}
					}
					if (array[1].startsWith("/")) {
						system.curLocation = system.root;
					}
					int i;
					for (i = 0; i < tok - 1; i++) {
						verificare1 = system.curLocation;
						doit[i] = comm.getCommand("cd");
						doit[i].run(tokens[i], helper, system);
						if ((system.curLocation == verificare1
								&& !data[i][1].equals(".."))) {
							if ((system.curLocation == verificare1
									&& !data[i][1].equals(".")))
								break;
						}
					}
					if (system.curLocation.name.equals(data[tok - 2][tok - 1])
							|| retinere.parent.name
									.equals(system.curLocation.name)) {
						doit[tok - 1] = comm.getCommand("rm");
						doit[tok - 1].run(tokens[tok - 1], helper, system);
						system.curLocation = retinere;
						return;
					} else {
						system.curLocation = retinere;
						return;
					}

				}

			}
		}

	}
}

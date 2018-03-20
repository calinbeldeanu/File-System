/**
 * Clasa in care implementez comanda chmod
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Chmod implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (!array[2].contains("/")) {
			if (system.curUser.equals("root")) {
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println("-12: " + helper + ": No such file or directory");
					return;
				}
				for (Entity ent : system.curLocation.getSubordinates()) {

					if (ent.name.equals(array[2])) {
						if (array[1].equals("00")
								&& ent.rights.startsWith("d")) {
							ent.rights = "d------";
							return;
						} else if (array[1].equals("00")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "f------";
							return;
						} else if (array[1].equals("07")
								&& ent.rights.startsWith("d")) {
							ent.rights = "d---rwx";
							return;
						} else if (array[1].equals("07")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "f---rwx";
							return;
						}
						if (array[1].equals("71")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwx--x";
							return;
						} else if (array[1].equals("71")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwx--x";
							return;
						}
						if (array[1].equals("73")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwx-wx";
							return;
						} else if (array[1].equals("73")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwx-wx";
							return;
						}
						if (array[1].equals("74")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwxr--";
							return;
						} else if (array[1].equals("74")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwxr--";
							return;
						}
						if (array[1].equals("77")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwxrwx";
							return;
						} else if (array[1].equals("77")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwxrwx";
							return;
						}
					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()
											- 1).name) {
						System.out.println("-12: " + helper
								+ ": No such file or directory");
						return;
					}
				}
			} else if (!system.curUser.equals("root")) {
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println(
							"-12: " + helper + ": No such file or directory");
					return;
				}
				for (Entity ent : system.curLocation.getSubordinates()) {

					if (ent.name.equals(array[2])
							&& system.curUser.equals(ent.owner)) {

						if (array[1].equals("00")
								&& ent.rights.startsWith("d")) {
							ent.rights = "d------";
							return;
						} else if (array[1].equals("00")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "f------";
							return;
						} else if (array[1].equals("07")
								&& ent.rights.startsWith("d")) {
							ent.rights = "d---rwx";
							return;
						} else if (array[1].equals("07")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "f---rwx";
							return;
						}
						if (array[1].equals("71")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwx--x";
							return;
						} else if (array[1].equals("71")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwx--x";
							return;
						}
						if (array[1].equals("73")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwx-wx";
							return;
						} else if (array[1].equals("73")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwx-wx";
							return;
						}
						if (array[1].equals("74")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwxr--";
							return;
						} else if (array[1].equals("74")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwxr--";
							return;
						}
						if (array[1].equals("77")
								&& ent.rights.startsWith("d")) {
							ent.rights = "drwxrwx";
							return;
						} else if (array[1].equals("77")
								&& !ent.rights.startsWith("d")) {
							ent.rights = "frwxrwx";
							return;
						}
					} else if (!ent.name.equals(array[2])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()- 1).name) {
						System.out.println("-12: " + helper + ": No such file or directory");
						return;

					} else if (ent.name.equals(array[2])
							&& !system.curUser.equals(ent.owner)) {

						if (array[1].equals("00") && ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "d------";
							return;
						} else if (array[1].equals("00")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "f------";
							return;
						} else if (array[1].equals("07")
								&& ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "d---rwx";
							return;
						} else if (array[1].equals("07")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "f---rwx";
							return;
						}
						if (array[1].equals("71") && ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "drwx--x";
							return;
						} else if (array[1].equals("71")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "frwx--x";
							return;
						}
						if (array[1].equals("73") && ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "drwx-wx";
							return;
						} else if (array[1].equals("73")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "frwx-wx";
							return;
						}
						if (array[1].equals("74") && ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "drwxr--";
							return;
						} else if (array[1].equals("74")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "frwxr--";
							return;
						}
						if (array[1].equals("77") && ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "drwxrwx";
							return;
						} else if (array[1].equals("77")
								&& !ent.rights.startsWith("d")
								&& ent.rights.substring(5, 6).equals("w")) {
							ent.rights = "frwxrwx";
							return;
						} else if (!ent.rights.substring(5, 6).equals("w")) {
							System.out.println(
									"-5: " + helper + ": No rights to write");
							return;
						} else if (!ent.name.equals(array[2])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name) {
							System.out.println("-12: " + helper + ": No such file or directory");
							return;
						}
					}

				}

			}

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
			if (tok == 1 && array[2].startsWith("/")) {
				for (int i = 0; i < tok; i++) {
					tokens[i] = "chmod " + array[1] + " " + st.nextToken();
				}

				doit[0] = comm.getCommand("chmod");
				doit[0].run(tokens[0], helper, system);
				system.curLocation = retinere;
				return;
			} else if (tok == 1) {
				tokens[0] = "chmod " + array[1] + " " + st.nextToken();
				doit[0] = comm.getCommand("chmod");
				doit[0].run(tokens[0], helper, system);
				system.curLocation = retinere;
				return;

			} else {
				for (int i = 0; i < tok - 1; i++) {
					tokens[i] = "cd " + st.nextToken();
				}
				tokens[tok - 1] = "chmod " + array[1] + " " + st.nextToken();
				for (int i = 0; i < tok; i++) {
					int j = 0;
					StringTokenizer st1 = new StringTokenizer(tokens[i]);
					nTokens = st1.countTokens();
					for (j = 0; j < nTokens; j++) {
						data[i][j] = st1.nextToken();
					}
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

				if (i == tok - 1) {

					doit[i] = comm.getCommand("chmod");
					doit[i].run(tokens[i], helper, system);
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

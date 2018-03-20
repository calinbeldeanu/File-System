/**
 * Clasa in care implementez comanda mkdir
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Mkdir implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (array[1].equals("/") && system.root == null) {
			system.root = new Entity(true, "/", "drwxr-x", "root");
			system.curUser = system.root.owner;
			system.users.add("root");
			system.curLocation = system.root;
			system.root.parent = system.root;
			return;
		} else {
			if (array[1].equals("/")) {
				System.out.println("-1: " + helper + ": Is a directory");
				return;
			}
		}
		if (!array[1].contains("/")) {
			if (system.curUser.equals("root")) {
				if (system.curLocation.subordinates.isEmpty()) {
					Entity dir = new Entity(true, array[1], "drwx---",
							system.curUser);
					dir.parent = system.root;
					system.curLocation.add(dir);
					return;
				}
				for (Entity ent : system.curLocation.getSubordinates()) {
					if (ent.name.equals(array[1])
							&& ent.rights.startsWith("d")) {
						System.out.println(
								"-1:" + " " + helper + ": Is a directory");
						return;
					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")) {
						System.out.println(
								"-3:" + " " + helper + ": Not a directory");
						return;

					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()
											- 1).name) {
						Entity dir = new Entity(true, array[1], "drwx---",
								system.curUser);
						dir.parent = system.root;
						system.curLocation.add(dir);
						return;
					}

				}
			} else if (system.curLocation.owner.equals(system.curUser)) {

				if (system.curLocation.subordinates.isEmpty()) {

					Entity dir = new Entity(true, array[1], "drwx---",
							system.curUser);
					system.curLocation.add(dir);
					return;
				} else if (!system.curLocation.subordinates.isEmpty()) {
					for (Entity ent : system.curLocation.getSubordinates()) {
						if (ent.name.equals(array[1])
								&& ent.rights.startsWith("d")) {
							System.out.println("-1:" + " " + helper + ": Is a directory");
							return;
						} else if (ent.name.equals(array[1])
								&& ent.rights.startsWith("f")) {
							System.out.println("-3:" + " " + helper + ": Not a directory");
							return;
						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name) {
							Entity dir = new Entity(true, array[1], "drwx---",
									system.curUser);
							system.curLocation.add(dir);
							return;
						}
					}
				}
			} else {
				if (system.curLocation.subordinates.isEmpty()
						&& system.curLocation.rights.endsWith("w-")) {

					Entity dir = new Entity(true, array[1], "drwx---",
							system.curUser);
					system.curLocation.add(dir);
					return;
				} else if (system.curLocation.subordinates.isEmpty()
						&& system.curLocation.rights.endsWith("wx")) {

					Entity dir = new Entity(true, array[1], "drwx---",system.curUser);
					system.curLocation.add(dir);
					return;
				} else if (!system.curLocation.rights.substring(5, 6)
						.equals("w")) {
					System.out
							.println("-5: " + helper + ": No rights to write");
					return;
				} else if (!system.curLocation.subordinates.isEmpty()) {
					for (Entity ent : system.curLocation.getSubordinates()) {
						if (ent.name.equals(array[1])
								&& ent.rights.startsWith("d")) {
							System.out.println("-1:" + " " + helper + ": Is a directory");
							return;
						} else if (ent.name.equals(array[1])
								&& ent.rights.startsWith("f")) {
							System.out.println("-3:" + " " + helper + ": Not a directory");
							return;
						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name
								&& system.curLocation.rights.endsWith("w-")) {
							Entity dir = new Entity(true, array[1], "drwx---",system.curUser);
							system.curLocation.add(dir);
							return;
						}
					}
				}
			}
		}
		if (array[1].contains("/")) {
			StringTokenizer st = new StringTokenizer(array[1], "/");
			CommandFactory comm = new CommandFactory();
			Entity retinere = new Entity(true, input, input, input);
			Entity verificare = new Entity(true, input, input, input);
			retinere = system.curLocation;
			int tok = st.countTokens();
			Command[] doit = new Command[tok];
			String[] tokens = new String[tok];
			String[][] data = new String[tok][tok];
			int nTokens;
			for (int i = 0; i < tok; i++) {
				tokens[i] = "mkdir " + st.nextToken();
			}
			if (array[1].startsWith("/")) {
				system.curLocation = system.root;
			}
			if (tok == 1) {
				doit[0] = comm.getCommand("mkdir");
				doit[0].run(tokens[0], helper, system);
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
					verificare = system.curLocation;
					doit[i] = comm.getCommand("cd");
					doit[i].run(tokens[i], helper, system);
					if ((system.curLocation == verificare
							&& !data[i][1].equals(".."))) {
						if ((system.curLocation == verificare
								&& !data[i][1].equals(".")))
							break;
					}
				}
				if (system.curLocation.name.equals(data[tok - 2][tok - 1])) {
					doit[tok - 1] = comm.getCommand("mkdir");
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

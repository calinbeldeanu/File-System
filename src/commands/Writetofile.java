/**
 * Clasa in care implementez comanda writetofile
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Writetofile implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		String reuniune = new String();
		reuniune = array[2];
		for (int i = 3; i < array.length; i++) {
			reuniune = reuniune + " " + array[i];
		}
		if (!array[1].contains("/")) {
			if (system.curUser.equals("root")
					|| system.curLocation.owner.equals(system.curUser)) {
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println("-11: " + helper + ": No such file");
					return;
				}
				for (Entity ent : system.curLocation.getSubordinates()) {
					if (ent.name.equals(array[1])
							&& ent.rights.startsWith("d")) {
						System.out.println("-1:" + " " + helper + ": Is a directory");
						return;
					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")
							&& !ent.rights.substring(2, 3).equals("w")) {
						System.out.println("-5: " + helper + ": No rights to write");
						return;

					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")) {
						ent.contain = reuniune;
						return;

					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()- 1).name) {
						System.out.println("-11: " + helper + ": No such file");
						return;

					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")) {
						ent.contain = reuniune;
						return;
					}
				}
			} else if (!system.curUser.equals("root")) {

				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println("-11: " + helper + ": No such file");
					return;
				}
				for (Entity ent : system.curLocation.getSubordinates()) {
					if (ent.name.equals(array[1])
							&& ent.rights.startsWith("d")) {
						System.out.println("-1:" + " " + helper + ": Is a directory");
						return;
					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")
							&& ent.rights.substring(5, 6).equals("w")) {
						ent.contain = reuniune;
						return;
					} else if (ent.name.equals(array[1])
							&& ent.rights.startsWith("f")
							&& !ent.rights.substring(5, 6).equals("w")) {
						System.out.println(
								"-5: " + helper + ": No rights to write");
						return;
					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size() - 1).name) {
						System.out.println("-11: " + helper + ": No such file");
						return;

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
				tokens[i] = "writetofile " + st.nextToken();
			}
			if (array[1].startsWith("/")) {
				system.curLocation = system.root;
			}
			if (tok == 1) {
				doit[0] = comm.getCommand("writetofile");
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
					doit[tok - 1] = comm.getCommand("writetofile");
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

/**
 * Clasa in care implementez comanda ls
 * @author Beldeanu Calin 
 */
package commands;

import java.util.StringTokenizer;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Ls implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		Entity verificare = new Entity(true, input, input, input);

		if (!array[1].contains("/")) {
			if (system.curUser.equals("root")) {
				if (array[1].equals(".")) {
					for (Entity ent : system.curLocation.getSubordinates()) {
						System.out.println(
								ent.name + " " + ent.rights + " " + ent.owner);
					}
					return;
				} else {
					verificare = system.curLocation;
					for (Entity ent : system.curLocation.getSubordinates()) {
						if (ent.name.equals(array[1])) {
							system.curLocation = ent;
							for (Entity ent1 : system.curLocation.getSubordinates()) {
								System.out.println(ent1.name + " " + ent1.rights + " " + ent1.owner);
							}
							system.curLocation = verificare;
							return;
						} else if (!ent.name.equals(array[1])
								&& ent.name == system.curLocation.subordinates
										.get(system.curLocation.subordinates.size() - 1).name) {
							System.out.println("-12: " + helper + ": No such file or directory");
							return;
						}
					}
				}
			} else {
				
				if (system.curLocation.subordinates.isEmpty()) {
					System.out.println(
							"-12: " + helper + ": No such file or directory");
				}
				
				if (array[1].equals(".")
						&& system.curLocation.owner.equals(system.curUser)) {
					for (Entity ent : system.curLocation.getSubordinates()) {
						System.out.println(
								ent.name + " " + ent.rights + " " + ent.owner);
					}
					return;
				
				} else if (array[1].equals(".") && system.curLocation.rights
						.substring(4, 5).equals("r")) {
					for (Entity ent : system.curLocation.getSubordinates()) {
						System.out.println(
								ent.name + " " + ent.rights + " " + ent.owner);

					}
					return;
				
				} else if (array[1].equals(".") && !system.curLocation.rights
						.substring(4, 5).equals("r")) {
					System.out.println("-4: " + helper + ": No rights to read");
					return;
				}
				
				verificare = system.curLocation;
				for (Entity ent : system.curLocation.getSubordinates()) {
					if (ent.name.equals(array[1])
							&& ent.rights.substring(4, 5).equals("r")) {
						system.curLocation = ent;
						for (Entity ent1 : system.curLocation
								.getSubordinates()) {
							System.out.println(ent1.name + " " + ent1.rights + " " + ent1.owner);
						}
						system.curLocation = verificare;
						return;
					
					} else if (ent.name.equals(array[1])
							&& ent.owner.equals(system.curUser)) {
						system.curLocation = ent;
						for (Entity ent1 : system.curLocation
								.getSubordinates()) {
							System.out.println(ent1.name + " " + ent1.rights+ " " + ent1.owner);
						}
						system.curLocation = verificare;
						return;
					
					} else if (ent.name.equals(array[1])
							&& !ent.rights.substring(4, 5).equals("r")) {
						System.out.println(
								"-4: " + helper + ": No rights to read");
					
					} else if (!ent.name.equals(array[1])
							&& ent.name == system.curLocation.subordinates
									.get(system.curLocation.subordinates.size()
											- 1).name) {
						System.out.println("-12: " + helper
								+ ": No such file or directory");
						return;
					}
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
			
			if (array[1].length() == 1) {
				system.curLocation = system.root;
				for (Entity ent : system.curLocation.getSubordinates()) {
					System.out.println(
							ent.name + " " + ent.rights + " " + ent.owner);
				}
				return;

			}
			for (int i = 0; i < tok; i++) {
				tokens[i] = "cd " + st.nextToken();
			}
			
			if (array[1].startsWith("/")) {
				system.curLocation = system.root;
			}
			
			if (tok == 1 && tokens[0].contains("..")) {
				doit[0] = comm.getCommand("cd");
				doit[0].run(tokens[0], helper, system);
				doit[0] = comm.getCommand("ls");
				doit[0].run("ls .", helper, system);
				system.curLocation = retinere;
				return;
			}
			
			if (tok == 1) {
				verificare1 = system.curLocation;
				doit[0] = comm.getCommand("cd");
				doit[0].run(tokens[0], helper, system);
				if (system.curLocation.equals(verificare1)) {
					system.curLocation = retinere;
					return;
				} else {
					doit[0] = comm.getCommand("ls");
					doit[0].run("ls .", helper, system);
					system.curLocation = retinere;
					return;
				}
			
			} else {
				for (int i = 0; i < tok; i++) {
					int j = 0;
					StringTokenizer st1 = new StringTokenizer(tokens[i]);
					nTokens = st1.countTokens();
					for (j = 0; j < nTokens; j++) {
						data[i][j] = st1.nextToken();
					}
				}
				
				int i;
				for (i = 0; i < tok; i++) {
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
				
				if (i == tok) {
					doit[i - 1] = comm.getCommand("ls");
					doit[i - 1].run("ls .", helper, system);
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

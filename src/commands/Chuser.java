/**
 * Clasa in care implementez comanda chuser
 * @author Beldeanu Calin 
 */
package commands;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Chuser implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);

		if (array[1].equals("root")) {
			system.curLocation = system.root;
			system.curUser = array[1];
			return;
		}

		if (!system.users.contains(array[1])) {
			System.out.println("-8:" + " " + input + ": User does not exist");
			return;
		} else {
			for (Entity ent : system.root.getSubordinates()) {
				if (ent.name.equals(array[1])) {

					system.curLocation = ent;
					system.curUser = array[1];
					return;
				}

			}
		}

	}

}

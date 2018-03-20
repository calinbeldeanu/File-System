/**
 * Clasa in care implementez comanda deluser
 * @author Beldeanu Calin 
 */
package commands;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Deluser implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (!system.curUser.equals("root")) {
			System.out.println(
					"-10:" + " " + input + ": No rights to change user status");
			return;
		}

		if (system.users.contains(array[1])) {
			for (Entity ent : system.root.getSubordinates()) {
				if (ent.name.equals(array[1])) {
					system.users.remove(array[1]);
					ent.owner = system.users.get(1);
					return;
				}
			}

			System.out.println("-8:" + " " + input + ": User does not exist");
			return;
		}

	}

}

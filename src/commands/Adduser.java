/**
 * Clasa in care implementez comanda adduser
 * @author Beldeanu Calin 
 */
package commands;

import Date.ReadData;
import FileSystem.Entity;
import FileSystem.Information;

public class Adduser implements Command {

	@Override
	public void run(String input, String helper, Information system) {
		String[] array = ReadData.InputArray(input);
		if (system.users.contains(array[1])) {
			System.out.println("-9:" + " " + input + ": User already exists");
			return;
		}
		if (!system.curUser.equals("root")) {
			System.out.println("-10:" + " " + input + ": No rights to change user status");
			return;
		}
		system.users.add(array[1]);
		Entity user = new Entity(true, array[1], "drwx---", array[1]);
		system.add(user);
		user.parent = system.root;
	}

}

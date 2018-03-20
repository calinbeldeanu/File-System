/**
 * Clasa in care implementez Factory-ul care va alege o comanda pe care sa o execute
 * @author Beldeanu Calin 
 */
package commands;

public class CommandFactory {
	public Command getCommand(String commandType) {
		if (commandType == null) {
			return null;
		}

		if (commandType.equalsIgnoreCase("adduser")) {
			return new Adduser();

		} else if (commandType.equalsIgnoreCase("deluser")) {
			return new Deluser();

		} else if (commandType.equalsIgnoreCase("chuser")) {
			return new Chuser();

		} else if (commandType.equalsIgnoreCase("cd")) {
			return new Cd();

		} else if (commandType.equalsIgnoreCase("mkdir")) {
			return new Mkdir();

		} else if (commandType.equalsIgnoreCase("ls")) {
			return new Ls();

		} else if (commandType.equalsIgnoreCase("chmod")) {
			return new Chmod();

		} else if (commandType.equalsIgnoreCase("touch")) {
			return new Touch();

		} else if (commandType.equalsIgnoreCase("rm")) {
			return new Rm();

		} else if (commandType.equalsIgnoreCase("rmdir")) {
			return new Rmdir();

		} else if (commandType.equalsIgnoreCase("writetofile")) {
			return new Writetofile();

		} else if (commandType.equalsIgnoreCase("cat")) {
			return new Cat();
		}

		return null;
	}
}

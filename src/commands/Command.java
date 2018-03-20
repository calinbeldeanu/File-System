/**
 * Clasa in care implementez metoda pe care o implementeaza fiecare comanda
 * @author Beldeanu Calin 
 */
package commands;

import FileSystem.Information;

public interface Command {
	public void run(String input, String helper, Information system);
}

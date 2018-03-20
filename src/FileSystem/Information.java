/**
 * Clasa in care definesc Informatiile pe care fiecare comanda trebuie sa le primeasca
 * @author Beldeanu Calin 
 */

package FileSystem;
import java.util.LinkedList;
import java.util.List;

public class Information {
	public Entity root;
	public List<String> users;
	public Entity curLocation;
	public String curUser;

	public Information() {
		super();
		this.root = null;
		this.users = new LinkedList<String>();
		this.curLocation = null;
		this.curUser = null;
	}

	public void add(Entity ent) {
		root.subordinates.add(ent);
	}

}

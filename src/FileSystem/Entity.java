/**
 * Clasa in care definesc entitatea
 * @author Beldeanu Calin 
 */
package FileSystem;
import java.util.LinkedList;
import java.util.List;

public class Entity {
	public String name;
	public String rights;
	public String owner;
	public String contain;
	public Entity parent;
	public List<Entity> subordinates;

	public Entity(boolean alfa, String name, String rights, String owner) {
		this.name = name;
		this.rights = rights;
		this.owner = owner;
		this.parent = null;
		if (alfa == true) {
			subordinates = new LinkedList<Entity>();
		}
	}

	public void add(Entity ent) {
		subordinates.add(ent);
	}

	public List<Entity> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Entity> subordinates) {
		this.subordinates = subordinates;
	}

}

package todo.spring.vuejs.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ctodo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String label;
	private String description;
	private int avancement;
	private int poids;
	
	public Ctodo(String label) {
		super();
		this.id = (long) 0;
		this.label = label;
		this.description = "";
		this.avancement = 0;
		this.poids = 0;
	}
	
	public Ctodo() {
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAvancement() {
		return avancement;
	}
	public void setAvancement(int avancement) {
		this.avancement = avancement;
	}
	public int getPoids() {
		return poids;
	}
	public void setPoids(int poids) {
		this.poids = poids;
	}
}

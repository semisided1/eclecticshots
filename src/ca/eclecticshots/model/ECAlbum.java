/**
 * 
 */
package ca.eclecticshots.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author dd
 *
 */
@Entity
public class ECAlbum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String cover;
	private String description;
	
	
	public ECAlbum(String name, String cover, String description) {
		this.name = name;
		this.cover = cover;
		this.description = description;
	}
	
	public String toString(){
		String out = "<ecalbum><id>" + this.id + "</id>" +
		"<name>" + this.name + "</name>" + 
		"<cover>" + this.cover + "</cover>" +
		"<description>" + this.description + "</description>" +
		"</ecalbum>";
		return out;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getId() {
		return this.id.toString();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// cover title
	public String getCover() {
		return cover;
	}

	public void setCover(String cover){
		this.cover = cover;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	
}

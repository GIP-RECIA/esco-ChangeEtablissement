/**
 * 
 */
package org.esco.portlet.changeetab.model;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class Etablissement {

	private String id;

	private String name;

	private String description;

	public Etablissement() {
		super();
	}

	public Etablissement(final String id, final String name, final String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Getter of Id.
	 *
	 * @return the Id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter of Id.
	 *
	 * @param id the Id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Getter of name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter of name.
	 *
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter of description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter of description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}

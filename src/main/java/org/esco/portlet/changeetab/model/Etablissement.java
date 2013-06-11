/**
 * 
 */
package org.esco.portlet.changeetab.model;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class Etablissement {

	private String uai;

	private String name;

	private String description;

	/**
	 * Getter of uai.
	 *
	 * @return the uai
	 */
	public String getUai() {
		return this.uai;
	}

	/**
	 * Setter of uai.
	 *
	 * @param uai the uai to set
	 */
	public void setUai(final String uai) {
		this.uai = uai;
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

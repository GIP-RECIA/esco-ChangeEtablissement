/**
 * 
 */
package org.esco.portlet.changeetab.mvc;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class ChangeEtabCommand {

	private String selectedEtabId;

	/**
	 * Getter of selectedEtabId.
	 *
	 * @return the selectedEtabId
	 */
	public String getSelectedEtabId() {
		return this.selectedEtabId;
	}

	/**
	 * Setter of selectedEtabId.
	 *
	 * @param selectedEtabId the selectedEtabId to set
	 */
	public void setSelectedEtabId(final String selectedEtabId) {
		this.selectedEtabId = selectedEtabId;
	}

}

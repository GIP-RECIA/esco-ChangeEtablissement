/**
 * 
 */
package org.esco.portlet.changeetab.mvc;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class ChangeEtabCommand {

	public static final String DEFAULT_ID = "currentEtab";

	private String selectedEtabId = ChangeEtabCommand.DEFAULT_ID;

	public void reset() {
		this.selectedEtabId = ChangeEtabCommand.DEFAULT_ID;
	}

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

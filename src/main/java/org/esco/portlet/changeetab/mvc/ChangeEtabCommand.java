/**
 * Copyright (C) 2012 RECIA http://www.recia.fr
 * @Author (C) 2012 Maxime Bossard <mxbossard@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * Copyright (C) 2017 GIP RECIA http://www.recia.fr
 * @Author (C) 2013 Maxime Bossard <mxbossard@gmail.com>
 * @Author (C) 2016 Julien Gribonvald <julien.gribonvald@recia.fr>
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
package org.esco.portlet.changeetab.web;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Data
@NoArgsConstructor
public class ChangeStructCommand {

	public static final String DEFAULT_CODE = "currentStruct";

	private String selectedStructId = ChangeStructCommand.DEFAULT_CODE;

	public void reset() {
		this.selectedStructId = ChangeStructCommand.DEFAULT_CODE;
	}
}

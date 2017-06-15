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
package org.esco.portlet.changeetab.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 * @author GIP RECIA - Julien Gribonvald
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UniteAdministrativeImmatriculee extends Structure {

	/** Etab code. */
	@NonNull
	private String code;

	/**
	 * @param id Identifier
	 * @param code Code
	 * @param name Complete Name
	 * @param displayName Display Name
	 * @param description Description
	 * @param otherAttributes All other attributes
	 */
	public UniteAdministrativeImmatriculee(@NonNull String id, @NonNull String code, @NonNull String name, @NonNull String displayName,
			String description, Map<String, List<String>> otherAttributes) {
		super(id, name, displayName, description, otherAttributes);
		// TODO Auto-generated constructor stub
		this.code = code;
	}

}

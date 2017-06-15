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
package org.esco.portlet.changeetab.dao.bean;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.model.Structure;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Created by jgribonvald on 28/07/16.
 */
@Slf4j
@NoArgsConstructor
public class LdapStructureDisplayNameFormater implements IStructureFormatter, InitializingBean {

	@Getter
	private String groupsRegex;
	private Pattern groupsRegexPattern;
	@Setter
	@Getter
	private Map<Integer, String> indexListReplacement;

	public void setGroupsRegex(final String groupsRegex) {
		this.groupsRegex = groupsRegex;
		this.groupsRegexPattern = Pattern.compile(groupsRegex);
	}

	@Override
	public Structure format(Structure input) {
		if (input != null) {
			// setting as new displayName the origin displayName formatted
			input.setDisplayName(format(input.getDisplayName()));
		}
		return input;
	}

	private String format(String input) {
		if (input != null && !input.isEmpty()) {
			Matcher group = this.groupsRegexPattern.matcher(input);
			if (group.find()) {
				StringBuilder displayName = new StringBuilder(input);
				for (Map.Entry<Integer, String> entry : this.indexListReplacement.entrySet()) {
					displayName.replace(group.start(entry.getKey()), group.end(entry.getKey()), entry.getValue());
				}
				//                for (int i = 0; i < group.groupCount(); i++) {
				//                    if (this.indexListReplacement.containsKey(i)) {
				//                        displayName += group.group(this.indexListReplacement.get(i));
				//                    }
				//
				//                }
				log.debug("Matcher found groups displayName, and applied replacement value is : {}", displayName);
				return displayName.toString();
			}

		}
		return input;
	}

	public String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence, String replacement) {
		Matcher m = Pattern.compile(regex).matcher(source);
		for (int i = 0; i < groupOccurrence; i++)
			if (!m.find())
				return source; // pattern not met, may also throw an exception here
		return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement)
				.toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.groupsRegexPattern,
				"Attribut groupsRegex wasn't initialized, a regular expression with groups should be passed");
		Assert.isTrue(!this.indexListReplacement.isEmpty(),
				"Attribut indexListToKeep wasn't initialized, you should list all regexp group where you want to make replacemnt");
	}
}

/*
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
package org.esco.portlet.changeetab.service.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.esco.portlet.changeetab.dao.IUserDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.service.IUserService;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
//@Service
@Data
@NoArgsConstructor
public class BasicUserService implements IUserService {

	//@Autowired
	private IUserDao userDao;

	@Override
	public void changeCurrentStructure(final String userId, final Structure struct) {
		Assert.hasText(userId, "No user Id supplied !");
		Assert.notNull(struct, "No structure supplied !");

		this.userDao.saveCurrentStructure(userId, struct);
	}
}

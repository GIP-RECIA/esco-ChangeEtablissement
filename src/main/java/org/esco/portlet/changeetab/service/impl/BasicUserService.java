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
package org.esco.portlet.changeetab.service.impl;

import org.esco.portlet.changeetab.dao.IUserDao;
import org.esco.portlet.changeetab.model.Etablissement;
import org.esco.portlet.changeetab.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class BasicUserService implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	public void changeCurrentEtablissement(final String userId, final Etablissement etab) {
		Assert.hasText(userId, "No user Id supplied !");
		Assert.notNull(etab, "No etablishement supplied !");
		
		this.userDao.saveCurrentEtablissement(userId, etab.getId());
	}

	/**
	 * Getter of userDao.
	 *
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * Setter of userDao.
	 *
	 * @param userDao the userDao to set
	 */
	public void setUserDao(final IUserDao userDao) {
		this.userDao = userDao;
	}

}

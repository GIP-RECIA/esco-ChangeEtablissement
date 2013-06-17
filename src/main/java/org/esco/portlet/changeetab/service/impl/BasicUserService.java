/**
 * 
 */
package org.esco.portlet.changeetab.service.impl;

import org.esco.portlet.changeetab.dao.IUserDao;
import org.esco.portlet.changeetab.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class BasicUserService implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	public void changeCurrentEtablissement(final String userId, final String etabId) {
		this.userDao.saveCurrentEtablissement(userId, etabId);
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

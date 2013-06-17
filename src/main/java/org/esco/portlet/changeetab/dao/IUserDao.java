/**
 * 
 */
package org.esco.portlet.changeetab.dao;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IUserDao {

	void saveCurrentEtablissement(String userId, String etabId);

}

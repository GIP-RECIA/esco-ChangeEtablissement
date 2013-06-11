/**
 * 
 */
package org.esco.portlet.changeetab.service;

import java.util.Collection;

import javax.portlet.PortletRequest;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IUserInfoService {

	Collection<String> getEscoUai(PortletRequest request);
	
	String getEscoUaiCourant(PortletRequest request);
	
}

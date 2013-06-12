/**
 * 
 */
package org.esco.portlet.changeetab.caching;

import javax.portlet.PortletRequest;

import org.joda.time.Interval;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface ICacheKeyGenerator {

	public String getKey(PortletRequest request, Interval period);

}

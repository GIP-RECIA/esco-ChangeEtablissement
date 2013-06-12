/**
 * 
 */
package org.esco.portlet.changeetab.caching.impl;

import javax.portlet.PortletRequest;

import org.esco.portlet.changeetab.caching.ICacheKeyGenerator;
import org.joda.time.Interval;

/**
 * Generator for cache key with a period validity.
 * The cache key is the period end time in ms.
 * 
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class PeriodValidityCacheKeyGenerator implements ICacheKeyGenerator {

	@Override
	public String getKey(final PortletRequest request, final Interval period) {
		return String.valueOf(period.getEnd().getMillis());
	}

}

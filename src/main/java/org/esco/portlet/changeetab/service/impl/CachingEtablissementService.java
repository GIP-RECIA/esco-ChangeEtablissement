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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.esco.portlet.changeetab.dao.IEtablissementDao;
import org.esco.portlet.changeetab.model.Etablissement;
import org.esco.portlet.changeetab.service.IEtablissementService;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class CachingEtablissementService implements IEtablissementService, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CachingEtablissementService.class);

	@Autowired
	private IEtablissementDao etablissementDao;

	@Autowired
	private Cache etablissementCache;

	/** Configured caching duration (default 1 hour). */
	private Duration cachingDuration = Duration.standardHours(1L);

	/** Instant when the cache will be expiring. */
	private Instant expiringInstant;

	@Override
	public Map<String, Etablissement> retrieveEtablissementsByIds(final Collection<String> ids) {
		Assert.notEmpty(ids, "No Etablissement Id supplied !");

		final Map<String, Etablissement> etabs = new HashMap<String, Etablissement>(ids.size());

		this.loadEtablissementCacheIfExpired();

		for (final String id : ids) {

			final String cacheKey = this.genCacheKey(id);
			final ValueWrapper cachedValue = this.etablissementCache.get(cacheKey);
			if (cachedValue == null) {
				CachingEtablissementService.LOG.warn("No etablissement found in cache for Id: [{}] !", id);
			} else {
				etabs.put(id, (Etablissement) cachedValue.get());
			}
		}

		CachingEtablissementService.LOG.debug("{} etablissement(s) found.", etabs.size());

		return etabs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.etablissementDao, "No IEtablissementDao configured !");
		Assert.notNull(this.etablissementCache, "No Etablissement cache configured !");
	}

	/** Laod the etablissement cache if it is expired. */
	protected synchronized void loadEtablissementCacheIfExpired() {
		if ((this.expiringInstant == null) || this.expiringInstant.isBeforeNow()) {
			// If cache expired
			this.forceLoadEtablissementCache();
		}
	}

	/** Laod the etablissement cache. */
	protected synchronized void forceLoadEtablissementCache() {
		this.expiringInstant = new Instant().plus(this.cachingDuration);
		this.etablissementCache.clear();

		final Collection<Etablissement> allEtabs = this.etablissementDao.findAllEtablissements();
		for (final Etablissement etab : allEtabs) {
			final String etabCacheKey = this.genCacheKey(etab.getId());
			this.etablissementCache.put(etabCacheKey, etab);
		}
	}

	/**
	 * Generate an etablissement cache key.
	 * 
	 * @param uai
	 * @return the cache key
	 */
	protected String genCacheKey(final String uai) {
		final StringBuilder cacheKeyBuilder = new StringBuilder(32);
		cacheKeyBuilder.append(uai);
		cacheKeyBuilder.append("_");
		cacheKeyBuilder.append(this.expiringInstant.getMillis());

		return cacheKeyBuilder.toString();
	}

	/**
	 * Getter of etablissementDao.
	 *
	 * @return the etablissementDao
	 */
	public IEtablissementDao getEtablissementDao() {
		return this.etablissementDao;
	}

	/**
	 * Setter of etablissementDao.
	 *
	 * @param etablissementDao the etablissementDao to set
	 */
	public void setEtablissementDao(final IEtablissementDao etablissementDao) {
		this.etablissementDao = etablissementDao;
	}

	/**
	 * Getter of etablissementCache.
	 *
	 * @return the etablissementCache
	 */
	public Cache getEtablissementCache() {
		return this.etablissementCache;
	}

	/**
	 * Setter of etablissementCache.
	 *
	 * @param etablissementCache the etablissementCache to set
	 */
	public void setEtablissementCache(final Cache etablissementCache) {
		this.etablissementCache = etablissementCache;
	}

	/**
	 * Getter of expiringInstant.
	 *
	 * @return the expiringInstant
	 */
	public Instant getExpiringInstant() {
		return this.expiringInstant;
	}

	/**
	 * Getter of cachingDuration.
	 *
	 * @return the cachingDuration
	 */
	public long getCachingDuration() {
		return this.cachingDuration.getMillis();
	}

	/**
	 * Setter of cachingDuration (in ms).
	 *
	 * @param cachingDuration the cachingDuration to set
	 */
	public void setCachingDuration(final long cachingDuration) {
		this.cachingDuration = Duration.millis(cachingDuration);
	}

}

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.esco.portlet.changeetab.service.IUniteAdministrativeImmatriculeService;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
@Slf4j
public class CachingStructureService implements IUniteAdministrativeImmatriculeService, InitializingBean {

	@Autowired
	@Getter
	@Setter
	private IStructureDao structureDao;

	@Autowired
	@Qualifier("structuresCache")
	@Getter
	@Setter
	private Cache structureCache;

	@Autowired
	@Qualifier("etabsCodeIdCache")
	@Getter
	@Setter
	private Cache etabsCodeIdCache;

	/** Configured caching duration (default 1 hour). */
	private Duration cachingDuration = Duration.standardHours(1L);

	/** Instant when the cache will be expiring. */
	@Getter
	private volatile Instant expiringInstant;

	/** True if cache is loading. */
	private volatile boolean loadingInProgress = false;

	/** Cache Read / Write lock. */
	private final ReentrantReadWriteLock cacheRwl = new ReentrantReadWriteLock();
	private final Lock cacheRl = cacheRwl.readLock();
	private final Lock cacheWl = cacheRwl.writeLock();

	@Override
	public Map<String, Structure> retrieveStructuresByIds(final Collection<String> ids) {
		Assert.notEmpty(ids, "No Structure code supplied !");

		final Map<String, Structure> structs = new HashMap<String, Structure>(ids.size());

		this.forceLoadStructureCache();

		for (final String id : ids) {
			final Structure struct = this.retrieveStructureById(id);
			if (struct != null) {
				structs.put(id, struct);
			}
		}

		log.debug("{} structure(s) found.", structs.size());

		return structs;
	}

	@Override
	public Structure retrieveStructureById(final String id) {
		Assert.hasText(id, "No Structure code supplied !");

		Structure struct = null;

		this.forceLoadStructureCache();

		final String cacheKey = this.genCacheKey(id);
		ValueWrapper cachedValue = null;

		// Aquire read lock to avoid cache unconsistency
		this.cacheRl.lock();
		try {
			cachedValue = this.structureCache.get(cacheKey);
		} finally {
			this.cacheRl.unlock();
		}
		if (cachedValue == null) {
			log.warn("No structure found in cache for id: [{}] !", id);
		} else {
			struct = (Structure) cachedValue.get();
		}

		return struct;
	}

	@Override
	@Deprecated
	public Map<String, UniteAdministrativeImmatriculee> retrieveEtablissementsByCodes(final Collection<String> codes) {
		Assert.notEmpty(codes, "No UniteAdministrativeImmatriculee code supplied !");

		final Map<String, UniteAdministrativeImmatriculee> etabs = new HashMap<String, UniteAdministrativeImmatriculee>(
				codes.size());

		this.forceLoadStructureCache();

		for (final String code : codes) {
			final UniteAdministrativeImmatriculee etab = this.retrieveEtablissementByCode(code);
			if (etab != null) {
				etabs.put(code, etab);
			}
		}

		log.debug("{} UniteAdministrativeImmatriculee(s) found.", etabs.size());

		return etabs;
	}

	@Override
	@Deprecated
	public UniteAdministrativeImmatriculee retrieveEtablissementByCode(final String code) {
		Assert.hasText(code, "No UniteAdministrativeImmatriculee code supplied !");

		UniteAdministrativeImmatriculee etab = null;

		this.forceLoadStructureCache();

		final String cacheKey = this.genCacheKeyForMapCodeId(code);
		ValueWrapper cachedValue = null;

		// Aquire read lock to avoid cache unconsistency
		this.cacheRl.lock();
		try {
			ValueWrapper cachedMapping = this.etabsCodeIdCache.get(cacheKey);
			if (cachedMapping != null) {
				final String cacheIdKey = this.genCacheKey((String) cachedMapping.get());
				cachedValue = this.structureCache.get(cacheIdKey);
			} else {
				log.warn("No id found in association [id - code] cache for code: [{}] !", code);
			}
		} finally {
			this.cacheRl.unlock();
		}
		if (cachedValue == null) {
			log.warn("No UniteAdministrativeImmatriculee found in cache for code: [{}] !", code);
		} else {
			etab = (UniteAdministrativeImmatriculee) cachedValue.get();
		}

		return etab;
	}

	/**
	 * @see org.esco.portlet.changeetab.service.IStructureService#reloadStructureById(java.lang.String)
	 */
	@Override
	public void reloadStructureById(final String id) {
		Assert.hasText(id, "No Structure id supplied !");

		if (this.cacheLoadingNeeded()) {
			this.forceLoadStructureCache();
		} else {
			this.loadingInProgress = true;

			log.debug("Refreshing cached structure with id [{}] ...", id);

			final Structure struct = this.structureDao.findOneStructureById(id);

			final String cacheKey = this.genCacheKey(id);

			if (struct != null) {
				this.cacheWl.lock();
				try {
					this.structureCache.evict(cacheKey);
					this.structureCache.put(cacheKey, struct);
				} finally {
					this.cacheWl.unlock();
					this.loadingInProgress = false;
				}
			} else {
				log.warn("Loading doesn't find the structure with id {} !", id);
				this.loadingInProgress = false;
			}
		}

		if (this.cacheLoadingNeeded()) {
			this.forceLoadStructureCache();
		}

	}

	/** Load the structure cache. */
	protected synchronized void forceLoadStructureCache() {
		// Test if another concurrent thread just didn't already load the cache
		if (this.cacheLoadingNeeded()) {
			this.loadingInProgress = true;
			log.debug("Loading structure cache...");

			final Collection<? extends Structure> allstructs = this.structureDao.findAllStructures();
			if (allstructs != null && allstructs.size() > 0) {
				// Aquire write lock to avoid cache unconsistency
				this.cacheWl.lock();
				try {
					this.structureCache.clear();
					this.etabsCodeIdCache.clear();
					final Instant refreshedInstant = new Instant().plus(this.cachingDuration);
					for (final Structure struct : allstructs) {
						log.debug("Adding to cache : {}", struct);
						final String structCacheKey = this.genCacheKey(struct.getId());
						this.structureCache.put(structCacheKey, struct);
						if (struct instanceof UniteAdministrativeImmatriculee) {
							final String mappingIdCodeCacheKey = this
									.genCacheKeyForMapCodeId(((UniteAdministrativeImmatriculee) struct).getCode());
							this.etabsCodeIdCache.put(mappingIdCodeCacheKey, struct.getId());
						}
					}
					this.expiringInstant = refreshedInstant;

					log.debug("structure cache loaded with new expiration time: {}", refreshedInstant.toString());
				} finally {
					this.cacheWl.unlock();
					this.loadingInProgress = false;
				}
			} else {
				log.warn("Loading doesn't find any structure !");
				this.loadingInProgress = false;
			}
		}
	}

	/**
	 * Test if a cache loading is needed.
	 * Cache loading is needed if Cache is not initialized or is expired and no loading is already in progress.
	 *
	 * @return true if cache loading is needed.
	 */
	protected boolean cacheLoadingNeeded() {
		return (this.expiringInstant == null || this.expiringInstant.isBeforeNow()) && !this.loadingInProgress;
	}

	/**
	 * Generate an etablissement cache key.
	 *
	 * @param siren Id of the Structure.
	 * @return the cache key
	 */
	protected String genCacheKey(final String siren) {
		final StringBuilder cacheKeyBuilder = new StringBuilder(32);
		cacheKeyBuilder.append(siren.toLowerCase());
		return cacheKeyBuilder.toString();
	}

	/**
	 * Generate an etablissement cache key.
	 *
	 * @param uai Id of the UniteAdministrativeImmatriculee.
	 * @return the cache key
	 */
	protected String genCacheKeyForMapCodeId(final String uai) {
		final StringBuilder cacheKeyBuilder = new StringBuilder(32);
		cacheKeyBuilder.append(uai.toLowerCase());
		//cacheKeyBuilder.append("_");
		//cacheKeyBuilder.append(instant.getMillis());

		return cacheKeyBuilder.toString();
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

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.structureDao, "No IStructureDao configured !");
		Assert.notNull(this.structureCache, "No Structure cache configured !");
		Assert.notNull(this.etabsCodeIdCache, "No etabsCodeId cache configured !");
	}
}

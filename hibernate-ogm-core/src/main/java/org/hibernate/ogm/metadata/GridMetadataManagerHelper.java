package org.hibernate.ogm.metadata;

import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.ogm.grid.Key;

/**
 * Helper class to be refactored and abstracted from specific grid implementation
 * @author Emmanuel Bernard
 */
public class GridMetadataManagerHelper {
	public static final String ENTITY_CACHE = "ENTITIES";

	public static GridMetadataManager getGridMetadataManager(SessionFactoryImplementor factory) {
		final SessionFactoryObserver sessionFactoryObserver = factory.getFactoryObserver();
		if ( sessionFactoryObserver instanceof GridMetadataManager ) {
			return ( GridMetadataManager ) sessionFactoryObserver;
		}
		else {
			StringBuilder error = new StringBuilder("Cannot get CacheManager for OGM. ");
			if ( sessionFactoryObserver == null ) {
				error.append("SessionFactoryObserver not configured");
			}
			else {
				error.append("SessionFactoryObserver not of type " + GridMetadataManager.class);
			}
			throw new HibernateException( error.toString() );
		}
	}

	public static Cache<Key, Map<String, Object>> getEntityCache(SessionFactoryImplementor factory) {
		final CacheContainer cacheContainer = getGridMetadataManager(factory).getCacheContainer();
		final Cache<Key, Map<String, Object>> cache = cacheContainer.getCache( ENTITY_CACHE );
		return cache;
	}
}
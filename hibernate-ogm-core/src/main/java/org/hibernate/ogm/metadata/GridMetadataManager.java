package org.hibernate.ogm.metadata;

import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.CacheManager;
import org.infinispan.manager.DefaultCacheManager;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.ogm.type.TypeTranslator;

/**
 * Start and stop the Infinispan CacheManager with the SearchFactory
 * TODO abstract that to other grids
 *
 * @author Emmanuel Bernard
 */
public class GridMetadataManager implements SessionFactoryObserver {
	private CacheContainer manager;
	private final TypeTranslator typeTranslator;

	public GridMetadataManager() {
		typeTranslator = new TypeTranslator();
	}

	@Override
	public void sessionFactoryCreated(SessionFactory factory) {
		manager = new DefaultCacheManager( );
		manager.start();
	}

	//TODO abstract to other grids
	public CacheContainer getCacheContainer() { return manager; }

	//TODO move to a *Implementor interface
	public TypeTranslator getTypeTranslator() { return typeTranslator; }

	@Override
	public void sessionFactoryClosed(SessionFactory factory) {
		manager.stop();
	}
}
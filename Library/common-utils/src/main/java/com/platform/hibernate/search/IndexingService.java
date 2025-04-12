package com.platform.hibernate.search;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import com.platform.logging.Log;

import jakarta.persistence.EntityManager;

/**
 * @author Muhil
 */
@Configuration
public class IndexingService implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private EntityManager entityManager;

	@Transactional("transactionManager")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SearchSession searchSession = Search.session(entityManager);
		try {
			// searchSession.massIndexer().idFetchSize(1000).batchSizeToLoadObjects(500).threadsToLoadObjects(5).startAndWait();
			searchSession.massIndexer().start().thenRun(() -> Log.getLogger().info("Indexing completed!"));
		} catch (Exception e) {
			Log.platform.error(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}
	
	@Transactional("transactionManager")
    public void rebuildIndex() {
        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer()
            .start()
            .thenRun(() -> Log.getLogger().info("Re-Indexing completed!"));
    }

}

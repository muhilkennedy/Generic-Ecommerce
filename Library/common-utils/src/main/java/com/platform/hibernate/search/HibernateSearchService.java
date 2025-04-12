package com.platform.hibernate.search;

import java.util.List;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.platform.entity.MultiTenantEntity;
import com.platform.model.SearchFilterDTO;
import com.platform.server.BaseSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class HibernateSearchService {

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * @param class - entity
	 * @param keyword - search word
	 * @param fields - search fields on entity
	 * @return list of entities (default 10 results)
	 */
	public List<?> search(Class<?> cls, String keyword, String... fields) {
		return search(cls, keyword, 10, fields);
	}

	/**
	 * @param class - entity
	 * @param keyword - search word
	 * @param hits - total results expected
	 * @param fields - search fields on entity
	 * @return list of entities
	 */
	public List<?> search(Class<?> cls, String keyword, int hits, String... fields) {
		SearchSession searchSession = Search.session(entityManager);
		return searchSession.search(cls)
				.where(f -> f.bool()
						.must(f.match().fields(fields).matching(keyword))
						.must(f.match().field(MultiTenantEntity.KEY_TENANTID).matching(BaseSession.getTenantId())))
				.fetchHits(hits);
	}

	/**
	 * @param class - entity
	 * @param keyword - search word
	 * @param hits - total results expected
	 * @param fuzzyEditDistance - maxdistance to allow modifications like levenstein distance.
	 * @param fields - search fields on entity
	 * @return list of entities
	 */
	public List<?> search(Class<?> cls, String keyword, int hits, int fuzzyEditDistance, String... fields) {
		SearchSession searchSession = Search.session(entityManager);
		return searchSession.search(cls)
				.where(f -> f.bool()
						.must(f.match().fields(fields).matching(keyword).fuzzy(fuzzyEditDistance))
						.must(f.match().field(MultiTenantEntity.KEY_TENANTID).matching(BaseSession.getTenantId())))
				.fetchHits(hits);
	}
	
	/**
	 * @param cls - entity to query
	 * @param filters - List<SearchFilterDTO>
	 * @param pageSize
	 * @param pageNumber
	 * @param sortField
	 * @param sortOrder
	 * @return
	 * TODO: can be enhanced based on AND/OR combination of search criteria.
	 */
	public SearchResult<?> advancedSearch(Class<?> cls, List<SearchFilterDTO> filters, int pageSize, int pageNumber,
			String sortField, String sortOrder) {
		SearchSession searchSession = Search.session(entityManager);
		SearchResult<?> result = searchSession.search(cls)    
				.where(f -> {
		    	BooleanPredicateClausesStep<?> boolQuery = f.bool();
		        for (SearchFilterDTO filter : filters) {
		            switch (filter.getMatchMode()) {
		                case "contains":
		                    boolQuery.must(f.wildcard()
		                            .field(filter.getField())
		                            .matching("*" + filter.getValue() + "*"));
		                    break;
		                case "notContains":
		                    boolQuery.mustNot(f.wildcard()
		                            .field(filter.getField())
		                            .matching("*" + filter.getValue() + "*"));
		                    break;
		                case "startsWith":
		                    boolQuery.must(f.wildcard()
		                            .field(filter.getField())
		                            .matching(filter.getValue() + "*"));
		                    break;	
		                case "endsWith":
		                	boolQuery.must(f.wildcard()
		                            .field(filter.getField())
		                            .matching("*" + filter.getValue()));
		                    break;
		                case "equals":
		                	 boolQuery.must(f.match()
		                                .field(filter.getField())
		                                .matching(filter.getValue()));
		                        break;
		                case "notEquals":
		                	 boolQuery.mustNot(f.match()
		                                .field(filter.getField())
		                                .matching(filter.getValue()));
		                        break;
		                default: throw new IllegalArgumentException("Invalid Match Type");
		            }
		        }
		        return boolQuery.must(f.match().field(MultiTenantEntity.KEY_TENANTID).matching(BaseSession.getTenantId()));
		    })
		    .sort(f -> sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? f.field(sortField).asc() : f.field(sortField).desc()).toQuery()
		    .fetch((pageNumber - 1) * pageSize, pageSize);
		return result;
	}
	
	

}

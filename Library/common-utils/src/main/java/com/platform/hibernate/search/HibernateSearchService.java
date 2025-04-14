package com.platform.hibernate.search;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
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

/**
 * @author Muhil 
 */
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
		return search(cls, keyword, 25, fields);
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
		return searchSession.search(cls).where(f -> {
			BooleanPredicateClausesStep<?> bool = f.bool();
			BooleanPredicateClausesStep<?> orFields = f.bool();
			for (String field : fields) {
				orFields.should(f.wildcard().field(field).matching("*" + keyword + "*"));
			}
			bool.must(orFields);
			bool.must(f.match().field(MultiTenantEntity.KEY_TENANTID).matching(BaseSession.getTenantId()));
			return bool;
		}).fetchHits(hits);
		/*List<SearchFilterDTO> filters = new ArrayList<SearchFilterDTO>();
		for(String field: fields) {
			SearchFilterDTO filter = new SearchFilterDTO();
			filter.setField(field);
			filter.setOperator("OR");
			filter.setMatchMode("contains");
			filter.setValue(keyword);
			filters.add(filter);
		}
		SearchResult<?> result = advancedSearch(List.of(cls), filters, 10, 0, null, null);
		return result.hits();*/
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
		return advancedSearch(List.of(cls), filters, pageSize, pageNumber, sortField, sortOrder);
	}
	
	public SearchResult<?> advancedSearchMust(List<Class<?>> clsList, List<SearchFilterDTO> filters, int pageSize, int pageNumber,
			String sortField, String sortOrder) {
		SearchSession searchSession = Search.session(entityManager);
		SearchResult<?> result = searchSession.search(clsList)    
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
	
	public SearchResult<?> advancedSearch(List<Class<?>> clsList, List<SearchFilterDTO> filters, int pageSize,
			int pageNumber, String sortField, String sortOrder) {
		SearchSession searchSession = Search.session(entityManager);
		SearchResult<?> result = searchSession.search(clsList)
				.where(f -> {
					BooleanPredicateClausesStep<?> rootBool = f.bool();
					for (SearchFilterDTO filter : filters) {
						SearchPredicate predicate = buildPredicate(f, filter);
						if ("OR".equalsIgnoreCase(filter.getOperator())) {
							rootBool.should(predicate);
						} else {
							rootBool.must(predicate); // default is AND
						}
					}
					// Always filter by tenant
					rootBool.must(f.match().field(MultiTenantEntity.KEY_TENANTID).matching(BaseSession.getTenantId()));
					rootBool.minimumShouldMatchNumber(1);
					return rootBool;
				})
				.sort(f -> sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? f.field(sortField).asc() : f.field(sortField).desc())
				.toQuery()
				.fetch((pageNumber - 1) * pageSize, pageSize);
		return result;
	}

	private SearchPredicate buildPredicate(SearchPredicateFactory f, SearchFilterDTO filter) {
		String field = filter.getField();
		Object value = filter.getValue();
		return switch (filter.getMatchMode()) {
		case "contains" -> f.wildcard().field(field).matching("*" + value + "*").toPredicate();
		case "notContains" -> f.bool().mustNot(f.wildcard().field(field).matching("*" + value + "*")).toPredicate();
		case "startsWith" -> f.wildcard().field(field).matching(value + "*").toPredicate();
		case "endsWith" -> f.wildcard().field(field).matching("*" + value).toPredicate();
		case "equals" -> f.match().field(field).matching(value).toPredicate();
		case "notEquals" -> f.bool().mustNot(f.match().field(field).matching(value)).toPredicate();
		default -> throw new IllegalArgumentException("Invalid match mode: " + filter.getMatchMode());
		};
	}
	

}

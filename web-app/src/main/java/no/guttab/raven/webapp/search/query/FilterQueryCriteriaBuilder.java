package no.guttab.raven.webapp.search.query;

public interface FilterQueryCriteriaBuilder<T> {
   String buildQueryCriteria(T fieldValue);
}

package no.guttab.raven.search.query;

public interface FilterQueryCriteriaBuilder<T> {
   String buildQueryCriteria(T fieldValue);
}

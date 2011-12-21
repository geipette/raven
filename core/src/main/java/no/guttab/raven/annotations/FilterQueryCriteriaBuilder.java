package no.guttab.raven.annotations;

import java.util.Collection;

public interface FilterQueryCriteriaBuilder<T> {
   Collection<String> buildQueryCriterias(T fieldValue);
}

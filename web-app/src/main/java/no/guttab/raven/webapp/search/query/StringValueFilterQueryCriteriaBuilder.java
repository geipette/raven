package no.guttab.raven.webapp.search.query;

public class StringValueFilterQueryCriteriaBuilder implements FilterQueryCriteriaBuilder<Object> {
   @Override
   public String buildQueryCriteria(Object fieldValue) {
      return String.valueOf(fieldValue);
   }
}

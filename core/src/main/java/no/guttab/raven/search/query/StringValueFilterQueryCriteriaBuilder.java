package no.guttab.raven.search.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import no.guttab.raven.annotations.FilterQueryCriteriaBuilder;

public class StringValueFilterQueryCriteriaBuilder implements FilterQueryCriteriaBuilder<Object> {

   @Override
   public Collection<String> buildQueryCriterias(Object fieldValue) {
      if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
         return buildQueryCriteriasForCollection((Collection) fieldValue);
      } else if (fieldValue.getClass().isArray()) {
         return buildQueryCriteriasForCollection(Arrays.asList((Object[]) fieldValue));
      }
      return Arrays.asList(String.valueOf(fieldValue));
   }

   private Collection<String> buildQueryCriteriasForCollection(Collection fieldValue) {
      List<String> criterias = new ArrayList<String>(fieldValue.size());
      for (Object value : fieldValue) {
         criterias.add(String.valueOf(value));
      }
      return criterias;
   }
}

package no.guttab.raven.search.solr;

import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

public class NavigationField {

   private Set<String> fqs;

   public NavigationField(Set<String> fqs) {
      this.fqs = fqs;
   }

   public boolean hasSelection() {
      return !isEmpty(fqs);
   }

   public boolean isFilterQuerySelected(String filterQuery) {
      return fqs.contains(filterQuery);
   }
}

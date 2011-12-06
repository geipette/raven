package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.FacetField;

public class SelectNavigatorItem implements NavigatorItem {
   private FacetField.Count count;

   public SelectNavigatorItem(FacetField.Count count) {
      this.count = count;
   }
}

package no.guttab.raven.webapp.controller;

import no.guttab.raven.webapp.annotations.IndexFieldName;
import no.guttab.raven.webapp.search.response.SingleSelectNavigator;

public class SearchResponse {
   @IndexFieldName("cat")
   SingleSelectNavigator categoryNavigator;

   public SingleSelectNavigator getCategoryNavigator() {
      return categoryNavigator;
   }
}

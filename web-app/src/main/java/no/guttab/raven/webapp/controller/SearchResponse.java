package no.guttab.raven.webapp.controller;

import no.guttab.raven.webapp.annotations.IndexFieldName;
import no.guttab.raven.webapp.search.response.Navigators;
import no.guttab.raven.webapp.search.response.SingleSelectNavigator;

public class SearchResponse {
   Navigators navigators;

   public Navigators getNavigators() {
      return navigators;
   }
}

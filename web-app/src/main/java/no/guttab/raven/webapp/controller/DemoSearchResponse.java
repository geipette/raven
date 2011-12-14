package no.guttab.raven.webapp.controller;

import no.guttab.raven.search.response.SearchResponse;
import no.guttab.raven.search.response.navigators.Navigators;

public class DemoSearchResponse implements SearchResponse {
   private Navigators navigators;

   @Override
   public void setNavigators(Navigators navigators) {
      this.navigators = navigators;
   }

   public Navigators getNavigators() {
      return navigators;
   }
}

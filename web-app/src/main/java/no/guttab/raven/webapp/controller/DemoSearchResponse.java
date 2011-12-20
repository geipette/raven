package no.guttab.raven.webapp.controller;

import java.util.List;

import no.guttab.raven.search.response.SearchResponse;
import no.guttab.raven.search.response.navigators.Navigator;
import no.guttab.raven.search.response.navigators.Navigators;

public class DemoSearchResponse implements SearchResponse {
   private Navigators navigators;

   @Override
   public void setNavigators(Navigators navigators) {
      this.navigators = navigators;
   }

   public List<Navigator<?>> getSelectedNavigators() {
      return navigators.getSelectedNavigators();
   }

   public List<Navigator<?>> getNavigators() {
      return navigators.getNavigators();
   }
}

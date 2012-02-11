package no.guttab.raven.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.guttab.raven.search.response.navigators.Navigator;
import no.guttab.raven.search.response.navigators.Navigators;

public class SearchResponse<T> {
   private List<T> documents = new ArrayList<T>();

   private Navigators navigators;

   public void addDocument(T document) {
      documents.add(document);
   }

   public void setNavigators(Navigators navigators) {
      this.navigators = navigators;
   }

   public List<T> getDocuments() {
      return Collections.unmodifiableList(documents);
   }

   public List<Navigator<?>> getSelectedNavigators() {
      return navigators.getSelectedNavigators();
   }

   public List<Navigator<?>> getNavigators() {
      return navigators.getNavigators();
   }

}

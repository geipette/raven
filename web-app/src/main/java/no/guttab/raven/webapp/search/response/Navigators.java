package no.guttab.raven.webapp.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Navigators {
   private List<Navigator<?>> selectedNavigators = new ArrayList<Navigator<?>>();
   private List<Navigator<?>> navigators = new ArrayList<Navigator<?>>();

   public List<Navigator<?>> getSelectedNavigators() {
      return Collections.unmodifiableList(selectedNavigators);
   }

   public List<Navigator<?>> getNavigators() {
      return Collections.unmodifiableList(navigators);
   }

   public void addNavigator(Navigator<?> navigator) {
      if (navigator.isSelected()) {
         selectedNavigators.add(navigator);
      } else {
         navigators.add(navigator);
      }
   }
}

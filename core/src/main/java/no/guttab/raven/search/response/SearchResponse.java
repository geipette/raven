package no.guttab.raven.search.response;

import java.util.List;

import no.guttab.raven.search.response.navigators.Navigator;

public interface SearchResponse<T> {
   List<T> getDocuments();

   List<Navigator<?>> getSelectedNavigators();

   List<Navigator<?>> getNavigators();

   long getResultCount();
}

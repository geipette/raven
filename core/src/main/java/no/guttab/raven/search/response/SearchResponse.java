package no.guttab.raven.search.response;

import java.util.List;

public interface SearchResponse<T> {
    List<T> getDocuments();

    List<Navigator<?>> getSelectedNavigators();

    List<Navigator<?>> getNavigators();

    long getResultCount();
}

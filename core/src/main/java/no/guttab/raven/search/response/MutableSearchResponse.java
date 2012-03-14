package no.guttab.raven.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutableSearchResponse<T> implements SearchResponse<T> {
    private List<T> documents = new ArrayList<T>();
    private Navigators navigators;
    private long resultCount;

    @Override
    public List<T> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    public void addDocument(T document) {
        documents.add(document);
    }

    @Override
    public List<Navigator<?>> getSelectedNavigators() {
        return navigators.getSelectedNavigators();
    }

    @Override
    public List<Navigator<?>> getNavigators() {
        return navigators.getNavigators();
    }

    public void setNavigators(Navigators navigators) {
        this.navigators = navigators;
    }

    @Override
    public long getResultCount() {
        return resultCount;
    }

    public void setResultCount(long resultCount) {
        this.resultCount = resultCount;
    }
}

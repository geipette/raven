package no.guttab.raven.search.response;

public interface SearchResponseFactory<T extends SearchResponse> {
   T newInstance();
}

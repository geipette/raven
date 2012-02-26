package no.guttab.raven.search;

import no.guttab.raven.search.response.ResponseBuilder;

public interface SearchContext<T> {
    QueryBuilder queryBuilder();

    ResponseBuilder<T> responseBuilder();
}

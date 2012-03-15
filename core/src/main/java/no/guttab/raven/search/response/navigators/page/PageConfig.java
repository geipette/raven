package no.guttab.raven.search.response.navigators.page;

public interface PageConfig {
    String getPageRequestFieldName();

    String getStartRequestFieldName();

    Integer getResultsPerPage();
}

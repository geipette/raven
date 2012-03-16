package no.guttab.raven.search.response.navigators.page;

import org.apache.solr.client.solrj.response.QueryResponse;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PageNavigation {
    private static final int FIRST_PAGE = 1;
    private PageConfig pageConfig;
    private QueryResponse queryResponse;

    public PageNavigation(PageConfig pageConfig, QueryResponse queryResponse) {
        this.pageConfig = pageConfig;
        this.queryResponse = queryResponse;
    }

    public boolean requestHasPagination() {
        return !isEmpty(pageConfig.getPageRequestFieldName());
    }

    public long getNumberOfPages() {
        return queryResponse.getResults().getNumFound() / pageConfig.getResultsPerPage() + FIRST_PAGE;
    }

    public long getCurrentPage() {
        if (pageConfig.getResultsPerPage() == 0) {
            return FIRST_PAGE;
        } else {
            return calculatePage();
        }
    }

    public long getCurrentResultStartIndex() {
        return queryResponse.getResults().getStart();
    }

    public boolean resultsStartsAtZero() {
        return queryResponse.getResults().getStart() == 0;
    }

    public String getPageRequestFieldName() {
        return pageConfig.getPageRequestFieldName();
    }

    public String getStartRequestFieldName() {
        return pageConfig.getStartRequestFieldName();
    }

    public boolean requestHasStartIndexFieldName() {
        return !isEmpty(pageConfig.getStartRequestFieldName());
    }

    private long calculatePage() {
        return queryResponse.getResults().getStart() / pageConfig.getResultsPerPage() + FIRST_PAGE;
    }
}

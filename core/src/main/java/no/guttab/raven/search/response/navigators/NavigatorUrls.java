package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.response.UrlFragments;

public class NavigatorUrls {
    private final UrlFragments urlFragments;

    public NavigatorUrls(SearchRequestTypeInfo searchRequestTypeInfo) {
        urlFragments = new UrlFragments(searchRequestTypeInfo);
    }

    public void addUrlFragment(String indexFieldName, String fqCriteria) {
        urlFragments.addFragment(indexFieldName, fqCriteria);
    }

    public String resetUrlFor(String indexFieldName) {
        final UrlFragments significantFragments = urlFragments.withoutIndexField(indexFieldName);
        return buildUrlFor(significantFragments);
    }

    public String resetUrlFor(String indexFieldName, String fqCriteria) {
        final UrlFragments significantFragments = urlFragments.withoutFragment(indexFieldName, fqCriteria);
        return buildUrlFor(significantFragments);
    }

    public String buildUrlFor(String indexFieldName, String fqCriteria) {
        final UrlFragments significantFragments = urlFragments.withAddedFragment(indexFieldName, fqCriteria);
        return buildUrlFor(significantFragments);
    }

    private String buildUrlFor(UrlFragments significantFragments) {
        final StringBuilder urlBuilder = new StringBuilder();
        for (UrlFragments.UrlFragmentEntry urlFragmentEntry : significantFragments) {
            appendAmpersandIfNeeded(urlBuilder);
            appendFragment(urlBuilder, urlFragmentEntry);
        }
        prependUrlQueryString(urlBuilder);
        return urlBuilder.toString();
    }

    private void prependUrlQueryString(StringBuilder urlBuilder) {
        urlBuilder.insert(0, '?');
    }

    private void appendFragment(StringBuilder urlBuilder, UrlFragments.UrlFragmentEntry urlFragmentEntry) {
        urlBuilder.append(urlFragmentEntry.getFragment());
    }

    private void appendAmpersandIfNeeded(StringBuilder urlBuilder) {
        if (isNotFirstIncludedFragment(urlBuilder)) {
            urlBuilder.append('&');
        }
    }

    private boolean isNotFirstIncludedFragment(StringBuilder urlBuilder) {
        return urlBuilder.length() > 0;
    }

}

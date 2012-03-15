package no.guttab.raven.search.response.navigators;

import java.util.HashSet;
import java.util.Set;

public class NavigatorUrls {
    private final UrlFragments urlFragments;
    private Set<String> volatileFragmentIndexFieldNames = new HashSet<String>();

    public NavigatorUrls(SearchRequestTypeInfo searchRequestTypeInfo) {
        urlFragments = new UrlFragments(searchRequestTypeInfo);
    }

    public void addUrlFragment(String indexFieldName, String fqCriteria) {
        urlFragments.addFragment(indexFieldName, fqCriteria);
    }

    public void addVolatileUrlFragment(String indexFieldName, String fqCriteria) {
        volatileFragmentIndexFieldNames.add(indexFieldName);
        addUrlFragment(indexFieldName, fqCriteria);
    }

    public String resetUrlFor(String indexFieldName) {
        final UrlFragments significantFragments = urlFragments.withoutIndexField(indexFieldName);
        return buildUrlFor(significantFragments, indexFieldName);
    }

    private boolean isVolatile(String indexFieldName) {
        return volatileFragmentIndexFieldNames.contains(indexFieldName);
    }

    public String resetUrlFor(String indexFieldName, String fqCriteria) {
        final UrlFragments significantFragments = urlFragments.withoutFragment(indexFieldName, fqCriteria);
        return buildUrlFor(significantFragments, indexFieldName);
    }


    public String buildUrlFor(String indexFieldName, String fqCriteria) {
        final UrlFragments significantFragments = urlFragments.withAddedFragment(indexFieldName, fqCriteria);
        return buildUrlFor(significantFragments, indexFieldName);
    }

    private String buildUrlFor(UrlFragments significantFragments, String indexFieldName) {
        final StringBuilder urlBuilder = new StringBuilder();
        final UrlFragments usedFragments = applyVolatility(indexFieldName, significantFragments);
        appendFragmentsFor(usedFragments, urlBuilder);
        prependUrlQueryString(urlBuilder);
        return urlBuilder.toString();
    }

    private UrlFragments applyVolatility(String indexFieldName, UrlFragments significantFragments) {
        if (isVolatile(indexFieldName)) {
            return significantFragments;
        } else {
            return significantFragments.withoutIndexFields(volatileFragmentIndexFieldNames);
        }
    }

    private void appendFragmentsFor(UrlFragments significantFragments, StringBuilder urlBuilder) {
        for (UrlFragmentEntry urlFragmentEntry : significantFragments) {
            appendAmpersandIfNeeded(urlBuilder);
            appendFragment(urlBuilder, urlFragmentEntry);
        }
    }

    private void prependUrlQueryString(StringBuilder urlBuilder) {
        urlBuilder.insert(0, '?');
    }

    private void appendFragment(StringBuilder urlBuilder, UrlFragmentEntry urlFragmentEntry) {
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

package no.guttab.raven.search.response;

import no.guttab.raven.search.SearchRequestTypeInfo;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UrlFragments implements Iterable<UrlFragmentEntry> {
    final MultiValueMap<String, UrlFragment> urlFragmentMap = new LinkedMultiValueMap<String, UrlFragment>();
    private SearchRequestTypeInfo searchRequestTypeInfo;


    public UrlFragments(SearchRequestTypeInfo searchRequestTypeInfo) {
        this.searchRequestTypeInfo = searchRequestTypeInfo;
    }

    public void addFragment(String indexFieldName, String fqCriteria) {
        addFragment(indexFieldName, new UrlFragment(searchRequestTypeInfo.requestFieldNameFor(indexFieldName), fqCriteria));
    }

    public void addFragment(String indexFieldName, UrlFragment fragment) {
        if (hasFragment(indexFieldName, fragment)) {
            return;
        }
        if (searchRequestTypeInfo.isIndexFieldMultiSelect(indexFieldName)) {
            urlFragmentMap.add(indexFieldName, fragment);
        } else {
            urlFragmentMap.set(indexFieldName, fragment);
        }
    }

    public UrlFragments withAddedFragment(String indexFieldName, String fqCriteria) {
        return withAddedFragment(indexFieldName,
                new UrlFragment(searchRequestTypeInfo.requestFieldNameFor(indexFieldName), fqCriteria));
    }

    public UrlFragments withoutFragment(String indexFieldName, String fqCriteria) {
        return withoutFragment(
                new UrlFragment(searchRequestTypeInfo.requestFieldNameFor(indexFieldName), fqCriteria));
    }

    public UrlFragments withAddedFragment(String indexFieldName, UrlFragment fragment) {
        final UrlFragments urlFragments = new UrlFragments(searchRequestTypeInfo);
        copyUrlFragmentEntries(this, urlFragments);
        addFragment(indexFieldName, fragment, urlFragments);
        return urlFragments;
    }

    public UrlFragments withoutFragment(UrlFragment fragment) {
        final UrlFragments urlFragments = new UrlFragments(searchRequestTypeInfo);
        for (UrlFragmentEntry urlFragmentEntry : this) {
            addFragmentEntryIfNotEqualTo(fragment, urlFragmentEntry, urlFragments);
        }
        return urlFragments;
    }

    private void addFragmentEntryIfNotEqualTo(UrlFragment fragment, UrlFragmentEntry urlFragmentEntry, UrlFragments urlFragments) {
        if (!fragment.equals(urlFragmentEntry.getFragment())) {
            urlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
        }
    }

    public UrlFragments withoutIndexField(String indexFieldName) {
        return withoutIndexFields(Arrays.asList(indexFieldName));
    }

    public UrlFragments withoutIndexFields(Collection<String> indexFieldNames) {
        final UrlFragments urlFragments = new UrlFragments(searchRequestTypeInfo);
        for (UrlFragmentEntry urlFragmentEntry : this) {
            if (!indexFieldNames.contains(urlFragmentEntry.getIndexFieldName())) {
                urlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
            }
        }
        return urlFragments;
    }

    private void addFragment(String indexFieldName, UrlFragment fragment, UrlFragments urlFragments) {
        urlFragments.addFragment(indexFieldName, fragment);
    }

    private void copyUrlFragmentEntries(UrlFragments srcUrlFragments, UrlFragments destUrlFragments) {
        for (UrlFragmentEntry urlFragmentEntry : srcUrlFragments) {
            destUrlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
        }
    }

    private boolean hasFragment(String indexFieldName, UrlFragment fragment) {
        final List<UrlFragment> fragments = urlFragmentMap.get(indexFieldName);
        return fragments != null && fragments.contains(fragment);
    }

    @Override
    public Iterator<UrlFragmentEntry> iterator() {
        return new UrlFragmentEntryIterator(this);
    }

    @Override
    public String toString() {
        return "UrlFragments{" +
                "urlFragmentMap=" + urlFragmentMap +
                '}';
    }
}

package no.guttab.raven.search.response;

public class UrlFragmentEntry {
    private String indexFieldName;

    private UrlFragment fragment;

    public UrlFragmentEntry(String indexFieldName, UrlFragment fragment) {
        this.indexFieldName = indexFieldName;
        this.fragment = fragment;
    }

    public String getIndexFieldName() {
        return indexFieldName;
    }

    public UrlFragment getFragment() {
        return fragment;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "indexFieldName='" + indexFieldName + '\'' +
                ", fragment=[" + fragment + ']' +
                '}';
    }
}

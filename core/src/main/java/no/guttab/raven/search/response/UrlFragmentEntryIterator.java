package no.guttab.raven.search.response;

import java.util.*;

class UrlFragmentEntryIterator implements Iterator<UrlFragmentEntry> {
    private Iterator<Map.Entry<String, List<UrlFragment>>> entryIterator;
    private Iterator<UrlFragment> fragmentIterator = Collections.<UrlFragment>emptyList().iterator();
    private Map.Entry<String, List<UrlFragment>> currentFragmentEntry;

    public UrlFragmentEntryIterator(UrlFragments urlFragments) {
        entryIterator = urlFragments.urlFragmentMap.entrySet().iterator();
    }

    @Override
    public boolean hasNext() {
        if (fragmentIterator.hasNext()) {
            return true;
        } else if (entryIterator.hasNext()) {
            currentFragmentEntry = entryIterator.next();
            fragmentIterator = currentFragmentEntry.getValue().iterator();
            return true;
        }
        return false;
    }

    @Override
    public UrlFragmentEntry next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return new UrlFragmentEntry(currentFragmentEntry.getKey(), fragmentIterator.next());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

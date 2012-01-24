package no.guttab.raven.search.response;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import no.guttab.raven.search.config.SearchRequestConfig;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class UrlFragments implements Iterable<UrlFragments.UrlFragmentEntry> {
   private final MultiValueMap<String, UrlFragment> urlFragmentMap = new LinkedMultiValueMap<String, UrlFragment>();
   private SearchRequestConfig searchRequestConfig;


   public UrlFragments(SearchRequestConfig searchRequestConfig) {
      this.searchRequestConfig = searchRequestConfig;
   }

   public void addFragment(String indexFieldName, String fqCriteria) {
      addFragment(indexFieldName, new UrlFragment(searchRequestConfig.requestFieldNameFor(indexFieldName), fqCriteria));
   }

   public void addFragment(String indexFieldName, UrlFragment fragment) {
      if (searchRequestConfig.isIndexFieldMultiSelect(indexFieldName)) {
         urlFragmentMap.add(indexFieldName, fragment);
      } else {
         urlFragmentMap.set(indexFieldName, fragment);
      }
   }

   public UrlFragments withAddedFragment(String indexFieldName, String fqCriteria) {
      return withAddedFragment(indexFieldName,
            new UrlFragment(searchRequestConfig.requestFieldNameFor(indexFieldName), fqCriteria));
   }

   public UrlFragments withoutFragment(String indexFieldName, String fqCriteria) {
      return withoutFragment(
            new UrlFragment(searchRequestConfig.requestFieldNameFor(indexFieldName), fqCriteria));
   }

   public UrlFragments withAddedFragment(String indexFieldName, UrlFragment fragment) {
      final UrlFragments urlFragments = new UrlFragments(searchRequestConfig);
      for (UrlFragmentEntry urlFragmentEntry : this) {
         urlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
      }
      if (!urlFragments.hasFragment(indexFieldName, fragment)) {
         urlFragments.addFragment(indexFieldName, fragment);
      }
      return urlFragments;
   }

   public UrlFragments withoutFragment(UrlFragment fragment) {
      final UrlFragments urlFragments = new UrlFragments(searchRequestConfig);
      for (UrlFragmentEntry urlFragmentEntry : this) {
         if (!fragment.equals(urlFragmentEntry.getFragment())) {
            urlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
         }
      }
      return urlFragments;
   }

   public UrlFragments withoutIndexField(String indexFieldName) {
      final UrlFragments urlFragments = new UrlFragments(searchRequestConfig);
      for (UrlFragmentEntry urlFragmentEntry : this) {
         if (!indexFieldName.equals(urlFragmentEntry.getIndexFieldName())) {
            urlFragments.addFragment(urlFragmentEntry.getIndexFieldName(), urlFragmentEntry.getFragment());
         }
      }
      return urlFragments;
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

   public static class UrlFragmentEntry {
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

   private static class UrlFragmentEntryIterator implements Iterator<UrlFragmentEntry> {
      private Iterator<Map.Entry<String, List<UrlFragment>>> entryIterator;
      private Iterator<UrlFragment> fragmentIterator = Collections.<UrlFragment>emptyList().iterator();
      private Map.Entry<String,List<UrlFragment>> currentFragmentEntry;

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
}

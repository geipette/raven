package no.guttab.raven.search.response;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import no.guttab.raven.search.config.SearchRequestConfig;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class UrlFragments implements Iterable<UrlFragments.Entry> {
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
      for (Entry entry : this) {
         urlFragments.addFragment(entry.getIndexFieldName(), entry.getFragment());
      }
      urlFragments.addFragment(indexFieldName, fragment);
      return urlFragments;
   }

   public UrlFragments withoutFragment(UrlFragment fragment) {
      final UrlFragments urlFragments = new UrlFragments(searchRequestConfig);
      for (Entry entry : this) {
         if (!fragment.equals(entry.getFragment())) {
            urlFragments.addFragment(entry.getIndexFieldName(), entry.getFragment());
         }
      }
      return urlFragments;
   }

   public UrlFragments withoutIndexField(String indexFieldName) {
      final UrlFragments urlFragments = new UrlFragments(searchRequestConfig);
      for (Entry entry : this) {
         if (!indexFieldName.equals(entry.getIndexFieldName())) {
            urlFragments.addFragment(entry.getIndexFieldName(), entry.getFragment());
         }
      }
      return urlFragments;
   }

   @Override
   public Iterator<Entry> iterator() {
      return new Iterator<Entry>() {
         Iterator<Map.Entry<String, List<UrlFragment>>> entryIterator = urlFragmentMap.entrySet().iterator();
         Iterator<UrlFragment> fragmentIterator = Collections.<UrlFragment>emptyList().iterator();
         Map.Entry<String,List<UrlFragment>> currentFragmentEntry;

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
         public Entry next() {
            if (!hasNext()) {
               throw new NoSuchElementException();
            }
            return new Entry(currentFragmentEntry.getKey(), fragmentIterator.next());
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static class Entry {
      private String indexFieldName;
      private UrlFragment fragment;

      public Entry(String indexFieldName, UrlFragment fragment) {
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


   @Override
   public String toString() {
      return "UrlFragments{" +
            "urlFragmentMap=" + urlFragmentMap +
            '}';
   }
}

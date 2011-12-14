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

   public void addFragment(String indexFieldName, UrlFragment fragment) {
      if (searchRequestConfig.isIndexFieldMultiSelect(indexFieldName)) {
         urlFragmentMap.add(indexFieldName, fragment);
      } else {
         urlFragmentMap.set(indexFieldName, fragment);
      }
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
      private String key;
      private UrlFragment fragment;

      public Entry(String key, UrlFragment fragment) {
         this.key = key;
         this.fragment = fragment;
      }

      public String getKey() {
         return key;
      }

      public UrlFragment getFragment() {
         return fragment;
      }
   }



}

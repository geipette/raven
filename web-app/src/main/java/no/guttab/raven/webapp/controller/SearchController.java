package no.guttab.raven.webapp.controller;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import no.guttab.raven.search.DefaultSearch;
import no.guttab.raven.search.RavenSearcher;
import no.guttab.raven.search.SearchServer;
import no.guttab.raven.search.UrlBasedSearchResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

   private SearchServer searchServer;
   private RavenSearcher<DemoSearchResponse> searcher;

   public SearchController() {
      String solrServerUrl = "http://localhost:8093";
      try {
         searchServer = new SearchServer(new UrlBasedSearchResource(solrServerUrl));
         searcher = new RavenSearcher<DemoSearchResponse>(searchServer);
      } catch (MalformedURLException e) {
         throw new CouldNotInitializeSolrServerException(solrServerUrl, e);
      }
   }

   @RequestMapping(value = {"/search", "/"})
   public ModelAndView search(@Valid DemoSearchRequest searchRequest) {
      final DefaultSearch<DemoSearchResponse> search =
            new DefaultSearch<DemoSearchResponse>(searchRequest, DemoSearchResponse.class);
      DemoSearchResponse searchResponse = searcher.search(search);

      Map<String, Object> modelMap = new HashMap<String, Object>();
      modelMap.put("searchResponse", searchResponse);

      return new ModelAndView("search-result", modelMap);
   }

   private class CouldNotInitializeSolrServerException extends RuntimeException {
      public CouldNotInitializeSolrServerException(String solrServerUrl, MalformedURLException e) {
         super("Could not initialize solr server at: " + solrServerUrl, e);
      }
   }
}

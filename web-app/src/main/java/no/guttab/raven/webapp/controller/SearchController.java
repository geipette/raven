package no.guttab.raven.webapp.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import no.guttab.raven.search.SearchServer;
import no.guttab.raven.search.query.QueryProcessor;
import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.SearchResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController implements InitializingBean {
   @Autowired
   SearchServer searchServer;

   @Autowired
   QueryProcessor queryProcessor;

   @Autowired
   ResponseProcessor responseProcessor;

   private RavenSearcher<SearchRequest, DemoSearchResponse> searcher;

   @Override
   public void afterPropertiesSet() throws Exception {
      searcher = new RavenSearcher<SearchRequest, DemoSearchResponse>(
            DemoSearchResponse.class,
            responseProcessor,
            searchServer,
            queryProcessor
      );
   }

   @RequestMapping(value = {"/search", "/"})
   public ModelAndView search(@Valid SearchRequest searchRequest) {
      DemoSearchResponse searchResponse = searcher.search(searchRequest);

      Map<String, Object> modelMap = new HashMap<String, Object>();
//      modelMap.put("queryResponse", queryResponse);
      modelMap.put("searchResponse", searchResponse);

      return new ModelAndView("search-result", modelMap);
   }

   public static class RavenSearcher<S, R extends SearchResponse> {
      private Class<R> responseType;
      private SearchServer searchServer;
      private ResponseProcessor responseProcessor;
      private QueryProcessor queryProcessor;

      public RavenSearcher(
            Class<R> responseType,
            ResponseProcessor responseProcessor,
            SearchServer searchServer,
            QueryProcessor queryProcessor) {
         this.responseType = responseType;
         this.searchServer = searchServer;
         this.responseProcessor = responseProcessor;
         this.queryProcessor = queryProcessor;
      }

      public R search(S searchRequest) {
         SolrQuery solrQuery = new SolrQuery();
         queryProcessor.buildQuery(searchRequest, solrQuery);
         QueryResponse queryResponse = searchServer.search(solrQuery);
         R searchResponse = instantiateResponse();
         responseProcessor.processResponse(queryResponse, searchResponse);
         return searchResponse;
      }

      public R instantiateResponse() {
         try {
            return responseType.newInstance();
         } catch (InstantiationException e) {
            throw new IllegalArgumentException(
                  "ResponseType: " + responseType.getName() + " can not be instantiated. Does it have a default constructor?", e);
         } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                  "ResponseType:" + responseType.getName() + " can not be instantiated.", e);
         }
      }
   }
}

package no.guttab.raven.webapp.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import no.guttab.raven.search.SearchServer;
import no.guttab.raven.search.query.QueryProcessor;
import no.guttab.raven.search.response.ResponseProcessor;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
   @Autowired
   SearchServer searchServer;

   @Autowired
   QueryProcessor queryProcessor;

   @Autowired
   ResponseProcessor responseProcessor;


   @RequestMapping(value = {"/"})
   public ModelAndView helloWorld(@Valid Hello hello) {
      return new ModelAndView("hello", "hello", hello);
   }

   @RequestMapping(value = {"/search"})
   public ModelAndView search(@Valid SearchRequest searchRequest, BindingResult bindingResult) {
      SolrQuery solrQuery = new SolrQuery();
      queryProcessor.buildQuery(searchRequest, solrQuery);
      QueryResponse queryResponse = searchServer.search(solrQuery);
      DemoSearchResponse demoSearchResponse = new DemoSearchResponse();
      responseProcessor.processResponse(queryResponse, demoSearchResponse);

      Map<String, Object> modelMap = new HashMap<String, Object>();
      modelMap.put("queryResponse", queryResponse);
      modelMap.put("searchResponse", demoSearchResponse);

      return new ModelAndView("search-result", modelMap);
   }

}

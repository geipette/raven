package no.guttab.raven.webapp.controller;

import javax.validation.Valid;

import no.guttab.raven.webapp.search.SearchServer;
import no.guttab.raven.webapp.search.query.QueryProcessor;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
   @Autowired
   SearchServer searchServer;

   @Autowired
   QueryProcessor queryProcessor;


   @RequestMapping(value = {"/"})
   public ModelAndView helloWorld(@Valid Hello hello) {
      return new ModelAndView("hello", "hello", hello);
   }

   @RequestMapping(value = {"/search"})
   public ModelAndView search(SearchRequest searchRequest) {
      SolrQuery solrQuery = new SolrQuery();
      queryProcessor.buildQuery(searchRequest, solrQuery);
      return new ModelAndView("search-result", "searchResponse", searchServer.search(solrQuery));
   }

}

package no.guttab.raven.webapp.controller;

import no.guttab.raven.webapp.command.SearchRequest;
import no.guttab.raven.webapp.search.SearchServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
   @Autowired
   private SearchServer searchServer;

   @RequestMapping(value = {"/"})
   public ModelAndView helloWorld(Hello hello) {
      return new ModelAndView("hello", "hello", hello);
   }

   @RequestMapping(value = {"/search"})
   public ModelAndView search(SearchRequest searchRequest) {

      return new ModelAndView("search-result", "searchResponse", null);
   }

}

package no.guttab.raven.webapp.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
   @RequestMapping(value = {"/"})
   public ModelAndView helloWorld(@Valid Hello hello) {
      return new ModelAndView("hello", "hello", hello);
   }

   @RequestMapping(value = {"/search"})
   public ModelAndView search(SearchRequest searchRequest) {

      return new ModelAndView("search-result", "searchResponse", null);
   }

}

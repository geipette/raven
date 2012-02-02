package no.guttab.raven.webapp.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

   @Autowired
   RavenSearcher<SearchRequest, DemoSearchResponse> searcher;

   @RequestMapping(value = {"/search", "/"})
   public ModelAndView search(@Valid SearchRequest searchRequest) {
      DemoSearchResponse searchResponse = searcher.search(searchRequest);

      Map<String, Object> modelMap = new HashMap<String, Object>();
      modelMap.put("searchResponse", searchResponse);

      return new ModelAndView("search-result", modelMap);
   }

}

package no.guttab.raven.webapp.controller;

import no.guttab.raven.search.RavenSearcher;
import no.guttab.raven.search.SearchServer;
import no.guttab.raven.search.UrlBasedSearchResource;
import no.guttab.raven.search.response.SearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchController {

    private RavenSearcher<DemoDocument> searcher;

    public SearchController() {
        String solrServerUrl = "http://localhost:8093";
        try {
            SearchServer searchServer = new SearchServer(new UrlBasedSearchResource(solrServerUrl));
            searcher = new RavenSearcher<DemoDocument>(searchServer);
        } catch (MalformedURLException e) {
            throw new CouldNotInitializeSolrServerException(solrServerUrl, e);
        }
    }

    @RequestMapping(value = {"/search", "/"})
    public ModelAndView search(@Valid DemoSearchRequest searchRequest) {
        SearchResponse<DemoDocument> searchResponse = searcher.search(searchRequest, DemoDocument.class);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("searchResponse", searchResponse);

        return new ModelAndView("search-result", modelMap);
    }

    public static class CouldNotInitializeSolrServerException extends RuntimeException {
        public CouldNotInitializeSolrServerException(String solrServerUrl, MalformedURLException e) {
            super("Could not initialize solr server at: " + solrServerUrl, e);
        }
    }
}

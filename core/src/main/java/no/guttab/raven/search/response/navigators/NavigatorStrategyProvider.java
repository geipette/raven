package no.guttab.raven.search.response.navigators;

import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public interface NavigatorStrategyProvider {
    List<NavigatorStrategy> initStrategies(QueryResponse queryResponse);
}

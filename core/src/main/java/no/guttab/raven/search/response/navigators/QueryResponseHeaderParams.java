package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.filter.FilterQueries;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.util.Assert.notNull;

public class QueryResponseHeaderParams {

    private final SimpleOrderedMap<?> map;
    private FilterQueries filterQueries;

    public QueryResponseHeaderParams(NamedList<?> queryResponseHeader) {
        notNull(queryResponseHeader);
        map = (SimpleOrderedMap<?>) queryResponseHeader.get("params");
    }

    public FilterQueries getFilterQueries() {
        if (filterQueries == null) {
            filterQueries = new FilterQueries(getFqList());
        }
        return filterQueries;
    }

    public String getSort() {
        return (String) map.get("sort");
    }

    @SuppressWarnings({"unchecked"})
    private List<String> getFqList() {
        final List<String> fqs = new ArrayList<String>();

        for (Object fq : map.getAll("fq")) {
            if (fq instanceof String) {
                fqs.add((String) fq);
            } else if (fq instanceof List) {
                fqs.addAll((Collection<? extends String>) fq);
            }
        }

        return fqs;
    }
}

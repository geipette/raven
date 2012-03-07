package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorUrls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;

class SortNavigatorUrlsProcessor {
    private Pattern sortPattern = Pattern.compile("(?:^|,)(.*?)(asc|desc)");
    private String sortValue;
    private String sortFieldName;

    public SortNavigatorUrlsProcessor(String sortFieldName, String sortValue) {
        this.sortFieldName = sortFieldName;
        this.sortValue = sortValue;
    }

    public void process(NavigatorUrls navigatorUrls) {
        if (sortFieldName_And_SortValue_ContainsData()) {
            navigatorUrls.addUrlFragment(sortFieldName, urlFragmentSortValue());
        }
    }

    private boolean sortFieldName_And_SortValue_ContainsData() {
        return !(isEmpty(sortValue) || isEmpty(sortFieldName));
    }

    private String urlFragmentSortValue() {
        StringBuilder sortValueBuilder = new StringBuilder();
        final Matcher matcher = sortPattern.matcher(sortValue);
        while (matcher.find()) {
            appendSortValueForMatch(sortValueBuilder, matcher);
        }
        return sortValueBuilder.toString();
    }

    private void appendSortValueForMatch(StringBuilder sortValueBuilder, Matcher matcher) {
        appendCommaSeparatorIfRequired(sortValueBuilder);
        appendSingleSortValue(sortValueBuilder, matcher);
    }

    private void appendSingleSortValue(StringBuilder sortValueBuilder, Matcher matcher) {
        String directionString = matcher.group(2);
        if ("desc".equals(directionString)) {
            sortValueBuilder.append('-');
        }
        sortValueBuilder.append(matcher.group(1).trim());
    }

    private void appendCommaSeparatorIfRequired(StringBuilder sortValueBuilder) {
        if (sortValueBuilderHasContent(sortValueBuilder)) {
            sortValueBuilder.append(", ");
        }
    }

    private boolean sortValueBuilderHasContent(StringBuilder sortValueBuilder) {
        return sortValueBuilder.length() > 0;
    }
}

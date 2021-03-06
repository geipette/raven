package no.guttab.raven.search.response;

public interface Navigator<T extends NavigatorItem> extends NavigatorItems<T> {
    String getDisplayName();

    boolean isSelected();

    T getFirstSelectedItem();

}

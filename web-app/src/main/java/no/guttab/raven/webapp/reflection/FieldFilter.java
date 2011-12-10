package no.guttab.raven.webapp.reflection;

import java.lang.reflect.Field;

public interface FieldFilter {
   boolean matches(Field field);
}

package no.guttab.raven.reflection;

import java.lang.reflect.Field;

public interface FieldFilter {
   boolean matches(Field field);
}

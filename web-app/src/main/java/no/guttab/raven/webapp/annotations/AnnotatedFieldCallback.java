package no.guttab.raven.webapp.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotatedFieldCallback<T extends Annotation> {
   void doFor(Field field, T annotation);
}

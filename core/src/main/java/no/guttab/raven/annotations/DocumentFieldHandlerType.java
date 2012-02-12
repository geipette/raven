package no.guttab.raven.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import no.guttab.raven.search.response.content.DocumentFieldHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DocumentFieldHandlerType {
   Class<? extends DocumentFieldHandler> value();
}

package no.guttab.raven.search.response;

import no.guttab.raven.search.response.navigators.Navigators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultConstructorSearchResponseFactory.class, DefaultConstructorSearchResponseFactoryTest.TestSearchResponse.class})
public class DefaultConstructorSearchResponseFactoryTest {

   @Test
   public void newInstance_should_create_a_new_instance_of_type_using_the_default_constructor() throws Exception {
      TestSearchResponse expectedResponse = new TestSearchResponse();
      whenNewInstanceOnSearchResponse().thenReturn(expectedResponse);

      DefaultConstructorSearchResponseFactory<TestSearchResponse> responseFactory =
            new DefaultConstructorSearchResponseFactory<TestSearchResponse>(TestSearchResponse.class);
      TestSearchResponse actual = responseFactory.newInstance();

      assertThat(actual, sameInstance(expectedResponse));
   }

   @Test(expected = DefaultConstructorSearchResponseFactory.CouldNotInstantiateResponseTypeException.class)
   public void newInstance_should_throw_expected_exception_when_newInstance_throws_InstantiationException() throws Exception {
      whenNewInstanceOnSearchResponse().thenThrow(new InstantiationException());

      DefaultConstructorSearchResponseFactory<TestSearchResponse> responseFactory =
            new DefaultConstructorSearchResponseFactory<TestSearchResponse>(TestSearchResponse.class);
      responseFactory.newInstance();
   }

   @Test(expected = DefaultConstructorSearchResponseFactory.CouldNotInstantiateResponseTypeException.class)
   public void newInstance_should_throw_expected_exception_when_newInstance_throws_IllegalAccessException() throws Exception {
      whenNewInstanceOnSearchResponse().thenThrow(new IllegalAccessException());

      DefaultConstructorSearchResponseFactory<TestSearchResponse> responseFactory =
            new DefaultConstructorSearchResponseFactory<TestSearchResponse>(TestSearchResponse.class);
      responseFactory.newInstance();
   }


   private OngoingStubbing<TestSearchResponse> whenNewInstanceOnSearchResponse() throws InstantiationException, IllegalAccessException {
      PowerMockito.mockStatic(DefaultConstructorSearchResponseFactoryTest.TestSearchResponse.class);
      return when(TestSearchResponse.class.newInstance());
   }

   class TestSearchResponse implements SearchResponse {
      @Override
      public void setNavigators(Navigators navigators) {
      }
   }


}

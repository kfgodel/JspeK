package ar.com.dgarcia.javaspec.api;

/**
 * This type is implemented by classes that define test without requiring inheritance
 * Created by tenpines on 02/01/16.
 */
public interface JavaSpecTestable<T extends TestContext> {

  /**
   * Implemented by subtypes to define the specs that should be run as this test instance
   * @param spec The spec api to define the specs
   */
  void defineSpecs(JavaSpecApi<T> spec);

}

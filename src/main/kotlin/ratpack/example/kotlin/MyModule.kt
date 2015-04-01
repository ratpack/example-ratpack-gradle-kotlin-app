package ratpack.example.kotlin

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import ratpack.handling.HandlerDecorator

/**
 * An example Guice module.
 */
class MyModule : AbstractModule() {

  /**
   * Adds a service impl to the application, and registers a decorator so that all requests are logged.
   * Registered implementations of {@link ratpack.handling.HandlerDecorator} are able to decorate the application handler.
   *
   * @see MyHandler
   */
  protected override fun configure() {
    bind(javaClass<MyService>()).to(javaClass<MyServiceImpl>())
    bind(javaClass<MyHandler>())
    Multibinder.newSetBinder(binder(), javaClass<HandlerDecorator>())
        .addBinding()
        .toInstance(HandlerDecorator.prepend(LoggingHandler()))
  }
}

package ratpack.example.kotlin

import com.google.inject.AbstractModule
import com.google.inject.Injector
import ratpack.guice.HandlerDecoratingModule
import ratpack.handling.Handler
import ratpack.handling.Handlers.chain

/**
 * An example Guice module.
 */
class MyModule : AbstractModule(), HandlerDecoratingModule {

  /**
   * Adds a service impl to the application.
   *
   * @see MyHandler
   */
  protected override fun configure() {
    bind(javaClass<MyService>()).to(javaClass<MyServiceImpl>())
  }

  /**
   * Modules that implement {@link HandlerDecoratingModule} are able to decorate the application handler in some way.
   * <p/>
   * In this case, we are wrapping that app handler in a logging handler so that all requests are logged
   *
   * @see HandlerDecoratingModule
   */
  override fun decorate(injector : Injector?, handler : Handler?) = chain(LoggingHandler(), handler)
}

fun AbstractModule.chain(vararg handlers : Handler) = chain(handlers.toList())

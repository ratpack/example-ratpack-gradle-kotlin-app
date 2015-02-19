package ratpack.example.kotlin

import ratpack.handling.Context
import ratpack.handling.Handler
import javax.inject.Singleton
import javax.inject.Inject

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 * @see HandlerFactory
 */
Singleton class MyHandler [Inject] (private val myService : MyService) : Handler {
  override fun handle(context : Context) {
    context.getResponse().send("service value: ${myService.getValue()}")
  }
}

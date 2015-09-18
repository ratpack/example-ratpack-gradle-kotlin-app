package ratpack.example.kotlin

import ratpack.handling.Context
import ratpack.handling.Handler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 */
@Singleton class MyHandler @Inject constructor(val myService: MyService) : Handler {
  override fun handle(context: Context) =
    context.response.send("service value: ${myService.getValue()}")
}

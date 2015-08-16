package ratpack.example.kotlin

import ratpack.handling.Context
import ratpack.handling.Handler

/**
 * An example of a handler implicitly set up by a module
 *
 * @see MyModule
 */
class LoggingHandler : Handler {
  override fun handle(context: Context) {
    println("Received: ${context.getRequest()?.getUri()}")
    context.next()
  }
}

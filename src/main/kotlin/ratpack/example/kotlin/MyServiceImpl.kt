package ratpack.example.kotlin

/**
 * The service implementation.
 *
 * @see MyHandler
 */
class MyServiceImpl : MyService {
  override fun getValue() = "service-value"
}

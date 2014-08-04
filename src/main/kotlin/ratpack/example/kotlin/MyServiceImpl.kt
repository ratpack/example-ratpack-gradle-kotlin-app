package ratpack.example.kotlin

/**
 * The service implementation.
 *
 * @see ratpack.example.java.MyHandler
 */
class MyServiceImpl : MyService {

    override fun getValue() : String {
        return "service-value"
    }

}

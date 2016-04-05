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
     * Registered implementations of {@link ratpack.handling.HandlerDecorator} are able to decorate the
     * application handler.
     *
     * @see MyHandler
     */
    override fun configure() {
        bind(MyService::class.java).to(MyServiceImpl::class.java)
        bind(MyHandler::class.java)
        Multibinder.newSetBinder(binder(), HandlerDecorator::class.java)
            .addBinding()
            .toInstance(HandlerDecorator.prepend {
                println("Received: ${it.request.uri}")
                it.next()
            })
    }
}

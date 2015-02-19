package ratpack.example.kotlin

import ratpack.registry.Registry
import ratpack.guice.Guice

class HandlerFactory : ratpack.launch.HandlerFactory {
  override fun create(registry : Registry) = Guice.builder(registry)
      .bindings({ registry -> registry.add(MyModule()) })
      .build({ chain ->
        chain
            // Map to /foo
            .handler("foo") { context -> context.render("from the foo handler") }

            // Map to /bar
            .handler("bar") { context -> context.render("from the bar handler") }

            // Set up a nested routing block, which is delegated to `nestedHandler`
            .prefix("nested") { nested ->
              // Map to /nested/*/*
              nested.handler(":var1/:var2?") { context ->
                // The path tokens are the :var1 and :var2 path components above
                val pathTokens = context.getPathTokens()
                context.render("from the nested handler, var1: ${pathTokens["var1"]}, var2: ${pathTokens["var2"]}")
              }
            }

            // Map to a dependency injected handler
            .handler("injected", chain.getRegistry()[javaClass<MyHandler>()])

            // Bind the /static app path to the src/ratpack/assets/images dir
            // Try /static/logo.png
            .prefix("static") { nested -> nested.assets("assets/images") }

            // If nothing above matched, we'll get to here.
            .handler { context -> context.render("root handler!") }
      })
}
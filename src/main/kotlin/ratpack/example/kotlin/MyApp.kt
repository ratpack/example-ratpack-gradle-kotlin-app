package ratpack.example.kotlin

import org.slf4j.LoggerFactory.getLogger
import ratpack.guice.Guice
import ratpack.server.RatpackServer
import ratpack.server.ServerConfig

private val log = getLogger(::main.javaClass)

fun main(args : Array<String>) {
  try {
    RatpackServer.of {
      it.serverConfig(ServerConfig.findBaseDir())
          .registry(Guice.registry { bindingSpec -> bindingSpec.module(javaClass<MyModule>()) })
          .handlers { chain ->
            chain.handler("foo", { context -> context.render("from the foo handler") }) // Map to /foo
                .handler("bar", { context -> context.render("from the bar handler") }) // Map to /bar
                .prefix("nested", { nested ->  // Set up a nested routing block, which is delegated to `nestedHandler`
                  nested.handler(":var1/:var2?", { context ->  // The path tokens are the :var1 and :var2 path components above
                    val pathTokens = context.getPathTokens()
                    context.render("from the nested handler, var1: ${pathTokens.get("var1")}, var2: ${pathTokens.get("var2")}")
                  })
                })
                .handler("injected", chain.getRegistry().get(javaClass<MyHandler>())) // Map to a dependency injected handler
                .prefix("static", { nested -> nested.assets("assets/images") }) // Bind the /static app path to the src/ratpack/assets/images dir
                .handler { context -> context.render("root handler!") }
          }
    }.start()
  } catch (e : Exception) {
    log.error("", e)
    System.exit(1)
  }
}

package ratpack.example.kotlin

import org.slf4j.LoggerFactory.getLogger
import ratpack.file.FileHandlerSpec
import ratpack.guice.Guice
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.server.RatpackServer
import ratpack.server.ServerConfig

private val log = getLogger(::main.javaClass)

fun main(args : Array<String>) {
  try {
    RatpackServer.of {
      it.serverConfig(ServerConfig.findBaseDir())
          .registry(Guice.registry { bindingSpec -> bindingSpec.module(javaClass<MyModule>()) })
          .handlers { chain: Chain -> chain
                .path("foo", { context: Context -> context.render("from the foo handler") }) // Map to /foo
                .path("bar", { context: Context-> context.render("from the bar handler") }) // Map to /bar
                .prefix("nested", { nested: Chain->  // Set up a nested routing block, which is delegated to `nestedHandler`
                  nested.path(":var1/:var2?", { context: Context ->  // The path tokens are the :var1 and :var2 path components above
                    val pathTokens = context.getPathTokens()
                    context.render("from the nested handler, var1: ${pathTokens.get("var1")}, var2: ${pathTokens.get("var2")}")
                  })
                })
                .path("injected", chain.getRegistry().get(javaClass<MyHandler>())) // Map to a dependency injected handler
                .files({ fileHandlerSpec: FileHandlerSpec -> fileHandlerSpec.path("static").dir("assets/images")})
                .all { context: Context -> context.render("root handler!") }
          }
    }.start()
  } catch (e : Exception) {
    log.error("", e)
    System.exit(1)
  }
}

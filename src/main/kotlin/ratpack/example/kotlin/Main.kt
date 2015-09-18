package ratpack.example.kotlin

import org.slf4j.LoggerFactory.getLogger
import ratpack.guice.Guice
import ratpack.server.BaseDir
import ratpack.server.RatpackServer

object Main {
  private val log = getLogger(Main::class.java)

  @JvmStatic
  fun main(args: Array<String>) {
    try {
      RatpackServer.of { server ->
        server
          .serverConfig { config ->
            config.baseDir(BaseDir.find())
          }
          .registry(Guice.registry { bindingSpec ->
            bindingSpec.module(MyModule())
          })
          .handlers { chain ->
            chain
              // Map to /foo
              .path("foo", { context ->
                context.render("from the foo handler")
              })
              // Map to /bar
              .path("bar", { context ->
                context.render("from the bar handler")
              })
              // Set up a nested routing block, which is delegated to `nestedHandler`
              .prefix("nested", { nested ->
                nested
                  .path(":var1/:var2?", { context ->
                    // The path tokens are the :var1 and :var2 path components above
                    val pathTokens = context.getPathTokens()
                    context
                      .render("from the nested handler, var1: ${pathTokens.get("var1")}, var2: ${pathTokens.get("var2")}")
                  })
              })
              // Map to a dependency injected handler
              .path("injected", MyHandler::class.java)
              // Bind the /static app path to the src/ratpack/assets/images dir
              .prefix("static", { nested ->
                nested.fileSystem("assets/images", { f -> f.files() })
              })
              .all { context -> context.render("root handler!") }
          }
      }.start()
    } catch (e: Exception) {
      log.error("", e)
      System.exit(1)
    }
  }
}

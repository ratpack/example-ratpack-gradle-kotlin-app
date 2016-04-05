package ratpack.example.kotlin

import org.slf4j.LoggerFactory.getLogger
import ratpack.handling.Context
import ratpack.server.BaseDir

object Main // Only a tag for the logger
private val log = getLogger(Main::class.java)

fun main(args: Array<String>) {
    try {
        createServer().start()
    }
    catch (e: Exception) {
        log.error("", e)
        System.exit(1)
    }
}

fun createServer() = kserverOf {
    kserverConfig {
        baseDir(BaseDir.find())
    }

    kguiceRegistry {
        module(MyModule())
    }

    khandlers {
        kpath("foo") { render("from the foo handler") }
        kpath("bar") { render("from the bar handler") }

        // Map to /baz using a Kotlin function
        kpath("baz", ::bazHandler)

        // Set up a nested routing block, which is delegated to `nestedHandler`
        kprefix("nested") {
            kpath(":var1/:var2?") {
                // The path tokens are the :var1 and :var2 path components above
                render("from the nested handler, var1: ${pathTokens["var1"]}, var2: ${pathTokens["var2"]}")
            }
        }

        // Map to a dependency injected handler
        path("injected", MyHandler::class.java)

        // Bind the /static app path to the src/ratpack/assets/images dir
        kprefix("static") {
            kfileSystem("assets/images") { files() }
        }

        kall { render("root handler!") }
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")

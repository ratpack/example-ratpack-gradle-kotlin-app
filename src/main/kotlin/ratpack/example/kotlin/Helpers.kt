@file:Suppress("Unused")

/*
 * Helper functions to make the example more Kotlin like. All of them are extension functions that add
 * syntactic sugar to Ratpack's methods.
 *
 * The names are prefixed by 'k' because the signature of some methods is the same that Ratpack's ones and
 * they take precedence over extension functions. This could be fixed working with Ratpack's subclasses (but
 * that goes beyond the scope of the example).
 */

package ratpack.example.kotlin

import ratpack.guice.BindingsSpec
import ratpack.guice.Guice.registry as guiceRegistry
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder

fun kserverOf(cb: RatpackServerSpec.() -> Unit) = RatpackServer.of { it.(cb)() }
fun kserverStart(cb: RatpackServerSpec.() -> Unit) = RatpackServer.start { it.(cb)() }

fun RatpackServerSpec.kserverConfig(cb: ServerConfigBuilder.() -> Unit) = this.serverConfig { it.(cb)() }
fun RatpackServerSpec.kguiceRegistry(cb: BindingsSpec.() -> Unit) = this.registry(guiceRegistry { it.(cb)() })
fun RatpackServerSpec.khandlers(cb: Chain.() -> Unit) = this.handlers { it.(cb)() }

fun Chain.kfileSystem(path: String = "", cb: Chain.() -> Unit) = this.fileSystem (path) { it.(cb)() }
fun Chain.kprefix(path: String = "", cb: Chain.() -> Unit) = this.prefix (path) { it.(cb)() }

fun Chain.kall(cb: Context.() -> Unit) = this.all { it.(cb)() }
fun Chain.kpath(path: String = "", cb: Context.() -> Unit) = this.path (path) { it.(cb)() }

@Suppress("ReplaceGetOrSet")
fun Chain.kget(path: String = "", cb: Context.() -> Unit) = this.get (path) { it.(cb)() }
fun Chain.kput(path: String = "", cb: Context.() -> Unit) = this.put (path) { it.(cb)() }
fun Chain.kpost(path: String = "", cb: Context.() -> Unit) = this.post (path) { it.(cb)() }
fun Chain.kdelete(path: String = "", cb: Context.() -> Unit) = this.delete (path) { it.(cb)() }
fun Chain.koptions(path: String = "", cb: Context.() -> Unit) = this.options (path) { it.(cb)() }
fun Chain.kpatch(path: String = "", cb: Context.() -> Unit) = this.patch (path) { it.(cb)() }

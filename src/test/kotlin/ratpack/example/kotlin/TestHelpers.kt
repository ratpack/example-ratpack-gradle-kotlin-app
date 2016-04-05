@file:Suppress("Unused")

/*
 * Test helpers.
 */

package ratpack.example.kotlin

import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.server.RatpackServer
import ratpack.test.embed.EmbeddedApp
import ratpack.test.embed.EmbeddedApp.*
import ratpack.test.http.TestHttpClient

fun kappFromHandler(callback: Context.() -> Unit): EmbeddedApp = fromHandler { it.(callback) () }
fun kappFromHandlers(callback: Chain.() -> Unit): EmbeddedApp = fromHandlers { it.(callback) () }
fun kappFromServer(startServer: RatpackServer) = fromServer (startServer)
fun EmbeddedApp.ktest(callback: TestHttpClient.() -> Unit): Unit = this.test { it.(callback)() }
fun TestHttpClient.getBody(path: String) = get(path).body.text

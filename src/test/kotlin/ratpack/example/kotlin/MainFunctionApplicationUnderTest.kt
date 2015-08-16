package ratpack.example.kotlin

import ratpack.registry.Registry
import ratpack.server.RatpackServer
import ratpack.server.internal.ServerCapturer
import ratpack.test.ServerBackedApplicationUnderTest

open class MainFunctionApplicationUnderTest(val mainFun: (Array<String>) -> Unit) : ServerBackedApplicationUnderTest() {
  protected open fun createOverrides(serverRegistry: Registry): Registry = Registry.empty()

  override fun createServer(): RatpackServer =
    ServerCapturer.capture(
      ServerCapturer.Overrides()
        .port(0)
        .development(true)
        .registry { registry -> createOverrides(registry) }
    ) {
      mainFun(arrayOf<String>())
    }.orElseThrow { ->
      IllegalStateException("${mainFun} did not start a Ratpack server")
    }
}
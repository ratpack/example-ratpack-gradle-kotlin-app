package ratpack.example.kotlin

import ratpack.registry.Registries
import ratpack.registry.Registry
import ratpack.server.RatpackServer
import ratpack.server.internal.ServerCapturer
import ratpack.test.ServerBackedApplicationUnderTest

class KotlinApplicationUnderTest(val mainFun : (Array<String>) -> Unit) : ServerBackedApplicationUnderTest() {
  protected fun createOverrides(serverRegistry : Registry) : Registry = Registries.empty()

  override fun createServer() : RatpackServer =
      ServerCapturer.capture(
          ServerCapturer.Overrides()
              .port(0)
              .development(true)
              .registry { registry -> createOverrides(registry) }
      ) {
        mainFun(array<String>())
      }.orElseThrow { ->
        IllegalStateException("${mainFun} did not start a Ratpack server")
      }
}
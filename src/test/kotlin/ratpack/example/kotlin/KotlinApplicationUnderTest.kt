package ratpack.example.kotlin

import ratpack.test.ServerBackedApplicationUnderTest
import ratpack.server.RatpackServer
import ratpack.server.internal.ServerCapturer
import ratpack.registry.Registries
import ratpack.registry.Registry
import kotlin.reflect.KFunction1

class KotlinApplicationUnderTest(private val mainFun : KFunction1<Array<String>, Unit>) : ServerBackedApplicationUnderTest() {
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
        IllegalStateException("${mainFun.javaClass}.main() did not start a Ratpack server")
      }
}
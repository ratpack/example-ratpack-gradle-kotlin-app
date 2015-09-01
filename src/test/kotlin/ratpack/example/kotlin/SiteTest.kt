package ratpack.example.kotlin

import com.google.common.io.CharStreams
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ratpack.test.MainClassApplicationUnderTest
import java.io.InputStreamReader
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(JUnit4::class)
class SiteTest {

  val aut = MainClassApplicationUnderTest(Main.javaClass)

  Test fun fooHandler() {
    assertEquals("from the foo handler", get("/foo"))
  }

  Test fun barHandler() {
    assertEquals("from the bar handler", get("/bar"))
  }

  Test fun nestedHandler() {
    assertEquals("from the nested handler, var1: x, var2: null",
                 get("/nested/x"))
    assertEquals("from the nested handler, var1: x, var2: y",
                 get("/nested/x/y"))
  }

  Test fun injectedHandler() {
    assertEquals("service value: service-value", get("/injected"))
  }

  Test fun staticHandler() {
    assertEquals("text asset\n", get("/static/test.txt"))
  }

  Test fun rootHandler() {
    assertEquals("root handler!", get("/"))
    assertEquals("root handler!", get("/unknown-path"))
  }

  private fun get(path: String): String {
    val uri = aut.getAddress().resolve(path)
    val stream = uri.toURL().openStream()
    try {
      val reader = InputStreamReader(stream)
      try {
        return CharStreams.toString(reader)
      } finally {
        reader.close()
      }
    } catch (ex: Exception) {
      fail(ex.toString())
      return "" // unreachable
    } finally {
      stream?.close()
    }
  }

}

package ratpack.example.kotlin

import com.google.common.io.CharStreams
import org.jetbrains.spek.api.Spek
import ratpack.test.MainClassApplicationUnderTest
import java.io.InputStreamReader
import kotlin.test.assertEquals

class SiteSpec : Spek({

    val aut = MainClassApplicationUnderTest(Main::class.java)

    fun MainClassApplicationUnderTest.response(path: String): String {
        val uri = address.resolve(path)
        val stream = uri.toURL().openStream()
        return stream.use {
            InputStreamReader(stream).use {
                CharStreams.toString(it)
            }
        }
    }

    describe("foo handler") {
        it("should return foo handler value") {
            assertEquals("from the foo handler", aut.response("/foo"))
        }
    }

    describe("bar handler") {
        it("should return bar handler value") {
            assertEquals("from the bar handler", aut.response("/bar"))
        }
    }

    describe("baz handler") {
        it("should return baz handler value") {
            assertEquals("from the baz handler", aut.response("/baz"))
        }
    }

    describe("nested handler") {
        it("should return value for single extra level path") {
            assertEquals("from the nested handler, var1: x, var2: null", aut.response("/nested/x"))
        }

        it("should return value for multiple extra level paths") {
            assertEquals("from the nested handler, var1: x, var2: y", aut.response("/nested/x/y"))
        }
    }

    describe("injected handler") {
        it("should return service value") {
            assertEquals("service value: service-value", aut.response("/injected"))
        }
    }

    describe("static handler") {
        it("should return static value") {
            assertEquals("text asset\n", aut.response("/static/test.txt"))
        }
    }

    describe("root handler") {
        it("should return root handler value for request with no path") {
            assertEquals("root handler!", aut.response("/"))
        }

        it("should return root handler value for request with no an unknown path") {
            assertEquals("root handler!", aut.response("/unknown-path"))
        }
    }

    /**
     * This is hackish solution for closing the 'aut' that works because Spek will always run all the tests
     * in the order they where declared in the source code.
     *
     * The solution is temporary until Spek supports some general kind of resource cleanup solution (See https://github.com/JetBrains/spek/issues/95)
     */
    it("should close the aut") {
        aut.close()
    }
})

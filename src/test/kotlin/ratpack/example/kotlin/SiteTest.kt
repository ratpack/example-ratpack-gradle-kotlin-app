package ratpack.example.kotlin

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SiteTest {
    val app = appFromServer(createServer ())

    @Test fun fooHandler() {
        app.check {
            assert("from the foo handler" == getBody("/foo"))
        }
    }

    @Test fun barHandler() {
        app.check {
            assert("from the bar handler" == getBody("/bar"))
        }
    }

    @Test fun bazHandler() {
        app.check {
            assert("from the baz handler" == getBody("/baz"))
        }
    }

    @Test fun nestedHandler() {
        app.check {
            assert("from the nested handler, var1: x, var2: null" == getBody("/nested/x"))
            assert("from the nested handler, var1: x, var2: y" == getBody("/nested/x/y"))
        }
    }

    @Test fun injectedHandler() {
        app.check {
            assert("service value: service-value" == getBody("/injected"))
        }
    }

    @Test fun staticHandler() {
        app.check {
            assert("text asset\n" == getBody("/static/test.txt"))
        }
    }

    @Test fun rootHandler() {
        app.check {
            assert("root handler!" == getBody("/"))
            assert("root handler!" == getBody("/unknown-path"))
        }
    }
}

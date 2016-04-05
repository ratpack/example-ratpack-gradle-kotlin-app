package ratpack.example.kotlin

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SiteTest {
    val app = kappFromServer(createServer ())

    @Test fun fooHandler() {
        app.ktest {
            assert("from the foo handler" == getBody("/foo"))
        }
    }

    @Test fun barHandler() {
        app.ktest {
            assert("from the bar handler" == getBody("/bar"))
        }
    }

    @Test fun bazHandler() {
        app.ktest {
            assert("from the baz handler" == getBody("/baz"))
        }
    }

    @Test fun nestedHandler() {
        app.ktest {
            assert("from the nested handler, var1: x, var2: null" == getBody("/nested/x"))
            assert("from the nested handler, var1: x, var2: y" == getBody("/nested/x/y"))
        }
    }

    @Test fun injectedHandler() {
        app.ktest {
            assert("service value: service-value" == getBody("/injected"))
        }
    }

    @Test fun staticHandler() {
        app.ktest {
            assert("text asset\n" == getBody("/static/test.txt"))
        }
    }

    @Test fun rootHandler() {
        app.ktest {
            assert("root handler!" == getBody("/"))
            assert("root handler!" == getBody("/unknown-path"))
        }
    }
}

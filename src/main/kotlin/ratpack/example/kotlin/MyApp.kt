package ratpack.example.kotlin

import ratpack.server.RatpackServer
import ratpack.server.ServerConfig

fun main(args : Array<String>) {
  RatpackServer.of {
    it.serverConfig(ServerConfig.findBaseDirProps())
    it.handler { registry ->
      HandlerFactory().create(registry)
    }
  }.start()
}

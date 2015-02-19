package ratpack.example.kotlin

import ratpack.server.RatpackServer
import ratpack.server.ServerConfig

fun main(args : Array<String>) {
  RatpackServer.of({ b ->
    b.serverConfig(ServerConfig.findBaseDirProps())
    b.handler({ registry ->
      HandlerFactory().create(registry)
    })
  }).start()
}

package tw.harry.springboot.spring07.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import tw.harry.springboot.spring07.handler.MyWebSocketHandler

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        val handler = MyWebSocketHandler()
        registry.addHandler(
            handler,
            "/ws", "/wss", "/wb"
        )
        .setAllowedOrigins("*")
    }
}
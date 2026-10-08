package tw.harry.springboot.spring07.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal

@Configuration
@EnableWebSocketMessageBroker
class StompConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        super.configureMessageBroker(registry)

        registry.setApplicationDestinationPrefixes("/app")
        registry.enableSimpleBroker("/topic")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
//        super.registerStompEndpoints(registry)
        registry.addEndpoint("/ws-chat")
            .addInterceptors(object : HandshakeInterceptor {
                override fun beforeHandshake(
                    request: ServerHttpRequest,
                    response: ServerHttpResponse,
                    wsHandler: WebSocketHandler,
                    attributes: MutableMap<String, Any>
                ): Boolean {
//                    if (request is ServletServerHttpRequest) {
//                        val httpReq = request.servletRequest
//                        val token = httpReq.getHeader("Authorization")
//                        if (!token.isNullOrEmpty() && token.startsWith("Bearer ")) {
//                            val token = token.split(" ").getOrNull(1) ?: ""
//                            val account = JwtToken.parseToken(token)
//                            attributes["account"] = account
//                            return true
//                        }
//                    }
//
//                    return false

                    return true
                }

                override fun afterHandshake(
                    request: ServerHttpRequest,
                    response: ServerHttpResponse,
                    wsHandler: WebSocketHandler,
                    exception: Exception?
                ) {

                }
            })
            .setHandshakeHandler(object : DefaultHandshakeHandler() {
                override fun determineUser(
                    request: ServerHttpRequest,
                    wsHandler: WebSocketHandler,
                    attributes: Map<String, Any>
                ): Principal? {
                    return super.determineUser(request, wsHandler, attributes)
                }
            })
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }

}
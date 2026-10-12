package tw.harry.springboot.spring07.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import tw.harry.springboot.spring07.response.ChatMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Controller
class ChatController {

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    fun chatMessage(content: String): ChatMessage {
        val chatMessage = ChatMessage()
        chatMessage.content = content
        chatMessage.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        return chatMessage
    }

    @MessageMapping("/chat/{roomId}/send")
    @SendTo("/topic/rooms/{roomId}")
    fun chatMessage(@DestinationVariable roomId: String?, content: String): ChatMessage {
        val chatMessage = ChatMessage()
        chatMessage.content = content
        chatMessage.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        return chatMessage
    }
}
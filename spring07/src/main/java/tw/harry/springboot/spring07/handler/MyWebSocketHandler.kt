package tw.harry.springboot.spring07.handler

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class MyWebSocketHandler : TextWebSocketHandler() {

    companion object {
        private val sessions = HashSet<WebSocketSession>()
        private var isTeacherExists = false
        private var teacherSession: WebSocketSession? = null
    }

    override fun afterConnectionEstablished(
        session: WebSocketSession
    ) {
        println("afterConnectionEstablished() called")
        sessions.add(session)
    }

    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        val msg = message.payload
        if (!isTeacherExists && msg.contains("isTeacher")) {
            teacherSession = session
            isTeacherExists = true
        } else if (session == teacherSession) {
            for (session in sessions) {
                if (session.isOpen) {
                    session.sendMessage(message)
                }
            }
        }
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        if (sessions == teacherSession) {
            isTeacherExists = false
        }
        sessions.remove(session)

    }

}
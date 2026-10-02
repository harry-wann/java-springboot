package tw.harry.springboot.spring01.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ReadConfig(
    @Value("\${file.upload.dir}")
    private val uploadDir: String,
) {

    fun getUploadDir(): String {
        return uploadDir
    }

}
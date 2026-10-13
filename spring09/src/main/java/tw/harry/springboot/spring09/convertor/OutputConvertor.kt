package tw.harry.springboot.spring09.convertor

import jakarta.validation.Validator
import org.springframework.ai.converter.BeanOutputConverter
import tw.harry.springboot.spring09.exception.ModelCallException
import tw.harry.springboot.spring09.exception.ModelOutputException
import kotlin.reflect.KClass


/*
*
* .entity(Record.class)
*
*
*/
class OutputConvertor<T : Any>(
    clazz: KClass<T>,
    private val validator: Validator,
) : BeanOutputConverter<T>(clazz.java) {


    override fun convert(text: String): T {
        val result = try {
            super.convert(text)
        } catch (e: Exception) {
            e.printStackTrace()
            throw ModelCallException(cause = e)
        }

        val violations = validator.validate(result)

        if (violations.isNotEmpty()) {
            violations.forEach { violation ->
                println(
                    "欄位：${violation.propertyPath}, "
                        + "錯誤：${violation.message}, "
                        + "值：${violation.invalidValue}"
                )
            }

            throw ModelOutputException()
        }

        return result
    }

}
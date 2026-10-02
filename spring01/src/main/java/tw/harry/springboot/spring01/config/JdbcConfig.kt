package tw.harry.springboot.spring01.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class JdbcConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun harryDatasource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    fun harryJdbc(@Qualifier("harryDatasource") dataSource: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }


}
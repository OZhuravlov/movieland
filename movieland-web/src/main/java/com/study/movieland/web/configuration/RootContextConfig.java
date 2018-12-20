package com.study.movieland.web.configuration;

import com.study.movieland.web.controller.interceptor.PermissionCheckInterceptor;
import com.study.movieland.web.controller.interceptor.SecurityInterceptor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ComponentScan(basePackages = "com.study.movieland",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "com\\.study\\.movieland\\.web\\..*"))
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class RootContextConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new PermissionCheckInterceptor())
                .addPathPatterns("/review/**")
                .addPathPatterns("/movie/**");
    }

    @Bean
    public BasicDataSource dataSource(@Value("${datasource.driver}") String driverClassName,
                                      @Value("#{systemEnvironment['DATABASE_URL']}") String databaseUrl,
                                      @Value("${datasource.init-size}") int initSize) throws URISyntaxException {
        URI dbUrl = new URI(databaseUrl);
        String url = "jdbc:postgresql://" + dbUrl.getHost() + ':' + dbUrl.getPort() + dbUrl.getPath() + "?sslmode=require";
        String username = dbUrl.getUserInfo().split(":")[0];
        String password = dbUrl.getUserInfo().split(":")[1];
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initSize);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ExecutorServiceAdapter executorService(@Value("${executor.corePoolSize}") int corePoolSize,
                                               @Value("${executor.maxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        ExecutorServiceAdapter executorService = new ExecutorServiceAdapter(executor);
        return executorService;
    }

}

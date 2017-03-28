package com.gcmassari.mastermind.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableScheduling // enable job scheduling
@EnableWebMvc // same as <mvc:annotation-driven/>
@Configuration
@ComponentScan(basePackages={
        "com.gcmassari.mastermind.controller",
        "com.gcmassari.mastermind.cronjobs",
        "com.gcmassari.mastermind.data",
        "com.gcmassari.mastermind.validators",
})
public class WebConfig extends WebMvcConfigurerAdapter {

	// Allows direct access to static resources: equivalent for <mvc:resources/> tags
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

	// TODO use (multi-language?) properties
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource  messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] {"/resources/messages/messages"});
		return messageSource;
	}

}

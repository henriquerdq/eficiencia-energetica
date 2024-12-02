package br.com.tcia.eficienciaenergetica.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfig {

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource =
				new ReloadableResourceBundleMessageSource();
		
		msgSource.setBasename("classpath:messages");
		msgSource.setDefaultEncoding("UTF-8");
		msgSource.setDefaultLocale(Locale.getDefault());
		return msgSource;
	}
	
	@Bean
	LocalValidatorFactoryBean validatorFactoryBean() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
}

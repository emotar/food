package ga.javatw.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setDefaultEncoding("UTF-8");
		source.setBasenames("i18n/message");
		return source;
	}

	@Bean
	public LocaleResolver localeResolver()
	{

		// CookieLocaleResolver
		CookieLocaleResolver clr = new CookieLocaleResolver();
		clr.setCookieName("lang");
		clr.setDefaultLocale(Locale.TAIWAN);
		clr.setCookieMaxAge(86400);


		/*	SessionLocaleResolver
			SessionLocaleResolver slr = new SessionLocaleResolver();
			slr.setDefaultLocale(Locale.ENGLISH);
		*/
		return clr;
	}


	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/images/**")
          .addResourceLocations("file:D:\\images\\");
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}

}
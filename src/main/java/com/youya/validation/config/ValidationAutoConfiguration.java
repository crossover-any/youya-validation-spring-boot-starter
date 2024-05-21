package com.youya.validation.config;

import com.youya.validation.adapter.ResultAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.Properties;

/**
 * @author tengxq
 */
@ComponentScan("com.youya")
public class ValidationAutoConfiguration {

    @Bean
    public Validator validator(MessageSource messageSource){
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        // 国际化
        factoryBean.setValidationMessageSource(messageSource);
        // 设置使用 HibernateValidator 校验器
        factoryBean.setProviderClass(HibernateValidator.class);
        Properties properties = new Properties();
        // 设置 快速异常返回
        properties.setProperty("hibernate.validator.fail_fast", "true");
        factoryBean.setValidationProperties(properties);
        // 加载配置
        factoryBean.afterPropertiesSet();
        return factoryBean.getValidator();
    }

    //@ConditionalOnMissingBean
    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }

    @ConditionalOnMissingBean(value = ResultAdapter.class)
    @Bean
    public ResultAdapter resultAdapter() {
        return new ResultAdapter();
    }


    /**
     * 获取请求头国际化信息
     */
    static class I18nLocaleResolver implements LocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest httpServletRequest) {
            String language = httpServletRequest.getHeader("Accept-Language");
            Locale locale = Locale.getDefault();
            if (language != null) {
                String[] split = language.split("_");
                if (split.length > 1) {
                    locale = new Locale(split[0], split[1]);
                } else {
                    if (language.contains("zh")) {
                        locale = Locale.CHINESE;
                    } else {
                        locale = Locale.ENGLISH;
                    }
                }
            }
            return locale;
        }

        @Override
        public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

        }
    }
}

package com.mty.property.common;

import com.mty.property.common.infrastructure.CachingMetadataReaderFactoryProvider;
import com.mty.property.common.utils.PropertySourceUtils;
import com.mty.property.common.utils.SpringUtils;
import com.mty.property.common.utils.TraceUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

/**
 * @author mty
 * @date 2022/09/16 14:53
 **/
@Configurable
@EnableAspectJAutoProxy
public class AopsCommonConfiguration {
    @Bean
    public static CachingMetadataReaderFactoryProvider cachingMetadataReaderFactoryProvider(ApplicationContext applicationContext) throws Exception {
        return new CachingMetadataReaderFactoryProvider(applicationContext);
    }

    @Bean
    public TraceUtils traceUtils(){
        return new TraceUtils();
    }

    @Bean
    public SpringUtils aopsBeanUtils(){
        return new SpringUtils();
    }

    @Bean
    public static ApplicationNameBeanPostProcessor applicationNameBeanPostProcessor(Environment environment) {
        String appName = PropertySourceUtils.getAppName(environment);
        return new ApplicationNameBeanPostProcessor(appName);
    }

    @Bean
    public EnabledContionOutcameCacheCleaner enabledContionOutcameCacheCleaner(){
        return new EnabledContionOutcameCacheCleaner();
    }

}

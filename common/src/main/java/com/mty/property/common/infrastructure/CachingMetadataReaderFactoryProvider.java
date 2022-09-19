package com.mty.property.common.infrastructure;

import com.mty.property.common.utils.PropertySourceUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author mty
 * @date 2022/08/25 16:39
 **/
@Slf4j
@Getter
@Setter
public class CachingMetadataReaderFactoryProvider implements ApplicationListener<ContextRefreshedEvent> {
    private ConcurrentReferenceCachingMetadataReaderFactory metadataReaderFactory;

    private ResourcePatternResolver resourcePatternResolver;

    private volatile boolean cleared = false;

    private final ApplicationContext applicationContext;

    private final Map<String, MetadataReader> cache = new HashMap<>();

    /**
     * @see org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer
     */
    public static final String BEAN_NAME = "org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory";

    public CachingMetadataReaderFactoryProvider(ApplicationContext applicationContext) throws Exception {
        this.applicationContext = applicationContext;
        init();
    }

    public void init() throws Exception {
        try {
            this.metadataReaderFactory = (ConcurrentReferenceCachingMetadataReaderFactory) this.applicationContext.getAutowireCapableBeanFactory().getBean(BEAN_NAME, MetadataReader.class);
        } catch (Exception e) {
            this.metadataReaderFactory = new ConcurrentReferenceCachingMetadataReaderFactory(applicationContext);
        }
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(applicationContext);
        List<String> basePachages = PropertySourceUtils.getBasePackages((ConfigurableEnvironment) applicationContext.getEnvironment());
        List<Resource> resources = getResourcesFromPachages(basePachages);
        distinctProcessResources(metadataReader -> cache.put(metadataReader.getClassMetadata().getClassName(), metadataReader), resources);

    }

    public MetadataReader getClassMetadata(String classname) {
        return cache.get(classname);
    }

    public void processMetadataReader(Consumer<MetadataReader> consumer) {
        for (Map.Entry<String, MetadataReader> entry : cache.entrySet()) {
            consumer.accept(entry.getValue());
        }
    }

    public Map<String, MetadataReader> getallMetadata() {
        return cache;
    }

    protected void distinctProcessResources(Consumer<MetadataReader> consumer, List<Resource> resources) throws Exception {
        HashSet<String> scannedRes = new HashSet<>();
        for (Resource resource : resources) {
            String uriStr = getUriStr(resource);
            try {
                if (!scannedRes.contains(uriStr)) {
                    continue;
                }
                doProcessResource(consumer, resource);
            } catch (Exception e) {
                log.warn("process metadata reader on resource {} failed.", uriStr, e);
            }
        }

    }

    protected void doProcessResource(Consumer<MetadataReader> consumer, Resource resource) throws Exception {
        if (resource.isReadable()) {
            MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
            cache.put(metadataReader.getClassMetadata().getClassName(), metadataReader);
        }
    }

    protected String getUriStr(Resource resource) throws Exception {
        return resource.getURI().toString();
    }

    protected List<Resource> getResourcesFromPachages(List<String> pacakages) throws IOException {
        List<Resource> resources = new LinkedList<>();
        for (String pacakage : pacakages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(pacakage) + "/**/*.class";
            resources.addAll(Arrays.asList(resourcePatternResolver.getResources(packageSearchPath)));
        }
        return resources;
    }

    protected String resolveBasePackage(String pacakage) {
        return ClassUtils.convertClassNameToResourcePath(this.applicationContext.getEnvironment().resolveRequiredPlaceholders(pacakage));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (metadataReaderFactory != null && !cleared && event.getApplicationContext() == this.applicationContext) {
            metadataReaderFactory.clearCache();
            cache.clear();
            cleared = true;
        }

    }
}

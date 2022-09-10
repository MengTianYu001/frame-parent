package com.mty.property.common.advisor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * @author mty
 * @date 2022/08/25 16:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanAnnotation<T extends Annotation> {
    private String beanName;

    private T annotation;
}

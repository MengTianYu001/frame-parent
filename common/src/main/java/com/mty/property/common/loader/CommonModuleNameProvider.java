package com.mty.property.common.loader;

import com.mty.property.common.CommonConstants;
import com.mty.property.common.version.ModuleNameProvider;

/**
 * @author mty
 * @since 2022/09/16 15:33
 **/
public class CommonModuleNameProvider implements ModuleNameProvider {
    @Override
    public String getModuleName() {
        return CommonConstants.COMMON_MODULE;
    }
}

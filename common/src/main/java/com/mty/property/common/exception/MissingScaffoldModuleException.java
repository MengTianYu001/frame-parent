package com.mty.property.common.exception;

import java.util.List;

/**
 * @author mty
 * @date 2022/09/19 10:39
 **/
public class MissingScaffoldModuleException extends RuntimeException {
    private final List<String> missingModules;

    public MissingScaffoldModuleException(List<String> missingModules) {
        this.missingModules = missingModules;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Missing scaffold module!\n\n");
        sb.append("请在pom.xml文件中增加如下依赖: \n\n");
        for (String missingModules : missingModules) {
            sb.append("  <dependency>\n");
            sb.append("        <groupId>com.mty.property</groupId>\n");
            sb.append("        <artifactId>").append(missingModules).append("</artifactId>\n");
            sb.append("  </dependency>\n\n");
        }
        return sb.toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

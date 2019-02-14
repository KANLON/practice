package com.kanlon.config;

import com.kanlon.enable.EnableSelfBean;
import com.kanlon.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 自己的定义的自动注解配置类
 *
 * @author zhangcanlong
 * @since 2019/2/14 10:45
 **/
public class SelfEnableAutoConfig implements ImportSelector {

    Logger logger = LoggerFactory.getLogger(SelfEnableAutoConfig.class);

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //获取EnableEcho注解的所有属性的value
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableSelfBean.class.getName());
        if(attributes==null){return new String[0]; }
        //获取package属性的value
        String[] packages = (String[]) attributes.get("packages");
        if(packages==null || packages.length<=0 || StringUtils.isEmpty(packages[0])){
            return new String[0];
        }
        logger.info("加载该包所有类到spring容器中的包名为："+ Arrays.toString(packages));
        Set<String> classNames = new HashSet<>();

        for(String packageName:packages){
            classNames.addAll(ClassUtils.getClassName(packageName,true));
        }
        //将类打印到日志中
        for(String className:classNames){
            logger.info(className+"加载到spring容器中");
        }
        String[] returnClassNames = new String[classNames.size()];
        returnClassNames= classNames.toArray(returnClassNames);

        return  returnClassNames;
    }
}

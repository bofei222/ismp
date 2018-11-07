package com.nei.ismp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author bofei
 * @date 2018/11/6 17:26
 */
@Component
@ConfigurationProperties(prefix = "iamp")
@RefreshScope
public class IampProperties {

    /**1.Enum中不能注入IampProperties 对象，2.内部Enum中不能使用非静态属性***/
    /**为了能让Enum使用：IampPropertis.属性名直接调用，此处属性定义为公开的、静态的*/
    public static String username;
    public static String url;
    public static String webKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

}

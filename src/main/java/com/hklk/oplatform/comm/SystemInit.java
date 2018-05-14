package com.hklk.oplatform.comm;

import com.hklk.oplatform.util.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
public class SystemInit {
    private static final Logger LOG = LoggerFactory.getLogger(SystemInit.class);
    @PostConstruct
    public void init() {
        PropUtil.setProperties(loadProp("/application.properties"));
    }

    /**
     * 读取配置文件
     *
     * @param propsName
     * @return
     */
    private Properties loadProp(String propsName) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(propsName);
            props.load(new InputStreamReader(is, "UTF-8"));
        } catch (IOException e) {
            LOG.error("初始化配置文件" + propsName + "出现异常", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOG.error("初始化配置文件时关闭is流出现异常", e);
            }
        }
        return props;
    }
}

package com.snicesoft.freefir.qiniu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QiniuStoreConfig {

	@Bean(name="qiniuConfig")
	@ConfigurationProperties(prefix = "qiniu")
	public QiniuConfig qiniuConfig() {
		return new QiniuConfig();
	}
}

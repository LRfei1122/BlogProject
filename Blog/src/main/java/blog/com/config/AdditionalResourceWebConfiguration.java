package blog.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//自動的に、画像を更新する作業
//Springの設定をするクラスだと宣言
@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
	// Spring MVCの設定をカスタマイズ
	@Override
	// addResourceHandlers(ResourceHandlerRegistry registry):
	// このメソッドは、静的リソースのリソースハンドラーを追加するために使用
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	}
}

package cn;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@MapperScan({"cn.users.dao"})
//@EnableCaching
public class shiro_start {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(shiro_start.class, args);
		EhCacheManager bean = run.getBean("ehCacheManager",EhCacheManager .class);
		System.out.println(bean);
	}
}

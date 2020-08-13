package com.zzvcom.stat.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
//@MapperScan("com.zzvcom.stat.business.*.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     * mybatis 查询数据返回map会去掉为null的字段，以下代码可以保留为null的字段
     * @param dataSource
     * @return
     * @throws Exception
     */
//    @Bean(name = "baseSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory setSqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        //------------------------------------------------加入的代码开始------------------------------------------------
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setCallSettersOnNulls(true);
//        bean.setConfiguration(configuration);
//        //------------------------------------------------加入的代码结束------------------------------------------------
//        return bean.getObject();
//    }
}

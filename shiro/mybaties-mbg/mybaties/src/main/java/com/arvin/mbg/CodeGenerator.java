package com.arvin.mbg;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zw.ft.common.base.BaseEntity;

import java.util.ArrayList;

/**
 * @ClassName CodeGenerator
 * @Description 代码生成器
 * @Author Savior
 * @Date 2020/8/17 16:00
 * @Version 1.0
 **/
public class CodeGenerator {

    public static void main(String[] args) {
        //设置要映射的表名
        String setInclude = "rel_user_department";

        AutoGenerator autoGenerator = new AutoGenerator();
        GlobalConfig globalConfig = new GlobalConfig();

        String currentPath = System.getProperty("user.dir");
        //输出目录
        globalConfig.setOutputDir(currentPath+"/src/main/java");
        //作者
        globalConfig.setAuthor("Savior");
        //是否打开资源管理器
        globalConfig.setOpen(false);
        //是否覆盖之前代码
        globalConfig.setFileOverride(false);
        //省略Service接口前缀
        globalConfig.setServiceName("%sService");
        //日期格式
        globalConfig.setDateType(DateType.ONLY_DATE);
        //是否生成swagger配置
        //globalConfig.setSwagger2(true);
        autoGenerator.setGlobalConfig(globalConfig);

        //设置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://81.68.92.240:3306/fund_trade?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setPassword("314159");
        dataSourceConfig.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dataSourceConfig);

        //包的配置
        PackageConfig packageConfig = new PackageConfig();
        //packageConfig.setModuleName("blog");
        packageConfig.setParent("com.zw.ft.modules.sys");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("repository");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude(setInclude);
        //下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        //strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //逻辑删除
        strategy.setLogicDeleteFieldName("deleted");

        //自动填充
        TableFill gmtCreate = new TableFill("createdTime", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("updatedTime", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableField = new ArrayList<>();
        tableField.add(gmtCreate);
        tableField.add(gmtModified);
        strategy.setTableFillList(tableField);
        //乐观锁
        strategy.setVersionFieldName("version");
        //restful 格式
        strategy.setRestControllerStyle(true);
        //url 设置成下划线
        strategy.setControllerMappingHyphenStyle(true);
        autoGenerator.setStrategy(strategy);
        autoGenerator.execute();
    }
}

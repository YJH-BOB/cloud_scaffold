package com.wdt.security;

import com.wdt.common.config.FeignClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients    //feign扫描
@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
@Import({FeignClientConfiguration.class})
public class RCSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCSecurityApplication.class, args);
    }
}
/**
 * 自动化配置原理
 *
 * 1.进入SpringBootApplication 注解
 *
 * 发现有
 * @SpringBootConfiguration
 * @EnableAutoConfiguration
 * @ComponentScan(
 *     excludeFilters = {@Filter(
 *     type = FilterType.CUSTOM,
 *     classes = {TypeExcludeFilter.class}
 * ), @Filter(
 *     type = FilterType.CUSTOM,
 *     classes = {AutoConfigurationExcludeFilter.class}
 * )}
 * )
 *
 * 先说@SpringBootConfiguration ，里边是Configuration ，Configuration表示这个类是一个配置类，
 *  Configuration里边是@commpant, 作用是把这个类注册到spring容器中  。那其实说白了，这个注解最最终的作用就是将这个启动类交给spring 容器管理
 *
 *  再说 @EnableAutoConfiguration
 *      里边有
 *          @AutoConfigurationPackage
 *          和
 *          @Import({AutoConfigurationImportSelector.class})
 *
 *
 *      先说第一个AutoConfigurationPackage 。里边有  @Import({AutoConfigurationPackages.Registrar.class})，点进去发现
 *
 *
 *      public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
 *             AutoConfigurationPackages.register(registry, (String[])(new PackageImports(metadata)).getPackageNames().toArray(new String[0]));
 *         }
 *      看到了吗，接收一个元数据，和 注册器调用这个方法  ，再看这个方法
 *
 *
 *          public static void register(BeanDefinitionRegistry registry, String... packageNames) {
 *         if (registry.containsBeanDefinition(BEAN)) {
 *             addBasePackages(registry.getBeanDefinition(BEAN), packageNames);
 *         } else {
 *             RootBeanDefinition beanDefinition = new RootBeanDefinition(BasePackages.class);
 *             beanDefinition.setRole(2);
 *             addBasePackages(beanDefinition, packageNames);
 *             registry.registerBeanDefinition(BEAN, beanDefinition);
 *         }
 *
 *     }
 *
 *
 *     把BasePackages 注册 到了spring 的容器中，这个把BasePackages是啥，是当前类的 包名 ，例如 com.wdt。security ，这样就可以扫描到所以的组件了
 *
 *
 *
 *
 *     好接着看 @Import({AutoConfigurationImportSelector.class})
 *
 *
 *     其实看类名就大概可以猜到他要干嘛    AutoConfigurationImportSelector，变换一下   ImportSelector  导入选择器，这个应该不陌生，那这个的作用会不会是选择导入呢，点进去看
 *
 *
 *
 *     先看   public String[] selectImports(AnnotationMetadata annotationMetadata) {
 *         if (!this.isEnabled(annotationMetadata)) {
 *             return NO_IMPORTS;
 *         } else {
 *             AutoConfigurationEntry autoConfigurationEntry = this.getAutoConfigurationEntry(annotationMetadata);
 *             return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
 *         }
 *     }
 *
 *
 *     getAutoConfigurationEntry 这个方法就是在拿到所有的配置了，配置是什么呢，接着看这个方法
 *
 *
 *
 *     protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
 *         if (!this.isEnabled(annotationMetadata)) {
 *             return EMPTY_ENTRY;
 *         } else {
 *             AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
 *             List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
 *             configurations = this.removeDuplicates(configurations);
 *             Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
 *             this.checkExcludedClasses(configurations, exclusions);
 *             configurations.removeAll(exclusions);
 *             configurations = this.getConfigurationClassFilter().filter(configurations);
 *             this.fireAutoConfigurationImportEvents(configurations, exclusions);
 *             return new AutoConfigurationEntry(configurations, exclusions);
 *         }
 *     }
 *
 *
 *
 *
 *
 * @Candidate 这个注解应该熟悉吧，ImportSelector这个是代码实现的选择导入器，Candidate是 注解实现的选择导入器，他有好多实现的
 *
 * 好，那我们看getCandidateConfigurations这个方法
 *
 *
 *
 *     protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
 *         List<String> configurations = ImportCandidates.load(AutoConfigurationAutoConfiguration.class, this.getBeanClassLoader()).getCandidates();
 *         Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports. If you are using a custom packaging, make sure that file is correct.");
 *         return configurations;
 *     }
 *
 *
 *     我们看到带有 AutoConfiguration 这个注解   @Configuration(
 *     proxyBeanMethods = false
 * ) 其实是   关闭了cglib动态代理  的 Configuration
 *
 *
 * 再看这个方法ImportCandidates.load
 *
 *
 *  public static ImportCandidates load(Class<?> candidateClass, ClassLoader classLoader) {
 *     // 构建 location 字符串，用于指定要查找的配置文件路径
 *     String location = "META-INF/spring/" + candidateClass.getName();
 *
 *     // 调用 findUrlsInClasspath() 方法，在类路径中查找匹配的资源 URL
 *     Enumeration<URL> urls = findUrlsInClasspath(classLoader, location);
 *
 *     // 遍历 urls 枚举对象中的所有 URL，并读取对应的资源文件
 *     List<CandidateConfiguration> importCandidates = new ArrayList<>();
 *     while (urls.hasMoreElements()) {
 *         URL url = urls.nextElement();
 *         List<CandidateConfiguration> candidates = readCandidateConfigurations(url);
 *         importCandidates.addAll(candidates);
 *     }
 *
 *     return new ImportCandidates(importCandidates);
 *
 * }
 *
 *
 *在 load() 方法中，首先构建了一个 location 字符串，用于指定要查找的配置文件路径。然后通过调用 findUrlsInClasspath() 方法，在类路径中查找匹配的资源 URL。该方法返回一个枚举对象 urls，其中包含了所有匹配的 URL。
 *
 * 接下来，通过一个循环遍历 urls 枚举对象中的每个 URL，调用 readCandidateConfigurations() 方法读取该 URL 对应的资源文件，并将返回的候选配置类添加到 importCandidates 列表中。
 *
 * 最后，通过 new ImportCandidates(importCandidates) 创建一个新的 ImportCandidates 对象，并将其作为方法的返回值。
 *
 *
 *
 * 这样就得到了要自动装配的类
 *
 * 然后通过@Candidate的衍生注解控制这个类是否真正被加载，以及做到自动配置属性
 *
 */


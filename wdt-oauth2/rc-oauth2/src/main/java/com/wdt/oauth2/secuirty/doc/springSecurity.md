# springSecurity

######  经过的过滤器：

```java
Spring Security中默认执行的过滤器顺序如下：

WebAsyncManagerIntegrationFilter
SecurityContextPersistenceFilter
HeaderWriterFilter
CsrfFilter
LogoutFilter
UsernamePasswordAuthenticationFilter
DefaultLoginPageGeneratingFilter
DefaultLogoutPageGeneratingFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter：如果之前的认证机制都没有更新 SecurityContextHolder 拥有的 Authentication，那么一个 AnonymousAuthenticationToken 将会设给 SecurityContextHolder。
SessionManagementFilter
ExceptionTranslationFilter：用于处理在 FilterChain 范围内抛出的 AccessDeniedException 和 AuthenticationException，并把它们转换为对应的 Http 错误码返回或者跳转到对应的页面。
FilterSecurityInterceptor：负责保护 Web URI，并且在访问被拒绝时抛出异常。
```

需要关注UsernamePasswordAuthenticationFilter

拿到 用户信息会后 在执行这个类的代码   类图如下

<img src="C:\Users\a2316\AppData\Roaming\Typora\typora-user-images\image-20240108180943394.png" alt="image-20240108180943394" style="zoom:200%;" />

此时，如果 你  希望 拿到  用户信息  后 执行自己的逻辑  ，那就需要重写这个类，实现自己的逻辑




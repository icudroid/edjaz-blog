package com.edjaz.blog.security;

import com.edjaz.blog.repository.BlogRepository;
import com.edjaz.blog.security.dsl.DslSecurity;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BlogSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final BlogRepository blogRepository;

    public BlogSecurityExpressionHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
        Authentication authentication, MethodInvocation invocation) {
        DslSecurity root = new DslSecurity(authentication, blogRepository);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(this.getPermissionEvaluator());
        root.setTrustResolver(this.getTrustResolver());
        root.setRoleHierarchy(this.getRoleHierarchy());
        // Si le préfix ROLE_ ne nous plait pas, nous pouvons le changer
        root.setDefaultRolePrefix(this.getDefaultRolePrefix());
        return root;

    }
}

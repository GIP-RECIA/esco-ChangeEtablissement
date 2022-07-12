/*
 * Copyright (C) 2017 GIP RECIA http://www.recia.fr
 * @Author (C) 2013 Maxime Bossard <mxbossard@gmail.com>
 * @Author (C) 2016 Julien Gribonvald <julien.gribonvald@recia.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esco.portlet.changeetab.security;

import static org.apereo.portal.soffit.service.AbstractJwtService.DEFAULT_SIGNATURE_KEY;
import static org.apereo.portal.soffit.service.AbstractJwtService.SIGNATURE_KEY_PROPERTY;


import lombok.extern.slf4j.Slf4j;
import org.apereo.portal.soffit.security.SoffitApiAuthenticationManager;
import org.apereo.portal.soffit.security.SoffitApiPreAuthenticatedProcessingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

@EnableWebSecurity
@Slf4j
@ComponentScan(basePackages = "org.esco.portlet.changeetab.web.rest", lazyInit = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${authorizedIpRange}")
    private String authorizedIpRange;

    @Value("${" + SIGNATURE_KEY_PROPERTY + ":" + DEFAULT_SIGNATURE_KEY + "}")
    private String signatureKey;

    @Override
    public void configure(WebSecurity web) throws Exception {
        /*
         * Since this module includes portlets, we only want to apply Spring Security to requests
         * targeting our REST APIs.
         */
        final RequestMatcher pathMatcher = new AntPathRequestMatcher("/rest/**");
        final RequestMatcher inverseMatcher = new NegatedRequestMatcher(pathMatcher);
        web.ignoring().requestMatchers(inverseMatcher);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Assert.notNull(authorizedIpRange, "Property authorizedIpRange should be defined with an IP range");
        final String[] ips = authorizedIpRange.split(",");
        StringBuilder hasIpRangeAccessExpresion = new StringBuilder();
        for (String ip: ips) {
            hasIpRangeAccessExpresion.append(" or hasIpAddress('").append(ip).append("')");
        }

        log.debug("Constructing security with authorized ips : {}", authorizedIpRange);

        http
                .addFilter(preAuthenticatedProcessingFilter())
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/rest/**").access("isAuthenticated()" + hasIpRangeAccessExpresion)
                .antMatchers(HttpMethod.POST,"/rest/**").access("isAuthenticated()" + hasIpRangeAccessExpresion)
                .antMatchers(HttpMethod.DELETE,"/rest/**").denyAll()
                .antMatchers(HttpMethod.PUT,"/rest/**").denyAll()
                .anyRequest().permitAll()
                .and()
                /*
                 * Session fixation protection is provided by uPortal.  Since portlet tech requires
                 * sessionCookiePath=/, we will make the portal unusable if other modules are changing
                 * the sessionId as well.
                 */
                .sessionManagement()
                .sessionFixation().none()
                .and()
                /*
                 * Portlet POST requests include (Spring-based) CSRF protection managed by uPortal.
                 * REST APIs are secured by OIDC Id tokens.
                 */
                .csrf()
                .ignoringAntMatchers("/rest/**");
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new SoffitApiAuthenticationManager();
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() {
        log.debug("Initializing security with signature key: {}", signatureKey);
        final AbstractPreAuthenticatedProcessingFilter rslt = new SoffitApiPreAuthenticatedProcessingFilter(signatureKey);
        rslt.setAuthenticationManager(authenticationManager());
        return rslt;
    }

    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter() {
        /*
         * The ErrorPageFilter (Spring) makes extra calls to HttpServletResponse.flushBuffer(),
         * and this behavior produces many warnings in the portal logs during portlet requests.
         */
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(errorPageFilter());
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }
}


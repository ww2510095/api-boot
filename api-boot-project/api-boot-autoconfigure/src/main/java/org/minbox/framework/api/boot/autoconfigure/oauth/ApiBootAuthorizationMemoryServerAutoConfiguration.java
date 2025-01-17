/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.autoconfigure.oauth;

import org.minbox.framework.api.boot.plugin.oauth.ApiBootAuthorizationServerConfiguration;
import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;


/**
 * ApiBoot 授权服务器内存方式实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(ApiBootAuthorizationServerConfiguration.class)
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
public class ApiBootAuthorizationMemoryServerAutoConfiguration extends ApiBootAuthorizationServerAutoConfiguration {
    public ApiBootAuthorizationMemoryServerAutoConfiguration(ObjectProvider<List<ApiBootOauthTokenGranter>> objectProvider, ApiBootOauthProperties apiBootOauthProperties) {
        super(objectProvider, apiBootOauthProperties);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(apiBootOauthProperties.getClientId())
                .authorizedGrantTypes(apiBootOauthProperties.getGrantTypes())
                .secret(passwordEncoder().encode(apiBootOauthProperties.getClientSecret()))
                .scopes(apiBootOauthProperties.getScopes())
                .resourceIds(apiBootOauthProperties.getResourceId());
    }

    /**
     * 配置内存方式令牌存储
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }
}

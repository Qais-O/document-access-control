package qais.omari.document_access_control_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import qais.omari.document_access_control_system.constants.ErrorCodes;
import qais.omari.document_access_control_system.constants.MessageKeys;
import qais.omari.document_access_control_system.constants.ResponseConstants;
import qais.omari.document_access_control_system.repository.UserRepository;
import qais.omari.document_access_control_system.security.CustomPermissionEvaluator;
import qais.omari.document_access_control_system.security.UsernameAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomPermissionEvaluator permissionEvaluator;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/users").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable()) // For H2 console
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            )
            .addFilterBefore(new UsernameAuthenticationFilter(userRepository), 
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
            String localizedMessage = messageService.getMessage(MessageKeys.Error.UNAUTHORIZED, acceptLanguage);

            Map<String, Object> body = new HashMap<>();
            body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
            body.put(ResponseConstants.Fields.STATUS, HttpServletResponse.SC_UNAUTHORIZED);
            body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.AUTHENTICATION_FAILED);
            body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Security.UNAUTHORIZED);
            body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
            body.put(ResponseConstants.Fields.PATH, request.getRequestURI());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(body));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            String acceptLanguage = request.getHeader(ResponseConstants.Headers.ACCEPT_LANGUAGE);
            String localizedMessage = messageService.getMessage(MessageKeys.Error.ACCESS_DENIED, acceptLanguage);

            Map<String, Object> body = new HashMap<>();
            body.put(ResponseConstants.Fields.TIMESTAMP, LocalDateTime.now());
            body.put(ResponseConstants.Fields.STATUS, HttpServletResponse.SC_FORBIDDEN);
            body.put(ResponseConstants.Fields.ERROR, ResponseConstants.ErrorTypes.ACCESS_DENIED);
            body.put(ResponseConstants.Fields.ERROR_CODE, ErrorCodes.Security.ACCESS_DENIED);
            body.put(ResponseConstants.Fields.MESSAGE, localizedMessage);
            body.put(ResponseConstants.Fields.PATH, request.getRequestURI());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(body));
        };
    }
}
package az.lms.config;

import az.lms.security.JwtAuthenticationEntryPoint;
import az.lms.security.JwtAuthenticationFilter;
import az.lms.security.PasswordEncoder;
import az.lms.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static az.lms.model.enums.RoleType.*;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

   private final JwtAuthenticationFilter jwtAuthenticationFilter;
   private final UserDetailServiceImpl userDetailsService;
   private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
   private final PasswordEncoder passwordEncoder;

   @Bean(BeanIds.AUTHENTICATION_MANAGER)
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }


   @Bean
   public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder.getPasswordEncoder());
      return authProvider;
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authManagerBuilder =
              http.getSharedObject(AuthenticationManagerBuilder.class);
      authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder.getPasswordEncoder());


      http.exceptionHandling()
              .authenticationEntryPoint(jwtAuthenticationEntryPoint).and()

              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

              .cors().disable().httpBasic().and()

              .csrf().disable().authorizeRequests()
                  .antMatchers(HttpMethod.POST, "/student/").permitAll()
                  .antMatchers("/student/orders/{fin}").hasAnyRole(STUDENT.name(), LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.GET, "/student/", "/student/{fin}").hasAnyRole(LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.PUT, "/student/").hasAnyRole(LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.DELETE, "/student/{fin}").hasAnyRole(ADMIN.name())
                  .antMatchers(HttpMethod.GET, "/book/", "/book/name/{}bookName").permitAll()
                  .antMatchers(HttpMethod.GET, "/book/{id}").hasAnyRole(LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.PUT, "/book/").hasAnyRole(LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.POST, "/book/").hasAnyRole(LIBRARIAN.name(), ADMIN.name())
                  .antMatchers(HttpMethod.DELETE, "/book/{id}").hasAnyRole(ADMIN.name())
                  .antMatchers("/order/").hasAnyRole(ADMIN.name(), LIBRARIAN.name())
                  .antMatchers("/order/**").hasAnyRole(STUDENT.name())
                  .antMatchers("/librarian/**", "/author/**").hasAnyRole(ADMIN.name(), LIBRARIAN.name())
                  .antMatchers("/auth/**", "/category/**").permitAll()
                  .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**").permitAll()
                  .anyRequest().authenticated();

      http.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
              .logout(lg -> lg
                      .logoutUrl("/auth/logout")
                      .addLogoutHandler(new SecurityContextLogoutHandler())
                      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                      )
              );
      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
   }
}
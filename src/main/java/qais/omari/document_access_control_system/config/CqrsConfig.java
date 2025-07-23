package qais.omari.document_access_control_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qais.omari.document_access_control_system.middleware.CommandBus;
import qais.omari.document_access_control_system.middleware.CommandBusExecutor;
import qais.omari.document_access_control_system.middleware.QueryBusExecutor;
import qais.omari.document_access_control_system.middleware.QueryBus;

@Configuration
public class CqrsConfig {

    @Bean
    public QueryBus queryBus() {
        return new QueryBusExecutor();
    }

    @Bean
    public CommandBus commandBus() {
        return new CommandBusExecutor();
    }
}

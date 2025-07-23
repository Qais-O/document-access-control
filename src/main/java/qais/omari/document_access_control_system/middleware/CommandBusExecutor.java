package qais.omari.document_access_control_system.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import qais.omari.document_access_control_system.command.Command;
import qais.omari.document_access_control_system.command.handler.CommandHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandBusExecutor implements CommandBus {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private final Map<Class<?>, CommandHandler<?, ?>> handlerCache = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <TResponse> TResponse execute(Command<TResponse> command) {
        CommandHandler<Command<TResponse>, TResponse> handler =
                (CommandHandler<Command<TResponse>, TResponse>) getHandler(command.getClass());
        
        if (handler == null) {
            throw new RuntimeException("No handler found for command: " + command.getClass().getName());
        }
        
        return handler.handle(command);
    }

    private CommandHandler<?, ?> getHandler(Class<?> commandClass) {
        return handlerCache.computeIfAbsent(commandClass, this::findHandler);
    }

    private CommandHandler<?, ?> findHandler(Class<?> commandClass) {
        return applicationContext.getBeansOfType(CommandHandler.class)
                .values()
                .stream()
                .filter(handler -> supportsCommand(handler, commandClass))
                .findFirst()
                .orElse(null);
    }

    private boolean supportsCommand(CommandHandler<?, ?> handler, Class<?> commandClass) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType paramType) {
                if (paramType.getRawType().equals(CommandHandler.class)) {
                    Type commandType = paramType.getActualTypeArguments()[0];
                    return commandType.equals(commandClass);
                }
            }
        }
        return false;
    }
}
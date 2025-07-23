package qais.omari.document_access_control_system.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import qais.omari.document_access_control_system.query.Query;
import qais.omari.document_access_control_system.query.handler.QueryHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QueryBusExecutor implements QueryBus {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private final Map<Class<?>, QueryHandler<?, ?>> handlerCache = new HashMap<>();
    private List<QueryMiddleware> middlewares;

    @Override
    public <T> T execute(Query<T> query) {
        // Start with the core handler
        Function<Query<T>, T> handler = this::handleQuery;
        
        // Wrap with each middleware (reverse order for correct execution)
        List<QueryMiddleware> orderedMiddlewares = getOrderedMiddlewares();
        for (int i = orderedMiddlewares.size() - 1; i >= 0; i--) {
            handler = orderedMiddlewares.get(i).process(handler);
        }
        
        return handler.apply(query);
    }

    @SuppressWarnings("unchecked")
    private <T> T handleQuery(Query<T> query) {
        QueryHandler<Query<T>, T> handler = (QueryHandler<Query<T>, T>) getHandler(query.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler found for query: " + query.getClass().getName());
        }
        return handler.handle(query);
    }

    private List<QueryMiddleware> getOrderedMiddlewares() {
        if (middlewares == null) {
            middlewares = applicationContext.getBeansOfType(QueryMiddleware.class)
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(QueryMiddleware::getOrder))
                    .collect(Collectors.toList());
        }
        return middlewares;
    }

    private QueryHandler<?, ?> getHandler(Class<?> queryClass) {
        return handlerCache.computeIfAbsent(queryClass, this::findHandler);
    }

    private QueryHandler<?, ?> findHandler(Class<?> queryClass) {
        return applicationContext.getBeansOfType(QueryHandler.class)
                .values()
                .stream()
                .filter(handler -> supportsQuery(handler, queryClass))
                .findFirst()
                .orElse(null);
    }

    private boolean supportsQuery(QueryHandler<?, ?> handler, Class<?> queryClass) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType paramType) {
                if (paramType.getRawType().equals(QueryHandler.class)) {
                    Type queryType = paramType.getActualTypeArguments()[0];
                    return queryType.equals(queryClass);
                }
            }
        }
        return false;
    }
}
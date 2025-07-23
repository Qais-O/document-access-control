package qais.omari.document_access_control_system.middleware;

import qais.omari.document_access_control_system.query.Query;

import java.util.function.Function;

public interface QueryMiddleware {
    <T> Function<Query<T>, T> process(Function<Query<T>, T> next);
    
    default int getOrder() {
        return 0;
    }
}
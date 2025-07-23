package qais.omari.document_access_control_system.query.handler;

import qais.omari.document_access_control_system.query.Query;

public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> {
    TResponse handle(TQuery query);
}
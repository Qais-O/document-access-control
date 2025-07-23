package qais.omari.document_access_control_system.middleware;

import qais.omari.document_access_control_system.query.Query;

public interface QueryBus {
    <TResponse> TResponse execute(Query<TResponse> query);
}
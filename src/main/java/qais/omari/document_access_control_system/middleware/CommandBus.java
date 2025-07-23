package qais.omari.document_access_control_system.middleware;

import qais.omari.document_access_control_system.command.Command;

public interface CommandBus {
    <TResponse> TResponse execute(Command<TResponse> command);
}
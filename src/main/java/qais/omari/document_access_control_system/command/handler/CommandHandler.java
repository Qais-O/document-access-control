package qais.omari.document_access_control_system.command.handler;

import qais.omari.document_access_control_system.command.Command;

public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> {
    TResponse handle(TCommand command);
}
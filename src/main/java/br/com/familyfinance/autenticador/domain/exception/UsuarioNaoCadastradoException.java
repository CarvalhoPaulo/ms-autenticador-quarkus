package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class UsuarioNaoCadastradoException extends BusinessException {
    public UsuarioNaoCadastradoException() {
        super(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDAS, "Usuário não encontrado");
    }
}

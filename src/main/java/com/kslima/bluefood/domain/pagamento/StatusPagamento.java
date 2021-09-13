package com.kslima.bluefood.domain.pagamento;

public enum StatusPagamento {

    AUTORIZADO("Autorizado"),
    NAO_AUTORIZADO("Não autorizado pela instituição financeira"),
    CARTAO_INVALIDO("Cartão inválido ou bloqueado");

    String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

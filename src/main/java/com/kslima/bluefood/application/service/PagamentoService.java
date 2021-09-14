package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.pagamento.Pagamento;
import com.kslima.bluefood.domain.pagamento.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void salvar(Pagamento pagamento) {
        pagamentoRepository.save(pagamento);
    }
}

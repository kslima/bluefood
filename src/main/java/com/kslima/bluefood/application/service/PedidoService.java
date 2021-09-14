package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.pagamento.DadosCartao;
import com.kslima.bluefood.domain.pagamento.Pagamento;
import com.kslima.bluefood.domain.pagamento.StatusPagamento;
import com.kslima.bluefood.domain.pedido.*;
import com.kslima.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Value("${bluefood.kpay.url}")
    private String kPayUrl;

    @Value("${bluefood.kpay.token}")
    private String kPayToken;

    @Autowired
    private PagamentoService pagamentoService;

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor =  PagamentoException.class)
    public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(SecurityUtils.loggetdCliente());
        pedido.setRestaurante(carrinho.getRestaurante());
        pedido.setStatus(Pedido.Status.PRODUCAO);
        pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
        pedido.setSubtotal(carrinho.getPrecoTotal(false));
        pedido.setTotal(carrinho.getPrecoTotal(true));

        pedido = pedidoRepository.save(pedido);
        int ordem = 1;

        for (ItemPedido itemPedido : carrinho.getItens()) {
            itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
            itemPedidoService.save(itemPedido);
        }

        DadosCartao dadosCartao = new DadosCartao();
        dadosCartao.setNumCartao(numCartao);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Token", kPayToken);

        HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> response;
        try {
            response = restTemplate.postForObject(kPayUrl, requestEntity, Map.class);
        } catch (Exception e) {
            throw new PagamentoException("Erro no servidor de pagamento");
        }

        if (response == null) {
            throw new PagamentoException("Erro no servidor de pagamento");
        }

        StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));

        if (statusPagamento != StatusPagamento.AUTORIZADO) {
            throw new PagamentoException(statusPagamento.getDescricao());
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setData(LocalDateTime.now());
        pagamento.setPedido(pedido);
        pagamento.definirNumeroEBandeira(numCartao);
        pagamentoService.salvar(pagamento);
        return pedido;
    }

    public Pedido findById(Integer pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
    }

    public List<Pedido> listPedidosByCliente(Integer clienteId) {
        return pedidoRepository.listPedidosByCliente(clienteId);
    }

}

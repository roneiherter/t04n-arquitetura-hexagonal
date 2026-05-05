package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto.PedidoEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.mapper.PedidoDTOMapper;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SqsPedidoAdapter {

    private static final Logger log = LoggerFactory.getLogger(SqsPedidoAdapter.class);

    @Autowired
    private PedidoServicePort orderService;

    @SqsListener("${queue.order-events}")
    public void listen(PedidoEventDTO dto) {
        try {
            log.info("Evento de pedido recebido para o cliente {}", dto.getCustomerId());

            orderService.criarPedido(PedidoDTOMapper.toBO(dto));

            log.info("Pedido processado com sucesso para o cliente {}", dto.getCustomerId());
        } catch (Exception e) {
            log.error("Erro ao processar o evento de pedido para o cliente {}", dto.getCustomerId(), e);
            throw e;
        }
    }

}
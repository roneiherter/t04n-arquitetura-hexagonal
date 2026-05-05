package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto.OrdemItemDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto.PedidoEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper.PessoaDTOMapper;

import java.util.ArrayList;
import java.util.List;

public class PedidoDTOMapper {

    public static PedidoBO toBO(PedidoEventDTO pedidoEventDto) {
        if (pedidoEventDto == null) {
            return null;
        }

        PedidoBO pedido = new PedidoBO();
        pedido.setCep(pedidoEventDto.getZipCode());
        
        if (pedidoEventDto.getPessoa() != null) {
            pedido.setPessoa(PessoaDTOMapper.toBo(pedidoEventDto.getPessoa()));
        }

        if (pedidoEventDto.getOrderItems() != null && !pedidoEventDto.getOrderItems().isEmpty()) {
            List<PedidoProdutoBO> itens = new ArrayList<>();
            
            for (OrdemItemDTO orderItem : pedidoEventDto.getOrderItems()) {
                PedidoProdutoBO item = new PedidoProdutoBO();
                item.setQuantidade(orderItem.getAmount());

                ProdutoBO produto = new ProdutoBO();
                item.setProduto(produto);

                itens.add(item);
            }
            
            pedido.setItens(itens);
        }

        return pedido;
    }
}

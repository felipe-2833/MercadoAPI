package br.com.fiap.mercadoApi.specification;

import br.com.fiap.mercadoApi.controller.ItemController.ItemFilter;
import br.com.fiap.mercadoApi.model.ItemTipo;
import br.com.fiap.mercadoApi.model.ItemRaridade;
import br.com.fiap.mercadoApi.model.Item;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ItemSpecification {
    public static Specification<Item> withFilters(ItemFilter filtro) {
        return Specification.where(temNome(filtro.nome()))
                .and(temTipo(filtro.tipo()))
                .and(temRaridade(filtro.raridade()))
                .and(temDono(filtro.donoId()))
                .and(precoMaiorQue(filtro.precoMin()))
                .and(precoMenorQue(filtro.precoMax()));
    }

    public static Specification<Item> temNome(String nome) {
        return (root, query, builder) -> {
            if (nome == null || nome.isBlank()) return null;
            return builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Item> temTipo(ItemTipo tipo) {
        return (root, query, builder) -> {
            if (tipo == null) return null;
            return builder.equal(root.get("tipo"), tipo);
        };
    }
    
    public static Specification<Item> temRaridade(ItemRaridade raridade) {
        return (root, query, builder) -> {
            if (raridade == null) return null;
            return builder.equal(root.get("raridade"), raridade);
        };
    }

    public static Specification<Item> temDono(Long donoId) {
        return (root, query, builder) -> {
            if (donoId == null) return null;
            Join<Object, Object> dono = root.join("dono");
            return builder.equal(dono.get("id"), donoId);
        };
    }

    public static Specification<Item> precoMaiorQue(BigDecimal precoMin) {
        return (root, query, builder) -> {
            if (precoMin == null) return null;
            return builder.greaterThanOrEqualTo(root.get("preco"), precoMin);
        };
    }

    public static Specification<Item> precoMenorQue(BigDecimal precoMax) {
        return (root, query, builder) -> {
            if (precoMax == null) return null;
            return builder.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }
}
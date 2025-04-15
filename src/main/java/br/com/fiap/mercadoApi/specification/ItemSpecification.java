package br.com.fiap.mercadoApi.specification;

import br.com.fiap.mercadoApi.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ItemSpecification {
    public static Specification<Item> temTipo(ItemTipo tipo) {
        return (root, query, builder) -> tipo == null ? null : builder.equal(root.get("tipo"), tipo);
    }
}
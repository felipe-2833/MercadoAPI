package br.com.fiap.mercadoApi.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.mercadoApi.controller.PersonagemController.PersonagemFilter;
import br.com.fiap.mercadoApi.model.Personagem;
import jakarta.persistence.criteria.Predicate;

public class PersonagemSpecification {
    
     public static Specification<Personagem> withFilters(PersonagemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }
            if (filter.classe() != null) {
                predicates.add(cb.equal(root.get("classe"), filter.classe()));
            }
            

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

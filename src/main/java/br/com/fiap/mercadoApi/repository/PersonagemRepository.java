package br.com.fiap.mercadoApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.mercadoApi.model.Personagem;

public interface  PersonagemRepository extends JpaRepository<Personagem, Long>, JpaSpecificationExecutor<Personagem>{
    
}

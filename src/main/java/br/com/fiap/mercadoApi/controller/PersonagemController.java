package br.com.fiap.mercadoApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.mercadoApi.model.Personagem;
import br.com.fiap.mercadoApi.model.PersonagemClasse;
import br.com.fiap.mercadoApi.repository.PersonagemRepository;
import br.com.fiap.mercadoApi.specification.PersonagemSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("personagem")
@Slf4j
public class PersonagemController {

    public record PersonagemFilter(String nome, PersonagemClasse classe) {
    }

    @Autowired
    private PersonagemRepository repository;

    @GetMapping
    public Page<Personagem> index(PersonagemFilter filter,
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
        var specification = PersonagemSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "personagem", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    }, tags = "personagem", summary = "Adicionar personagem", description = "Adicionar personagem")
    public Personagem create(@RequestBody @Valid Personagem personagem) {
        log.info("Cadastrando animes " + personagem.getNome());
        return repository.save(personagem);
    }

    @GetMapping("/{id}")
    @Operation(description = "Listar personagem pelo id", tags = "personagem", summary = "Listar personagem")
    public Personagem get(@PathVariable Long id) {
        log.info("Buscando personagem " + id);
        return getPersonagem(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar personagem pelo id", tags = "personagem", summary = "Deletar personagem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando personagem " + id);
        repository.delete(getPersonagem(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update personagem pelo id", tags = "personagem", summary = "Update personagem")
    public Personagem update(@PathVariable Long id, @RequestBody @Valid Personagem personagem) {
        log.info("Atualizando ifos personagem " + id + " " + personagem);
        getPersonagem(id);
        personagem.setId(id);
        return repository.save(personagem);
    }

    private Personagem getPersonagem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado na lista"));
    }

}
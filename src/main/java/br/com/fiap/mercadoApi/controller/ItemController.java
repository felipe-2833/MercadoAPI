package br.com.fiap.mercadoApi.controller;

import java.math.BigDecimal;

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

import br.com.fiap.mercadoApi.model.Item;
import br.com.fiap.mercadoApi.model.ItemRaridade;
import br.com.fiap.mercadoApi.model.ItemTipo;
import br.com.fiap.mercadoApi.repository.ItemRepository;
import br.com.fiap.mercadoApi.specification.ItemSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("Item")
@Slf4j
public class ItemController {

    public record ItemFilter(
        String nome,
        ItemTipo tipo,
        ItemRaridade raridade,
        Long donoId,
        BigDecimal precoMin,
        BigDecimal precoMax
    ) {
    }

    @Autowired
    private ItemRepository repository;

    @GetMapping
    public Page<Item> index(ItemFilter filter,
            @PageableDefault(size = 10, sort = "name", direction = Direction.ASC) Pageable pageable) {
        var specification = ItemSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "item", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    }, tags = "item", summary = "Adicionar item", description = "Adicionar item ao mercado")
    public Item create(@RequestBody @Valid Item item) {
        log.info("Cadastrando item: " + item.getNome());
        return repository.save(item);
    }

    @GetMapping("/{id}")
    @Operation(description = "Listar item pelo id", tags = "item", summary = "Listar item")
    public Item get(@PathVariable Long id) {
        log.info("Buscando item " + id);
        return getItem(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar item pelo id", tags = "item", summary = "Deletar item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando item " + id);
        repository.delete(getItem(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update item pelo id", tags = "item", summary = "Update item")
    public Item update(@PathVariable Long id, @RequestBody @Valid Item item) {
        log.info("Atualizando ifos personagem " + id + " " + item);
        getItem(id);
        item.setId(id);
        return repository.save(item);
    }

    private Item getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado na lista"));
    }
}
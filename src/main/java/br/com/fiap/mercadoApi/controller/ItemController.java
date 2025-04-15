package br.com.fiap.mercadoApi.controller;

import br.com.fiap.mercadoApi.model.Item;
import br.com.fiap.mercadoApi.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        BigDecimal precoMax,
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
    public Personagem create(@RequestBody @Valid Personagem personagem) {
        log.info("Cadastrando item: " + personagem.getNome());
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
package br.com.fiap.mercadoApi.model;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Personagem {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo obrigatório")
    @Size(min = 5, max = 255, message = "deve ter pelo menos 5 caracteres")
    private String nome;

    @Positive(message = "deve ser maior que zero")
    private BigDecimal moedas;
    
    @Min(value = 1, message = "O nível mínimo permitido é 1")
    @Max(value = 99, message = "O nível máximo permitido é 99")
    private int nivel;
    
    @NotNull(message = "campo obrigatório, deve ser (guerreiro,mago ou arqueiro)")
    @Enumerated(EnumType.STRING)
    private PersonagemClasse classe;


}
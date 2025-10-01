**Teste unitário (JUnit 5) que detecta a mutação `EmptyObjectReturnValsMutator`**


package com.exemple.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link CalcController}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code subtracao}
 * não retorne um {@link java.util.Collections#emptyMap() emptyMap}.
 * Se a mutação for aplicada, o método sempre retornará um mapa vazio
 * e o teste falhará.</p>
 */
class CalcControllerTest {

    @Test
    @DisplayName("subtracao deve retornar um mapa não vazio")
    void subtracao_retornandoMapaNaoVazio() {
        // Arrange
        CalcController controller = new CalcController();

        // Act
        Map<String, Integer> resultado = controller.subtracao(10, 4);

        // Assert
        assertNotNull(resultado, "O mapa retornado não pode ser null");
        assertFalse(resultado.isEmpty(),
                "O método subtracao deve retornar um mapa contendo o resultado da subtração");
    }

    /**
     * Caso o método subtracao retorne um mapa contendo a chave
     * "resultado" (ou equivalente), este teste adicional garante
     * que o valor calculado está correto. Se a mutação substituir
     * o retorno por {@code Collections.emptyMap()}, o teste falhará
     * porque a chave não será encontrada.
     */
    @Test
    @DisplayName("subtracao deve conter a chave 'resultado' com o valor correto")
    void subtracao_contendoResultadoCorreto() {
        // Arrange
        CalcController controller = new CalcController();

        // Act
        Map<String, Integer> resultado = controller.subtracao(15, 7);

        // Assert
        assertTrue(resultado.containsKey("resultado"),
                "O mapa deve conter a chave 'resultado'");
        assertEquals(8, resultado.get("resultado"),
                "O valor da subtração deve ser 8 (15 - 7)");
    }
}


### Como funciona

1. **Primeiro teste (`subtracao_retornandoMapaNaoVazio`)**  
   - Cria uma instância de `CalcController`.  
   - Chama `subtracao(10, 4)` e obtém o mapa retornado.  
   - Verifica que o mapa não é `null` e que não está vazio.  
   - Se a mutação substituir o retorno por `Collections.emptyMap()`, o mapa será vazio e o teste falhará.

2. **Segundo teste (`subtracao_contendoResultadoCorreto`)**  
   - Além de garantir que o mapa não está vazio, verifica se a chave `"resultado"` está presente e se o valor corresponde à subtração esperada.  
   - Esse teste reforça a verificação e ajuda a identificar mutações que alteram o conteúdo do mapa.

> **Observação**: Se o método `subtracao` usar outra chave (por exemplo, `"subtracao"` ou `"diff"`), basta trocar `"resultado"` pelo nome correto. O importante é que o teste verifique a presença de uma chave que só existe quando o método funciona corretamente.
**Teste unitário que expõe a mutação `EmptyObjectReturnValsMutator`**


package com.exemple.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link CalcController}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code soma} não retorne
 * {@link java.util.Collections#emptyMap()} (mutação gerada pelo Pitest).  O
 * teste verifica se o mapa retornado contém a chave esperada e o valor correto
 * da soma.</p>
 */
class CalcControllerTest {

    /** Instância do controlador a ser testada. */
    private final CalcController controller = new CalcController();

    @Test
    @DisplayName("soma(2, 3) deve retornar um mapa com a chave 'resultado' e valor 5")
    void somaDeveRetornarResultadoCorreto() {
        // Arrange
        int a = 2;
        int b = 3;

        // Act
        Map<String, Integer> resultado = controller.soma(a, b);

        // Assert
        assertNotNull(resultado, "O mapa retornado não pode ser nulo");
        assertFalse(resultado.isEmpty(), "O mapa retornado não pode estar vazio");
        assertTrue(resultado.containsKey("resultado"),
                "O mapa deve conter a chave 'resultado'");
        assertEquals(5, resultado.get("resultado"),
                "O valor da soma deve ser 5");
    }

    @Test
    @DisplayName("soma(-1, 4) deve retornar um mapa com a chave 'resultado' e valor 3")
    void somaComNumerosNegativos() {
        // Arrange
        int a = -1;
        int b = 4;

        // Act
        Map<String, Integer> resultado = controller.soma(a, b);

        // Assert
        assertNotNull(resultado, "O mapa retornado não pode ser nulo");
        assertFalse(resultado.isEmpty(), "O mapa retornado não pode estar vazio");
        assertTrue(resultado.containsKey("resultado"),
                "O mapa deve conter a chave 'resultado'");
        assertEquals(3, resultado.get("resultado"),
                "O valor da soma deve ser 3");
    }
}


### Por que esse teste expõe a mutação?

- **`EmptyObjectReturnValsMutator`** substitui o valor de retorno original por `Collections.emptyMap()`.  
- O teste acima verifica explicitamente que o mapa retornado **não está vazio** e contém a chave `"resultado"` com o valor esperado.  
- Se a mutação for aplicada, `Collections.emptyMap()` será retornado, fazendo com que `resultado.isEmpty()` seja `true` e o teste falhe.  

Assim, qualquer mutação que altere o valor de retorno para um mapa vazio será detectada imediatamente pelo teste.
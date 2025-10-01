**Teste unitário (JUnit 5) que detecta a mutação “PrimitiveReturnsMutator” em `CalcService.divisaoSegura`**


package com.exemple.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes para {@link CalcService}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code divisaoSegura(int, int)}
 * devolva o resultado correto da divisão. Se a mutação
 * <code>org.pitest.mutationtest.engine.gregor.mutators.returns.PrimitiveReturnsMutator</code>
 * substituir o retorno real por <code>0</code>, o teste falhará, revelando a mutação.</p>
 */
class CalcServiceTest {

    /** Instância do serviço que será testada. */
    private final CalcService calcService = new CalcService();

    @Test
    @DisplayName("divisaoSegura deve retornar o resultado correto da divisão")
    void testDivisaoSeguraReturnsCorrectResult() {
        // Arrange
        int numerador = 10;
        int denominador = 2;
        int expected = 5;          // 10 / 2 = 5

        // Act
        int actual = calcService.divisaoSegura(numerador, denominador);

        // Assert
        assertEquals(expected, actual,
                () -> String.format("divisaoSegura(%d, %d) deveria retornar %d, mas retornou %d",
                        numerador, denominador, expected, actual));
    }

    @Test
    @DisplayName("divisaoSegura deve lidar corretamente com números negativos")
    void testDivisaoSeguraWithNegativeNumbers() {
        // Arrange
        int numerador = -10;
        int denominador = 2;
        int expected = -5;          // -10 / 2 = -5

        // Act
        int actual = calcService.divisaoSegura(numerador, denominador);

        // Assert
        assertEquals(expected, actual,
                () -> String.format("divisaoSegura(%d, %d) deveria retornar %d, mas retornou %d",
                        numerador, denominador, expected, actual));
    }
}


### Onde colocar o arquivo



### Por que esse teste revela a mutação

- **Mutação**: O mutador `PrimitiveReturnsMutator` troca o valor retornado por `0`.  
- **Teste**: O teste compara o valor real retornado pelo método com o valor esperado (`5` ou `-5`).  
- **Resultado**: Se a mutação for aplicada, o método retornará `0` e o `assertEquals` falhará, indicando que a mutação não foi corrigida pelo código original.

Assim, o teste garante que a lógica de divisão está funcionando corretamente e que a mutação não passa despercebida.
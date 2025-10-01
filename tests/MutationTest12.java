**Teste unitário (JUnit 5) que detecta a mutação “return 0” em `CalcService.soma`**


package com.exemple.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes para a classe {@link CalcService}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code soma(int, int)}
 * retorne a soma correta dos dois operandos. Se a mutação
 * {@code PrimitiveReturnsMutator} substituir o retorno real por {@code 0},
 * o teste falhará, revelando a mutação.</p>
 */
class CalcServiceTest {

    @Test
    @DisplayName("soma(2, 3) deve retornar 5")
    void somaDeveRetornarSomaCorreta() {
        // Arrange
        CalcService calcService = new CalcService();

        // Act
        int resultado = calcService.soma(2, 3);

        // Assert
        assertEquals(5, resultado,
                "O método soma deve retornar a soma real dos parâmetros");
    }

    // Opcional: teste adicional para garantir que a mutação não afeta outros casos
    @Test
    @DisplayName("soma(10, -4) deve retornar 6")
    void somaComNumeroNegativo() {
        CalcService calcService = new CalcService();
        int resultado = calcService.soma(10, -4);
        assertEquals(6, resultado,
                "O método soma deve lidar corretamente com números negativos");
    }
}


### Por que esse teste revela a mutação?

- **Mutação**: `org.pitest.mutationtest.engine.gregor.mutators.returns.PrimitiveReturnsMutator` troca o valor de retorno de um método que devolve um primitivo (`int`) por `0`.
- **Efeito**: Se `soma` for mutado, a chamada `calcService.soma(2, 3)` retornará `0` em vez de `5`.
- **Detecção**: O `assertEquals(5, resultado)` falhará, indicando que a mutação está presente.

Assim, ao rodar o teste com Pitest (ou qualquer ferramenta de mutação), a mutação será detectada e marcada como **killed**.
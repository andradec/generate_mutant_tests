**Teste JUnit 5 que detecta a mutação “replaced int return with 0” no método `max`**


package com.exemple.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link CalcService}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code max(int, int)} realmente
 * devolve o maior dos dois valores. Se a mutação do Pitest substituir o retorno
 * real por {@code 0}, o teste falhará, revelando a mutação.</p>
 */
class CalcServiceTest {

    private final CalcService calcService = new CalcService();

    @Test
    @DisplayName("max deve retornar o maior valor entre dois inteiros")
    void testMaxReturnsCorrectValue() {
        // Caso onde o maior valor não é 0
        assertEquals(5, calcService.max(5, 3), "max(5, 3) deve retornar 5");

        // Caso onde ambos os valores são iguais
        assertEquals(10, calcService.max(10, 10), "max(10, 10) deve retornar 10");

        // Caso onde o maior valor é 0 (para garantir que a mutação não afeta esse cenário)
        assertEquals(0, calcService.max(0, -1), "max(0, -1) deve retornar 0");
    }
}


### Por que esse teste revela a mutação?

- **Mutação**: `org.pitest.mutationtest.engine.gregor.mutators.returns.PrimitiveReturnsMutator` substitui o retorno real por `0`.
- **Detecção**: O teste compara o resultado real com o valor esperado (`5` e `10`). Se o método retornar `0` (como a mutação faz), a asserção falhará, indicando a presença da mutação.

Assim, ao executar o teste com Pitest, a mutação será detectada e reportada.
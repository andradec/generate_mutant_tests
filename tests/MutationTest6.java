**Teste unitário que expõe a mutação “BooleanTrueReturnValsMutator”**


package com.exemple.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link CalcService}.  
 * <p>
 * O objetivo deste teste é garantir que o método {@code isPar(int)}  
 * devolva <b>false</b> para números ímpares. Se a mutação
 * <code>org.pitest.mutationtest.engine.gregor.mutators.returns.BooleanTrueReturnValsMutator</code>
 * for aplicada (substituindo o retorno real por <code>true</code>), o teste falhará,
 * revelando a mutação.
 * </p>
 */
class CalcServiceTest {

    private CalcService calcService;

    @BeforeEach
    void setUp() {
        calcService = new CalcService();
    }

    @Test
    @DisplayName("isPar deve retornar true para números pares")
    void testIsParWithEvenNumber() {
        assertTrue(calcService.isPar(4), "4 é par, portanto isPar deve retornar true");
        assertTrue(calcService.isPar(0), "0 é par, portanto isPar deve retornar true");
        assertTrue(calcService.isPar(-2), "-2 é par, portanto isPar deve retornar true");
    }

    @Test
    @DisplayName("isPar deve retornar false para números ímpares")
    void testIsParWithOddNumber() {
        assertFalse(calcService.isPar(3), "3 é ímpar, portanto isPar deve retornar false");
        assertFalse(calcService.isPar(1), "1 é ímpar, portanto isPar deve retornar false");
        assertFalse(calcService.isPar(-5), "-5 é ímpar, portanto isPar deve retornar false");
    }
}


### Por que esse teste revela a mutação?

- **Mutação aplicada**: `org.pitest.mutationtest.engine.gregor.mutators.returns.BooleanTrueReturnValsMutator` troca o retorno real de um método booleano por `true`.
- **Efeito**: Se `isPar(int)` for mutado, ele sempre retornará `true`, independentemente do argumento.
- **Detecção**: O segundo teste (`testIsParWithOddNumber`) verifica que `isPar` devolve `false` para números ímpares.  
  - Se a mutação estiver presente, o método retornará `true` e o teste falhará com uma mensagem de erro indicando que o valor esperado era `false`.
  - Se a mutação não estiver presente, o método funciona corretamente e o teste passa.

Assim, ao executar a suíte de testes com Pitest, a mutação será detectada porque o teste falhará quando a mutação for aplicada.
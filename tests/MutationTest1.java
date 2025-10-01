**Teste JUnit 5 que expõe a mutação “integer division → multiplication”**


package com.example.calc;          // ajuste o pacote conforme o seu projeto

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalcServiceTest {

    /**
     * O método {@code divide} (linha 20 de CalcService.java) deve
     * realizar a divisão inteira de dois inteiros.
     *
     * Se a mutação do PIT for aplicada, a operação de divisão será
     * trocada por multiplicação, fazendo com que o resultado esperado
     * (5) seja alterado para 20. Este teste garante que a lógica
     * correta (divisão) seja executada.
     */
    @Test
    void divide_shouldReturnCorrectResult() {
        CalcService calcService = new CalcService();

        // Caso de teste simples: 10 / 2 = 5
        int dividend = 10;
        int divisor  = 2;
        int expected = 5;

        int actual = calcService.divide(dividend, divisor);

        assertEquals(expected, actual,
                () -> String.format("Expected %d but got %d for %d/%d",
                                    expected, actual, dividend, divisor));
    }

    /**
     * Teste adicional para garantir que a divisão com resto seja
     * tratada corretamente (ex.: 7 / 3 = 2 em divisão inteira).
     */
    @Test
    void divide_withRemainder_shouldReturnFloorResult() {
        CalcService calcService = new CalcService();

        int dividend = 7;
        int divisor  = 3;
        int expected = 2;   // 7 / 3 = 2 (int division)

        int actual = calcService.divide(dividend, divisor);

        assertEquals(expected, actual,
                () -> String.format("Expected %d but got %d for %d/%d",
                                    expected, actual, dividend, divisor));
    }
}


### Por que esse teste revela a mutação?

- **Mutação aplicada**: `10 / 2` → `10 * 2`  
  Se a mutação for ativada, o método retornará `20` em vez de `5`.  
  O `assertEquals(5, actual)` falhará, indicando que a mutação foi detectada.

- **Cobertura de casos**: O segundo teste (`divide_withRemainder_shouldReturnFloorResult`) garante que a lógica de divisão inteira (com truncamento) continue correta, reforçando a detecção de mutações que alteram a operação aritmética.

> **Observação**: Ajuste o nome do pacote e o nome do método (`divide`) conforme a sua implementação real em `CalcService.java`. Se o método tiver outro nome, basta trocar `calcService.divide(...)` pelo nome correto.
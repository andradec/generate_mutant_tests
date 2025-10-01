**Teste unitário (JUnit 5) que detecta a mutação “addition → subtraction”**


package com.example.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link CalcService}.  
 * O objetivo é garantir que o método {@code add(int, int)} realmente
 * soma os dois operandos. Se a mutação do Pitest trocar a operação
 * de soma por subtração, o teste falhará (ex.: 2 + 3 → 5, mas 2 - 3 → -1).
 */
class CalcServiceTest {

    private CalcService calcService;

    @BeforeEach
    void setUp() {
        calcService = new CalcService();
    }

    @Test
    @DisplayName("add(2, 3) deve retornar 5 (não 2 - 3)")
    void testAdd_Sum() {
        int a = 2;
        int b = 3;
        int expected = 5;          // 2 + 3
        int actual   = calcService.add(a, b);

        assertEquals(expected, actual,
                () -> String.format("Expected %d but got %d for add(%d, %d)",
                        expected, actual, a, b));
    }

    /* -------------------------------------------------------------
     *  Se quiser testar mais combinações, pode usar um teste parametrizado
     *  (ou mais métodos).  O importante é que o valor esperado seja a
     *  soma real, não a subtração.
     * ------------------------------------------------------------- */
}


### Por que esse teste “revela” a mutação?

- **Mutação esperada**: `MathMutator` troca `+` por `-`.  
- **Comportamento original**: `add(2, 3)` → `5`.  
- **Comportamento mutado**: `add(2, 3)` → `-1`.  
- O teste compara o resultado real com o valor esperado (`5`).  
  Se a mutação ocorrer, o `assertEquals` falhará, indicando que a
  operação de soma foi alterada para subtração.

### Como usar

1. Coloque a classe `CalcService` no pacote `com.example.calc`.  
2. Adicione a dependência do JUnit 5 ao seu projeto (Maven/Gradle).  
3. Execute os testes. Se a mutação estiver presente, o teste falhará
   com uma mensagem semelhante a:



Assim, o teste confirma que a mutação foi detectada.
**Teste unitário (JUnit 5) que detecta a mutação `RemoveConditionalMutator_ORDER_ELSE`**


package com.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para {@link CalcService}.  
 * O objetivo é revelar a mutação que substitui a condição por {@code false}
 * (RemoveConditionalMutator_ORDER_ELSE).  
 * Quando a condição for removida, o ramo “else” será executado de forma
 * indevida, alterando o resultado esperado.
 */
class CalcServiceTest {

    /**
     * Quando o primeiro argumento é maior que o segundo, o método deve
     * retornar a diferença (a - b).  
     * Se a condição for removida e sempre falsa, o método retornará
     * (b - a), que é o valor oposto e fará o teste falhar.
     */
    @Test
    void testCalculate_WhenFirstGreaterThanSecond() {
        CalcService service = new CalcService();

        int a = 5;
        int b = 3;
        int expected = a - b;          // 2
        int actual   = service.calculate(a, b);

        assertEquals(expected, actual,
                "calculate(a,b) deve retornar a diferença quando a > b");
    }

    /**
     * Quando o segundo argumento é maior ou igual ao primeiro, o método
     * deve retornar a diferença (b - a).  Este caso não revela a mutação
     * (pois o ramo “else” já é o que deveria ser executado), mas serve
     * como cobertura adicional.
     */
    @Test
    void testCalculate_WhenSecondGreaterOrEqualFirst() {
        CalcService service = new CalcService();

        int a = 3;
        int b = 5;
        int expected = b - a;          // 2
        int actual   = service.calculate(a, b);

        assertEquals(expected, actual,
                "calculate(a,b) deve retornar a diferença quando b >= a");
    }
}


### Por que esse teste revela a mutação?

- **Código original** (linha 24) provavelmente tem algo como:

  
  if (a > b) {
      return a - b;
  } else {
      return b - a;
  }
  

- **Mutação**: a condição `a > b` é substituída por `false`.  
  Assim, o método sempre executa o bloco `else`, retornando `b - a`.

- **Teste**: `testCalculate_WhenFirstGreaterThanSecond()` chama `calculate(5, 3)`.  
  O resultado esperado é `2` (`5 - 3`). Se a mutação ocorrer, o método retornará `-2` (`3 - 5`), fazendo o `assertEquals` falhar e, portanto, revelando a mutação.

> **Observação**: Ajuste o pacote (`com.example.service`) e o nome do método (`calculate`) de acordo com a sua implementação real.
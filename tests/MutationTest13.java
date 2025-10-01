**Teste unitário (JUnit 5) que detecta a mutação “subtração → adição”**


package org.exemplo.calc;          // <-- ajuste para o pacote real

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link CalcService}.  
 * O objetivo é garantir que a operação de subtração esteja correta.
 * Se a mutação do Pitest substituir a subtração por adição, o teste falhará.
 */
class CalcServiceTest {

    private final CalcService calcService = new CalcService();

    @Test
    @DisplayName("Subtração simples (5 - 3 = 2)")
    void testSubtracaoSimples() {
        int resultado = calcService.subtract(5, 3);   // método na linha 13
        assertEquals(2, resultado,
                "O método subtract deveria retornar 2 (5 - 3), mas retornou " + resultado);
    }

    @Test
    @DisplayName("Subtração com número negativo (10 - (-4) = 14)")
    void testSubtracaoComNegativo() {
        int resultado = calcService.subtract(10, -4);
        assertEquals(14, resultado,
                "O método subtract deveria retornar 14 (10 - (-4)), mas retornou " + resultado);
    }

    @Test
    @DisplayName("Subtração que resulta em zero (7 - 7 = 0)")
    void testSubtracaoZero() {
        int resultado = calcService.subtract(7, 7);
        assertEquals(0, resultado,
                "O método subtract deveria retornar 0 (7 - 7), mas retornou " + resultado);
    }
}


### Como funciona

1. **`CalcService.subtract(int a, int b)`** – método que está na linha 13 do arquivo `CalcService.java`.  
2. O teste chama o método com valores conhecidos e verifica se o resultado corresponde à subtração correta (`a - b`).  
3. Se a mutação do Pitest trocar a subtração por adição, o método retornará `a + b`.  
   - Exemplo: `subtract(5, 3)` retornaria `8` em vez de `2`.  
   - O `assertEquals` então falhará, revelando a mutação.

### Observação

- Caso o método tenha outro nome ou assinatura, basta ajustar a chamada (`calcService.<nomeDoMetodo>(…)`) e os valores esperados.  
- Os três testes cobrem cenários básicos (positivo, negativo e zero) e já são suficientes para detectar a mutação descrita.
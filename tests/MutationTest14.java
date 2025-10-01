**Teste unitário (JUnit 5) que detecta a mutação “PrimitiveReturnsMutator” em `CalcService.subtracao`**


package com.exemple.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para {@link CalcService}.  
 * O objetivo é garantir que o método {@code subtracao} devolva o resultado real da subtração
 * e não o valor 0 que a mutação (PrimitiveReturnsMutator) introduz.
 */
class CalcServiceTest {

    private CalcService calcService;

    @BeforeEach
    void setUp() {
        calcService = new CalcService();
    }

    @Nested
    @DisplayName("subtracao(int, int)")
    class SubtracaoTests {

        @Test
        @DisplayName("deve retornar a diferença correta (caso positivo)")
        void deveRetornarDiferencaPositiva() {
            int resultado = calcService.subtracao(10, 3);
            assertEquals(7, resultado, "10 - 3 deve ser 7");
        }

        @Test
        @DisplayName("deve retornar a diferença correta (caso negativo)")
        void deveRetornarDiferencaNegativa() {
            int resultado = calcService.subtracao(5, 10);
            assertEquals(-5, resultado, "5 - 10 deve ser -5");
        }

        @Test
        @DisplayName("deve retornar 0 quando os operandos forem iguais")
        void deveRetornarZeroQuandoIguais() {
            int resultado = calcService.subtracao(4, 4);
            assertEquals(0, resultado, "4 - 4 deve ser 0");
        }

        @Test
        @DisplayName("não deve retornar 0 para qualquer entrada válida (detecta mutação)")
        void deveDetectarMutacao() {
            // Se a mutação estiver ativa, o método sempre retornará 0.
            // Este teste garante que o valor real da subtração é diferente de 0
            // quando os operandos não são iguais.
            int resultado = calcService.subtracao(12, 7);
            assertNotEquals(0, resultado, "12 - 7 não deve ser 0 (mutação detectada)");
            assertEquals(5, resultado, "12 - 7 deve ser 5");
        }
    }
}


### Por que esse teste “revela” a mutação?

- **Mutação `PrimitiveReturnsMutator`**: substitui o valor de retorno real por `0`.  
- O teste `deveDetectarMutacao()` compara o resultado com `0` **e** com o valor esperado (`5` no exemplo).  
- Se a mutação estiver ativa, o método sempre devolverá `0`, fazendo com que o `assertNotEquals(0, resultado)` falhe **e** o `assertEquals(5, resultado)` também falhe.  
- Assim, o teste falha imediatamente, indicando que a mutação está presente.

### Como usar

1. Coloque o arquivo `CalcServiceTest.java` no mesmo pacote (`com.exemple.demo.service`) ou em um pacote de teste correspondente.  
2. Execute os testes com sua ferramenta de build (Maven, Gradle, IDE).  
3. Se algum teste falhar, a mutação foi detectada. Se todos passarem, o método está correto.
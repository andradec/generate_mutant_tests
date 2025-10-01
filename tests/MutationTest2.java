**Teste unitário JUnit 5 que expõe a mutação `RemoveConditionalMutator_EQUAL_ELSE`**


package com.seuprojeto.calc;          // ajuste o pacote conforme seu projeto

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes para {@link CalcService}.  
 * <p>
 * O objetivo é garantir que a lógica que depende de uma comparação de igualdade
 * (linha 17 de CalcService) esteja correta. Se a mutação
 * {@code RemoveConditionalMutator_EQUAL_ELSE} for aplicada, a condição
 * será sempre falsa e o teste falhará, revelando a mutação.
 * </p>
 */
class CalcServiceTest {

    private final CalcService service = new CalcService();

    @Test
    @DisplayName("Quando os dois operandos são iguais, deve retornar a soma")
    void testCalculateWhenOperandsAreEqual() {
        int a = 7;
        int b = 7;

        int resultado = service.calculate(a, b);

        // Se a condição for mutada para 'false', o método retornará a diferença (0),
        // fazendo o assert falhar.
        assertEquals(a + b, resultado,
                "Quando os operandos são iguais, o resultado deve ser a soma");
    }

    @Test
    @DisplayName("Quando os operandos são diferentes, deve retornar a diferença")
    void testCalculateWhenOperandsAreDifferent() {
        int a = 10;
        int b = 3;

        int resultado = service.calculate(a, b);

        // Mesmo com a mutação, este caso continua válido, pois a condição
        // nunca é verdadeira. O teste serve apenas como cobertura adicional.
        assertEquals(a - b, resultado,
                "Quando os operandos são diferentes, o resultado deve ser a diferença");
    }
}


### Como funciona

1. **Primeiro teste (`testCalculateWhenOperandsAreEqual`)**  
   - Chama `service.calculate(7, 7)`.  
   - Se a condição `a == b` for mantida, o método retorna `14`.  
   - Se a mutação substituir a condição por `false`, o método sempre executa o ramo `else` e retorna `0`.  
   - O `assertEquals` falhará, revelando a mutação.

2. **Segundo teste (`testCalculateWhenOperandsAreDifferent`)**  
   - Serve como cobertura adicional e garante que o ramo `else` funciona corretamente.  
   - Não é sensível à mutação, mas ajuda a garantir que o método não foi alterado de forma inesperada.

### Onde colocar

- Salve o arquivo como `CalcServiceTest.java` dentro de `src/test/java/com/seuprojeto/calc/` (ajuste o caminho de acordo com seu projeto).

### Rodando o PIT



Se a mutação estiver presente, o relatório do PIT mostrará que o teste acima falhou, indicando que a condição de igualdade foi removida. Se o teste passar, a mutação não está presente.
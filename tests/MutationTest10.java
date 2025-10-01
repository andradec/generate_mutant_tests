**Teste unitário que expõe a mutação “replaced int return with 0”**  
(para a classe `CalcService` que está em `com.exemple.demo.service`)


package com.exemple.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes para a classe {@link CalcService}.
 *
 * <p>O objetivo deste teste é garantir que o método {@code max(int, int)} realmente
 * devolve o maior dos dois valores. Se a mutação do Pitest substituir o retorno
 * por {@code 0}, o teste falhará, revelando a mutação.</p>
 */
class CalcServiceTest {

    private final CalcService calcService = new CalcService();

    @Test
    @DisplayName("max deve retornar o maior valor entre dois inteiros")
    void testMaxReturnsCorrectValue() {
        // Casos de teste variados
        assertEquals(10, calcService.max(10, 5),  "max(10, 5) deveria ser 10");
        assertEquals(5,  calcService.max(5, 10), "max(5, 10) deveria ser 10");
        assertEquals(0,  calcService.max(0, 0),  "max(0, 0) deveria ser 0");
        assertEquals(-3, calcService.max(-3, -7), "max(-3, -7) deveria ser -3");
        assertEquals(7,  calcService.max(-7, 7),  "max(-7, 7) deveria ser 7");
    }
}


### Por que este teste detecta a mutação?

- A mutação `org.pitest.mutationtest.engine.gregor.mutators.returns.PrimitiveReturnsMutator` altera o corpo do método `max` para sempre retornar `0`.  
- O teste acima verifica explicitamente que o retorno corresponde ao maior dos dois parâmetros.  
- Se o método for mutado para retornar `0`, todas as asserções que esperam valores diferentes de `0` falharão, revelando a mutação.

### Como executar



Ao rodar o teste com a mutação ativa, você verá algo como:



Isso confirma que a mutação foi detectada. Se o teste passar, significa que a implementação original está correta e a mutação não está presente.
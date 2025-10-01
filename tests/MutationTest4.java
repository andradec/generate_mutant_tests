**Teste unitário (JUnit 5) que detecta a mutação “replaced integer modulus with multiplication”**


package com.example.calc;          // ajuste o pacote conforme o seu projeto

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes da classe {@link CalcService}.
 *
 * <p>O objetivo deste teste é garantir que o método que calcula o resto
 * (modulus) esteja realmente usando a operação “%”. Se a mutação
 * do Pitest trocar o operador “%” por “*”, o resultado esperado
 * (ex.: 10 % 3 = 1) não será obtido (10 * 3 = 30) e o teste falhará,
 * revelando a mutação.</p>
 */
class CalcServiceTest {

    @Test
    @DisplayName("Deve retornar o resto da divisão (modulus) corretamente")
    void testRemainderComModulus() {
        // Arrange
        CalcService service = new CalcService();

        // Act
        int resultado = service.getRemainder(10, 3);   // 10 % 3

        // Assert
        assertEquals(1, resultado,
                "O método getRemainder deve retornar 1 para 10 % 3");
    }

    @Test
    @DisplayName("Deve lidar corretamente com números negativos")
    void testRemainderComNegativos() {
        // Arrange
        CalcService service = new CalcService();

        // Act
        int resultado1 = service.getRemainder(-10, 3); // -10 % 3
        int resultado2 = service.getRemainder(10, -3); // 10 % -3

        // Assert
        assertEquals(-1, resultado1,
                "O método getRemainder deve retornar -1 para -10 % 3");
        assertEquals(1, resultado2,
                "O método getRemainder deve retornar 1 para 10 % -3");
    }
}


### Como funciona

| Passo | O que acontece | Por que isso detecta a mutação |
|-------|----------------|--------------------------------|
| 1. **Arrange** | Instancia `CalcService`. | Prepara o objeto sob teste. |
| 2. **Act** | Chama `getRemainder(10, 3)`. | Se o método usar `%`, o resultado será `1`. Se a mutação trocar por `*`, o resultado será `30`. |
| 3. **Assert** | Verifica que o resultado é `1`. | Se a mutação estiver presente, a asserção falhará, revelando o erro. |

### Observação

- Ajuste o nome do método (`getRemainder`) e o pacote (`com.example.calc`) de acordo com a sua implementação real.
- Se o método que contém a mutação for outro, basta substituir a chamada `service.getRemainder(10, 3)` pelo método correto e ajustar os valores esperados.
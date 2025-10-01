**Teste unitário que revela a mutação `ConditionalsBoundaryMutator`**

O *mutator* altera a fronteira de uma condição (`>`, `>=`, `<`, `<=`, `==`, `!=`).  
Para que o teste falhe quando a mutação estiver presente, precisamos testar exatamente o valor que fica na fronteira da condição.

A seguir está um exemplo de teste JUnit 5 que faz isso.  
O método que está na linha 24 (o qual contém a condição de fronteira) é chamado **`calculate`** e recebe um `int`.  
Se a condição for `value >= 10` (por exemplo), o teste verifica:

* o valor **exato** da fronteira (`10`) – deve satisfazer a condição original;
* um valor **logo abaixo** da fronteira (`9`) – deve *não* satisfazer a condição original.

Se a mutação mudar a condição de `>=` para `>`, o teste falhará porque o valor `10` deixará de satisfazer a condição.


package com.example.calc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para {@link CalcService}.
 *
 * <p>Este teste foi criado especificamente para detectar a mutação
 * {@code ConditionalsBoundaryMutator}. Ele verifica a fronteira da
 * condição presente na linha 24 do {@code CalcService}.
 */
class CalcServiceTest {

    @Test
    @DisplayName("Teste de fronteira da condição em CalcService.calculate")
    void testCalculateBoundaryCondition() {
        // Arrange
        CalcService service = new CalcService();

        // A condição na linha 24 provavelmente tem a forma:
        //     if (value >= 10) { ... }
        // Portanto, 10 é o valor de fronteira.
        final int BOUNDARY = 10;

        // Act & Assert – valor de fronteira (deve satisfazer a condição)
        assertTrue(
                service.calculate(BOUNDARY),
                "O valor de fronteira (%d) deve satisfazer a condição original".formatted(BOUNDARY));

        // Act & Assert – valor logo abaixo da fronteira (não deve satisfazer)
        assertFalse(
                service.calculate(BOUNDARY - 1),
                "O valor abaixo da fronteira (%d) não deve satisfazer a condição original".formatted(BOUNDARY - 1));
    }
}


### Como o teste revela a mutação

| Cenário | Valor de entrada | Condição original | Resultado esperado | Resultado com mutação (`>=` → `>`) |
|---------|------------------|-------------------|-------------------|-----------------------------------|
| Fronteira | `10` | `10 >= 10` → **true** | `true` | `10 > 10` → **false** |
| Abaixo da fronteira | `9` | `9 >= 10` → **false** | `false` | `9 > 10` → **false** |

Se a mutação for aplicada, o teste falhará na primeira asserção (`assertTrue`), pois o método retornará `false` para o valor `10`.  
Assim, o teste confirma que a condição de fronteira não foi alterada.

> **Observação**: Se o método que está na linha 24 tiver outro nome ou assinatura, basta substituir `service.calculate(...)` pelo nome e parâmetros corretos. O princípio permanece o mesmo: testar o valor que fica exatamente na fronteira da condição.
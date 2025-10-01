**Teste unitário (JUnit 5) que detecta a mutação  
`RemoveConditionalMutator_EQUAL_ELSE` (linha 30 do `CalcService`)**


// CalcServiceTest.java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalcServiceTest {

    /**
     * Quando os dois operandos são iguais, o método deve retornar a soma.
     * Se a condição `a == b` for removida (mutação), o método sempre
     * executará o bloco `else` e retornará a diferença, fazendo o teste
     * falhar.
     */
    @Test
    @DisplayName("calculate() deve somar quando os operandos são iguais")
    void testCalculateWhenOperandsAreEqual() {
        CalcService service = new CalcService();

        int a = 7;
        int b = 7;

        int resultado = service.calculate(a, b);

        assertEquals(a + b, resultado,
                "Quando a == b, o resultado esperado é a soma");
    }

    /**
     * Teste adicional (opcional) que garante que o caminho `else`
     * continua funcionando corretamente.
     */
    @Test
    @DisplayName("calculate() deve subtrair quando os operandos são diferentes")
    void testCalculateWhenOperandsAreDifferent() {
        CalcService service = new CalcService();

        int a = 10;
        int b = 3;

        int resultado = service.calculate(a, b);

        assertEquals(a - b, resultado,
                "Quando a != b, o resultado esperado é a diferença");
    }
}


### Por que esse teste revela a mutação?

- **Código original (linha 30)**  
  
  if (a == b) {
      return a + b;
  } else {
      return a - b;
  }
  
- **Mutação `RemoveConditionalMutator_EQUAL_ELSE`**  
  Substitui a condição `a == b` por `false`.  
  O bloco `if` nunca é executado, e o método sempre retorna `a - b`.

O teste `testCalculateWhenOperandsAreEqual()` verifica explicitamente que, quando `a` e `b` são iguais, o resultado deve ser a soma. Se a mutação for aplicada, o método retornará a diferença e o teste falhará, revelando a presença da mutação.
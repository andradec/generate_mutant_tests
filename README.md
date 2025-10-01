# Generate Mutant Tests

![Python](https://img.shields.io/badge/python-3.12+-blue)
![Java](https://img.shields.io/badge/java-17+-red)
![License](https://img.shields.io/badge/license-MIT-green)

Este projeto gera automaticamente **testes unitários em Java (JUnit5)** para detectar mutações em código-fonte, usando relatórios gerados pelo **Pitest** e uma **LLM local** (como Llama3 via Ollama).

---

## Funcionalidades

- Analisa o relatório XML do **Pitest** (`mutations.xml`) e identifica mutações **sobreviventes** ou **não cobertas**.
- Gera testes unitários em Java, **estruturados e indentados**, usando **JUnit5**.
- Integra com **LLM local** (Llama3) para criar código de teste baseado nas mutações.
- Salva os testes gerados na pasta `tests/` organizada por classe e método.

---

## Estrutura do Projeto

generate_mutant_tests/
├── config.yaml # Template de configuração da LLM e prompt
├── generate_mutant.py # Script Python principal
├── pitest_reports/ # Relatórios gerados pelo Pitest
├── tests/ # Testes unitários gerados
└── README.md



---

## Requisitos

- Python 3.12+
- Pipenv ou venv
- Pacotes Python:
  - `requests`
  - `pyyaml`
  - `xmltodict`
  - `jinja2`
- **LLM local** instalada (ex: Llama3 via Ollama)
- **Java 17+** e **JUnit5** para executar os testes
- Projeto Java com Pitest configurado para gerar o relatório XML

---

## Configuração

Crie um arquivo `config.yaml` seguro (não inclua chaves de API reais):

```yaml
llm:
  api_url: "<LLM_API_URL>"
  model: "<MODEL_NAME>"
  temperature: 0.2

prompt_template: |
  Gere um teste unitário em Java usando JUnit5.
  A classe alvo é {{ class_name }}.
  Método: {{ method_name }} (linha {{ line }}).
  Mutação: {{ mutator }}.
  Descrição: {{ description }}.
  Código fonte:
  {{ source_code }}


python -m venv llama_env
source llama_env/bin/activate  # Linux / WSL
# Para Windows PowerShell:
# .\llama_env\Scripts\Activate.ps1


pip install requests pyyaml xmltodict jinja2


python generate_mutant.py


tests/
├── CalcServiceTest_soma.py
├── CalcServiceTest_subtracao.py
└── ...


mvn test


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalcServiceTest {

    @Test
    public void testSoma_MutationDetected() {
        // Arrange
        CalcService calcService = new CalcService();

        // Act
        int result = calcService.soma(2, 3);

        // Assert: verificar mutação
        assert(result != 0);
    }
}


Workflow

O script Python lê o arquivo mutations.xml gerado pelo Pitest.

Para cada mutação sobrevivente ou não coberta, é gerado um prompt baseado no config.yaml.

O prompt é enviado para a LLM local via API (Llama3).

A LLM retorna o código do teste unitário em Java.

O teste é salvo em tests/, nomeado por classe e método.

Observações

Certifique-se que o relatório XML do Pitest esteja disponível em pitest_reports/mutations.xml.

O script suporta múltiplas mutações e gera arquivos separados para cada método afetado.

É possível personalizar o prompt_template no config.yaml para ajustar o estilo dos testes gerados.

Para integração com LLM local (Llama3) via Ollama, certifique-se de que o endpoint da API esteja ativo.
# Generate Mutant Tests

Este projeto tem como objetivo gerar automaticamente **testes unitários em Java (JUnit5)** para detectar mutações em código-fonte, usando relatórios gerados pelo **Pitest** e uma **LLM local** (como Llama3 via Ollama).

---

## Funcionalidades

- Analisa o relatório XML do **Pitest** (`mutations.xml`) e identifica mutações sobreviventes ou não cobertas.
- Gera testes unitários em Java, estruturados e indentados, usando **JUnit5**.
- Integra com **LLM local** (Llama3) para criar código de teste baseado nas mutações.
- Salva os testes gerados na pasta `tests/` de forma organizada por classe e método.

---

## Estrutura do Projeto

generate_mutant_tests/
├── config.yaml # Configurações da LLM e prompt
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

No arquivo `config.yaml` você define:

```yaml
llm:
  api_url: "http://localhost:11434/api/generate"
  model: "llama3"
  temperature: 0.2

prompt_template: |
  Gere um teste unitário em Java usando JUnit5.
  A classe alvo é {{ class_name }}.
  Método: {{ method_name }} (linha {{ line }}).
  Mutação: {{ mutator }}.
  Descrição: {{ description }}.
  Código fonte:
  {{ source_code }}

  Crie um teste que revele a mutação.



Como usar

1 - Instale dependências Python:

python -m venv llama_env
source llama_env/bin/activate
pip install requests pyyaml xmltodict jinja2

2 - Execute o script:
python generate_mutant.py

3 - Os testes gerados serão salvos em:

tests/
├── CalcServiceTest_soma.py
├── CalcServiceTest_subtracao.py
└── ...

4 - Execute os testes com Maven/Gradle no projeto Java:

mvn test


Observações

- Certifique-se que o relatório XML do Pitest esteja disponível em pitest_reports/mutations.xml.

- O script suporta múltiplas mutações e gera arquivos separados para cada método afetado.

- É possível personalizar o prompt_template no config.yaml para ajustar o estilo dos testes gerados.


Licença

MIT License

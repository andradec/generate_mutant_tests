import requests
import json
import yaml
import re
import xmltodict
from jinja2 import Template
from pathlib import Path

# =============================
# Carregar config.yaml
# =============================
with open("config.yaml", "r") as f:
    config = yaml.safe_load(f)

API_URL = config["llm"]["api_url"]
MODEL = config["llm"]["model"]
TEMPERATURE = config["llm"].get("temperature", 0.2)
API_KEY = config["llm"]["api_key"]

# =============================
# Carregar mutations.xml
# =============================
with open("/mnt/c/Users/caio_/OneDrive/Área de Trabalho/generate_mutant_tests/pitest_reports/mutations.xml", "r") as f:
    data = xmltodict.parse(f.read())

mutations = data["mutations"]["mutation"]
if isinstance(mutations, dict):
    mutations = [mutations]

# =============================
# Preparar template
# =============================
prompt_template = Template(config["prompt_template"])

def limpar_codigo_llm(texto):
    """
    Remove blocos de Markdown e explicações, deixando apenas o código Java.
    """
    codigo = re.sub(r"```java(.*?)```", r"\1", texto, flags=re.DOTALL)
    codigo = re.sub(r"```.*?```", "", codigo, flags=re.DOTALL)
    linhas = codigo.split("\n")
    linhas = [l for l in linhas if not l.strip().startswith("Este teste")]
    return "\n".join(linhas).strip()

def chamar_llm(prompt):
    """
    Envia prompt para o modelo configurado no OpenRouter.
    """
    headers = {
        "Authorization": f"Bearer {API_KEY}",
        "Content-Type": "application/json"
    }

    payload = {
        "model": MODEL,
        "messages": [
            {"role": "user", "content": prompt}
        ],
        "temperature": TEMPERATURE,
        "reasoning": {"effort": "high"}
    }

    response = requests.post(API_URL, headers=headers, data=json.dumps(payload))
    response.raise_for_status()
    result = response.json()

    return result["choices"][0]["message"].get("content", "")

# =============================
# Gerar pasta tests
# =============================
tests_dir = Path("tests")
tests_dir.mkdir(exist_ok=True)

# =============================
# Gerar arquivos para cada mutação
# =============================
for i, mutation in enumerate(mutations, start=1):
    prompt = prompt_template.render(
        class_name=mutation.get("class", ""),
        method_name=mutation.get("method", ""),
        line=mutation.get("lineNumber", ""),
        mutator=mutation.get("mutator", ""),
        description=mutation.get("description", ""),
        source_code=mutation.get("sourceFile", "")
    )

    resposta_llm = chamar_llm(prompt)
    codigo_limpo = limpar_codigo_llm(resposta_llm)

    # Nome do arquivo de saída
    file_name = tests_dir / f"MutationTest{i}.java"

    with open(file_name, "w", encoding="utf-8") as f:
        f.write(codigo_limpo)

    print(f"✅ Arquivo gerado: {file_name}")
# =============================
# Fim do script 
import os
import requests
import yaml
import xmltodict
import re
from jinja2 import Template

# Carregar config.yaml
with open("config.yaml", "r") as f:
    config = yaml.safe_load(f)

API_URL = config["llm"]["api_url"]
MODEL = config["llm"]["model"]
TEMPERATURE = config["llm"].get("temperature", 0.2)

# Carrega mutations.xml do PIT
with open("/mnt/c/Users/caio_/OneDrive/Área de Trabalho/generate_mutant_tests/pitest_reports/mutations.xml", "r") as f:
    data = xmltodict.parse(f.read())

mutations = data["mutations"]["mutation"]
if isinstance(mutations, dict):
    mutations = [mutations]

prompt_template = Template(config["prompt_template"])



def limpar_codigo_llm(texto):
    """
    Remove blocos de Markdown e explicações, deixando apenas o código Java.
    """
    # Remove blocos ```java ... ```
    codigo = re.sub(r"```java(.*?)```", r"\1", texto, flags=re.DOTALL)
    # Remove outros blocos ```...``` genéricos
    codigo = re.sub(r"```.*?```", "", codigo, flags=re.DOTALL)
    # Remove linhas de texto que não façam parte do código (ex: explicações)
    linhas = codigo.split("\n")
    linhas = [l for l in linhas if not l.strip().startswith("Este teste")]
    return "\n".join(linhas).strip()


def gerar_teste(mutant):
    prompt = prompt_template.render(
        class_name=mutant.get("mutatedClass", "N/A"),
        method_name=mutant.get("mutatedMethod", "N/A"),
        line=mutant.get("lineNumber", "N/A"),
        mutator=mutant.get("mutator", "N/A"),
        description=mutant.get("description", "N/A"),
        source_code=mutant.get("sourceFile", "N/A"),
    )

    payload = {
        "model": MODEL,
        "prompt": prompt,
        "options": {"temperature": TEMPERATURE}
    }

    response = requests.post(API_URL, json=payload, stream=True)
    output = ""
    for line in response.iter_lines():
        if line:
            try:
                obj = line.decode("utf-8")
                if '"response":"' in obj:
                    text = obj.split('"response":"')[1].split('"')[0]
                    output += text
            except Exception:
                pass

    return output

def salvar_java(codigo, class_name, method_name):
    # Criar pasta tests se não existir
    os.makedirs("tests", exist_ok=True)
    # Nome do arquivo
    arquivo = f"tests/{class_name.split('.')[-1]}Test_{method_name}.java"

    # Indentação simples baseada em chaves
    linhas = codigo.split("\n")
    nivel = 0
    linhas_formatadas = []
    for l in linhas:
        l = l.strip()
        if l.endswith("}"):
            nivel -= 1
        linhas_formatadas.append("    " * max(nivel, 0) + l)
        if l.endswith("{"):
            nivel += 1

    with open(arquivo, "w") as f:
        f.write("\n".join(linhas_formatadas))

    print(f"[INFO] Teste Java salvo: {arquivo}")

# Gerar testes
for m in mutations:
    status = m.get("@status", None)
    if status in ["SURVIVED", "NO_COVERAGE"]:
        print(f"\n[INFO] Gerando teste para {m.get('mutatedClass')}::{m.get('mutatedMethod')} ({status})")
        teste = gerar_teste(m)
        teste = limpar_codigo_llm(teste)  # <-- aqui
        salvar_java(teste, m.get("mutatedClass"), m.get("mutatedMethod"))


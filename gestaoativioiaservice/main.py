import os
from typing import TypedDict, List
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from langchain_community.llms import Ollama
from langchain_core.messages import HumanMessage, AIMessage
from langgraph.graph import StateGraph, END
from sqlalchemy import create_engine, text

app = FastAPI()

# Configuração do Banco de Dados PostgreSQL
DB_URL = "postgresql://meu_usuario:minha_senha@localhost:5432/meu_banco"
engine = create_engine(DB_URL)

# Inicializando o Ollama (modelo local)
llm = Ollama(model="llama3", base_url="http://localhost:11434")

# Estado do LangGraph
class AgentState(TypedDict):
    messages: List
    context: str
    response: str

# 1. Nó de Guardrail (Filtro de segurança e escopo)
def guardrail_node(state: AgentState):
    last_message = state["messages"][-1].content.lower()
    
    # Palavras proibidas ou fora do escopo de TI
    termos_proibidos = ["hackear", "senha de outro", "exploit"]
    if any(termo in last_message for termo in termos_proibidos):
        state["response"] = "Desculpe, não posso ajudar com essa solicitação por motivos de segurança."
        return state
    
    state["response"] = "OK"
    return state

# 2. Nó de RAG (Busca dados no PostgreSQL)
def retrieval_node(state: AgentState):
    query = state["messages"][-1].content
    try:
        # Exemplo simples de RAG buscando computadores ou dados cadastrados
        with engine.connect() as conn:
            result = conn.execute(text("SELECT numero_serie, modelo, nome_fabricante FROM computador LIMIT 5;"))
            rows = result.fetchall()
            context = "Ativos de TI encontrados no banco:\n" + "\n".join([str(row) for row in rows])
    except Exception as e:
        context = "Não foi possível recuperar dados do banco no momento."
    
    state["context"] = context
    return state

# 3. Nó de Geração com a LLM
def llm_node(state: AgentState):
    if state["response"] != "OK":
        return state # Já foi bloqueado pelo guardrail
    
    prompt = f"""
    Você é um assistente de IA especialista em Gestão de Ativos de TI.
    Use o contexto abaixo recuperado do banco de dados para responder à pergunta do usuário.
    
    Contexto:
    {state['context']}
    
    Pergunta do usuário: {state['messages'][-1].content}
    """
    
    resposta = llm.invoke(prompt)
    state["response"] = resposta
    return state

# Construindo o LangGraph
workflow = StateGraph(AgentState)
workflow.add_node("guardrail", guardrail_node)
workflow.add_node("retrieval", retrieval_node)
workflow.add_node("llm", llm_node)

workflow.set_entry_point("guardrail")
workflow.add_conditional_edges(
    "guardrail",
    lambda s: "stop" if s["response"] != "OK" else "proceed",
    {"stop": END, "proceed": "retrieval"}
)
workflow.edge("retrieval", "llm")
workflow.edge("llm", END)

app_graph = workflow.compile()

# API Request Model
class ChatRequest(BaseModel):
    message: str

@app.post("/chat")
def chat_endpoint(req: ChatRequest):
    initial_state = {
        "messages": [HumanMessage(content=req.message)],
        "context": "",
        "response": ""
    }
    result = app_graph.invoke(initial_state)
    return {"reply": result["response"]}
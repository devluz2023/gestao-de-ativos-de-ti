import streamlit as st
import requests

st.title("🤖 Chat de Gestão de Ativos de TI (RAG + Ollama)")

# Histórico de mensagens na sessão
if "messages" not in st.session_state:
    st.session_state.messages = []

for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.markdown(message["content"])

if prompt := st.chat_input("Digite sua dúvida sobre os ativos de TI..."):
    st.session_state.messages.append({"role": "user", "content": prompt})
    with st.chat_message("user"):
        st.markdown(prompt)

    with st.chat_message("assistant"):
        with st.spinner("Consultando banco e gerando resposta..."):
            try:
                response = requests.post("http://localhost:8000/chat", json={"message": prompt})
                if response.status_code == 200:
                    reply = response.json().get("reply", "Sem resposta.")
                else:
                    reply = "Erro ao se comunicar com o backend."
            except Exception as e:
                reply = f"Erro de conexão: {e}"
            
            st.markdown(reply)
            st.session_state.messages.append({"role": "assistant", "content": reply})
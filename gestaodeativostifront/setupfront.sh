#!/bin/bash

# Garante que estamos na pasta src do projeto React/Vite
SRC_DIR="src"

echo "Criando estrutura de diretórios e componentes para o Frontend..."
mkdir -p "$SRC_DIR/components"
mkdir -p "$SRC_DIR/services"

# 1. Criando o serviço de API (Axios ou fetch)
cat << 'EOF' > "$SRC_DIR/services/api.js"
const API_BASE_URL = 'http://localhost:8080/api';

async function request(endpoint, options = {}) {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  });

  if (response.status === 204) return null;
  if (!response.ok) throw new Error('Erro na requisição da API');
  return response.json();
}

export const api = {
  get: (endpoint) => request(endpoint),
  post: (endpoint, data) => request(endpoint, { method: 'POST', body: JSON.stringify(data) }),
  delete: (endpoint) => request(endpoint, { method: 'DELETE' }),
};
EOF

# 2. Criando o componente de Computadores
cat << 'EOF' > "$SRC_DIR/components/Computadores.jsx"
import React, { useState, useEffect } from 'react';
import { api } from '../services/api';

export function Computadores() {
  const [computadores, setComputadores] = useState([]);
  const [form, setForm] = useState({ numeroSerie: '', nomeFabricante: '', modelo: '', descricao: '', anoAquisicao: '' });

  const carregar = async () => {
    try {
      const data = await api.get('/computadores');
      setComputadores(data);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => { carregar(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.post('/computadores', { ...form, anoAquisicao: Number(form.anoAquisicao) });
    setForm({ numeroSerie: '', nomeFabricante: '', modelo: '', descricao: '', anoAquisicao: '' });
    carregar();
  };

  const deletar = async (id) => {
    await api.delete(`/computadores/${id}`);
    carregar();
  };

  return (
    <div className="space-y-6">
      <h2 className="text-xl font-bold text-gray-800">Gerenciar Computadores</h2>
      <form onSubmit={handleSubmit} className="bg-white p-4 shadow rounded space-y-3">
        <div className="grid grid-cols-2 gap-4">
          <input placeholder="Número de Série" value={form.numeroSerie} onChange={e => setForm({...form, numeroSerie: e.target.value})} className="border p-2 rounded" required />
          <input placeholder="Fabricante" value={form.nomeFabricante} onChange={e => setForm({...form, nomeFabricante: e.target.value})} className="border p-2 rounded" required />
          <input placeholder="Modelo" value={form.modelo} onChange={e => setForm({...form, modelo: e.target.value})} className="border p-2 rounded" required />
          <input placeholder="Ano de Aquisição" type="number" value={form.anoAquisicao} onChange={e => setForm({...form, anoAquisicao: e.target.value})} className="border p-2 rounded" />
        </div>
        <input placeholder="Descrição" value={form.descricao} onChange={e => setForm({...form, descricao: e.target.value})} className="border p-2 rounded w-full" />
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Salvar Computador</button>
      </form>

      <div className="bg-white shadow rounded overflow-hidden">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-gray-100 border-b">
              <th className="p-3">Série</th>
              <th className="p-3">Fabricante</th>
              <th className="p-3">Modelo</th>
              <th className="p-3">Ano</th>
              <th className="p-3">Ações</th>
            </tr>
          </thead>
          <tbody>
            {computadores.map(c => (
              <tr key={c.numeroSerie} className="border-b">
                <td className="p-3">{c.numeroSerie}</td>
                <td className="p-3">{c.nomeFabricante}</td>
                <td className="p-3">{c.modelo}</td>
                <td className="p-3">{c.anoAquisicao}</td>
                <td className="p-3"><button onClick={() => deletar(c.numeroSerie)} className="text-red-600 hover:underline">Excluir</button></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
EOF

# 3. Criando o componente Principal App.jsx
cat << 'EOF' > "$SRC_DIR/App.jsx"
import React, { useState } from 'react';
import { Computadores } from './components/Computadores';

export default function App() {
  const [aba, setAba] = useState('computadores');

  return (
    <div className="min-h-screen bg-gray-50 text-gray-900">
      <header className="bg-blue-600 text-white p-4 shadow">
        <h1 className="text-2xl font-bold max-w-5xl mx-auto">Gestão de Ativos de TI</h1>
      </header>
      <main className="max-w-5xl mx-auto p-6 space-y-6">
        <div className="flex space-x-4 border-b pb-2">
          <button onClick={() => setAba('computadores')} className={`pb-2 font-medium ${aba === 'computadores' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500'}`}>Computadores</button>
        </div>
        {aba === 'computadores' && <Computadores />}
      </main>
    </div>
  );
}
EOF

echo "Frontend gerado com sucesso!"
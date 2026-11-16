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

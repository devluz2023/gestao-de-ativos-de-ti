import React, { useEffect, useState } from 'react';
import { api } from '../services/api';

const buildEmptyForm = (fields) =>
  fields.reduce((acc, field) => {
    acc[field.key] = '';
    return acc;
  }, {});

const formatValue = (value) => {
  if (value === null || value === undefined || value === '') return '—';
  return value;
};

export function CrudPanel({ config }) {
  const { title, endpoint, idKey, fields, columns } = config;
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(buildEmptyForm(fields));
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const carregar = async () => {
    setLoading(true);
    try {
      const data = await api.get(endpoint);
      setItems(Array.isArray(data) ? data : []);
      setError('');
    } catch (err) {
      setError('Não foi possível carregar os dados desta tela.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregar();
  }, [endpoint]);

  const handleInputChange = (key, value) => {
    setForm((previous) => ({
      ...previous,
      [key]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const payload = Object.entries(form).reduce((acc, [key, value]) => {
        if (value === '') {
          if (['descricao', 'classe', 'nomeFornecedor', 'modelo', 'nomeFabricante', 'numeroSerie', 'cnpjFornecedor', 'tipo', 'versao', 'nome'].includes(key)) {
            acc[key] = value;
          }
          return acc;
        }

        if (['anoAquisicao', 'codSo'].includes(key)) {
          acc[key] = Number(value);
          return acc;
        }

        acc[key] = value;
        return acc;
      }, {});

      await api.post(endpoint, payload);
      setForm(buildEmptyForm(fields));
      await carregar();
    } catch (err) {
      setError('Não foi possível salvar o registro.');
      console.error(err);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Deseja realmente excluir este registro?')) {
      return;
    }

    try {
      await api.delete(`${endpoint}/${id}`);
      await carregar();
    } catch (err) {
      setError('Não foi possível remover o registro.');
      console.error(err);
    }
  };

  return (
    <section className="panel">
      <div className="panel-header">
        <h2>{title}</h2>
        {error && <span className="error-banner">{error}</span>}
      </div>

      <form onSubmit={handleSubmit} className="form-card">
        <div className="form-grid">
          {fields.map((field) => {
            const fieldProps = {
              key: field.key,
              value: form[field.key],
              onChange: (event) => handleInputChange(field.key, event.target.value),
              placeholder: field.label,
              required: field.required,
            };

            if (field.type === 'textarea') {
              return (
                <label key={field.key} className="field field-full">
                  <span>{field.label}</span>
                  <textarea {...fieldProps} rows="3" />
                </label>
              );
            }

            return (
              <label key={field.key} className="field">
                <span>{field.label}</span>
                <input {...fieldProps} type={field.type || 'text'} />
              </label>
            );
          })}
        </div>

        <button type="submit" className="button-primary">Salvar</button>
      </form>

      <div className="table-card">
        {loading ? (
          <p className="empty-state">Carregando registros...</p>
        ) : (
          <table>
            <thead>
              <tr>
                {columns.map((column) => (
                  <th key={column.key}>{column.label}</th>
                ))}
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {items.length === 0 ? (
                <tr>
                  <td colSpan={columns.length + 1} className="empty-state">
                    Nenhum registro encontrado.
                  </td>
                </tr>
              ) : (
                items.map((item) => (
                  <tr key={String(item[idKey] ?? `${item.numeroSerie ?? item.codSo ?? item.id}`)}>
                    {columns.map((column) => (
                      <td key={`${item[idKey] ?? item.numeroSerie ?? item.codSo ?? item.id}-${column.key}`}>
                        {formatValue(item[column.key])}
                      </td>
                    ))}
                    <td>
                      <button type="button" className="button-danger" onClick={() => handleDelete(item[idKey])}>
                        Excluir
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>
    </section>
  );
}

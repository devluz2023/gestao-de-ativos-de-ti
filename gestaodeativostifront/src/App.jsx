import { useState } from 'react';
import { CrudPanel } from './components/CrudPanel';

const configTabs = [
  {
    key: 'computadores',
    title: 'Computadores',
    endpoint: '/computadores',
    idKey: 'numeroSerie',
    columns: [
      { key: 'numeroSerie', label: 'Número de série' },
      { key: 'nomeFabricante', label: 'Fabricante' },
      { key: 'modelo', label: 'Modelo' },
      { key: 'anoAquisicao', label: 'Ano' },
    ],
    fields: [
      { key: 'numeroSerie', label: 'Número de série', required: true },
      { key: 'nomeFabricante', label: 'Fabricante', required: true },
      { key: 'modelo', label: 'Modelo', required: true },
      { key: 'anoAquisicao', label: 'Ano de aquisição', type: 'number' },
      { key: 'descricao', label: 'Descrição', type: 'textarea' },
    ],
  },
  {
    key: 'sistemas-operacionais',
    title: 'Sistemas operacionais',
    endpoint: '/sistemas-operacionais',
    idKey: 'codSo',
    columns: [
      { key: 'codSo', label: 'Código' },
      { key: 'nome', label: 'Nome' },
      { key: 'versao', label: 'Versão' },
    ],
    fields: [
      { key: 'codSo', label: 'Código do SO', type: 'number', required: true },
      { key: 'nome', label: 'Nome', required: true },
      { key: 'versao', label: 'Versão', required: true },
    ],
  },
  {
    key: 'aquisicoes',
    title: 'Aquisições',
    endpoint: '/aquisicoes',
    idKey: 'numeroSerie',
    columns: [
      { key: 'numeroSerie', label: 'Série' },
      { key: 'nomeFornecedor', label: 'Fornecedor' },
      { key: 'cnpjFornecedor', label: 'CNPJ' },
    ],
    fields: [
      { key: 'numeroSerie', label: 'Número de série', required: true },
      { key: 'nomeFornecedor', label: 'Nome do fornecedor', required: true },
      { key: 'cnpjFornecedor', label: 'CNPJ do fornecedor', required: true },
    ],
  },
  {
    key: 'problemas',
    title: 'Problemas',
    endpoint: '/problemas',
    idKey: 'id',
    columns: [
      { key: 'id', label: 'ID' },
      { key: 'numeroSerie', label: 'Série' },
      { key: 'data', label: 'Data' },
      { key: 'hora', label: 'Hora' },
      { key: 'classe', label: 'Classe' },
    ],
    fields: [
      { key: 'numeroSerie', label: 'Número de série', required: true },
      { key: 'data', label: 'Data', type: 'date', required: true },
      { key: 'hora', label: 'Hora', type: 'time', required: true },
      { key: 'descricao', label: 'Descrição', type: 'textarea' },
      { key: 'classe', label: 'Classe' },
    ],
  },
  {
    key: 'utiliza',
    title: 'Utiliza',
    endpoint: '/utiliza',
    idKey: 'id',
    columns: [
      { key: 'id', label: 'ID' },
      { key: 'numeroSerie', label: 'Série' },
      { key: 'codSo', label: 'Código SO' },
    ],
    fields: [
      { key: 'numeroSerie', label: 'Número de série', required: true },
      { key: 'codSo', label: 'Código do SO', type: 'number', required: true },
    ],
  },
  {
    key: 'manutencoes-corretivas',
    title: 'Manutenções corretivas',
    endpoint: '/manutencoes-corretivas',
    idKey: 'id',
    columns: [
      { key: 'id', label: 'ID' },
      { key: 'numeroSerie', label: 'Série' },
      { key: 'data', label: 'Data' },
      { key: 'hora', label: 'Hora' },
      { key: 'tipo', label: 'Tipo' },
    ],
    fields: [
      { key: 'numeroSerie', label: 'Número de série', required: true },
      { key: 'data', label: 'Data', type: 'date', required: true },
      { key: 'hora', label: 'Hora', type: 'time', required: true },
      { key: 'descricao', label: 'Descrição', type: 'textarea' },
      { key: 'tipo', label: 'Tipo' },
    ],
  },
];

export default function App() {
  const [aba, setAba] = useState('computadores');
  const activeTab = configTabs.find((tab) => tab.key === aba) ?? configTabs[0];

  return (
    <div className="page-shell">
      <header className="topbar">
        <div className="topbar-inner">
          <h1>Gestão de Ativos de TI</h1>
        </div>
      </header>

      <main className="content">
        <nav className="tab-nav" aria-label="Seções da aplicação">
          {configTabs.map((tab) => (
            <button
              key={tab.key}
              type="button"
              className={tab.key === aba ? 'tab-button active' : 'tab-button'}
              onClick={() => setAba(tab.key)}
            >
              {tab.title}
            </button>
          ))}
        </nav>

        <CrudPanel config={activeTab} />
      </main>
    </div>
  );
}

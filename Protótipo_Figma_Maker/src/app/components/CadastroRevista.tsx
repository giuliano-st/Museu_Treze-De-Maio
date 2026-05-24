import { useState } from "react";
import { useNavigate } from "react-router";
import { Library, ArrowLeft } from "lucide-react";

export function CadastroRevista() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    titulo: "",
    numeroEdicao: "",
    mes: "",
    ano: "",
    issn: "",
    editora: "",
    localizacaoFisica: "",
    urlCapa: "",
    assuntos: "",
    periodicidade: "",
    descricaoFisica: ""
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Revista cadastrada:", formData);
    navigate("/cadastro");
  };

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <button
        onClick={() => navigate("/cadastro")}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline"
      >
        <ArrowLeft size={20} />
        Voltar
      </button>

      <div className="bg-white border-2 border-[#2E5B37] rounded-lg shadow-xl p-8">
        <div className="text-center mb-8">
          <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
            <Library size={32} />
          </div>
          <h1 className="text-[#2E5B37]">Cadastrar Revista</h1>
          <p className="text-gray-600 mt-2">Adicione uma nova revista ao acervo do museu</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="titulo" className="block mb-2 text-[#2E5B37]">
                Título *
              </label>
              <input
                type="text"
                id="titulo"
                required
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.titulo}
                onChange={(e) => setFormData({ ...formData, titulo: e.target.value })}
                placeholder="Nome da revista"
              />
            </div>

            <div>
              <label htmlFor="numeroEdicao" className="block mb-2 text-[#2E5B37]">
                Número da Edição
              </label>
              <input
                type="text"
                id="numeroEdicao"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.numeroEdicao}
                onChange={(e) => setFormData({ ...formData, numeroEdicao: e.target.value })}
                placeholder="Ex: Vol. 5, Nº 12"
              />
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="mes" className="block mb-2 text-[#2E5B37]">
                Mês de Publicação
              </label>
              <select
                id="mes"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.mes}
                onChange={(e) => setFormData({ ...formData, mes: e.target.value })}
              >
                <option value="">Selecione</option>
                <option value="Janeiro">Janeiro</option>
                <option value="Fevereiro">Fevereiro</option>
                <option value="Março">Março</option>
                <option value="Abril">Abril</option>
                <option value="Maio">Maio</option>
                <option value="Junho">Junho</option>
                <option value="Julho">Julho</option>
                <option value="Agosto">Agosto</option>
                <option value="Setembro">Setembro</option>
                <option value="Outubro">Outubro</option>
                <option value="Novembro">Novembro</option>
                <option value="Dezembro">Dezembro</option>
              </select>
            </div>

            <div>
              <label htmlFor="ano" className="block mb-2 text-[#2E5B37]">
                Ano de Publicação
              </label>
              <input
                type="text"
                id="ano"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.ano}
                onChange={(e) => setFormData({ ...formData, ano: e.target.value })}
                placeholder="2024"
              />
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="issn" className="block mb-2 text-[#2E5B37]">
                ISSN
              </label>
              <input
                type="text"
                id="issn"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.issn}
                onChange={(e) => setFormData({ ...formData, issn: e.target.value })}
                placeholder="XXXX-XXXX"
              />
            </div>

            <div>
              <label htmlFor="editora" className="block mb-2 text-[#2E5B37]">
                Editora
              </label>
              <input
                type="text"
                id="editora"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.editora}
                onChange={(e) => setFormData({ ...formData, editora: e.target.value })}
                placeholder="Nome da editora"
              />
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="localizacaoFisica" className="block mb-2 text-[#2E5B37]">
                Localização Física
              </label>
              <input
                type="text"
                id="localizacaoFisica"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.localizacaoFisica}
                onChange={(e) => setFormData({ ...formData, localizacaoFisica: e.target.value })}
                placeholder="Ex: Estante B, Prateleira 2"
              />
            </div>

            <div>
              <label htmlFor="periodicidade" className="block mb-2 text-[#2E5B37]">
                Periodicidade
              </label>
              <input
                type="text"
                id="periodicidade"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.periodicidade}
                onChange={(e) => setFormData({ ...formData, periodicidade: e.target.value })}
                placeholder="Ex: Mensal, Bimestral, Trimestral"
              />
            </div>
          </div>

          <div>
            <label htmlFor="assuntos" className="block mb-2 text-[#2E5B37]">
              Assuntos
            </label>
            <input
              type="text"
              id="assuntos"
              className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
              value={formData.assuntos}
              onChange={(e) => setFormData({ ...formData, assuntos: e.target.value })}
              placeholder="Separe os assuntos por vírgula"
            />
          </div>

          <div>
            <label htmlFor="urlCapa" className="block mb-2 text-[#2E5B37]">
              URL da Capa
            </label>
            <input
              type="url"
              id="urlCapa"
              className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
              value={formData.urlCapa}
              onChange={(e) => setFormData({ ...formData, urlCapa: e.target.value })}
              placeholder="https://exemplo.com/capa.jpg"
            />
          </div>

          <div>
            <label htmlFor="descricaoFisica" className="block mb-2 text-[#2E5B37]">
              Descrição Física
            </label>
            <textarea
              id="descricaoFisica"
              rows={4}
              className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
              value={formData.descricaoFisica}
              onChange={(e) => setFormData({ ...formData, descricaoFisica: e.target.value })}
              placeholder="Quantidade de páginas e outros detalhes físicos"
            />
          </div>

          <div className="flex gap-4">
            <button
              type="submit"
              className="flex-1 bg-[#2E5B37] text-white py-3 rounded hover:bg-[#3d7248] transition-colors"
            >
              Cadastrar Revista
            </button>
            <button
              type="button"
              onClick={() => navigate("/cadastro")}
              className="px-8 py-3 border-2 border-[#2E5B37] text-[#2E5B37] rounded hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

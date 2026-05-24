import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router";
import { Book, ArrowLeft } from "lucide-react";

export function CadastroLivro() {
  const navigate = useNavigate();
  const location = useLocation();

  /**
   * FUNCIONALIDADE DE EDIÇÃO
   * ========================
   * Quando o usuário clica em "Editar Obra" na página de DetalhesObra,
   * esta página recebe a obra completa via location.state.obraParaEditar
   *
   * Para implementar a edição, descomente o useEffect abaixo que carrega
   * automaticamente os dados da obra nos campos do formulário.
   */

  // Verifica se há uma obra sendo editada
  // const obraParaEditar = location.state?.obraParaEditar;

  const [formData, setFormData] = useState({
    // Identificação
    chamada: "",
    chamadaLocal: "",
    id: "",
    isbn: "",
    exemplar: "",
    // Autoria
    autorPrincipal: "",
    colaboradores: "",
    tituloPrincipal: "",
    tituloOriginal: "",
    // Publicação
    edicao: "",
    editora: "",
    localPublicacao: "",
    dataPublicacao: "",
    serie: "",
    colecao: "",
    // Detalhes
    paginas: "",
    assuntos: "",
    urlImagem: "",
    // Notas
    notasGerais: ""
  });

  /**
   * EXEMPLO DE IMPLEMENTAÇÃO DE EDIÇÃO
   * ===================================
   * Descomente o código abaixo para habilitar o preenchimento automático
   * dos campos quando uma obra está sendo editada.
   *
   * useEffect(() => {
   *   if (obraParaEditar) {
   *     setFormData({
   *       chamada: obraParaEditar.chamada || "",
   *       chamadaLocal: obraParaEditar.chamadaLocal || "",
   *       id: obraParaEditar.id?.toString() || "",
   *       isbn: obraParaEditar.isbn || "",
   *       exemplar: obraParaEditar.exemplar || "",
   *       autorPrincipal: obraParaEditar.autor || "",
   *       colaboradores: obraParaEditar.colaboradores || "",
   *       tituloPrincipal: obraParaEditar.titulo || "",
   *       tituloOriginal: obraParaEditar.tituloOriginal || "",
   *       edicao: obraParaEditar.edicao || "",
   *       editora: obraParaEditar.editora || "",
   *       localPublicacao: obraParaEditar.localPublicacao || "",
   *       dataPublicacao: obraParaEditar.ano || "",
   *       serie: obraParaEditar.serie || "",
   *       colecao: obraParaEditar.colecao || "",
   *       paginas: obraParaEditar.paginas || "",
   *       assuntos: obraParaEditar.assuntos || "",
   *       urlImagem: obraParaEditar.capa || "",
   *       notasGerais: obraParaEditar.notasInternas || ""
   *     });
   *   }
   * }, [obraParaEditar]);
   */

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Livro cadastrado:", formData);
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
            <Book size={32} />
          </div>
          <h1 className="text-[#2E5B37]">
            {location.state?.obraParaEditar ? "Editar" : "Cadastrar"} Livro - Ficha Técnica
          </h1>
          <p className="text-gray-600 mt-2">
            {location.state?.obraParaEditar
              ? "Edite as informações do livro no acervo do museu"
              : "Adicione um novo livro ao acervo do museu"}
          </p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-8">
          {/* Seção Identificação */}
          <div>
            <h3 className="text-[#2E5B37] mb-4 pb-2 border-b-2 border-[#2E5B37]">
              Identificação
            </h3>
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <label htmlFor="chamada" className="block mb-2 text-[#2E5B37]">
                  Chamada
                </label>
                <input
                  type="text"
                  id="chamada"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.chamada}
                  onChange={(e) => setFormData({ ...formData, chamada: e.target.value })}
                  placeholder="Código de chamada"
                />
              </div>

              <div>
                <label htmlFor="chamadaLocal" className="block mb-2 text-[#2E5B37]">
                  Chamada Local
                </label>
                <input
                  type="text"
                  id="chamadaLocal"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.chamadaLocal}
                  onChange={(e) => setFormData({ ...formData, chamadaLocal: e.target.value })}
                  placeholder="Código local"
                />
              </div>

              <div>
                <label htmlFor="id" className="block mb-2 text-[#2E5B37]">
                  ID
                </label>
                <input
                  type="text"
                  id="id"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.id}
                  onChange={(e) => setFormData({ ...formData, id: e.target.value })}
                  placeholder="Identificador único"
                />
              </div>

              <div>
                <label htmlFor="isbn" className="block mb-2 text-[#2E5B37]">
                  ISBN *
                </label>
                <input
                  type="text"
                  id="isbn"
                  required
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.isbn}
                  onChange={(e) => setFormData({ ...formData, isbn: e.target.value })}
                  placeholder="978-3-16-148410-0"
                />
              </div>

              <div>
                <label htmlFor="exemplar" className="block mb-2 text-[#2E5B37]">
                  Exemplar
                </label>
                <input
                  type="text"
                  id="exemplar"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.exemplar}
                  onChange={(e) => setFormData({ ...formData, exemplar: e.target.value })}
                  placeholder="Ex: 1º exemplar"
                />
              </div>
            </div>
          </div>

          {/* Seção Autoria */}
          <div>
            <h3 className="text-[#2E5B37] mb-4 pb-2 border-b-2 border-[#2E5B37]">
              Autoria
            </h3>
            <div className="space-y-6">
              <div className="grid md:grid-cols-2 gap-6">
                <div>
                  <label htmlFor="autorPrincipal" className="block mb-2 text-[#2E5B37]">
                    Autor Principal *
                  </label>
                  <input
                    type="text"
                    id="autorPrincipal"
                    required
                    className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                    value={formData.autorPrincipal}
                    onChange={(e) => setFormData({ ...formData, autorPrincipal: e.target.value })}
                    placeholder="Nome do autor principal"
                  />
                </div>

                <div>
                  <label htmlFor="colaboradores" className="block mb-2 text-[#2E5B37]">
                    Colaboradores
                  </label>
                  <input
                    type="text"
                    id="colaboradores"
                    className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                    value={formData.colaboradores}
                    onChange={(e) => setFormData({ ...formData, colaboradores: e.target.value })}
                    placeholder="Nomes dos colaboradores"
                  />
                </div>
              </div>

              <div className="grid md:grid-cols-2 gap-6">
                <div>
                  <label htmlFor="tituloPrincipal" className="block mb-2 text-[#2E5B37]">
                    Título Principal *
                  </label>
                  <input
                    type="text"
                    id="tituloPrincipal"
                    required
                    className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                    value={formData.tituloPrincipal}
                    onChange={(e) => setFormData({ ...formData, tituloPrincipal: e.target.value })}
                    placeholder="Título do livro"
                  />
                </div>

                <div>
                  <label htmlFor="tituloOriginal" className="block mb-2 text-[#2E5B37]">
                    Título Uniforme/Original
                  </label>
                  <input
                    type="text"
                    id="tituloOriginal"
                    className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                    value={formData.tituloOriginal}
                    onChange={(e) => setFormData({ ...formData, tituloOriginal: e.target.value })}
                    placeholder="Título original (se aplicável)"
                  />
                </div>
              </div>
            </div>
          </div>

          {/* Seção Publicação */}
          <div>
            <h3 className="text-[#2E5B37] mb-4 pb-2 border-b-2 border-[#2E5B37]">
              Publicação
            </h3>
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <label htmlFor="edicao" className="block mb-2 text-[#2E5B37]">
                  Edição
                </label>
                <input
                  type="text"
                  id="edicao"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.edicao}
                  onChange={(e) => setFormData({ ...formData, edicao: e.target.value })}
                  placeholder="Ex: 1ª edição, 2ª edição"
                />
              </div>

              <div>
                <label htmlFor="editora" className="block mb-2 text-[#2E5B37]">
                  Editora (Entidade)
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

              <div>
                <label htmlFor="localPublicacao" className="block mb-2 text-[#2E5B37]">
                  Local de Publicação
                </label>
                <input
                  type="text"
                  id="localPublicacao"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.localPublicacao}
                  onChange={(e) => setFormData({ ...formData, localPublicacao: e.target.value })}
                  placeholder="Cidade, Estado"
                />
              </div>

              <div>
                <label htmlFor="dataPublicacao" className="block mb-2 text-[#2E5B37]">
                  Data de Publicação
                </label>
                <input
                  type="text"
                  id="dataPublicacao"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.dataPublicacao}
                  onChange={(e) => setFormData({ ...formData, dataPublicacao: e.target.value })}
                  placeholder="Ano ou data completa"
                />
              </div>

              <div>
                <label htmlFor="serie" className="block mb-2 text-[#2E5B37]">
                  Série
                </label>
                <input
                  type="text"
                  id="serie"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.serie}
                  onChange={(e) => setFormData({ ...formData, serie: e.target.value })}
                  placeholder="Nome da série"
                />
              </div>

              <div>
                <label htmlFor="colecao" className="block mb-2 text-[#2E5B37]">
                  Coleção
                </label>
                <input
                  type="text"
                  id="colecao"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.colecao}
                  onChange={(e) => setFormData({ ...formData, colecao: e.target.value })}
                  placeholder="Nome da coleção"
                />
              </div>
            </div>
          </div>

          {/* Seção Detalhes */}
          <div>
            <h3 className="text-[#2E5B37] mb-4 pb-2 border-b-2 border-[#2E5B37]">
              Detalhes
            </h3>
            <div className="space-y-6">
              <div>
                <label htmlFor="paginas" className="block mb-2 text-[#2E5B37]">
                  Páginas (Descrição Física)
                </label>
                <input
                  type="text"
                  id="paginas"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.paginas}
                  onChange={(e) => setFormData({ ...formData, paginas: e.target.value })}
                  placeholder="Ex: 256 p., il., 23 cm"
                />
              </div>

              <div>
                <label htmlFor="assuntos" className="block mb-2 text-[#2E5B37]">
                  Assuntos (Multivalorado)
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
                <label htmlFor="urlImagem" className="block mb-2 text-[#2E5B37]">
                  Imagem (Endereço Eletrônico)
                </label>
                <input
                  type="url"
                  id="urlImagem"
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.urlImagem}
                  onChange={(e) => setFormData({ ...formData, urlImagem: e.target.value })}
                  placeholder="https://exemplo.com/capa.jpg"
                />
              </div>
            </div>
          </div>

          {/* Seção Notas */}
          <div>
            <h3 className="text-[#2E5B37] mb-4 pb-2 border-b-2 border-[#2E5B37]">
              Notas
            </h3>
            <div>
              <label htmlFor="notasGerais" className="block mb-2 text-[#2E5B37]">
                Notas Gerais
              </label>
              <textarea
                id="notasGerais"
                rows={4}
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.notasGerais}
                onChange={(e) => setFormData({ ...formData, notasGerais: e.target.value })}
                placeholder="Informações adicionais sobre o livro"
              />
            </div>
          </div>

          {/* Botões de Ação */}
          <div className="flex gap-4 pt-4">
            <button
              type="submit"
              className="flex-1 bg-[#2E5B37] text-white py-3 rounded hover:bg-[#3d7248] transition-colors"
            >
              {location.state?.obraParaEditar ? "Salvar Alterações" : "Cadastrar Livro"}
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

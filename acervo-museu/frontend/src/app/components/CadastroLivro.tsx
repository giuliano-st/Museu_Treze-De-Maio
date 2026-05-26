import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router";
import { Book, ArrowLeft } from "lucide-react";
import { salvarObra } from "../services/api";
import { useAuth } from "../contexts/AuthContext";

export function CadastroLivro() {
  const navigate = useNavigate();
  const location = useLocation();
  const { user } = useAuth();

  const obraParaEditar = location.state?.obraParaEditar;

  const [formData, setFormData] = useState({
    chamada: "", chamadaLocal: "", isbn: "", exemplar: "",
    autorPrincipal: "", colaboradores: "", tituloPrincipal: "",
    tituloOriginal: "", edicao: "", editora: "", localPublicacao: "",
    dataPublicacao: "", serie: "", colecao: "", paginas: "",
    assuntos: "", urlImagem: "", notasGerais: "",
    localizacaoFisica: "", categoria: "", doador: "",
  });

  // Preenche os campos quando é edição
  useEffect(() => {
    if (obraParaEditar) {
      setFormData({
        chamada: obraParaEditar.chamada || "",
        chamadaLocal: obraParaEditar.chamadaLocal || "",
        isbn: obraParaEditar.isbn || "",
        exemplar: obraParaEditar.exemplar || "",
        autorPrincipal: obraParaEditar.autor || "",
        colaboradores: obraParaEditar.colaboradores || "",
        tituloPrincipal: obraParaEditar.titulo || "",
        tituloOriginal: obraParaEditar.tituloOriginal || "",
        edicao: obraParaEditar.edicao || "",
        editora: obraParaEditar.editora || "",
        localPublicacao: obraParaEditar.localPublicacao || "",
        dataPublicacao: obraParaEditar.dataPublicacao || "",
        serie: obraParaEditar.serie || "",
        colecao: obraParaEditar.colecao || "",
        paginas: obraParaEditar.paginas || "",
        assuntos: obraParaEditar.assuntos || "",
        urlImagem: obraParaEditar.capa || "",
        notasGerais: obraParaEditar.notasInternas || "",
        localizacaoFisica: obraParaEditar.localizacaoFisica || "",
        categoria: obraParaEditar.categoria || "",
        doador: obraParaEditar.doador || "",
      });
    }
  }, [obraParaEditar]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await salvarObra({
      id: obraParaEditar?.id,
      tipo: "LIVRO",
      titulo: formData.tituloPrincipal,
      autor: formData.autorPrincipal,
      colaboradores: formData.colaboradores,
      tituloOriginal: formData.tituloOriginal,
      chamada: formData.chamada,
      chamadaLocal: formData.chamadaLocal,
      isbn: formData.isbn,
      exemplar: formData.exemplar,
      edicao: formData.edicao,
      editora: formData.editora,
      localPublicacao: formData.localPublicacao,
      dataPublicacao: formData.dataPublicacao,
      serie: formData.serie,
      colecao: formData.colecao,
      paginas: formData.paginas,
      assuntos: formData.assuntos,
      capa: formData.urlImagem,
      notasInternas: formData.notasGerais,
      localizacaoFisica: formData.localizacaoFisica,
      categoria: formData.categoria,
      doador: formData.doador,
    }, user!.email);
    navigate("/cadastro");
  };

  const input = (id: keyof typeof formData, label: string, placeholder: string, opts?: { required?: boolean; type?: string }) => (
    <div>
      <label htmlFor={id} className="block mb-1.5 text-[#2E5B37] font-medium text-sm">
        {label}{opts?.required && " *"}
      </label>
      <input
        type={opts?.type || "text"}
        id={id}
        required={opts?.required}
        className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors text-sm"
        value={formData[id]}
        onChange={(e) => setFormData({ ...formData, [id]: e.target.value })}
        placeholder={placeholder}
      />
    </div>
  );

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <button onClick={() => navigate("/cadastro")}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline text-sm">
        <ArrowLeft size={18} /> Voltar
      </button>

      <div className="bg-white border-2 border-[#2E5B37] rounded-xl shadow-xl p-8">
        <div className="text-center mb-8">
          <div className="bg-[#2E5B37] text-white w-14 h-14 rounded-full flex items-center justify-center mx-auto mb-3 shadow-md">
            <Book size={28} />
          </div>
          <h1 className="text-[#2E5B37] text-2xl font-bold">
            {obraParaEditar ? "Editar" : "Cadastrar"} Livro
          </h1>
          <p className="text-gray-500 text-sm mt-1">Ficha técnica do acervo</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-8">
          {/* Identificação */}
          <section>
            <h3 className="text-[#2E5B37] font-bold mb-4 pb-2 border-b-2 border-[#2E5B37]">Identificação</h3>
            <div className="grid md:grid-cols-2 gap-5">
              {input("chamada", "Chamada", "Código de chamada")}
              {input("chamadaLocal", "Chamada Local", "Código local")}
              {/* ISBN não é obrigatório */}
              {input("isbn", "ISBN", "978-3-16-148410-0")}
              {input("exemplar", "Exemplar", "Ex: 1º exemplar")}
            </div>
          </section>

          {/* Autoria */}
          <section>
            <h3 className="text-[#2E5B37] font-bold mb-4 pb-2 border-b-2 border-[#2E5B37]">Autoria</h3>
            <div className="grid md:grid-cols-2 gap-5">
              {input("autorPrincipal", "Autor Principal", "Nome do autor", { required: true })}
              {input("colaboradores", "Colaboradores", "Nomes dos colaboradores")}
              {input("tituloPrincipal", "Título Principal", "Título do livro", { required: true })}
              {input("tituloOriginal", "Título Original", "Título original (se tradução)")}
            </div>
          </section>

          {/* Publicação */}
          <section>
            <h3 className="text-[#2E5B37] font-bold mb-4 pb-2 border-b-2 border-[#2E5B37]">Publicação</h3>
            <div className="grid md:grid-cols-2 gap-5">
              {input("edicao", "Edição", "1ª edição")}
              {input("editora", "Editora", "Nome da editora")}
              {input("localPublicacao", "Local de Publicação", "Cidade, Estado")}
              {input("dataPublicacao", "Data de Publicação", "Ano ou data completa")}
              {input("serie", "Série", "Nome da série")}
              {input("colecao", "Coleção", "Nome da coleção")}
              {input("categoria", "Categoria", "Ex: História, Cultura, Artes")}
            </div>
          </section>

          {/* Detalhes */}
          <section>
            <h3 className="text-[#2E5B37] font-bold mb-4 pb-2 border-b-2 border-[#2E5B37]">Detalhes</h3>
            <div className="space-y-5">
              {input("paginas", "Páginas (Descrição Física)", "Ex: 256 p., il., 23 cm")}
              {input("assuntos", "Assuntos", "Separe por vírgula")}
              {input("urlImagem", "URL da Capa", "https://exemplo.com/capa.jpg", { type: "url" })}
            </div>
          </section>

          {/* Localização e Doador */}
          <section>
            <h3 className="text-[#2E5B37] font-bold mb-4 pb-2 border-b-2 border-[#2E5B37]">Informações Administrativas</h3>
            <div className="grid md:grid-cols-2 gap-5">
              {input("localizacaoFisica", "Localização Física", "Ex: Estante A, Prateleira 3")}
              {input("doador", "Doador", "Nome de quem doou (se houver)")}
            </div>
            <div className="mt-5">
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Notas Gerais</label>
              <textarea rows={4}
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none text-sm"
                value={formData.notasGerais}
                onChange={(e) => setFormData({ ...formData, notasGerais: e.target.value })}
                placeholder="Informações adicionais" />
            </div>
          </section>

          <div className="flex gap-4 pt-2">
            <button type="submit"
              className="flex-1 bg-[#2E5B37] text-white py-3 rounded-lg font-bold hover:bg-[#1f4026] transition-colors">
              {obraParaEditar ? "Salvar Alterações" : "Cadastrar Livro"}
            </button>
            <button type="button" onClick={() => navigate("/cadastro")}
              className="px-8 py-3 border-2 border-[#2E5B37] text-[#2E5B37] rounded-lg hover:bg-gray-50 transition-colors">
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

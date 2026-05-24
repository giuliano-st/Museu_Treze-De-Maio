import { useLocation, useNavigate } from "react-router";
import { useAuth } from "../contexts/AuthContext";
import {
  ArrowLeft,
  BookOpen,
  User,
  Calendar,
  Tag,
  MapPin,
  FileText,
  Hash,
  Edit,
  Trash2,
  Lock
} from "lucide-react";

interface Obra {
  id: number;
  titulo: string;
  autor: string;
  ano: string;
  categoria: string;
  tipo: string; // "Livro", "Jornal", "Revista"
  capa: string;
  descricao: string;
  // Campos sensíveis (apenas para admin)
  localizacaoFisica?: string;
  issn?: string;
  isbn?: string;
  notasInternas?: string;
}

export function DetalhesObra() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, isAdmin } = useAuth();

  // Recebe os dados da obra via location.state
  const obra = location.state?.obra as Obra;

  // Validação: se não houver obra, redireciona
  if (!obra) {
    navigate("/pesquisa");
    return null;
  }

  // Função para editar a obra
  // IMPORTANTE: O formulário de destino (ex: CadastroLivro, CadastroJornal, CadastroRevista)
  // deve verificar location.state.obraParaEditar para carregar os valores iniciais nos inputs
  const handleEditar = () => {
    navigate(`/cadastro/${obra.tipo.toLowerCase()}`, {
      state: { obraParaEditar: obra }
    });
  };

  // Função para excluir a obra
  const handleExcluir = () => {
    const confirmacao = window.confirm(
      `Tem certeza que deseja excluir a obra "${obra.titulo}"? Esta ação não pode ser desfeita.`
    );

    if (confirmacao) {
      // Aqui seria feita a chamada à API para excluir
      alert("Obra excluída com sucesso!");
      navigate("/pesquisa");
    }
  };

  // Define a cor do badge baseado no tipo
  const getBadgeColor = () => {
    switch (obra.tipo) {
      case "Livro":
        return "bg-blue-100 text-blue-700 border-blue-300";
      case "Jornal":
        return "bg-green-100 text-green-700 border-green-300";
      case "Revista":
        return "bg-purple-100 text-purple-700 border-purple-300";
      default:
        return "bg-gray-100 text-gray-700 border-gray-300";
    }
  };

  return (
    <div className="max-w-5xl mx-auto p-8">
      {/* Botão Voltar */}
      <button
        onClick={() => navigate(-1)}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline"
      >
        <ArrowLeft size={20} />
        Voltar para o Acervo
      </button>

      {/* Barra de Ferramentas do Administrador */}
      {isAdmin && (
        <div className="bg-[#2E5B37] text-white rounded-lg p-4 mb-6 flex flex-wrap gap-4 justify-between items-center">
          <div className="flex items-center gap-2">
            <Lock size={20} />
            <span className="font-semibold">Modo Administrador</span>
          </div>
          <div className="flex gap-3">
            <button
              onClick={handleEditar}
              className="flex items-center gap-2 bg-white text-[#2E5B37] px-4 py-2 rounded hover:bg-gray-100 transition-colors"
            >
              <Edit size={18} />
              Editar Obra
            </button>
            <button
              onClick={handleExcluir}
              className="flex items-center gap-2 bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 transition-colors"
            >
              <Trash2 size={18} />
              Excluir Obra
            </button>
          </div>
        </div>
      )}

      {/* Container Principal */}
      <div className="bg-white border-2 border-[#2E5B37] rounded-lg shadow-xl overflow-hidden">
        <div className="grid md:grid-cols-2 gap-8 p-8">
          {/* Coluna Esquerda - Capa da Obra */}
          <div>
            <div className="border-4 border-[#2E5B37] rounded-lg overflow-hidden shadow-lg">
              <img
                src={obra.capa}
                alt={obra.titulo}
                className="w-full h-auto object-cover"
              />
            </div>

            {/* Badge do Tipo de Obra */}
            <div className="mt-4">
              <span
                className={`inline-block px-4 py-2 rounded-full border-2 font-semibold ${getBadgeColor()}`}
              >
                {obra.tipo}
              </span>
            </div>
          </div>

          {/* Coluna Direita - Informações */}
          <div className="space-y-6">
            {/* Título */}
            <div>
              <h1 className="text-[#2E5B37] mb-2">{obra.titulo}</h1>
            </div>

            {/* CAMPOS PÚBLICOS - Sempre Visíveis */}
            <div className="space-y-4">
              <div className="flex items-start gap-3">
                <User className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                <div>
                  <p className="text-gray-600 text-sm">Autor</p>
                  <p className="font-semibold text-gray-800">{obra.autor}</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <Calendar className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                <div>
                  <p className="text-gray-600 text-sm">Ano de Publicação</p>
                  <p className="font-semibold text-gray-800">{obra.ano}</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <Tag className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                <div>
                  <p className="text-gray-600 text-sm">Categoria</p>
                  <p className="font-semibold text-gray-800">{obra.categoria}</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <FileText className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                <div>
                  <p className="text-gray-600 text-sm">Descrição</p>
                  <p className="text-gray-700 leading-relaxed">{obra.descricao}</p>
                </div>
              </div>
            </div>

            {/* CAMPOS SENSÍVEIS - Apenas para Administrador */}
            {isAdmin && (
              <div className="border-t-2 border-[#2E5B37] pt-6 mt-6">
                <div className="bg-amber-50 border-2 border-amber-200 rounded-lg p-4 mb-4">
                  <p className="text-amber-800 text-sm font-semibold flex items-center gap-2">
                    <Lock size={16} />
                    Informações Sensíveis (Apenas Administrador)
                  </p>
                </div>

                <div className="space-y-4">
                  <div className="flex items-start gap-3">
                    <Hash className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                    <div>
                      <p className="text-gray-600 text-sm">ID da Obra</p>
                      <p className="font-semibold text-gray-800">#{obra.id}</p>
                    </div>
                  </div>

                  {obra.localizacaoFisica && (
                    <div className="flex items-start gap-3">
                      <MapPin className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                      <div>
                        <p className="text-gray-600 text-sm">Localização Física</p>
                        <p className="font-semibold text-gray-800">{obra.localizacaoFisica}</p>
                      </div>
                    </div>
                  )}

                  {(obra.isbn || obra.issn) && (
                    <div className="flex items-start gap-3">
                      <BookOpen className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                      <div>
                        <p className="text-gray-600 text-sm">
                          {obra.isbn ? "ISBN" : "ISSN"}
                        </p>
                        <p className="font-semibold text-gray-800">
                          {obra.isbn || obra.issn}
                        </p>
                      </div>
                    </div>
                  )}

                  {obra.notasInternas && (
                    <div className="flex items-start gap-3">
                      <FileText className="text-[#2E5B37] mt-1 flex-shrink-0" size={20} />
                      <div>
                        <p className="text-gray-600 text-sm">Notas Internas</p>
                        <p className="text-gray-700 leading-relaxed bg-gray-50 p-3 rounded border border-gray-200">
                          {obra.notasInternas}
                        </p>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* Aviso para Visitantes */}
            {!user && (
              <div className="bg-blue-50 border-2 border-blue-200 rounded-lg p-4 mt-6">
                <p className="text-blue-800 text-sm">
                  <strong>Informações Limitadas:</strong> Faça login como administrador para
                  visualizar informações adicionais sobre esta obra.
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

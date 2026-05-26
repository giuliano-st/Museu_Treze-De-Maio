import { useNavigate } from "react-router";
import { User, Mail, Shield, ArrowLeft } from "lucide-react";
import { useAuth } from "../contexts/AuthContext";

export function Perfil() {
  const navigate = useNavigate();
  const { user } = useAuth();

  if (!user) {
    navigate("/login");
    return null;
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <button
        onClick={() => navigate("/")}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline"
      >
        <ArrowLeft size={20} />
        Voltar
      </button>

      <div className="bg-white border-2 border-[#2E5B37] rounded-lg shadow-xl p-8">
        <div className="text-center mb-8">
          <div className="bg-[#2E5B37] text-white w-24 h-24 rounded-full flex items-center justify-center mx-auto mb-4">
            <img
              src={user.avatar || "https://ui-avatars.com/api/?name=User&background=2E5B37&color=fff"}
              alt="Avatar"
              className="w-full h-full rounded-full"
            />
          </div>
          <h1 className="text-[#2E5B37]">Perfil do Usuário</h1>
          <p className="text-gray-600 mt-2">Informações da sua conta</p>
        </div>

        <div className="space-y-6">
          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <label className="block mb-2 text-[#2E5B37] flex items-center gap-2">
                <User size={20} />
                Nome
              </label>
              <input
                type="text"
                value={user.nome}
                readOnly
                className="w-full px-4 py-2 border-2 border-gray-300 rounded bg-gray-50 cursor-not-allowed"
              />
            </div>

            <div>
              <label className="block mb-2 text-[#2E5B37] flex items-center gap-2">
                <Mail size={20} />
                E-mail
              </label>
              <input
                type="email"
                value={user.email}
                readOnly
                className="w-full px-4 py-2 border-2 border-gray-300 rounded bg-gray-50 cursor-not-allowed"
              />
            </div>
          </div>

          <div>
            <label className="block mb-2 text-[#2E5B37] flex items-center gap-2">
              <Shield size={20} />
              Nível de Acesso
            </label>
            <div className="w-full px-4 py-2 border-2 border-[#2E5B37] rounded bg-green-50">
              <span className="font-semibold text-[#2E5B37]">{user.papel}</span>
            </div>
          </div>

          <div className="bg-gray-50 rounded-lg p-6 border border-gray-200">
            <h3 className="text-[#2E5B37] mb-4 font-semibold">Permissões</h3>
            <ul className="space-y-2 text-gray-700">
              <li className="flex items-center gap-2">
                <span className="w-2 h-2 bg-[#2E5B37] rounded-full"></span>
                Visualizar acervo completo
              </li>
              <li className="flex items-center gap-2">
                <span className="w-2 h-2 bg-[#2E5B37] rounded-full"></span>
                Cadastrar novas obras (Livros, Jornais, Revistas)
              </li>
              <li className="flex items-center gap-2">
                <span className="w-2 h-2 bg-[#2E5B37] rounded-full"></span>
                Editar informações do acervo
              </li>
              <li className="flex items-center gap-2">
                <span className="w-2 h-2 bg-[#2E5B37] rounded-full"></span>
                Gerenciar sistema do museu
              </li>
            </ul>
          </div>

          <div className="flex gap-4 pt-4">
            <button
              onClick={() => navigate("/")}
              className="flex-1 bg-[#2E5B37] text-white py-3 rounded hover:bg-[#3d7248] transition-colors"
            >
              Voltar ao Início
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

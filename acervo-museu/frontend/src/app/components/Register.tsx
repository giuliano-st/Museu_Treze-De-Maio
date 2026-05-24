import { useState } from "react";
import { useNavigate, Link } from "react-router";
import { UserPlus, AlertCircle } from "lucide-react";
import { cadastrarUsuario } from "../services/api";

export function Register() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nome: "",
    email: "",
    senha: "",
    confirmar: "",
    papel: "VISITANTE",
  });
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro("");

    if (formData.senha !== formData.confirmar) {
      setErro("As senhas não coincidem.");
      return;
    }

    setCarregando(true);
    const data = await cadastrarUsuario({
      nome: formData.nome,
      email: formData.email,
      senha: formData.senha,
      papel: formData.papel,
    });
    setCarregando(false);

    if (data.erro) {
      setErro(data.erro);
    } else {
      navigate("/login");
    }
  };

  const campo = (id: keyof typeof formData) => ({
    value: formData[id],
    onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
      setFormData({ ...formData, [id]: e.target.value }),
  });

  return (
    <div className="min-h-[calc(100vh-20rem)] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white border-2 border-[#2E5B37] rounded-xl shadow-xl p-8">
          <div className="text-center mb-8">
            <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-md">
              <UserPlus size={32} />
            </div>
            <h2 className="text-[#2E5B37] text-2xl font-bold">Criar Conta</h2>
            <p className="text-gray-500 mt-1 text-sm">Junte-se ao Museu Treze de Maio</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            {erro && (
              <div className="bg-red-50 border border-red-300 rounded-lg p-3 flex items-center gap-2 text-red-700 text-sm">
                <AlertCircle size={18} />
                {erro}
              </div>
            )}

            <div>
              <label className="block mb-1 text-[#2E5B37] font-medium text-sm">Nome Completo</label>
              <input type="text" required {...campo("nome")}
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                placeholder="Seu nome" />
            </div>

            <div>
              <label className="block mb-1 text-[#2E5B37] font-medium text-sm">E-mail</label>
              <input type="email" required {...campo("email")}
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                placeholder="seu@email.com" />
            </div>

            <div>
              <label className="block mb-1 text-[#2E5B37] font-medium text-sm">Tipo de Acesso</label>
              <select {...campo("papel")}
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none bg-white transition-colors">
                <option value="VISITANTE">Visitante</option>
                <option value="ADMINISTRADOR">Administrador</option>
              </select>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block mb-1 text-[#2E5B37] font-medium text-sm">Senha</label>
                <input type="password" required {...campo("senha")}
                  className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                  placeholder="••••••••" />
              </div>
              <div>
                <label className="block mb-1 text-[#2E5B37] font-medium text-sm">Confirmar</label>
                <input type="password" required {...campo("confirmar")}
                  className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                  placeholder="••••••••" />
              </div>
            </div>

            <button type="submit" disabled={carregando}
              className="w-full bg-[#2E5B37] text-white py-3 rounded-lg font-bold transition-colors hover:bg-[#1f4026] disabled:opacity-60 mt-2">
              {carregando ? "Criando conta..." : "Criar Minha Conta"}
            </button>

            <p className="text-center text-gray-500 text-sm">
              Já tem conta?{" "}
              <Link to="/login" className="text-[#2E5B37] font-semibold hover:underline">
                Faça Login
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}

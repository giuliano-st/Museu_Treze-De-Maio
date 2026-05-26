import { useState } from "react";
import { useNavigate, Link } from "react-router";
import { UserPlus } from "lucide-react";

export function Register() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: "user", // 'user' ou 'admin'
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      alert("As senhas não coincidem!");
      return;
    }
    console.log("Cadastro enviado:", formData);
    navigate("/login");
  };

  return (
    <div className="min-h-[calc(100vh-20rem)] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white border-2 border-[#2E5B37] rounded-lg shadow-xl p-8">
          <div className="text-center mb-8">
            <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <UserPlus size={32} />
            </div>
            <h2 className="text-[#2E5B37] text-2xl font-bold">
              Criar Conta
            </h2>
            <p className="text-gray-600 mt-2">
              Junte-se à comunidade do Museu Treze de Maio
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            {/* Nome Completo */}
            <div>
              <label
                htmlFor="name"
                className="block mb-1 text-[#2E5B37] font-medium"
              >
                Nome Completo
              </label>
              <input
                type="text"
                id="name"
                required
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.name}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    name: e.target.value,
                  })
                }
                placeholder="Seu nome"
              />
            </div>

            {/* E-mail */}
            <div>
              <label
                htmlFor="email"
                className="block mb-1 text-[#2E5B37] font-medium"
              >
                E-mail
              </label>
              <input
                type="email"
                id="email"
                required
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.email}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    email: e.target.value,
                  })
                }
                placeholder="seu@email.com"
              />
            </div>

            {/* Tipo de Usuário */}
            <div>
              <label
                htmlFor="role"
                className="block mb-1 text-[#2E5B37] font-medium"
              >
                Tipo de Usuário
              </label>
              <select
                id="role"
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors bg-white cursor-pointer"
                value={formData.role}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    role: e.target.value,
                  })
                }
              >
                <option value="user">
                  Usuário Comum (Visitante)
                </option>
                <option value="admin">
                  Administrador (Gestão)
                </option>
              </select>
            </div>

            {/* Senha */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label
                  htmlFor="password"
                  className="block mb-1 text-[#2E5B37] font-medium"
                >
                  Senha
                </label>
                <input
                  type="password"
                  id="password"
                  required
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.password}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      password: e.target.value,
                    })
                  }
                  placeholder="••••••••"
                />
              </div>
              <div>
                <label
                  htmlFor="confirmPassword"
                  className="block mb-1 text-[#2E5B37] font-medium"
                >
                  Confirmar Senha
                </label>
                <input
                  type="password"
                  id="confirmPassword"
                  required
                  className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                  value={formData.confirmPassword}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      confirmPassword: e.target.value,
                    })
                  }
                  placeholder="••••••••"
                />
              </div>
            </div>

            {/* Botão com Hover Branco e Texto Preto */}
            <button
              type="submit"
              className="w-full bg-[#2E5B37] text-white py-3 rounded border-2 border-transparent transition-colors hover:bg-white hover:text-black hover:border-[#2E5B37] font-bold mt-4"
            >
              Criar Minha Conta
            </button>

            <p className="text-center text-gray-600">
              Já tem uma conta?{" "}
              <Link
                to="/login"
                className="text-[#2E5B37] font-bold hover:underline"
              >
                Faça Login
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}
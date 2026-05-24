import { Outlet, Link, useLocation, useNavigate } from "react-router";
import { Menu, X, Landmark, Lock, User, LogOut, Settings } from "lucide-react";
import { useState, useRef, useEffect } from "react";
import { useAuth } from "../contexts/AuthContext";

export function Layout() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout, isAdmin } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const isActive = (path: string) => location.pathname === path;

  // Fechar dropdown ao clicar fora
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setDropdownOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleLogout = () => {
    logout();
    setDropdownOpen(false);
    navigate("/");
  };

  // Função para padronizar o estilo dos links com o hover branco/texto preto
  const getLinkClasses = (path: string, disabled = false) => {
    if (disabled) {
      return "px-4 py-3 rounded transition-colors bg-gray-400 text-gray-200 cursor-not-allowed pointer-events-none flex items-center gap-2";
    }
    return `px-4 py-3 rounded transition-colors ${
      isActive(path)
        ? "bg-white text-[#2E5B37] font-semibold"
        : "text-white hover:bg-white hover:text-black"
    }`;
  };

  return (
    <div className="min-h-screen bg-white">
      <nav className="bg-[#2E5B37] text-white shadow-md">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex justify-between items-center h-20">
            {/* Logo Substituída pelo ícone Landmark */}
            <Link
              to="/"
              className="flex items-center gap-3 group"
            >
              <div className="bg-white text-[#2E5B37] p-2 rounded transition-transform group-hover:scale-110">
                <Landmark size={28} />
              </div>
              <span className="font-medium hidden sm:block text-lg">
                Museu Treze de Maio
              </span>
            </Link>

            <button
              className="md:hidden p-2 hover:bg-white/10 rounded"
              onClick={() => setMenuOpen(!menuOpen)}
            >
              {menuOpen ? <X size={24} /> : <Menu size={24} />}
            </button>

            {/* Menu Desktop */}
            <div className="hidden md:flex gap-4 items-center">
              <Link to="/" className={getLinkClasses("/")}>
                Início
              </Link>
              <Link
                to="/pesquisa"
                className={getLinkClasses("/pesquisa")}
              >
                Acervo
              </Link>

              {isAdmin && (
                <Link
                  to="/cadastro"
                  className={getLinkClasses("/cadastro")}
                >
                  Cadastrar Obra
                </Link>
              )}

              {!user ? (
                <Link
                  to="/login"
                  className={getLinkClasses("/login")}
                >
                  Login
                </Link>
              ) : (
                <div className="relative" ref={dropdownRef}>
                  <button
                    onClick={() => setDropdownOpen(!dropdownOpen)}
                    className="flex items-center gap-2 px-3 py-2 rounded hover:bg-white/10 transition-colors"
                  >
                    <img
                      src={user.avatar || "https://ui-avatars.com/api/?name=User&background=2E5B37&color=fff"}
                      alt="Avatar"
                      className="w-8 h-8 rounded-full border-2 border-white"
                    />
                    <span className="hidden lg:block">{user.nome}</span>
                  </button>

                  {dropdownOpen && (
                    <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-xl border-2 border-[#2E5B37] overflow-hidden z-50">
                      <button
                        onClick={() => {
                          setDropdownOpen(false);
                          navigate("/perfil");
                        }}
                        className="w-full px-4 py-3 text-left hover:bg-gray-100 flex items-center gap-2 text-gray-700 transition-colors"
                      >
                        <Settings size={18} />
                        Editar Perfil
                      </button>
                      <button
                        onClick={handleLogout}
                        className="w-full px-4 py-3 text-left hover:bg-gray-100 flex items-center gap-2 text-red-600 transition-colors border-t"
                      >
                        <LogOut size={18} />
                        Sair
                      </button>
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>

          {/* Menu Mobile */}
          {menuOpen && (
            <div className="md:hidden pb-4 flex flex-col gap-2">
              <Link
                to="/"
                className={getLinkClasses("/")}
                onClick={() => setMenuOpen(false)}
              >
                Início
              </Link>
              <Link
                to="/pesquisa"
                className={getLinkClasses("/pesquisa")}
                onClick={() => setMenuOpen(false)}
              >
                Acervo
              </Link>

              {!isAdmin ? (
                <div className={getLinkClasses("/cadastro", true)}>
                  <Lock size={16} />
                  Cadastrar Obra
                </div>
              ) : (
                <Link
                  to="/cadastro"
                  className={getLinkClasses("/cadastro")}
                  onClick={() => setMenuOpen(false)}
                >
                  Cadastrar Obra
                </Link>
              )}

              {!user ? (
                <Link
                  to="/login"
                  className={getLinkClasses("/login")}
                  onClick={() => setMenuOpen(false)}
                >
                  Login
                </Link>
              ) : (
                <>
                  <div className="px-4 py-2 border-t border-white/20 mt-2">
                    <div className="flex items-center gap-2 mb-2">
                      <img
                        src={user.avatar || "https://ui-avatars.com/api/?name=User&background=2E5B37&color=fff"}
                        alt="Avatar"
                        className="w-8 h-8 rounded-full border-2 border-white"
                      />
                      <span className="text-sm">{user.nome}</span>
                    </div>
                  </div>
                  <button
                    onClick={() => {
                      setMenuOpen(false);
                      navigate("/perfil");
                    }}
                    className="px-4 py-2 text-left hover:bg-white/10 flex items-center gap-2 text-white transition-colors"
                  >
                    <Settings size={18} />
                    Editar Perfil
                  </button>
                  <button
                    onClick={() => {
                      setMenuOpen(false);
                      handleLogout();
                    }}
                    className="px-4 py-2 text-left hover:bg-white/10 flex items-center gap-2 text-white transition-colors"
                  >
                    <LogOut size={18} />
                    Sair
                  </button>
                </>
              )}
            </div>
          )}
        </div>
      </nav>

      <main>
        <Outlet />
      </main>

      <footer className="bg-[#2E5B37] text-white mt-20">
        <div className="text-center py-4">
          <p>Museu Treze de Maio - Sistema de Acervo</p>
        </div>
      </footer>
    </div>
  );
}
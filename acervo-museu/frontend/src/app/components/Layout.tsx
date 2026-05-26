import { Outlet, Link, useLocation, useNavigate } from "react-router";
import { Menu, X, Landmark, LogOut, Settings, LayoutDashboard } from "lucide-react";
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

  const linkClass = (path: string) =>
    `px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
      isActive(path)
        ? "bg-white text-[#2E5B37]"
        : "text-white hover:bg-white/20"
    }`;

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-[#2E5B37] shadow-lg">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex justify-between items-center h-16">
            {/* Logo */}
            <Link to="/" className="flex items-center gap-3 group">
              <div className="bg-white text-[#2E5B37] p-1.5 rounded-lg transition-transform group-hover:scale-105 shadow-sm">
                <Landmark size={24} />
              </div>
              <span className="font-semibold hidden sm:block text-white">Museu Treze de Maio</span>
            </Link>

            {/* Botão mobile */}
            <button className="md:hidden p-2 text-white hover:bg-white/10 rounded-lg"
              onClick={() => setMenuOpen(!menuOpen)}>
              {menuOpen ? <X size={22} /> : <Menu size={22} />}
            </button>

            {/* Menu Desktop */}
            <div className="hidden md:flex gap-2 items-center">
              <Link to="/" className={linkClass("/")}>Início</Link>
              <Link to="/pesquisa" className={linkClass("/pesquisa")}>Acervo</Link>

              {isAdmin && (
                <>
                  <Link to="/cadastro" className={linkClass("/cadastro")}>Cadastrar</Link>
                  <Link to="/admin" className={linkClass("/admin")}>
                    <span className="flex items-center gap-1.5"><LayoutDashboard size={14} /> Painel</span>
                  </Link>
                </>
              )}

              {!user ? (
                <Link to="/login" className="ml-2 bg-white text-[#2E5B37] px-4 py-2 rounded-lg text-sm font-semibold hover:bg-gray-100 transition-colors">
                  Entrar
                </Link>
              ) : (
                <div className="relative ml-2" ref={dropdownRef}>
                  <button onClick={() => setDropdownOpen(!dropdownOpen)}
                    className="flex items-center gap-2 bg-white/10 hover:bg-white/20 px-3 py-2 rounded-lg transition-colors text-white text-sm">
                    <div className="w-7 h-7 bg-white text-[#2E5B37] rounded-full flex items-center justify-center font-bold text-xs">
                      {user.nome.charAt(0).toUpperCase()}
                    </div>
                    <span className="hidden lg:block max-w-24 truncate">{user.nome}</span>
                  </button>

                  {dropdownOpen && (
                    <div className="absolute right-0 mt-2 w-48 bg-white rounded-xl shadow-xl border-2 border-[#2E5B37] overflow-hidden z-50">
                      <button onClick={() => { setDropdownOpen(false); navigate("/perfil"); }}
                        className="w-full px-4 py-3 text-left hover:bg-gray-50 flex items-center gap-2 text-gray-700 text-sm transition-colors">
                        <Settings size={16} /> Meu Perfil
                      </button>
                      <button onClick={handleLogout}
                        className="w-full px-4 py-3 text-left hover:bg-gray-50 flex items-center gap-2 text-red-600 text-sm border-t transition-colors">
                        <LogOut size={16} /> Sair
                      </button>
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>

          {/* Menu Mobile */}
          {menuOpen && (
            <div className="md:hidden pb-4 flex flex-col gap-1 border-t border-white/20 pt-3">
              <Link to="/" className={linkClass("/")} onClick={() => setMenuOpen(false)}>Início</Link>
              <Link to="/pesquisa" className={linkClass("/pesquisa")} onClick={() => setMenuOpen(false)}>Acervo</Link>
              {isAdmin && (
                <>
                  <Link to="/cadastro" className={linkClass("/cadastro")} onClick={() => setMenuOpen(false)}>Cadastrar</Link>
                  <Link to="/admin" className={linkClass("/admin")} onClick={() => setMenuOpen(false)}>Painel Admin</Link>
                </>
              )}
              {!user ? (
                <Link to="/login" className="mt-2 bg-white text-[#2E5B37] px-4 py-2 rounded-lg text-sm font-semibold text-center"
                  onClick={() => setMenuOpen(false)}>Entrar</Link>
              ) : (
                <>
                  <button onClick={() => { setMenuOpen(false); navigate("/perfil"); }}
                    className="text-left px-4 py-2 text-white text-sm hover:bg-white/10 rounded-lg flex items-center gap-2">
                    <Settings size={16} /> Meu Perfil
                  </button>
                  <button onClick={() => { setMenuOpen(false); handleLogout(); }}
                    className="text-left px-4 py-2 text-white text-sm hover:bg-white/10 rounded-lg flex items-center gap-2">
                    <LogOut size={16} /> Sair
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
        <div className="text-center py-4 text-sm">
          Museu Treze de Maio — Sistema de Acervo Digital
        </div>
      </footer>
    </div>
  );
}

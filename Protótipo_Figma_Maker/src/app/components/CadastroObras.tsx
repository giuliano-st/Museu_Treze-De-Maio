import { useNavigate } from "react-router";
import { Book, Newspaper, Library, ArrowLeft } from "lucide-react"; // Corrigido aqui: Library com L maiúsculo

export function CadastroObras() {
  const navigate = useNavigate();

  const tiposDeCadastro = [
    {
      id: "livro",
      titulo: "Livros",
      descricao: "Obras literárias, técnicas e didáticas.",
      icon: <Book size={40} />,
      rota: "/cadastro/livro"
    },
    {
      id: "jornal",
      titulo: "Jornais",
      descricao: "Edições diárias, semanais ou recortes de época.",
      icon: <Newspaper size={40} />,
      rota: "/cadastro/jornal"
    },
    {
      id: "revista",
      titulo: "Revistas",
      descricao: "Publicações periódicas e revistas ilustradas.",
      icon: <Library size={40} />, // Agora vai funcionar!
      rota: "/cadastro/revista"
    }
  ];

  return (
    <div className="max-w-6xl mx-auto px-4 py-12">
      <button 
        onClick={() => navigate("/")}
        className="flex items-center text-[#2E5B37] hover:underline mb-8 transition-all font-medium"
      >
        <ArrowLeft size={20} className="mr-2" />
        Voltar para o início
      </button>

      <div className="text-center mb-12">
        <h1 className="text-[#2E5B37] mb-4 text-3xl font-bold">Qual tipo de obra você quer cadastrar?</h1>
        <p className="text-gray-600">
          Selecione a categoria abaixo para prosseguir com o registro no acervo.
        </p>
      </div>

      <div className="grid md:grid-cols-3 gap-8">
        {tiposDeCadastro.map((tipo) => (
          <button
            key={tipo.id}
            onClick={() => navigate(tipo.rota)}
            className="group bg-white border-2 border-[#2E5B37] rounded-xl p-10 text-center hover:bg-[#2E5B37] transition-all duration-300 shadow-md hover:shadow-2xl"
          >
            <div className="bg-[#2E5B37] text-white w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:bg-white group-hover:text-[#2E5B37] transition-colors">
              {tipo.icon}
            </div>
            <h3 className="text-[#2E5B37] group-hover:text-white mb-3 text-xl font-semibold transition-colors">
              {tipo.titulo}
            </h3>
            <p className="text-gray-500 group-hover:text-gray-100 transition-colors">
              {tipo.descricao}
            </p>
          </button>
        ))}
      </div>
    </div>
  );
}
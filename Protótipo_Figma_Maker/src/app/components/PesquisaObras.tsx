import { useState } from "react";
import { Search, BookOpen, Calendar, User } from "lucide-react";
import { useNavigate } from "react-router";
interface Obra {
  id: number;
  titulo: string;
  autor: string;
  ano: string;
  categoria: string;
  tipo: string;
  capa: string;
  descricao: string;
  // Campos sensíveis (apenas para admin)
  localizacaoFisica?: string;
  issn?: string;
  isbn?: string;
  notasInternas?: string;
}
const obrasExemplo: Obra[] = [
  {
    id: 1,
    titulo: "História dos Clubes Sociais Negros",
    autor: "José Antônio dos Santos",
    ano: "1998",
    categoria: "História",
    tipo: "Livro",
    capa: "https://images.unsplash.com/photo-1589998059171-988d887df646?w=400&h=600&fit=crop",
    descricao:
      "Uma análise profunda sobre os clubes sociais negros e sua importância cultural.",
    localizacaoFisica:
      "Estante A, Prateleira 3, Corredor Principal",
    isbn: "978-85-123-4567-8",
    notasInternas:
      "Exemplar em bom estado. Primeira edição. Obra rara de referência importante para pesquisadores.",
  },
  {
    id: 2,
    titulo: "Ferroviários: Memória e Resistência",
    autor: "Maria da Silva",
    ano: "2005",
    categoria: "História",
    tipo: "Revista",
    capa: "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=400&h=600&fit=crop",
    descricao:
      "Relatos e histórias dos trabalhadores ferroviários afro-brasileiros.",
    localizacaoFisica:
      "Estante B, Prateleira 2, Corredor Lateral",
    issn: "1234-5678",
    notasInternas:
      "Edição comemorativa. Volume especial com entrevistas exclusivas.",
  },
  {
    id: 3,
    titulo: "Cultura Afro-Brasileira: Raízes e Manifestações",
    autor: "Carlos Eduardo Oliveira",
    ano: "2012",
    categoria: "Cultura",
    tipo: "Livro",
    capa: "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=400&h=600&fit=crop",
    descricao:
      "Explorando as diversas manifestações culturais afro-brasileiras.",
    localizacaoFisica:
      "Estante C, Prateleira 1, Sala de Cultura",
    isbn: "978-85-987-6543-2",
    notasInternas:
      "Segunda edição revisada e ampliada. Inclui fotografias históricas.",
  },
  {
    id: 4,
    titulo: "Poesias da Resistência",
    autor: "Ana Paula Ferreira",
    ano: "2018",
    categoria: "Literatura",
    tipo: "Jornal",
    capa: "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&h=600&fit=crop",
    descricao:
      "Coletânea de poemas sobre resistência e identidade negra.",
    localizacaoFisica:
      "Arquivo Especial, Gaveta 5, Sala de Preservação",
    issn: "2345-6789",
    notasInternas:
      "Material sensível à luz. Manter em ambiente controlado. Edição histórica.",
  },
  {
    id: 5,
    titulo: "Arte Negra Contemporânea",
    autor: "Roberto Almeida",
    ano: "2020",
    categoria: "Artes",
    tipo: "Revista",
    capa: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=600&fit=crop",
    descricao:
      "Panorama da arte contemporânea produzida por artistas negros brasileiros.",
    localizacaoFisica:
      "Estante D, Prateleira 4, Setor de Artes",
    issn: "3456-7890",
    notasInternas:
      "Edição limitada. Contém obras de artistas em destaque nacional.",
  },
  {
    id: 6,
    titulo: "Biografias: Líderes Afro-Brasileiros",
    autor: "Fernanda Costa",
    ano: "2015",
    categoria: "Biografias",
    tipo: "Livro",
    capa: "https://images.unsplash.com/photo-1506880018603-83d5b814b5a6?w=400&h=600&fit=crop",
    descricao:
      "Histórias de vida de importantes líderes afro-brasileiros.",
    localizacaoFisica:
      "Estante E, Prateleira 2, Corredor de Biografias",
    isbn: "978-85-456-7890-1",
    notasInternas:
      "Doação especial. Exemplar autografado pelo autor. Alta demanda para consulta.",
  },
];
export function PesquisaObras() {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedTypes, setSelectedTypes] = useState<string[]>(
    [],
  );
  const tipos = ["Livro", "Revista", "Jornal"];
  const handleTypeChange = (tipo: string) => {
    setSelectedTypes((prev) =>
      prev.includes(tipo)
        ? prev.filter((t) => t !== tipo)
        : [...prev, tipo],
    );
  };
  const filteredObras = obrasExemplo.filter((obra) => {
    const matchesSearch =
      obra.titulo
        .toLowerCase()
        .includes(searchTerm.toLowerCase()) ||
      obra.autor
        .toLowerCase()
        .includes(searchTerm.toLowerCase()) ||
      obra.ano.includes(searchTerm);
    const matchesCategory =
      !selectedCategory || obra.categoria === selectedCategory;
    const matchesType =
      selectedTypes.length === 0 ||
      selectedTypes.includes(obra.tipo);
    return matchesSearch && matchesCategory && matchesType;
  });
  return (
    <div className="max-w-7xl mx-auto px-4 py-12">
      {" "}
      {/* HEADER */}{" "}
      <div className="text-center mb-12">
        {" "}
        <h1 className="mb-4 text-4xl font-bold text-[#2E5B37]">
          {" "}
          Acervo Digital{" "}
        </h1>{" "}
        <p className="text-gray-600 max-w-2xl mx-auto">
          {" "}
          Explore nossa coleção de livros, revistas e jornais
          sobre história e cultura afro-brasileira{" "}
        </p>{" "}
      </div>{" "}
      {/* FILTROS */}{" "}
      <div className="bg-white border-2 border-[#2E5B37] rounded-xl p-6 mb-10 shadow-sm">
        {" "}
        <div className="grid md:grid-cols-2 gap-6">
          {" "}
          {/* PESQUISA */}{" "}
          <div>
            {" "}
            <label
              htmlFor="search"
              className="block mb-2 font-medium text-[#2E5B37]"
            >
              {" "}
              Pesquisar por título, autor ou ano{" "}
            </label>{" "}
            <div className="relative">
              {" "}
              <Search
                className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
                size={20}
              />{" "}
              <input
                type="text"
                id="search"
                className="w-full pl-10 pr-4 py-3 border-2 border-gray-300 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                placeholder="Buscar por título, autor ou categoria..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />{" "}
            </div>{" "}
          </div>{" "}
          {/* CATEGORIAS */}{" "}
          <div>
            {" "}
            <label
              htmlFor="category"
              className="block mb-2 font-medium text-[#2E5B37]"
            >
              {" "}
              Filtrar por categoria{" "}
            </label>{" "}
            <select
              id="category"
              className="w-full px-4 py-3 border-2 border-gray-300 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
              value={selectedCategory}
              onChange={(e) =>
                setSelectedCategory(e.target.value)
              }
            >
              {" "}
              <option value="">Todas as categorias</option>{" "}
              <option value="História">História</option>{" "}
              <option value="Cultura">Cultura</option>{" "}
              <option value="Literatura">Literatura</option>{" "}
              <option value="Artes">Artes</option>{" "}
              <option value="Biografias">
                Biografias
              </option>{" "}
            </select>{" "}
          </div>{" "}
        </div>{" "}
        {/* CHECKBOXES */}{" "}
        <div className="mt-6">
          {" "}
          <label className="block mb-3 font-medium text-[#2E5B37]">
            {" "}
            Filtrar por tipo{" "}
          </label>{" "}
          <div className="flex flex-wrap gap-3">
            {" "}
            {tipos.map((tipo) => (
              <label
                key={tipo}
                className=" flex items-center gap-2 bg-gray-100 hover:bg-gray-200 px-4 py-2 rounded-lg cursor-pointer transition "
              >
                {" "}
                <input
                  type="checkbox"
                  checked={selectedTypes.includes(tipo)}
                  onChange={() => handleTypeChange(tipo)}
                  className="w-4 h-4 accent-[#2E5B37] cursor-pointer"
                />{" "}
                <span className="text-gray-700">
                  {" "}
                  {tipo}{" "}
                </span>{" "}
              </label>
            ))}{" "}
          </div>{" "}
        </div>{" "}
      </div>{" "}
      {/* CONTADOR */}{" "}
      <div className="mb-6 text-gray-600 font-medium">
        {" "}
        Encontradas {filteredObras.length} obra(s){" "}
      </div>{" "}
      {/* GRID DOS CARDS */}{" "}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 items-stretch">
        {" "}
        {filteredObras.map((obra) => (
          <div
            key={obra.id}
            className=" bg-white border-2 border-gray-200 rounded-xl overflow-hidden hover:border-[#2E5B37] hover:shadow-xl hover:scale-105 transition-all duration-300 flex flex-col h-full min-h-[650px] "
          >
            {" "}
            {/* IMAGEM */}{" "}
            <div className="h-72 bg-gray-200 overflow-hidden shrink-0">
              {" "}
              <img
                src={obra.capa}
                alt={obra.titulo}
                className=" w-full h-full object-cover hover:scale-105 transition-transform duration-500 "
              />{" "}
            </div>{" "}
            {/* CONTEÚDO */}{" "}
            <div className="p-5 flex flex-col flex-1">
              {" "}
              {/* TÍTULO */}{" "}
              <h3 className="mb-3 text-2xl font-bold text-[#2E5B37] line-clamp-2">
                {" "}
                {obra.titulo}{" "}
              </h3>{" "}
              {/* META */}{" "}
              <div className="space-y-2 mb-4">
                {" "}
                <div className="flex items-center gap-2 text-gray-600">
                  {" "}
                  <User size={16} />{" "}
                  <span>{obra.autor}</span>{" "}
                </div>{" "}
                <div className="flex items-center gap-2 text-gray-600">
                  {" "}
                  <Calendar size={16} />{" "}
                  <span>{obra.ano}</span>{" "}
                </div>{" "}
                <div className="flex items-center gap-2 text-gray-600">
                  {" "}
                  <BookOpen size={16} />{" "}
                  <span>{obra.categoria}</span>{" "}
                </div>{" "}
                {/* BADGE */}{" "}
                <div className="pt-2">
                  {" "}
                  <span className=" inline-block bg-[#2E5B37] text-white text-sm px-3 py-1 rounded-full ">
                    {" "}
                    {obra.tipo}{" "}
                  </span>{" "}
                </div>{" "}
              </div>{" "}
              {/* DESCRIÇÃO */}{" "}
              <p className="text-gray-600 line-clamp-4 mb-6 flex-1">
                {" "}
                {obra.descricao}{" "}
              </p>{" "}
              {/* BOTÃO */}{" "}
              <button
                onClick={() =>
                  navigate(`/obra/${obra.id}`, {
                    state: { obra },
                  })
                }
                className="w-full bg-[#2E5B37] text-white py-3 rounded-lg hover:bg-[#1f4026] transition-colors font-medium"
              >
                Ver Detalhes
              </button>
            </div>{" "}
          </div>
        ))}{" "}
      </div>{" "}
      {/* ESTADO VAZIO */}{" "}
      {filteredObras.length === 0 && (
        <div className="text-center py-20">
          {" "}
          <BookOpen
            size={72}
            className="mx-auto mb-4 text-gray-300"
          />{" "}
          <h3 className="mb-2 text-2xl text-gray-600">
            {" "}
            Nenhuma obra encontrada{" "}
          </h3>{" "}
          <p className="text-gray-500">
            {" "}
            Tente ajustar os filtros de pesquisa{" "}
          </p>{" "}
        </div>
      )}{" "}
    </div>
  );
}
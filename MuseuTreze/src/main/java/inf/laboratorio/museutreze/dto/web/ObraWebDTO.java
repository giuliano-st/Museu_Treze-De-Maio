package inf.laboratorio.museutreze.dto.web;

import java.util.Date;
import java.util.List;

public class ObraWebDTO {
    public String obra_tipo;
    public String titulo_Principal;
    public String capa;
    public String local;
    public Date data;
    public String descFisica;
    public String nome;
    public String numeroChamada;
    public String chamadaLocal;
    public String tituloUniforme;
    public String isbn;
    public String serie;
    public String edicao;
    public String colecao;
    public String notasGerais;
    public String issn;
    public Integer volume;
    public String periodicidade;
    public Long autorId;
    public Long editoraId;
    public List<Long> assuntosIds;

    // Getters e Setters
    public String getObra_tipo() { return obra_tipo; }
    public void setObra_tipo(String obra_tipo) { this.obra_tipo = obra_tipo; }

    public String getTitulo_Principal() { return titulo_Principal; }
    public void setTitulo_Principal(String titulo_Principal) { this.titulo_Principal = titulo_Principal; }

    public String getCapa() { return capa; }
    public void setCapa(String capa) { this.capa = capa; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getDescFisica() { return descFisica; }
    public void setDescFisica(String descFisica) { this.descFisica = descFisica; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNumeroChamada() { return numeroChamada; }
    public void setNumeroChamada(String numeroChamada) { this.numeroChamada = numeroChamada; }

    public String getChamadaLocal() { return chamadaLocal; }
    public void setChamadaLocal(String chamadaLocal) { this.chamadaLocal = chamadaLocal; }

    public String getTituloUniforme() { return tituloUniforme; }
    public void setTituloUniforme(String tituloUniforme) { this.tituloUniforme = tituloUniforme; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getEdicao() { return edicao; }
    public void setEdicao(String edicao) { this.edicao = edicao; }

    public String getColecao() { return colecao; }
    public void setColecao(String colecao) { this.colecao = colecao; }

    public String getNotasGerais() { return notasGerais; }
    public void setNotasGerais(String notasGerais) { this.notasGerais = notasGerais; }

    public String getIssn() { return issn; }
    public void setIssn(String issn) { this.issn = issn; }

    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }

    public String getPeriodicidade() { return periodicidade; }
    public void setPeriodicidade(String periodicidade) { this.periodicidade = periodicidade; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public Long getEditoraId() { return editoraId; }
    public void setEditoraId(Long editoraId) { this.editoraId = editoraId; }

    public List<Long> getAssuntosIds() { return assuntosIds; }
    public void setAssuntosIds(List<Long> assuntosIds) { this.assuntosIds = assuntosIds; }
}
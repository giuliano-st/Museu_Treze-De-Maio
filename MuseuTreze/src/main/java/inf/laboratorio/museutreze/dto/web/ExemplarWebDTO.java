package inf.laboratorio.museutreze.dto.web;

public class ExemplarWebDTO {
    public Boolean disponibilidade;
    public Integer numero;
    public Long obraId;

    public Boolean getDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(Boolean disponibilidade) { this.disponibilidade = disponibilidade; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Long getObraId() { return obraId; }
    public void setObraId(Long obraId) { this.obraId = obraId; }
}
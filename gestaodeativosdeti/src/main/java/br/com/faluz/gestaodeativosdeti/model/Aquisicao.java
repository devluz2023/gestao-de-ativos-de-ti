package br.com.faluz.gestaodeativosdeti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aquisicao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aquisicao {

    @Id
    @Column(name = "numero_serie", nullable = false, length = 100)
    private String numeroSerie;

    @Column(name = "nome_fornecedor", nullable = false, length = 150)
    private String nomeFornecedor;

    @Column(name = "cnpj_fornecedor", nullable = false, length = 20)
    private String cnpjFornecedor;
}

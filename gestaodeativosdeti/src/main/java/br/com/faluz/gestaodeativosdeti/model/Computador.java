package br.com.faluz.gestaodeativosdeti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "computador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Computador {

    @Id
    @Column(name = "numero_serie", nullable = false, length = 100)
    private String numeroSerie;

    @Column(name = "nome_fabricante", nullable = false, length = 100)
    private String nomeFabricante;

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ano_aquisicao")
    private Integer anoAquisicao;
}

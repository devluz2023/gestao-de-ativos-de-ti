package br.com.faluz.gestaodeativosdeti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sistema_operacional")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SistemaOperacional {

    @Id
    @Column(name = "cod_so", nullable = false)
    private Long codSo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "versao", nullable = false, length = 50)
    private String versao;
}

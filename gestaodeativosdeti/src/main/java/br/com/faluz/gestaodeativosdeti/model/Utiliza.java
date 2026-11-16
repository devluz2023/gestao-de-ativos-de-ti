package br.com.faluz.gestaodeativosdeti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utiliza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utiliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_serie", nullable = false, length = 100)
    private String numeroSerie;

    @Column(name = "cod_so", nullable = false)
    private Long codSo;
}

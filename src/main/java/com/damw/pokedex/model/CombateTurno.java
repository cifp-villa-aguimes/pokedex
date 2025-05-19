package com.damw.pokedex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "combate_turnos")
public class CombateTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "combate_id", nullable = false)
    private Combate combate;

    @Column(name = "numero_turno", nullable = false)
    private int numeroTurno;

    @Column(name = "dano_infligido", nullable = false)
    private int danoInfligido;

    @Column(name = "salud_atacante_antes", nullable = false)
    private int saludAtacanteAntes;

    @Column(name = "salud_defensor_antes", nullable = false)
    private int saludDefensorAntes;

    // Constructors
    public CombateTurno() {
    }

    public CombateTurno(Combate combate, int numeroTurno, int danoInfligido,
            int saludAtacanteAntes, int saludDefensorAntes) {
        this.combate = combate;
        this.numeroTurno = numeroTurno;
        this.danoInfligido = danoInfligido;
        this.saludAtacanteAntes = saludAtacanteAntes;
        this.saludDefensorAntes = saludDefensorAntes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Combate getCombate() {
        return combate;
    }

    public void setCombate(Combate combate) {
        this.combate = combate;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public int getDanoInfligido() {
        return danoInfligido;
    }

    public void setDanoInfligido(int danoInfligido) {
        this.danoInfligido = danoInfligido;
    }

    public int getSaludAtacanteAntes() {
        return saludAtacanteAntes;
    }

    public void setSaludAtacanteAntes(int saludAtacanteAntes) {
        this.saludAtacanteAntes = saludAtacanteAntes;
    }

    public int getSaludDefensorAntes() {
        return saludDefensorAntes;
    }

    public void setSaludDefensorAntes(int saludDefensorAntes) {
        this.saludDefensorAntes = saludDefensorAntes;
    }
}

package com.example.creditoscomplementariosfinal.ui.utilities

enum class Dia(val numero: Int, val displayName: String) {
    LUNES(1, "Lunes"),
    MARTES(2, "Martes"),
    MIERCOLES(3, "Miércoles"),
    JUEVES(4, "Jueves"),
    VIERNES(5, "Viernes"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    companion object {
        fun from(numero: Int): String {
            return values().firstOrNull { it.numero == numero }?.displayName ?: "Desconocido"
        }
    }
}
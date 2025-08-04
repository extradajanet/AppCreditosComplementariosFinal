package com.example.creditoscomplementariosfinal.ui.utilities

enum class Estados(val numero: Int, val displayName: String) {
    ACTIVO(1, "Activo"),
    ENPROCESO(2, "En proceso"),
    FINALIZADO(3, "Finalizado");

    companion object {
        fun from(numero: Int): String {
            return values().firstOrNull { it.numero == numero }?.displayName ?: "Desconocido"
        }
    }
}
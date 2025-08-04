# 📱 Nombre de la Aplicación

Esta es una aplicación Android desarrollada en **Kotlin**, diseñada para interactuar con un backend específico.

---

## ⚠️ Requisitos Previos

> 🚫 **Esta aplicación no puede ejecutarse de forma independiente.**

Requiere que el backend esté en funcionamiento para poder cargar y enviar datos correctamente.

### 🔗 Repositorio del Backend

El backend necesario para esta aplicación se encuentra en el siguiente repositorio:

➡️ [https://github.com/extradajanet/Sistema-de-Creditos-Complementarios](https://github.com/extradajanet/Sistema-de-Creditos-Complementarios)

<img width="717" height="385" alt="image" src="https://github.com/user-attachments/assets/29038776-721d-4864-ac29-e21e1e895cc6" />

📌 **Importante:**  
En el archivo `launchSettings.json` del backend, cambia la propiedad `applicationUrl` para que coincida con la de la imagen. Esto permite que la app se conecte correctamente con la API.

---

## ✅ Pasos para Ejecutar la App

1. Clona este repositorio.
2. Abre el proyecto en Android Studio.
3. Configura las variables necesarias para acceder al backend.
4. Ejecuta el backend:
   - Abre la terminal en la carpeta `.API` del backend.
   - Ejecuta el comando:
     ```bash
     dotnet run
     ```
5. Ejecuta la aplicación en un emulador o dispositivo físico.

---

## 🧠 Programación Funcional

Se aplicaron principios de programación funcional en las siguientes pantallas:

- **Login**: uso de `StateFlow`, `collectAsState` y `LaunchedEffect` para manejar el estado de forma inmutable y reactiva. Se definieron funciones puras para actualizar el estado y controlar la navegación sin efectos secundarios.

- **Cursos**: uso de `produceState` para manejar llamadas asincrónicas, `Result` para el control de errores funcional, y composables puros para una UI declarativa, limpia y reutilizable.

Este enfoque mejora la **legibilidad**, **reutilización** y **control del flujo de datos** en la app.

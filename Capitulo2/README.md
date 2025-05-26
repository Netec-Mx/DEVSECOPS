# Práctica 1. Modelado de amenazas con Microsoft Threat Modeling Tool

En este laboratorio debes de implementar un **modelo de amenazas** de una aplicación orientada a Microservicios. Además, observa las posibles amenazas y cómo puedes mitigarlas. 

## Objetivos de la práctica:

Al finalizar la práctica, serás capaz de:
- Comprender la solicitud de la aplicación.
- Usar **Microsoft Modeling Tool** para detectar posible amenazas de la arquitectura. 
- Generar un informe de las posibles amenazas encontradas. 

## Duración aproximada:
- 45 minutos.

---
<!--Este fragmento es la barra de 
navegación-->

<div style="width: 400px;">
        <table width="50%">
            <tr>
                <td style="text-align: center;">
                    <a href=""><img src="../images/anterior.png" width="40px"></a>
                    <br>anterior
                </td>
                <td style="text-align: center;">
                   <a href="../README.md">Lista Laboratorios</a>
                </td>
<td style="text-align: center;">
                    <a href="../Capitulo3/"><img src="../images/siguiente.png" width="40px"></a>
                    <br>siguiente
                </td>
            </tr>
        </table>
</div>

---

## Objetivo visual: 

Se espera que, al término de la práctica, analices la siguiente estructura de aplicación. 

![diagrama](../images/1/diagrama.png)

## Instrucciones:

Antes de comenzar, lee con atención la siguiente información de la aplicación propuesta en el diagrama anterior. 

> **IMPORTANTE:** Este diagrama ilustra la interacción entre varios componentes dentro de una arquitectura de microservicios con énfasis en el flujo de autenticación. Los elementos clave incluyen un servidor de descubrimiento, un cliente de microservicio, una base de datos MySQL, Spring Cloud Gateway y un proveedor de identidad. El flujo comienza cuando un cliente solicita un token al proveedor de identidad, quien responde con dicho token, lo que permite al cliente interactuar con los microservicios a través del gateway.

Una vez comprendida la funcionalidad de la aplicación solicitada, el siguiente paso es iniciar el modelado para detectar posibles amenazas en el diagrama anterior. 

1. Abrir **Microsoft Threat Modeling Tool**.

![alt text](../images/1/1.png)

2. Seleccionar **Create A Model**.

![alt text](../images/1/2.png)

3. Posteriormente, se abrirá un workspace con todas las herramientas necesarias para crear un modelo. 

![alt text](../images/1/3.png)

4. Definir el diagrama con los siguientes elementos:

![alt text](../images/1/4.png)

5. Los elementos utilizados para el diagrama son los siguientes: 

- **Web Application**
- **Identity Server**
- **Browser**
- **Generic Trust Line Boundary**
- **Database**
- **Request**
- **Response**

6. Guardar el modelo con el nombre **`microservices-model`**.

7. Generar el reporte: **Reports -> Create Full Report**.

![alt text](../images/1/5.png)

8. Almacenar el reporte en el escritorio con el nombre **`reporte`**.

9. Analizar el reporte generado.

![alt text](../images/1/6.png)

10. Listar en qué casos del ciclo de vida del software se implementa cada vulnerabilidad. 

- **Requirements (Requisitos)**

    - Se identifican los requisitos de seguridad.
    - Se determina el nivel de riesgo aceptable.

- **Design (Diseño)**

    - Se realiza el modelado de amenazas.
    - Se identifican amenazas, mitigaciones y se documentan.

- **Implementation (Implementación)**

    - Se codifican las mitigaciones.
    - Se aplican prácticas seguras de desarrollo (como validación de entradas, cifrado, etc).

- **Verification (Verificación / Pruebas)**

    - Pruebas de seguridad: Análisis estático, dinámico, pruebas de penetración, etc.

- **Release (Liberación)**

    - Revisión final de seguridad.

    - Se valida que todos los riesgos fueron tratados adecuadamente.

- **Response (Respuesta / Mantenimiento)**

    - Planes de respuesta a incidentes y correcciones post-lanzamiento.

## Resultado esperado:

![alt text](../images/1/7.png)

# Modificaciones y Día

Este archivo contiene un registro de todas las modificaciones realizadas en el proyecto, junto con la fecha correspondiente. Utiliza este archivo para documentar cada cambio importante y mantener el contexto del desarrollo.

---

**27/04/2025**
-Anthony Sosa
- [17:35] Creación de la carpeta "paginawebcursosvender" dentro de NAYE para iniciar el proyecto de la página web de venta de cursos.
- [17:40] Creación de la carpeta "front" dentro de paginawebcursosvender para la parte del frontend.

**28/04/2025**
-Anthony Sosa
- [01:24] Creación de la carpeta "back" dentro de paginawebcursosvender para la parte del backend.
- [01:24] Creación de la carpeta "futurecourses-backend" dentro de back para el backend de cursos.
- [01:24] Estructuración de carpetas internas en futurecourses-backend:
    - build, gradle, src, resources, libs, reports, tmp, etc.
    - Organización de carpetas para clases compiladas, recursos, reportes y archivos temporales.
- [01:24] Creación de la estructura de código fuente en src/main/java/com/futurewise/futurecourses_backend/:
    - Subcarpetas: controller, dto, model, repository, service, util.
    - Archivo principal: FuturecoursesBackendApplication.java
    - Configuración de seguridad: SecurityConfig.java y SecurityConfig$JwtRequestFilter.class
- [01:24] Creación de controladores en controller:
    - AuthController.java (autenticación y login)
    - CourseController.java (gestión de cursos)
    - ModuleController.java (gestión de módulos)
    - UserCourseController.java (relación usuario-curso, compras, progreso)
    - UserProfileController.java (perfil de usuario)
- [01:24] Creación de DTOs en dto:
    - CompleteModuleRequest.java
    - CourseDTO.java
    - ModuleDTO.java
    - PurchaseCourseRequest.java
    - UserCourseWithModulesDTO.java
    - UserProfileDTO.java
- [01:24] Creación de modelos y repositorios en model y repository para manejar la lógica y persistencia de cursos, módulos, usuarios, etc.
- [01:24] Agregado de archivos de configuración y build:
    - build.gradle, settings.gradle, gradlew, gradlew.bat, HELP.md
    - application.properties en resources/main
- [01:24] Generación de archivos compilados y empaquetados:
    - futurecourses-backend-0.0.1-SNAPSHOT.jar y otros en build/libs
    - Reportes de problemas en build/reports
    - Datos temporales de compilación y pruebas en build/tmp


# Principios SOLID en Spring Boot

## Single Responsibility Principle 
Este primer principio menciona que cada clase debe tener un y solo un propósito o razón de existir, es decir, que solamente debe encargarse de realizar una cosa en concreto.

Este principio es muy fácil observarlo en un proyecto elaborado con Spring Boot, pues este framework nos impulsa a llevar a cabo esta práctica, como en la necesidad de tener un controlador o una entidad.

En cuanto al proyecto elaborado podemos ver este principio en los diversos paquetes que tenemos:
 - Entity
 - Controller
 - Repository
 - Service

Los cuales cada uno tiene una responsabilidad única y diferente a las demás.

## Open-Closed Principle Liskov Substitution Principle 
Este principio marca que nuestras clases deben estar abiertas a la extensión, pero cerradas a la modificación, es decir, que nuestro código debe ser capaz añadir nuevas funcionalidades sin tener que mover el código ya creado.

En el proyecto lo podemos ver muy claramente en el siguiente fragmento de código:

```
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
}
```

En este fragmento de código podemos ver como agregamos la funcionalidad de buscar por username sin tener que modificar alguna clase, sino que lo realizamo solamente siguiente una serie de reglas para declarar este método y que Spring Boot haga lo demás.

## Liskov Substitution Principle
Este principio dicta que las subclases deben ser sustituibles por clases padre. Es decir, que si un método espera una clase padre, debería aceptar a su vez cualquier clase hija sin tener ningún problema o comportamiento irregular.

Este principio lo podemos ver en el proyecto, específicamente cuando tratamos con excepciones personalizadas, pues al extender de la clase base Exception, pueden ser enviadas en su lugar sin ningún problema, aquí podemos ver en código un ejemplo de ello:
#### Lanza la excepción:
```
public List<Alumno> getAllAlumnos() throws ControlEscolarException {
        List<Alumno> alumnos = alumnoRepository.findAll();

        if (alumnos.isEmpty()) {
            throw new ControlEscolarException("No se encontraron datos");
        }

        return  alumnos;
    }
```
#### Recibe la excepción
```
@GetMapping
    public ResponseEntity<?> getAllAlumnos() {
        try {
            return ResponseEntity.ok().body(alumnoService.getAllAlumnos());
        } catch (ControlEscolarException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
```
## Interface Segregation Principle
El principio de segregación de interfaces propone que es mejor tener varias interfaces con propósitos y funcionalidades específicas que interfaces grandes pero poco flexibles o reutilizables.

En nuestro proyecto podemos ver reflejado este principio en nuetras clases service del proyecto email-service, donde se implementa una interfaz con los métodos "sendEmail" y "sendEmailWithFile" la cual se implementa en el EmailService.

## Dependency Inversion Principle
Este último principio establece que nuestras nuestras clases deben depender de interfaces o clases abstractas en lugar de clases o funciones específicas. Es decir, que nuestras abstracciones no deben depender de los detalles, sino que los detalles deben ser dependientes de las abtracciones.

Este principio lo vimos desde un inicio cuando utilizamos la anotación "@Autowired", la cual nos ayuda a inyectar dependencias en tiempo de ejecución y permite desacoplar las clases, lo cual facilita la sustitución de implementaciones.

Un pequeño ejemplo de ello es el siguiente:
```
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;
	
	...
}
```


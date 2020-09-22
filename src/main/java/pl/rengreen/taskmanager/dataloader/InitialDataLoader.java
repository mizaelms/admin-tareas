package pl.rengreen.taskmanager.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.rengreen.taskmanager.model.Role;
import pl.rengreen.taskmanager.model.User;
import pl.rengreen.taskmanager.model.Task;
import pl.rengreen.taskmanager.service.RoleService;
import pl.rengreen.taskmanager.service.TaskService;
import pl.rengreen.taskmanager.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.admin.image}")
    private String defaultAdminImage;

    @Autowired
    public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //ROLES --------------------------------------------------------------------------------------------------------
        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        //USERS --------------------------------------------------------------------------------------------------------
        //1
        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword,
                defaultAdminImage);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        //2
        User manager = new User(
                "manager@mail.com",
                "Manager",
                "112233",
                "images/admin.png");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);

        //3
        userService.createUser(new User(
                "mizaelm@mail.com",
                "Mizael Morales",
                "mizaelm",
                "images/miza.png"));

        //4
        userService.createUser(new User(
                "diana@mail.com",
                "Diana Karen Herrea",
                "diana",
                "images/karen.jpg"));

        //5
        userService.createUser(new User(
                "ralf@mail.com",
                "Rafael Garcia",
                "ralf",
                "images/ralf.jpg"));

        //6
        userService.createUser(new User(
                "kate@mail.com",
                "Katy Mendoza",
                "kate",
                "images/kate.jpg"));

        //7
        userService.createUser(new User(
                "tom@mail.com",
                "Tomas Tapia",
                "tom",
                "images/tom.jpg"));

        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);


        //TASKS --------------------------------------------------------------------------------------------------------
        //tasks from Web Design Checklist
        //https://www.beewits.com/the-ultimate-web-design-checklist-things-to-do-when-launching-a-website/

        LocalDate today = LocalDate.now();

        //1
        taskService.createTask(new Task(
                "Recoger el documento informativo ",
                "Configurar la primera reunión con el cliente. Recopilar datos básicos sobre el cliente. Definir y recopilar el documento informativo del cliente.",
                today.minusDays(40),
                true,
                userService.getUserByEmail("mizaelm@mail.com").getName(),
                userService.getUserByEmail("mizaelm@mail.com")
        ));

        //2
        taskService.createTask(new Task(
                "Definir cuestionario del proyecto ",
                "Definir y enviar el cuestionario del proyecto al cliente y esperar la respuesta del cliente. Repita las dudas que tenga hasta que todos estén de acuerdo. Finalice el cuestionario del proyecto del cliente.",
                today.minusDays(30),
                true,
                userService.getUserByEmail("diana@mail.com").getName(),
                userService.getUserByEmail("diana@mail.com")
        ));

        //3
        taskService.createTask(new Task(
                "Investigar la empresa y la industria del cliente",
                "Investigue la empresa del cliente para comprender su marca, la forma en que se comunican, su demografía, público objetivo. Investigue la industria del cliente para encontrar formas de comunicarse específicamente con la industria, fortalezas y debilidades, tendencias y otros aspectos específicos de la industria.",
                today.minusDays(20),
                true,
                userService.getUserByEmail("ralf@mail.com").getName(),
                userService.getUserByEmail("ralf@mail.com")
        ));

        //4
        taskService.createTask(new Task(
                "Obtenga cotización para proyecto",
                "Obtenga cotización para el esfuerzo de desarrollo del proyecto. Estimar el trabajo de diseño con los diseñadores. Obtenga una cotización para la copia / contenido o haga un presupuesto de trabajo con sus redactores. Obtenga un presupuesto para la producción de fotografías y videos o calcule el esfuerzo involucrado.",
                today.minusDays(10),
                true,
                userService.getUserByEmail("kate@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        //5
        taskService.createTask(new Task(
                "Obtenga cotización para hosting y dominio",
                "Obtenga una cotización para el alojamiento y el dominio, especialmente si se trata de un alojamiento especializado, como alojamiento VPS, alojamiento en la nube o requisitos especiales de alojamiento o entorno.",
                today.minusDays(5),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        //6
        taskService.createTask(new Task(
                "Verifique la calidad de cada elemento de contenido",
                "Asegure la calidad de cada contenido que haya subcontratado o comprado, y solicite cambios cuando sea necesario. Complete el contenido del sitio web con los diversos elementos de contenido que haya acordado con el cliente.",
                today.minusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("mizaelm@mail.com")
        ));

        //7
        taskService.createTask(new Task(
                "Definir una página de contacto y los detalles de las redes sociales",
                "Defina una página Contáctenos con los detalles correctos del cliente y un mapa. Complete los enlaces y la icongrafía con enlaces a detalles relevantes de las redes sociales. Cree un enlace a su sitio web en el pie de página (asegúrese de que se haya acordado con el cliente para hacerlo).",
                today.minusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("mizaelm@mail.com")
        ));

        //8
        taskService.createTask(new Task(
                "Consultar toda la redacción publicitaria web",
                "Asegúrese de que la redacción de textos publicitarios web se haya revisado y se haya realizado un corrector ortográfico y gramatical para verificar que sea correcta. Utilice herramientas en línea como Reverso o Spellcheckplus.com. Compruebe que el contenido genérico, como lorem ipsum, se haya eliminado y reemplazado correctamente.",
                today,
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("mizaelm@mail.com")
        ));

        //9
        taskService.createTask(new Task(
                "Ver todas las imágenes y videos",
                "Verifique que todas las imágenes estén en los lugares correctos, difuminadas, formateadas, especificadas en ancho y alto y que funcionen en todos los dispositivos. Confirme que los archivos de video y audio estén en los lugares correctos, formateados y funcionando en todos los dispositivos.",
                today.plusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("diana@mail.com")
        ));

        //10
        taskService.createTask(new Task(
                "Ver todo el contenido vinculado",
                "Pruebe todo el contenido vinculado, como estudios de casos, libros electrónicos y documentos técnicos, y verifique que estén vinculados correctamente. Pruebe para ver que todos los enlaces internos de las páginas web funcionan correctamente. Asegúrese de que el logotipo de la empresa esté vinculado a la página de inicio.",
                today.plusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        //11
        taskService.createTask(new Task(
                "Verifique Contáctenos y otros formularios",
                "Asegúrese de que Contáctenos y otros formularios estén enviando datos correctamente. Si el formulario se envía a una dirección de correo electrónico, asegúrese de que el correo electrónico se reciba en un buzón de correo supervisado o asegúrese de que el contenido se almacene correctamente en su base de datos. Verifique el mensaje de agradecimiento o la página que se muestra después de enviar el formulario. Compruebe que las respuestas automáticas funcionen correctamente y que se haya corregido el texto de los correos electrónicos.",
                today.plusDays(3),
                false,
                userService.getUserByEmail("kate@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        //12
        taskService.createTask(new Task(
                "Verifique todos los enlaces externos",
                "Los enlaces externos en las páginas web funcionan correctamente y se abren en una nueva pestaña (corrija los enlaces rotos con esta herramienta). Asegúrese de que los íconos para compartir en las redes sociales funcionen correctamente, que haya una buena imagen para compartir y que la descripción para compartir sea adecuada. Corrija sus metadatos según sea necesario para asegurarse de que el intercambio en las redes sociales funcione correctamente.",
                today.plusDays(4),
                false,
                userService.getUserByEmail("diana@mail.com").getName(),
                userService.getUserByEmail("diana@mail.com")
        ));

        //13
        taskService.createTask(new Task(
                "Verifique la página 404 y redirecciones",
                "Pruebe con una dirección que no exista en su página para verificar que la página 404 y las páginas de redireccionamiento 404 estén en su lugar. Elija www vs no-www y asegúrese de que SÓLO uno de ellos funcione para asegurarse de que no se le penalice por contenido duplicado. Después de elegir uno, asegúrese de que uno redirija al otro.",
                today.plusDays(5),
                false,
                userService.getUserByEmail("ralf@mail.com").getName(),
                userService.getUserByEmail("ralf@mail.com")
        ));

        //14
        taskService.createTask(new Task(
                "Compruebe si el sitio web es compatible con dispositivos móviles",
                "Asegúrese de que se utilice la meta etiqueta de la ventana gráfica. Compruebe que el sitio web sea compatible con dispositivos móviles con al menos una puntuación MobileOk de 75. Compruebe si Google considera que su página es compatible con dispositivos móviles. Asegúrese de que se utilicen los tipos de entrada correctos para los campos de formulario de entrada de correo electrónico, teléfono y URL para garantizar que se muestren correctamente en los teléfonos móviles.",
                today.plusDays(6),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("manager@mail.com")
        ));

        //15
        taskService.createTask(new Task(
                "Pruebe el sitio web en emuladores y dispositivos reales",
                "Compruebe cómo se ve el sitio en emuladores como ipadpeek, screenfly, mobilephonesimulator. Pruebe el sitio utilizando dispositivos reales a los que tenga acceso o use opendevicelab.com.",
                today.plusDays(8),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        //16
        taskService.createTask(new Task(
                "Verifique los títulos de las páginas, las meta descripciones y las palabras clave",
                "Compruebe que todas las páginas tengan títulos de página únicos (con una longitud recomendada de menos de 70 caracteres, incluidas las palabras clave). Compruebe que todas las páginas tengan metadescripciones únicas (con una longitud recomendada de menos de 156 caracteres, incluidas las palabras clave). Verifique que las páginas tengan las palabras clave elegidas incluidas sin ningún relleno de palabras clave (no enfatice demasiado las palabras clave en particular).",
                today.plusDays(10),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        //17
        taskService.createTask(new Task(
                "Compruebe las URL de la página",
                "Compruebe que todas las URL de las páginas reflejen de forma coherente la arquitectura de la información del sitio. Si ha tenido otro sitio web antiguo, asegúrese de tener redireccionamientos 301 en su lugar para todas las URL antiguas (redireccionando las páginas antiguas a las nuevas).",
                today.plusDays(12),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        //18
        taskService.createTask(new Task(
                "Minificar y optimizar archivos",
                "Minimice los archivos javascript y CSS. Optimice el tamaño de las imágenes y reemplace las imágenes existentes con las imágenes optimizadas. Especifique las dimensiones de la imagen para cada imagen. Habilite la compresión gzip en su servidor de alojamiento.",
                today.plusDays(14),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        //19
        taskService.createTask(new Task(
                "Registrar propiedad de redes socialess",
                "Cree imágenes de portada para redes sociales como Facebook, Twitter, la página de la empresa LinkedIn, Pinterest, Instagram u otros, según sea necesario. Registre todas las propiedades de las redes sociales y configúrelas con imágenes de perfil, portadas y enlaces al sitio web.",
                today.plusDays(16),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        //20
        taskService.createTask(new Task(
                "Enviar el sitio terminado al cliente",
                "Envíe el sitio terminado al cliente y obtenga comentarios. Corrija y cambie cualquier solicitud del cliente Dar acceso al cliente a todas las cuentas creadas en su nombre. Envíe actualizaciones al cliente y espere la aprobación del cliente.",
                today.plusDays(18),
                false,
                userService.getUserByEmail("manager@mail.com").getName()
        ));

        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}

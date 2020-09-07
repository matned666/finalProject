package eu.mnrdesign.matned.final_project;

import eu.mnrdesign.matned.final_project.dto.RegistrationDTO;
import eu.mnrdesign.matned.final_project.model.*;
import eu.mnrdesign.matned.final_project.repository.CategoryRepository;
import eu.mnrdesign.matned.final_project.repository.TaskRepository;
import eu.mnrdesign.matned.final_project.repository.UserRoleRepository;
import eu.mnrdesign.matned.final_project.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeed implements InitializingBean {

    private final UserRoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public DataSeed(UserRoleRepository roleRepository,
                    CategoryRepository categoryRepository,
                    TaskRepository taskRepository,
                    UserService userService) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        for (UserRole.Role role: UserRole.Role.values()) {
            createRole(role);
        }
        for (Category.DefaultCategory defCat: Category.DefaultCategory.values()) {
            createCat(defCat);
        }
        createDefaultTasks();
        createDefaultUser();
    }

    private void createDefaultTasks() {
        createTask1();
        createTask2();
        createTask3();
        createTask4();
        createTask5();
        createTask6();
        createTask7();
    }

    private void createRole(UserRole.Role role) {
        String roleCheck = "ROLE_" + role.name();
        if (!roleRepository.roleExists(roleCheck)){
            roleRepository.save(UserRole.apply(role));
        }
    }

    private void createCat(Category.DefaultCategory defCat) {
        if (!categoryRepository.existsByCategoryName(defCat.name())){
            categoryRepository.save(new Category(defCat.name()));
        }
    }

    private void createDefaultUser(){
        String login = "guest@guest.pl";
        if (!userService.userWithEmailExists(login)) {
            RegistrationDTO defaultUser = new RegistrationDTO.RTDOBuilder()
                    .login(login)
                    .password("user")
                    .passwordConfirm("user")
                    .birthDate("2020-09-01")
                    .firstName("William")
                    .lastName("Blake")
                    .street("Zielińskiego 56")
                    .zipCode("53-534")
                    .city("Wrocław")
                    .country(Countries.POLAND.getSymbol())
                    .phoneNumber("+48785850868")
                    .preferEmails(false)
                    .build();
            userService.register(defaultUser);
        }
    }

    private void createTask1() {
        String name = "Nerd talking with nerds";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("Talking about bullshit with some beer and vodka");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(120));
            details.setComplicity(Complicity.EASY);
            details.setImageUrl("https://apprecs.org/ios/images/app-icons/256/89/1137834633.jpg");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_BRAIN_TASK.name()));
            details.setTimeInMinutes(200);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask2() {
        String name = "Walk with a dog";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("Small walk with my pet");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(0));
            details.setComplicity(Complicity.MEDIUM);
            details.setImageUrl("https://www.jing.fm/clipimg/detail/31-312093_holiday-dog-care-options-walk-the-dog-icon.png");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_MOVEMENT_TASK.name()));
            details.setTimeInMinutes(50);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask3() {
        String name = "Shopping with wife";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("A long walk around shops, a huge waste of time!!!");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(1150));
            details.setComplicity(Complicity.IMPOSSIBLE);
            details.setImageUrl("https://cdn4.iconfinder.com/data/icons/people-4-2/65/324-512.png");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_MOVEMENT_TASK.name()));
            details.setTimeInMinutes(120);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask4() {
        String name = "Running activity";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("5000m run , must be fit in 40 mins");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(0));
            details.setComplicity(Complicity.DIFFICULT);
            details.setImageUrl("https://sofearun.com/v2/main_theme/images/runicon.png");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_BRAIN_TASK.name()));
            details.setTimeInMinutes(40);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask5() {
        String name = "Shopping";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("Walking around and searching for a grocery useless stuff");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(200));
            details.setComplicity(Complicity.EASY);
            details.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSH7KJfOSPN_ZZPuxU_hi2-3_V1E7Ccg2utyQ&usqp=CAU");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_BRAIN_TASK.name()));
            details.setTimeInMinutes(30);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask6() {
        String name = "Beer task";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("Go with friends to the city, drinking drinks and having fun");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(240));
            details.setComplicity(Complicity.EASY);
            details.setImageUrl("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/b179b54a-c6c2-4d81-8417-1bb908ea5ec1/da1qwh7-62c2aaf2-a791-4d6e-b4bc-d41916a338d0.png");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_BRAIN_TASK.name()));
            details.setTimeInMinutes(340);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }

    private void createTask7() {
        String name = "SRUTUTUTU";
        if(!taskRepository.existsByName(name)){
            Task task = new Task();
            task.setTaskName(name);
            task.setDescription("majtki z drutu tutututu");
            TaskDetails details = new TaskDetails();
            details.setPrice(new BigDecimal(5));
            details.setComplicity(Complicity.EASY);
            details.setImageUrl("https://img.joemonster.org/mg/albums/new/110527dok/majtki.jpg");
            details.setCategory(categoryRepository.findByName(Category.DefaultCategory.STANDARD_BRAIN_TASK.name()));
            details.setTimeInMinutes(3);
            task.setTaskDetails(details);
            taskRepository.save(task);
        }
    }



}

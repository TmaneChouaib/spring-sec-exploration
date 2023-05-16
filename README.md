<h1 style="color:LightGreen"><a href="https://spring.io/" target="_blank" rel="noreferrer" > <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="20" height="20"/> </a >Développement web avec Spring et Thymeleaf</h1>

# La documentation n'est pas complete ...

<h1>dernière partie</h1>

<p>on vas Maintenant <code>securiser</code> notre application dans cette partie 😁 !!</p>
<p>la documentation de cette partie vas contenire les changements apportés au projet et les choses quand a ajouter</p>
<a href="https://github.com/TmaneChouaib/spring-mvc-app-patient-part-03">->la suite de l'Implémentation du CRUD - Partie 2</a>

<h2>1.Structure du Code :</h2>
<ul>
  <li><strong>entities</strong>: contient l'entité Patient</li>
  <li><strong>repositories</strong>: contient la couche DAO</li>
  <li><strong>web</strong>: contient la couche web</li>
  <li><strong>security</strong>: contient la logique de securité et doucoupé en 3 sous packages :</li>
  <ul>  
  <li><strong>entities</strong></li>
  <li><strong>service</strong></li>
  <li><strong>UserReporitory</strong></li>
  </ul>
</ul>
<img src="pictureDocumentation/StructureDuProject.png" alt="structure du code">

<h2>2.Le fichier<code>pom.xml</code> :</h2>

```Bash
       <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity6 -->
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
            <version>3.1.0.M1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <version>3.0.6</version>
        </dependency>
 ```

<ul>
  <li>J'ai ajoutté la dépendance de <strong>spring-boot-devtools</strong> une dépendance qui nous seras tres utile pour ne pas redémarer le project a chaque changement</li>
  <li>La dépendance du driver <strong>javax.servlet-api</strong>, pour les servlets qui fournit des classes et des interfaces pour gérer les reqêtes HTTP et les réponses su serveur web</li>
  <li>La dépendance de <strong>spring-extras-springsecurity6</strong> une dépendance pour fournire des fonctionnalités pour Spring Security 6</li>
  <li>Aussi la dépendance de <strong>spring-boot-starter-security</strong> une dépendance pour fournire des fonctionalités de sécurite pour notre application web</li>
</ul>

<h2>3.Le controller <code>PatientController.java</code> dans le package web :</h2>

```JAVA
package ma.emsi.patientsmvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.entities.Patient;
import ma.emsi.patientsmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/patientsJson")
    @ResponseBody
    public List<Patient> listePatients(){
        return patientRepository.findAll();
    }

    @GetMapping(path="/user/index")
    public String index(Model model ,
                                    @RequestParam(name="page",defaultValue = "0") int page ,
                                    @RequestParam(name="size",defaultValue = "5")  int size ,
                                    @RequestParam(name="keyWord", defaultValue = "") String keyWord)
    {
        Page<Patient> pagePatients=patientRepository.findByNameContains(keyWord,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyWord",keyWord);
        //retourne une vue appele patients.html
        return "patients";
    }

    @GetMapping("/admin/addPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "addPatients";
    }

    @GetMapping("/admin/editPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatients(Model model ,Long id ,String keyWord ,int page){
    Patient patient=patientRepository.findById(id).orElse(null);
    if(patient==null) throw new RuntimeException("Patient introuvable");
    model.addAttribute("patient",patient);
    model.addAttribute("page",page);
    model.addAttribute("keyWord",keyWord);
    return "editPatients";
    }

    @PostMapping(path="/admin/savePatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatients(Model mode,
                       @Valid Patient patient ,
                       BindingResult bindingResult ,
                       @RequestParam (defaultValue = "0") int page ,
                       @RequestParam (defaultValue = "") String keyWord){
        if(bindingResult.hasErrors())
            return "addPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyWord="+keyWord;
    }

    @GetMapping("/admin/deletePatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyWord, int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyWord="+keyWord;
    }

}
```

<h2>3.Le controller <code>SecurityController.java</code> dans le package web :</h2>

```JAVA
package ma.emsi.patientsmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
```

<h2 style="color:LightGreen">4.L'entité <code>AppRole.java</code> dans le package security.entities :</h2>

 ```JAVA
 package ma.emsi.patientsmvc.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {
    @Id
    private String role;
}
```

<h2 style="color:LightGreen">5.L'entité <code>AppUser.java</code> dans le package security.entities :</h2>

 ```JAVA
 package ma.emsi.patientsmvc.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
```

<h2 style="color:LightGreen">6.L'interface <code>AccountService.java</code> dans le package security.service :</h2>

 ```JAVA
package ma.emsi.patientsmvc.security.service;

import ma.emsi.patientsmvc.security.entities.AppRole;
import ma.emsi.patientsmvc.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password , String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);

}
```

<h2 style="color:LightGreen">7.La classe <code>AccountServiceImpl.java</code> dans le package security.service :</h2>

 ```JAVA
package ma.emsi.patientsmvc.security.service;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.security.UserRepository.AppRoleRepository;
import ma.emsi.patientsmvc.security.UserRepository.AppUserRepository;
import ma.emsi.patientsmvc.security.entities.AppRole;
import ma.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this User already exist");
        if(!password.equals(confirmPassword))throw new RuntimeException("Passwords not match");
        appUser=AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("This role already exist");
        appRole=AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
```

<h2 style="color:LightGreen">8.La classe <code>UserDetailServiceImpl.java</code> dans le package security.service :</h2>

 ```JAVA
package ma.emsi.patientsmvc.security.service;

import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if(appUser==null) throw new UsernameNotFoundException(String.format("User %S not found", username));

        String[] roles = appUser.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);
        UserDetails userDetails= User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}
```

<h2 style="color:LightGreen">9.L'interface <code>AppRoleRepository.java</code> dans le package security.UserRepository :</h2>

 ```JAVA
package ma.emsi.patientsmvc.security.UserRepository;

import ma.emsi.patientsmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}

 ```

 <h2 style="color:LightGreen">10.L'interface <code>AppUserRepository.java</code> dans le package security.UserRepository :</h2>

 ```JAVA
package ma.emsi.patientsmvc.security.UserRepository;

import ma.emsi.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
 ```

<h2 style="color:LightGreen">11.La classe <code>PatientsMvcApplication.java</code> la classe principale de l'App :</h2>

 ```JAVA
package ma.emsi.patientsmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
 ```

<h2><strong>Les vues</strong> dans le dossier templates :</h2>

<h3>12.La vue <strong>login.html</strong> dans le dossier templates :</h3>

 ```HTML
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.2.3/css/bootstrap.min.css">
    <script src="/webjars/bootstrap/5.2.3/js/bootstrap.bundle.js"></script>
</head>
<body>
<div class="row mt-3">
    <div class="col-md-6 offset-3">
        <div class="card">
            <div class="card-header">Authentication</div>
            <div class="card-body">
                <form method="post" th:action="@{/login}">
                    <div class="mt-2">
                        <label class="form-label">Username :</label>
                        <input type="text" name="username" class="form-control">
                    </div>
                    <div class="mt-2">
                        <label class="form-label">Password :</label>
                        <input type="password" name="password" class="form-control">
                    </div>
                    <div class="form-check mt-2">
                        <input type="checkbox" name="remember-me" class="form-check-input">
                        <label class="form-check-label">Remember me </label>
                    </div>
                    <div class="d-flex justify-content-center mt-5">
                        <button type="submit" class="btn btn-dark mt-2">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
 ```

<h3>13.La vue <strong>notAuthorized.html</strong> dans le dossier templates :</h3>

 ```HTML
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
                xmlns:layout="http//www.ultraq.net.nz/thymeleaf/layout"
                layout:decorate="template">

<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
</head>
<body>

<div layout:fragment="contentPagePatient">
  <div class="container-fluid">
    <div class="alert alert-danger">
      <h2>Not Authorized</h2>
    </div>
  </div>
</div>

</body>
</html>
 ```

<h2>Résultat :</h2>
<img src="pictureDocumentation/login.png" alt="Resultat">
<img src="pictureDocumentation/listePatients.png" alt="Resultat">
<img src="pictureDocumentation/addPatients.png" alt="Resultat">
<img src="pictureDocumentation/updatePatients.png" alt="Resultat">

<p>Fin !</p>
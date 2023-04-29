# spring03

<h1>dernière partie</h1>
<p>on vas Maintenant securiser notre application dans cette partie 😁 !!</p>
<p>la documentation de cette partie vas contenire les changements apportés au projet et les choses quand a ajouter</p>
<a href="https://github.com/TmaneChouaib/spring02">->la suite de Spring2</a>

<h2>1.Structure du Code :</h2>
<ul>
  <li><strong>entities</strong>: contient nos entités</li>
  <li><strong>repositories</strong>: contient la couche DAO</li>
  <li><strong>web</strong>: contient la couche web</li>
  <li><strong>security</strong>: contient la logique pour securité notre application</li>
</ul>
<img src="pictureDocumentation/StructureDuProject.png" alt="structure du code">

<h2>2.Le fichier<code>pom.xml</code> :</h2>
<a href="https://gist.github.com/TmaneChouaib/d4cebcb90d76890648cedc9a89d56f84">->fichier pom.xml</a>


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
  <li>On ajoute la dépendance de <strong>spring-boot-devtools</strong> une dépendance qui nous seras tres utile pour ne pas redémarer le project a chaque changement</li>
  <li>La dépendance du driver <strong>javax.servlet-api</strong>, pour les servlets qui fournit des classes et des interfaces pour gérer les reqêtes HTTP et les réponses su serveur web</li>
  <li>La dépendance de <strong>spring-extras-springsecurity6</strong> une dépendance pour fournire des fonctionnalités pour Spring Security 6</li>
  <li>Aussi la dépendance de <strong>spring-boot-starter-security</strong> une dépendance pour fournire des fonctionalités de sécurite pour notre application web</li>

<h2>3.Le controller <code>SecurityController.java</code> dans le package web :</h2>
<a href="https://gist.github.com/TmaneChouaib/65117754778ef2d1a86ca130c1c72c24">->le SecurityController</a>
<ul>
<li><strong>notAuthorized()</strong> : renvoie la vue "notAuthorized"</li>
<li><strong>login()</strong> : renvoie la vue login</li>
</ul>

<h2>4.<strong>Les vues</strong> dans le dossier templates :</h2>


<h3>4_1.La vue <strong>login.html</strong> dans le dossier templates :</h3>
<a href="https://gist.github.com/TmaneChouaib/fe886ddbf4d78b67bf5e747e78b715ba">->la vue login.html</a>

<h3>4_2.La vue <strong>notAuthorized.html</strong> dans le dossier templates :</h3>
<a href="https://gist.github.com/TmaneChouaib/46beb8527504e230c558fc37f884e9e3">->la vue notAuthorized.html</a>
</ul>
<img src="pictureDocumentation/dependency.png" alt="les dépendances">


<h2>Résultat :</h2>
<img src="pictureDocumentation/login.png" alt="Resultat">
<img src="pictureDocumentation/listePatients.png" alt="Resultat">
<img src="pictureDocumentation/addPatients.png" alt="Resultat">
<img src="pictureDocumentation/updatePatients.png" alt="Resultat">

<h2>Note :</h2>
<p>Le code source de cette partie a subi un petit refactoring comparé au project precedent ;a savoire des petites modifications de noms de fichiers et méthodes. Veillez consulter le code source de cette partie et ou les gistes pour connaître les changements apportés  😉 !!</p>
<a href="https://gist.github.com/TmaneChouaib/d4cebcb90d76890648cedc9a89d56f84">->pom.xml</a><br>
<a href="https://gist.github.com/TmaneChouaib/65117754778ef2d1a86ca130c1c72c24">->SecurityController.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/c66e10e738c97430903f03eb60efb7fb">->SecurityConfig.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/23d942ff6a6e2108f761e3e11ff8bf08">->la vue login.html</a><br>
<a href="https://gist.github.com/TmaneChouaib/46beb8527504e230c558fc37f884e9e3">->notAuthorized.html</a><br>
<a href="https://gist.github.com/TmaneChouaib/9f2fb1d922970d0eb3de967a816b1037">->AppUser.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/b7253e59bcb9b0d22564168fd61e1197">->AppUserRepository.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/9c9f9f88d05356daa5891acfa3f66cc1">->UserDetailServiceImpl.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/7526fc6c232cfe758b86ffe3cc7c82d9">->AppRole.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/b0513a58b42a45b5380cb3016e5b67fd">->AppRoleRepository.java</a><br>
<a href="https://gist.github.com/TmaneChouaib/35b15b4fa6698e0d99babca24b8fab1e">->AccountServiceImpl.java</a><br>


<br><br><br><br>
<p>Merci !</p>
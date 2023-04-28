# spring03

<h1>dernière partie</h1>
<p>la documentation de cette partie vas contenire les changements apportés au projet et les choses quand a ajouter</p>
<a href="https://github.com/TmaneChouaib/spring02">->la suite de Spring2</a>

<h2>1.Structure du Code :</h2>
<ul>
  <li><strong>entities</strong>: contient nos entités</li>
  <li><strong>repositories</strong>: contient la couche DAO</li>
  <li><strong>web</strong>: contient la couche web</li>
  <li><strong>security</strong>: contient la logique pour securité notre application</li>
</ul>
<img src="pictureDocumentation/structureDuCode.png" alt="structure du code">

<h2>2.Le fichier<code>pom.xml</code> :</h2>
<a href="https://gist.github.com/TmaneChouaib/d4cebcb90d76890648cedc9a89d56f84">->fichier pom.xml</a>
<ul>
  <li>On ajoute la dépendance de <strong>spring-boot-devtools</strong> une dépendance qui nous seras tres utile pour ne pas redémarer le project a chaque changement</li>
  <li>La dépendance du driver <strong>javax.servlet-api</strong>, pour les servlets qui fournit des classes et des interfaces pour gérer les reqêtes HTTP et les réponses su serveur web</li>
  <li>La dépendance de <strong>spring-extras-springsecurity6</strong> une dépendance pour fournire des fonctionnalités pour Spring Security 6</li>
  <li>Aussi la dépendance de <strong>spring-boot-starter-security</strong> une dépendance pour fournire des fonctionalités de sécurite pour notre application web</li>

<h2>3.Le controller <code>SecurityController.java</code> dans le package web :</h2>
<a href="https://gist.github.com/TmaneChouaib/65117754778ef2d1a86ca130c1c72c24">->le SecurityController</a>
<ul>
<li><strong>notAuthorized()</strong> : renvoie la vue "notAuthorized"</li>
<li><strong>editPatiens()</strong> : renvoie la vue login</li>
</ul>

<h2>4.<strong>Les vues</strong> dans le dossier templates :</h2>


<h3>4_1.La vue <strong>login.html</strong> dans le dossier templates :</h3>
<a href="https://gist.github.com/TmaneChouaib/fe886ddbf4d78b67bf5e747e78b715ba">->la vue login.html</a>

<h3>4_2.La vue <strong>editPatients.html</strong> dans le dossier templates :</h3>
<a href="https://gist.github.com/TmaneChouaib/46beb8527504e230c558fc37f884e9e3">->la vue notAuthorized.html</a>
</ul>
<img src="pictureDocumentation/dependency.png" alt="les dépendances">


<h2>Résultat :</h2>
<img src="pictureDocumentation/login.png" alt="Resultat">
<img src="pictureDocumentation/listePatients.png" alt="Resultat">
<img src="pictureDocumentation/notAuthorized.png" alt="Resultat">
<img src="pictureDocumentation/addPatients.png" alt="Resultat">
<img src="pictureDocumentation/updatePatients.png" alt="Resultat">


<p>Merci !</p>
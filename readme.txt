                                       Jeu Motus en JAVA 

1-Contenu du projet:

  Package controllers : 
  		
  		Dictionnary.java : responsable de la gestion du dictionnaire "Doc.txt" et la séléction d'un mot aléatoire selon la taille souhaitée.
        Score.java : contient toute la logique de calcule de score, l'historique du score ainsi que des pseudos est stockée dans le fichier "jeu.txt".
        
  Package graphics :
  
  		 ChoixFenetre.java : responsable de la représentation de la fenêtre du choix où le joueur peut entrer son pseudo nom, la taille du mot à deviner et le niveau de difficulté soit facile (7 tentatives), moyen (5 tentatives) ou difficile (3 tentatives).
  		 ConteneurChoixFenetre.java : sert à remplir la fenêtre créé par la classe ChoixFenetre.java.
  		 MotusFrame.java : responsable de la représentation de la fenêtre principale du jeu.
  		 ConteneurMotusFrame.java : sert à remplir la fenêtre créé par la classe MotusFrame.java et à la gestion de la logique du jeu. Cette classe gére l'affichage de la grille dynamique dont le nombre de colonnes est la taille du mot à deviner et le nombre de lignes correspond au niveau de difficulté choisi la premiére case de la grille contient la premiére lettre du mot à deviner et une deuxiéme lettre du mot est affichée dans une position aléatoire, l'initialisation du chrono (3 min),
  		                            un espace où le joueur peut entrer ses propositions. 
  		                            
  Package Test:
 
  		Main.java : c'est la classe main du projet.
  		
  		
2-Déroulement du jeu : 

Pour lancer le jeu exécutez la classe Main.java dans le package Test. Une fois exécutée, une fenêtre contenant un loadingbare s'affiche, et aprés 3 secondes, le jeu commence. 

D'abord, le joueur doit choisir un pseudo-nom, la taille du mot à deviner et le niveau de difficulté, aprés il doit cliquer sur le bouton OK pour commencer, sinon il clique sur le bouton CANCEL pour sortir. 

Ensuite, la fenêtre principale s'affiche. Le joueur peut entrer sa proposition, un message d'erreur s'affiche si la proposition entrée est de taille différente que celle du mot à deviner, si elle ne commence pas par la premiére lettre comme indiqué dans la grille ou si elle contient des caractéres spéciaux ou des chiffres. Ainsi le nombre de tentatives se décrémente.

Les lettres bien placées s'affichent en vert, mal placées en jaune sinon en rouge. On a aussi géré l'occurence de la lettre dans la proposition. 

Si le joueur devine correctement le mot un message de victoire s'affiche ainsi que son score. Ce score est cumulé et stocké pour les parties suivantes, à condition que le joueur rentre le même pseudo utilisé pour les parties précédentes.

Si le temps est écoulé et que le joueur n'as pas encore deviné le mot un message de notification s'affiche.

Le joueur peut à chaque fois changer le mot à deviner en cliquant sur le bouton RECOMMENCER, il peut sortir du jeu en cliquant sur le button QUITTER.





P.S : Pour aider le programmeur, le mot correct à deviner est affiché sur la console afin de tester les différentes fonctionnalités.



 


 
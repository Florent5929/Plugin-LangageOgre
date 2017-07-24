package fr.Florent59.LangageOgre;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	int width = 2;
	int height = 2;
	
	public static FileConfiguration config;
	public static String TableauMots[] = new String[185];
	public static String pseudolistogres = new String();
	public static String pseudolistmages = new String();
	

	
	@Override
	public void onEnable(){
		super.onEnable();
		
	    if (!this.getDataFolder().exists()) { 
	        this.saveDefaultConfig();
	        this.getConfig().options().copyDefaults(true);
	    } // S'il n'y a pas de dossier et de fichier de configuration, on cr�e ceux par d�faut. 
	    
	    config = this.getConfig();

	    
	    
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Langage(), this);
		// Ces deux lignes servent � enregistrer des �v�nements que l'on pourra exploiter dans Langage.java.
		// En l'occurence l'�v�nement qui nous int�resse est celui qui se d�clenche quand un joueur parle.
		
		int i;
		i = 0;
		
		List<String> Listemots = this.getConfig().getStringList("MotsOgre");
         for (String mot : Listemots){
        	 TableauMots[i] = mot;
        	 i = i+1;
         } // On r�cup�re les mots ogres list�s dans la configuration pour les ranger dans un tableau.
         
         i = 0;
         
     	 List<String> Ogres  = getConfig().getStringList("Ogres");
     	 pseudolistogres = "";
         for (String pseudo : Ogres){
        	 pseudolistogres = pseudolistogres + pseudo + ", ";
         } // On r�cup�re les ogres list�s dans la configuration pour les ranger dans une cha�ne de caract�res.
          // Elle aura la forme suivante : Pseudo1, Pseudo2, Pseudo3, [etc]
	
	List<String> Mages  = getConfig().getStringList("Mages");
	 pseudolistmages = "";
    for (String pseudo : Mages){
   	 pseudolistmages = pseudolistmages + pseudo + ", ";
    } // On r�cup�re les mages  list�s dans la configuration pour les ranger dans une cha�ne de caract�res.

	
}

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		if(sender instanceof Player ){
		
			// On v�rifie que l'entit� qui ex�cute la commande est un joueur.
		
		 if(label.equalsIgnoreCase("addogre")){ // Si c'est la commande /addogre <CompteMC> ...
			if(args.length != 1){ // ... on v�rifie qu'un compte MC a bien �t� renseign�.
				player.sendMessage("Usage : /addogre <CompteMC>"); // Sinon on envoie un message qui explique comment utiliser la commande.
			} else if(args.length == 1){
				 List<String> Ogres  = getConfig().getStringList("Ogres");
				 if(!main.pseudolistogres.contains(args[0])){ Ogres.add(args[0]);
				 getConfig().set("Ogres", Ogres);
				 saveConfig(); // Si l'ogre n'est pas dans la cha�ne d'ogres "Pseudo1, Pseudo2, etc", on le rajoute � la config.
				 player.sendMessage(ChatColor.DARK_AQUA + args[0] + " a �t� ajout�(e) � la liste des ogres du fichier de configuration.");
				 } else {
						player.sendMessage(ChatColor.RED + args[0] + " existe d�j� dans la liste des ogres du fichier de configuration !");
						 } // Sinon, on envoie un message disant qu'il existe d�j�.
			}		
			getServer().getPluginManager().disablePlugin(this);
            getServer().getPluginManager().enablePlugin(this);
		} // On relance le plugin pour qu'il recr�e la cha�ne de caract�res ogres en prenant en compte la modification.
		
		 
		 
		 else if(label.equalsIgnoreCase("delogre")){ // Si c'est la commande /delogre <CompteMC> ...
				if(args.length != 1){ // ... on v�rifie qu'un compte MC a bien �t� renseign�.
					player.sendMessage("Usage : /delogre <CompteMC>"); // Sinon on envoie un message qui explique comment utiliser la commande.
				} else if(args.length == 1){
					 List<String> Ogres  = getConfig().getStringList("Ogres");
					 if(main.pseudolistogres.contains(args[0])){ Ogres.remove(args[0]);
					 getConfig().set("Ogres", Ogres);
					 saveConfig(); // Si l'ogre est dans la cha�ne d'ogres "Pseudo1, Pseudo2, etc", on le supprime de la config.
					 player.sendMessage(ChatColor.DARK_AQUA + args[0] + " a �t� supprim�(e) de la liste des ogres du fichier de configuration.");
					 } else {
					player.sendMessage(ChatColor.RED + args[0] + " n'existe pas dans la liste des ogres du fichier de configuration, vous ne pouvez donc pas le supprimer.");
					 } // Sinon, on envoie un message disant qu'il n'existe pas.
					 }
				getServer().getPluginManager().disablePlugin(this);
                getServer().getPluginManager().enablePlugin(this);	
			} // On relance le plugin pour qu'il recr�e la cha�ne de caract�res ogres en prenant en compte la modification.
			
		 
		 
		 else if(label.equalsIgnoreCase("showogres")){ // Si c'est la commande /showogres ...
			 player.sendMessage(ChatColor.DARK_AQUA + "Les ogres r�pertori�s dans le fichier de configuration sont : " + pseudolistogres);
			} // ... on affiche tout simplement la cha�ne de caract�res ogres : Pseudo1, Pseudo2 ...
		 
		 // Pour la suite, c'est exactement la m�me chose, sauf que ce ne sont plus des ogres mais des mages.
		 
		 
		 
		 else if(label.equalsIgnoreCase("showmages")){ // Commande /showmages
			 player.sendMessage(ChatColor.DARK_AQUA + "Les mages r�pertori�s dans le fichier de configuration sont : " + pseudolistmages);
			}
			
		 
		 
		 else if(label.equalsIgnoreCase("addmage")){ // Commande /addmage <CompteMC>
				if(args.length != 1){
					player.sendMessage("Usage : /addmage <CompteMC>");
				} else if(args.length == 1){
					 List<String> Mages  = getConfig().getStringList("Mages");
					 if(!main.pseudolistmages.contains(args[0])){ Mages.add(args[0]);
					 getConfig().set("Mages", Mages);
					 saveConfig();
					 player.sendMessage(ChatColor.DARK_AQUA + args[0] + " a �t� ajout�(e) � la liste des mages du fichier de configuration.");
					 } else {
							player.sendMessage(ChatColor.RED + args[0] + " existe d�j� dans la liste des mages du fichier de configuration !");
							 }
				}		
				getServer().getPluginManager().disablePlugin(this);
	            getServer().getPluginManager().enablePlugin(this);
			}
		 
		 
		 
		 else if(label.equalsIgnoreCase("delmage")){ // Commande /delmage <CompteMC>
				if(args.length != 1){
					player.sendMessage("Usage : /delmage <CompteMC>");
				} else if(args.length == 1){
					 List<String> Mages  = getConfig().getStringList("Mages");
					 if(main.pseudolistmages.contains(args[0])){ Mages.remove(args[0]);
					 getConfig().set("Mages", Mages);
					 saveConfig();
					 player.sendMessage(ChatColor.DARK_AQUA + args[0] + " a �t� supprim�(e) de la liste des mages du fichier de configuration.");
					 } else {
					player.sendMessage(ChatColor.RED + args[0] + " n'existe pas dans la liste des mages du fichier de configuration, vous ne pouvez donc pas le supprimer.");
					 }
					 }
				getServer().getPluginManager().disablePlugin(this);
             getServer().getPluginManager().enablePlugin(this);	
			}
		 
		 
		 
		 
		}
		return false;
		
		}
	public static String TableauTrad[][] = { 
			{"Montrer", 
			"Pouvoir", 
			"Adieu", 
			"Beaucoup", 
			"Nombreux", 
			"Plusieurs", 
			"Vouloir", 
			"D�p�cher", 
			"Donner", 
			"Arr�ter", 
			"Stop", 
			"Mentir", 
			"Ta", 
			"Tes", 
			"Toi", 
			"Aurevoir", 
			"Niveau", 
			"Passer", 
			"Ralentir", 
			"Prendre", 
			"Vous", 
			"Non", 
			"Sandwich", 
			"Porte", 
			"Boule", 
			"Bourrin", 
			"Foncer", 
			"Marcher", 
			"�p�e", 
			"Arme", 
			"Loin", 
			"Lointain", 
			"Arracher", 
			"M�tre", 
			"Route", 
			"Pratique", 
			"L�viter", 
			"Oiseau", 
			"Voler", 
			"Pi�ge", 
			"Pas", 
			"Vieille", 
			"Vieux", 
			"Vaincre", 
			"Plus", 
			"F�ter", 
			"Nous", 
			"Attraper", 
			"Dormir", 
			"Sommeil", 
			"Porte", 
			"Nain", 
			"Merci", 
			"Comestible", 
			"Dur", 
			"Dragon", 
			"Gros", 
			"Attaquer", 
			"Chose", 
			"Balancer", 
			"Lancer", 
			"Entendre", 
			"Devoir", 
			"Donc", 
			"Tenir", 
			"Sortir", 
			"Humain", 
			"Pour", 
			"�craser", 
			"D�foncer", 
			"Fracasser", 
			"Enfoncer", 
			"Appuyer", 
			"Froid", 
			"Orque", 
			"M�chant", 
			"Mal", 
			"Mauvais", 
			"Acheter", 
			"Super", 
			"T�te", 
			"Finir", 
			"Terminer", 
			"Excuser", 
			"Pardon", 
			"Pardonner", 
			"Boire", 
			"Blaguer", 
			"D�conner", 
			"Voir", 
			"Amazone", 
			"Barbare", 
			"Emmerder", 
			"Gobelin", 
			"Chance", 
			"Grillade", 
			"Aimer", 
			"Pareil", 
			"Voil�", 
			"Cochon", 
			"Porc", 
			"Bonjour", 
			"Approcher", 
			"Attention",
			"Rep�rer", 
			"Casser", 
			"Guitare", 
			"Pourquoi", 
			"Garder", 
			"Toujours", 
			"Grand", 
			"Porter", 
			"Transporter", 
			"Couper", 
			"D�couper", 
			"Filer", 
			"Partir", 
			"Maison", 
			"Chuter", 
			"Tomber", 
			"Dans", 
			"Dedans", 
			"Int�rieur", 
			"�prouver", 
			"R�p�ter", 
			"Entrer", 
			"Exp�rience", 
			"Amuser", 
			"Pour", 
			"Ombre", 
			"Vite", 
			"Entendre", 
			"Trouver", 
			"Ami", 
			"F�liciter", 
			"Ailleurs", 
			"Habit", 
			"V�tement", 
			"Poubelle", 
			"Salet�", 
			"Manger", 
			"Frapper", 
			"Nanana", 
			"Perdu", 
			"Bonne", 
			"Charogne", 
			"Etage", 
			"Anesth�sier", 
			"Endormir", 
			"Orc", 
			"Graisse", 
			"Gras", 
			"Ne", 
			"Vraiment", 
			"Tr�s", 
			"Armure", 
			"Caca", 
			"Merde", 
			"Saucisse", 
			"G�nie", 
			"Bandit", 
			"De", 
			"Table", 
			"Gemme", 
			"Minerais", 
			"Exact", 
			"Bon", 
			"Bonne", 
			"�clabousser", 
			"Eau", 
			"Flaque", 
			"Besoin", 
			"Aplatir", 
			"�crabouiller", 
			"Tuer", 
			"Quoi", 
			"Balancer", 
			"Jeter", 
			"Offrir", 
			"Taille", 
			"Gratter", 
			"Petit", 
			"Mage", 
			"Magicien", 
			"Sorcier", 
			"Amadouer", 
			"Charmer", 
			"Dresser", 
			"Moquer", 
			"Bras", 
			"Peau-verte", 
			"Crever", 
			"Tous", 
			"Demander", 
			"Comprendre", 
			"Coucher", 
			"Troll", 
			"Troll", 
			"Idiot", 
			"Stupide", 
			"Chanson", 
			"Savoir", 
			"Ici", 
			"Cheveux", 
			"Cacher", 
			"Chier", 
			"Chanter", 
			"Jambon", 
			"Lait", 
			"Puer", 
			"Poss�der", 
			"Monstre", 
			"Avec", 
			"Avancer", 
			"Fl�che", 
			"Cave", 
			"Caveau", 
			"Dessous", 
			"Sous-sol", 
			"Pied", 
			"Pr�s", 
			"Proche", 
			"D�mon", 
			"Oui", 
			"Finir", 
			"Ouais", 
			"Mourir", 
			"Passage", 
			"Passer", 
			"Venir", 
			"Odeur", 
			"Sentir", 
			"Auberge", 
			"Tani�re", 
			"Faire", 
			"Attendre", 
			"Patienter", 
			"Bizarre", 
			"Caillou", 
			"Pierre", 
			"Roche", 
			"Aller" 
	},
			{"Achilika", 
		"Agh", 
		"Aglou", 
		"Agol", 
		"Agol", 
		"Agol", 
		"Akala", 
		"Akiita", 
		"Aknouh", 
		"Algo", 
		"Algo", 
		"Amzo", 
		"Ati", 
		"Ati", 
		"Ati", 
		"Atoonza", 
		"Atoulka", 
		"Azerofh", 
		"Azodo", 
		"Beula", 
		"Bladola", 
		"Blok", 
		"Bok", 
		"Borka", 
		"Boulik", 
		"Bourrino", 
		"Bourrino", 
		"Bozoh", 
		"Bragoul", 
		"Bragoul", 
		"Broudaf", 
		"Broudaf", 
		"Broum", 
		"Chokma", 
		"Clog", 
		"Crao", 
		"Cuicui", 
		"Cuicui", 
		"Cuicui", 
		"Da", 
		"Dak", 
		"Dalouk", 
		"Dalouk", 
		"Dam", 
		"Dar", 
		"Djinlgebelh", 
		"Dkajo", 
		"Dlakou", 
		"Dodo", 
		"Dodo", 
		"Doordo", 
		"Dorva", 
		"Doula", 
		"Doulo", 
		"Doumva", 
		"Dragoulou", 
		"Droula", 
		"Drulugah", 
		"Dzo", 
		"Dzoubala", 
		"Dzoubala", 
		"Kalounga", 
		"Eto", 
		"Eto", 
		"Etso", 
		"Exta", 
		"Faloukou", 
		"Forlaa", 
		"Frakass", 
		"Frakass", 
		"Frakass", 
		"Galoun", 
		"Gasna", 
		"Glagla", 
		"Glakou", 
		"Glakoul", 
		"Glakoul", 
		"Glakoul", 
		"Glikido", 
		"Glok", 
		"Glok", 
		"Glorkou", 
		"Gloubok", 
		"Gloudouk", 
		"Gloudouk", 
		"Gloudouk", 
		"Glouglou", 
		"Gluku", 
		"Gluku", 
		"Gnagof", 
		"Gnagola", 
		"Gnagolo", 
		"Gnolo", 
		"Gobbo", 
		"Gof", 
		"Gol", 
		"Gola", 
		"Golo", 
		"Golo", 
		"Gorki", 
		"Gorki", 
		"Gotfeurdom", 
		"Goulada", 
		"Goulak", 
		"Goz", 
		"Grao", 
		"Gratgrat", 
		"Gravoz", 
		"Grok", 
		"Groubouf", 
		"Grouf", 
		"Gruh", 
		"Gruh", 
		"Grunt", 
		"Grunt", 
		"Hatlouk", 
		"Hatlouk", 
		"Hol", 
		"Hopla", 
		"Hopla", 
		"Houla", 
		"Houla", 
		"Houla", 
		"Hounk", 
		"Huk", 
		"Inta", 
		"Ixpe", 
		"Jolzio", 
		"Kadoula", 
		"Kagoula", 
		"Kal", 
		"Kalounga", 
		"Kligudu", 
		"Kopain", 
		"Koulaga", 
		"Louk", 
		"Ma", 
		"Ma", 
		"Makkd'oh", 
		"Makkd'oh", 
		"Miam", 
		"Mourk", 
		"Nanana", 
		"Noal", 
		"Dlabou", 
		"Oklagh", 
		"Ol", 
		"Opog", 
		"Opog", 
		"Orkoum", 
		"Palaf", 
		"Palaf", 
		"pas", 
		"plaf", 
		"plaf", 
		"Plaktu", 
		"Popoh", 
		"Popoh", 
		"Pouk", 
		"Razogh", 
		"Rbindeboi", 
		"Schlobok", 
		"Rova", 
		"Sakoul", 
		"Sakoul", 
		"Shlova", 
		"Slouba", 
		"Slouba", 
		"Splach", 
		"Splach", 
		"Splach", 
		"Splati", 
		"Splonrch", 
		"Sprotch", 
		"Sprotch", 
		"Swobok", 
		"Taak", 
		"Taak", 
		"Tablok", 
		"Tadla", 
		"Tagala", 
		"Tagoula", 
		"Taka", 
		"Taka", 
		"Taka", 
		"Tako", 
		"Tako", 
		"Tako", 
		"Talof", 
		"Talok", 
		"Tchok", 
		"Tlakoh", 
		"Toka", 
		"Toko", 
		"Tol", 
		"Toudlag", 
		"Toukala", 
		"Toukalo", 
		"Toulos", 
		"Toulos", 
		"Tounda", 
		"Turlu", 
		"Ugluduk", 
		"Veuk", 
		"Vo", 
		"Volo", 
		"Vrotapa", 
		"Yabo", 
		"Yagourt", 
		"Zaboufka", 
		"Zadoum", 
		"Zaloss", 
		"Zamok", 
		"Zatal", 
		"Zavol", 
		"Znivo", 
		"Znivo", 
		"Znivo", 
		"Znivo", 
		"Zod", 
		"Zof", 
		"Zof", 
		"Zog", 
		"Zog Zog", 
		"Zogdo", 
		"Zogla", 
		"Zom", 
		"Zomva", 
		"Zomva", 
		"Zomva", 
		"Zoub", 
		"Zoub", 
		"Zoubla", 
		"Zoubla", 
		"Zoul", 
		"Zoulag", 
		"Zoulag", 
		"Zpotla", 
		"Zulu", 
		"Zulu", 
		"Zulu", 
		"Zyva" 
	} };
	
	
}


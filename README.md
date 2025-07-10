# Projet Thymeleaf

En partant de cette base de projet, le but sera de réaliser avec Spring Boot et Thymeleaf les fonctionnalités de gestion d'un panier.

## Fonctionnalités attendues

* Créer une petite classe de fixture permettant de remplir la base de données avec des produits et des users (pourquoi pas via un CommandLineRunner)
* Affichage de la liste des produits paginés avec un bouton/form "Add To Cart"
* Créer un CartService qui contiendra le panier permettant 
    * D'ajouter un produit au panier, si le produit n'est pas déjà dedans
    * De retirer un produit du panier
    * De récupérer le prix total du panier
    * De valider le panier, ce qui impliquera de le vider et de baisser le stock des produits qui sont dedans
    * Le panier doit être lié à la session
* Créer une page `/cart` dans laquelle on pourra visualiser les produits dans son panier, le total et le gérer. Les différentes requêtes HTTP déclencheront des méthodes du service depuis le contrôleur

### Fonctionnalités bonus

* Gérer la quantité de produit dans le panier (plusieurs manière, soit avec un objet supplémentaire qui a le produit et la quantité, soit avec une Map) et donc permettre d'ajouter plusieurs fois le même produit, voir d'ajouter plusieur produits à la fois
* Faire que le panier persiste pour le User connecté (ça demandera de faire pas mal de truc)

// URL de l'API côté serveur
const apiUrl = "http://localhost:8088/LogementRendezVous_Etudiant_war/api/logement"

// Fonction pour récupérer tous les logements
// Fonction pour récupérer tous les logements
function getAllLogements() {
    fetch(`${apiUrl}/all`, {
        method: "GET",
        headers: {
            "Accept": "application/json", // Se limiter à l'en-tête accept standard
            "Content-Type": "application/json" // Ajouter cet en-tête si nécessaire pour le serveur
        }
    })
    .then(response => response.json())
    .then(data => {
        let logementsDiv = document.getElementById("logements");
        logementsDiv.innerHTML = "";

        // Créer le tableau
        let table = document.createElement("table");
        table.setAttribute("border", "1");

        // Créer l'en-tête du tableau
        let header = table.createTHead();
        let headerRow = header.insertRow();
        headerRow.insertCell().textContent = "Référence";
        headerRow.insertCell().textContent = "Adresse";
        headerRow.insertCell().textContent = "Délégation";
        headerRow.insertCell().textContent = "Gouvernorat";
        headerRow.insertCell().textContent = "Type";
        headerRow.insertCell().textContent = "Description";
        headerRow.insertCell().textContent = "Prix";

        // Créer le corps du tableau
        let body = table.createTBody();
        data.forEach(logement => {
            let row = body.insertRow();
            row.insertCell().textContent = logement.reference;
            row.insertCell().textContent = logement.adresse;
            row.insertCell().textContent = logement.delegation;
            row.insertCell().textContent = logement.gouvernorat;
            row.insertCell().textContent = logement.type;
            row.insertCell().textContent = logement.description;
            row.insertCell().textContent = `${logement.prix}€`;
        });

        logementsDiv.appendChild(table);
    })
    .catch(error => console.error("Erreur lors de la récupération des logements :", error));
}



// Fonction pour récupérer un logement par sa référence
function getLogementByRef() {
    let ref = document.getElementById("searchRef").value;
    if (!ref) {
        alert("Veuillez entrer une référence.");
        return;
    }
    
    fetch(`${apiUrl}/${ref}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Logement non trouvé");
            }
            return response.json();
        })
        .then(data => {
            let logementDiv = document.getElementById("logement");
            logementDiv.innerHTML = `<p>Réf: ${data.ref} - Adresse: ${data.adresse} - Prix: ${data.prix}€</p>`;
        })
        .catch(error => {
            document.getElementById("logement").innerHTML = "Logement non trouvé";
            console.error("Erreur :", error);
        });
}

// Fonction pour ajouter un logement
function addLogement() {
    // Créer un objet logement avec le format correct
    let logement = {
        reference: document.getElementById("ref").value, // Utiliser 'reference' au lieu de 'ref'
        adresse: document.getElementById("adresse").value,
        delegation: document.getElementById("delegation").value,
        gouvernorat: document.getElementById("gouvernorat").value,
        type: document.getElementById("type").value,
        description: document.getElementById("description").value,
        prix: parseFloat(document.getElementById("prix").value) // Assurer que le prix soit un nombre
    };

    // Vérification de la structure de l'objet avant envoi
    console.log("Données envoyées : ", JSON.stringify(logement));

    fetch(`${apiUrl}/add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(logement) // Envoi des données formatées en JSON
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erreur lors de l'ajout du logement");
        }
        return response.json();
    })
    .then(data => {
        alert("Logement ajouté avec succès !");
        getAllLogements();
    })
    .catch(error => console.error("Erreur :", error));
}



// Fonction pour mettre à jour un logement
function updateLogement() {
    let ref = document.getElementById("updateRef").value;
    let logement = {
        adresse: document.getElementById("updateAdresse").value,
        delegation: document.getElementById("updateDelegation").value,
        gouvernorat: document.getElementById("updateGouvernorat").value,
        type: document.getElementById("updateType").value,
        description: document.getElementById("updateDescription").value,
        prix: document.getElementById("updatePrix").value
    };
 
    fetch(`${apiUrl}/update/${ref}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(logement)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erreur lors de la mise à jour du logement");
        }
        return response.json();
    })
    .then(data => {
        alert("Logement mis à jour avec succès !");
        getAllLogements();
    })
    .catch(error => console.error("Erreur :", error));
}

# 💰 SmartBudget

Application Android **offline-first** de gestion de budget personnel, développée en **Kotlin** avec **Jetpack Compose**. Elle permet de suivre ses dépenses, analyser ses habitudes de consommation par catégorie, et exporter ses données au format CSV.

---

## 📋 Table des matières

- [Contexte](#contexte)
- [Fonctionnalités](#fonctionnalités)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Modèles de données](#modèles-de-données)
- [Screenshots](#screenshots)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Export CSV](#export-csv)
- [Structure du projet](#structure-du-projet)
- [Auteur](#auteur)
- [Licence](#licence)

---

## 🎯 Contexte

Ce projet a été réalisé dans le cadre du **mini-projet Android** du cours de Développement Mobile (LSI S4). L'objectif métier est de fournir aux étudiants un outil simple et efficace pour gérer leurs dépenses récurrentes (transport, repas, loisirs) avec une visibilité claire par mois et par catégorie.


---

## ✨ Fonctionnalités

### Fonctionnalités principales (obligatoires)

| Fonctionnalité | Description |
|:--|:--|
| **CRUD Dépenses** | Ajouter, modifier, supprimer une dépense avec confirmation |
| **Catégorisation** | 8 catégories prédéfinies : Alimentation, Transport, Logement, Santé, Loisirs, Études, Shopping, Autre |
| **Filtrage temporel** | Vue par mois avec navigation mois précédent/suivant |
| **Synthèse** | Total du mois, répartition par catégorie, top catégories |
| **Offline-first** | Toutes les opérations fonctionnent sans connexion internet |

### Fonctionnalités bonus

| Fonctionnalité | Description |
|:--|:--|
| **Gestion des catégories** | Activer/désactiver des catégories personnalisées |
| **Export CSV** | Export des dépenses du mois en cours dans le dossier Téléchargements |
| **Budgets mensuels** | Définir des limites de dépenses par catégorie (structure prête) |

---

## 🏗️ Architecture

Le projet suit une architecture **MVVM (Model-View-ViewModel)** combinée à une approche **Clean Architecture** en couches :

```
┌─────────────────────────────────────┐
│           UI Layer (Compose)        │
│    Screens │ ViewModels │ States    │
├─────────────────────────────────────┤
│         Domain Layer                │
│    Models │ UseCases │ Repository   │
│         Interfaces                  │
├─────────────────────────────────────┤
│         Data Layer                  │
│    Room DB │ DAO │ Entities │ CSV   │
└─────────────────────────────────────┘
```

### Packages principaux

```
com.example.smartbudget/
├── data/               # Couche données
│   ├── local/          # Base Room + DAOs + Entities
│   ├── repository/     # Implémentations des repositories
│   └── mapper/         # Conversion Entity ↔ Domain
├── domain/             # Couche métier
│   ├── model/          # Modèles Kotlin purs
│   ├── repository/     # Interfaces abstraites
│   └── usecase/        # Cas d'utilisation
├── ui/                 # Couche présentation
│   ├── screens/        # Écrans Compose
│   ├── components/     # Composants réutilisables
│   ├── theme/          # Colors, Typography, Theme
│   └── navigation/     # Navigation entre écrans
├── di/                 # Injection de dépendances (Hilt)
└── utils/              # Utilitaires (Date, Validation, Currency)
```

---

## 🛠️ Technologies

| Technologie | Version | Usage |
|:--|:--|:--|
| **Kotlin** | 1.9.20 | Langage principal |
| **Jetpack Compose** | BOM 2023.10.01 | UI déclarative moderne |
| **Material Design 3** | - | Composants UI Material You |
| **Room** | 2.6.1 | Persistance locale SQLite |
| **Hilt** | 2.48 | Injection de dépendances |
| **Navigation Compose** | 2.7.6 | Navigation type-safe |
| **Coroutines + Flow** | 1.7.3 | Asynchrone et réactif |
| **OpenCSV** | 5.7.1 | Export CSV |

---

## 🗃️ Modèles de données

### Expense (Dépense)

| Champ | Type | Description |
|:--|:--|:--|
| `id` | Long | Identifiant auto-généré |
| `amount` | Double | Montant strictement positif |
| `currency` | String | Devise (MAD par défaut) |
| `date` | LocalDate | Date de la dépense |
| `categoryId` | Long | Référence vers la catégorie |
| `note` | String | Note libre (optionnel) |
| `paymentMethod` | Enum | ESPÈCE, CARTE, VIREMENT |
| `createdAt` | LocalDateTime | Date de création |
| `updatedAt` | LocalDateTime | Date de dernière modification |

### Category (Catégorie)

| Champ | Type | Description |
|:--|:--|:--|
| `id` | Long | Identifiant |
| `name` | String | Nom unique (Alimentation, Transport...) |
| `icon` | String | Emoji représentatif |
| `color` | String | Couleur hexadécimale UI |
| `isActive` | Boolean | Active ou archivée |

### MonthlyBudget (Budget mensuel) - Bonus

| Champ | Type | Description |
|:--|:--|:--|
| `id` | Long | Identifiant |
| `month` | String | Format AAAA-MM |
| `categoryId` | Long | Référence catégorie |
| `limitAmount` | Double | Montant limite |

---

## 📸 Screenshots

### Écran Dépenses
Liste des dépenses du mois avec filtrage par catégorie, tri par date/montant, et navigation temporelle.

```
┌─────────────────────────┐
│  ◀  Mai 2026  ▶        │
├─────────────────────────┤
│    💰 Total: 1,250.00   │
│         MAD             │
├─────────────────────────┤
│ [Toutes] [▼] [Sort ▼]  │
├─────────────────────────┤
│ 🍽️ Alimentation    45.50│
│ 🚌 Transport       120.00│
│ 🏠 Logement        800.00│
│ ...                     │
│           [+]           │
└─────────────────────────┘
```

### Écran Ajouter/Modifier
Formulaire avec validation inline (montant > 0, catégorie obligatoire, date requise).

### Écran Statistiques
Répartition visuelle par catégorie avec barres de progression et pourcentages.

### Écran Paramètres
Gestion des catégories (activation/désactivation), choix de devise, export CSV.

---

## 📋 Prérequis

- **Android Studio** Hedgehog (2023.1.1) ou supérieur
- **JDK** 17
- **SDK Android** 34 (compileSdk)
- **Min SDK** 24 (Android 7.0 Nougat)
- **Gradle** 8.10.2

---

## 🚀 Installation

### 1. Cloner le repository

```bash
git clone https://github.com/SalahKhazri/SmartBudget.git
cd SmartBudget
```

### 2. Ouvrir dans Android Studio

```bash
# Ou via Android Studio : File → Open → Sélectionner le dossier
```

### 3. Synchroniser Gradle

Cliquer sur **"Sync Now"** dans la barre de notification ou l'icône éléphant 🐘.

### 4. Lancer l'application

- **Émulateur** : Créer un device virtuel (Pixel 7, API 30+)
- **Appareil physique** : Activer le débogage USB et connecter le téléphone

```bash
# Ou via Android Studio : Run → Run 'app' (Shift + F10)
```

---

## 📱 Utilisation

### Navigation principale

| Onglet | Icône | Description |
|:--|:--|:--|
| **Dépenses** | 🧾 | Liste et gestion des dépenses |
| **Stats** | 📊 | Visualisation et analyse |
| **Paramètres** | ⚙️ | Catégories, devise, export |

### Ajouter une dépense

1. Cliquer sur le bouton **+** (FAB)
2. Remplir le formulaire :
   - Montant (obligatoire, > 0)
   - Catégorie (obligatoire)
   - Date (par défaut : aujourd'hui)
   - Note (optionnel)
3. Cliquer **Enregistrer**

### Naviguer entre les mois

Utiliser les flèches **◀ ▶** dans l'en-tête de l'écran Dépenses.

### Filtrer par catégorie

Sélectionner une catégorie dans le dropdown au-dessus de la liste.

---

## 📤 Export CSV

### Comment exporter

1. Aller dans l'onglet **Paramètres**
2. Cliquer sur **"Exporter en CSV"**
3. Un message de confirmation s'affiche avec le chemin du fichier

### Emplacement du fichier

```
Stockage interne/Download/smartbudget_2026-05.csv
```

Ou via l'app **Mes Fichiers** → **Téléchargements**

### Format du CSV

```csv
Date,Montant,Devise,Categorie_ID,Note,Methode_Paiement
2026-05-01,45.50,MAD,1,Déjeuner,CASH
2026-05-02,120.00,MAD,2,Bus mensuel,CARD
2026-05-03,800.00,MAD,3,Loyer,VIREMENT
```

---

## 🧪 Jeu de données de test

L'application est pré-remplie avec :
- **8 catégories** actives par défaut
- **30+ dépenses** réparties sur 2 mois pour la démonstration

---

## 📁 Structure du projet détaillée

```
SmartBudget/
├── 📁 app/src/main/java/com/example/smartbudget/
│   ├── 📁 data/
│   │   ├── 📁 local/
│   │   │   ├── 📁 dao/
│   │   │   │   ├── ExpenseDao.kt
│   │   │   │   ├── CategoryDao.kt
│   │   │   │   └── MonthlyBudgetDao.kt
│   │   │   ├── 📁 entity/
│   │   │   │   ├── ExpenseEntity.kt
│   │   │   │   ├── CategoryEntity.kt
│   │   │   │   ├── MonthlyBudgetEntity.kt
│   │   │   │   └── CategoryTotalEntity.kt
│   │   │   └── SmartBudgetDatabase.kt
│   │   ├── 📁 repository/
│   │   │   ├── ExpenseRepositoryImpl.kt
│   │   │   ├── CategoryRepositoryImpl.kt
│   │   │   ├── MonthlyBudgetRepositoryImpl.kt
│   │   │   └── CsvExportRepository.kt
│   │   └── 📁 mapper/
│   │       └── DataMapper.kt
│   ├── 📁 domain/
│   │   ├── 📁 model/
│   │   │   ├── Expense.kt
│   │   │   ├── Category.kt
│   │   │   └── MonthlyBudget.kt
│   │   ├── 📁 repository/
│   │   │   └── RepositoryInterfaces.kt
│   │   └── 📁 usecase/
│   │       ├── 📁 expense/
│   │       │   ├── AddExpenseUseCase.kt
│   │       │   ├── UpdateExpenseUseCase.kt
│   │       │   ├── DeleteExpenseUseCase.kt
│   │       │   └── GetExpensesUseCase.kt
│   │       └── 📁 category/
│   │           └── GetCategoriesUseCase.kt
│   ├── 📁 ui/
│   │   ├── 📁 theme/
│   │   │   ├── Color.kt
│   │   │   ├── Theme.kt
│   │   │   └── Type.kt
│   │   ├── 📁 components/
│   │   │   ├── ExpenseItem.kt
│   │   │   ├── CategoryChip.kt
│   │   │   ├── MonthSelector.kt
│   │   │   └── EmptyState.kt
│   │   ├── 📁 screens/
│   │   │   ├── 📁 expenses/
│   │   │   │   ├── ExpensesScreen.kt
│   │   │   │   ├── ExpensesViewModel.kt
│   │   │   │   └── ExpensesUiState.kt
│   │   │   ├── 📁 addedit/
│   │   │   │   ├── AddEditExpenseScreen.kt
│   │   │   │   ├── AddEditExpenseViewModel.kt
│   │   │   │   └── AddEditUiState.kt
│   │   │   ├── 📁 stats/
│   │   │   │   ├── StatsScreen.kt
│   │   │   │   └── StatsViewModel.kt
│   │   │   └── 📁 settings/
│   │   │       ├── SettingsScreen.kt
│   │   │       └── SettingsViewModel.kt
│   │   └── 📁 navigation/
│   │       └── SmartBudgetNavHost.kt
│   ├── 📁 di/
│   │   └── AppModule.kt
│   ├── 📁 utils/
│   │   ├── DateUtils.kt
│   │   ├── CurrencyUtils.kt
│   │   └── ValidationUtils.kt
│   ├── MainActivity.kt
│   └── SmartBudgetApplication.kt
│
├── 📁 app/src/main/res/
│   └── (ressources minimales avec Compose)
│
├── 📄 build.gradle.kts (Project)
├── 📄 app/build.gradle.kts (Module)
├── 📄 settings.gradle.kts
├── 📄 gradle.properties
├── 📄 README.md
└── 📄 .gitignore
```

---

## 👤 Auteur

- **Nom** : Khazri Salah Eddine
- **Formation** : LSI S4 - Développement Mobile Android
- **Date** : Mai 2026



---

## 🙏 Remerciements

- Professeur de Développement Mobile pour les consignes du projet
- Communauté Android et Jetpack Compose pour la documentation
- Open source contributors (Room, Hilt, Compose, OpenCSV)

---

> **Note** : Ce projet a été développé dans un cadre pédagogique. Certaines fonctionnalités avancées (synchronisation cloud, authentification, graphiques avancés) peuvent être ajoutées dans des versions futures.
---
# Smart Pantry Manager

A Java Android application for reducing food waste by suggesting recipes that can be prepared using ingredients already recorded in the user's pantry.

# USED
- Java and Android Studio
- SQLite via SQLiteOpenHelper
- Android ListView with custom BaseAdapter classes
- Android Activities and Intents

# Features
- Add, edit, list and delete pantry ingredients
- 20 recipes seeded automatically on first database creation
- Local persistent SQLite storage
- Strict recipe suggestions: every listed recipe ingredient must be present in the pantry
- Recipe detail view with ingredients and preparation steps
- Basic form validation and empty-match feedback
- Settings screen
  

## Run instructions
1. Install Android Studio with Android SDK Platform 35.
2. Open this folder as an existing Android Studio project.
3. Allow Gradle sync to finish.
4. Create or select an Android emulator (API 23 or newer), or connect an Android device with USB debugging enabled.
5. Press 'Run' for the `app` configuration.
6. Add pantry ingredients such as `egg`, `tomato`, `bread`, `mince`, `pasta`, or `garlic`, with minimum quantity of `1`. Open Suggested Recipes to see "suggested" matches.

# Database choice
SQLite is used because I am most familiar with with it & the assignment focuses on local persistent storage, CRUD operations and Android database fundamentals. It works offline and does 
not require a separate backend or account.


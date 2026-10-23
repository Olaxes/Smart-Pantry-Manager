# Smart Pantry Manager

A Java Android application for reducing food waste by suggesting recipes that can be prepared using ingredients already recorded in the user's pantry.

## Technology
- Java and Android Studio
- SQLite via `SQLiteOpenHelper`
- Android `ListView` with custom `BaseAdapter` classes
- Android Activities and Intents

## Features
- Add, edit, list and delete pantry ingredients
- Local persistent SQLite storage
- 20 recipes seeded automatically on first database creation
- Strict recipe suggestions: every listed recipe ingredient must be present in the pantry
- Recipe detail view with ingredients and preparation steps
- Settings/information screen
- Basic form validation and empty-match feedback

## Run instructions
1. Install Android Studio with Android SDK Platform 35.
2. Open this folder as an existing Android Studio project.
3. Allow Gradle sync to finish.
4. Create or select an Android emulator (API 23 or newer), or connect an Android device with USB debugging enabled.
5. Press **Run** for the `app` configuration.
6. Add pantry ingredients such as `egg`, `tomato`, `bread`, `cheese`, `pasta`, or `garlic`, with quantity at least `1`. Open Suggested Recipes to see strict matches.

## Database choice
SQLite is used because the assignment focuses on local persistent storage, CRUD operations and Android database fundamentals. It works offline and does not require a separate backend or account.

## Notes and limitations
- Recipe ingredient quantities are modelled as a simple requirement of at least one pantry unit per ingredient. The app does not yet model recipe serving sizes or convert units.
- Ingredient spelling is normalized for case and simple plural endings.
- Expiry dates are stored as optional text and are not yet used for alerts.
- Take genuine screenshots from the running app and replace/add them in the written report before submission.

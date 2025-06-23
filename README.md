# Matrimony App - Android Clean Architecture

A modern Android matrimony app built with Clean Architecture, Room Database, Dagger Hilt, and Retrofit. The app follows best practices for separation of concerns, dependency injection, and data management.

## Architecture Overview

The app follows Clean Architecture, divided into three main layers:

1. **Data Layer**  
   - API interfaces (Retrofit)  
   - Room DB interfaces (DAO)  
   - Entity models (DB and Network)  
   - Repository implementations  
   - Data sources (Local and Remote)

2. **Domain Layer**  
   - Business logic  
   - Use cases  
   - Repository interfaces  

3. **Presentation Layer**  
   - Views (Activities, Fragments)  
   - ViewModels  
   - UI components (Adapters, Custom Views)  
   - Dependency Injection setup  

## Data Flow

The app follows a unidirectional data flow:

1. **Service Interface** (`MatchesApiService` for API or `SdDAO` for DB)
2. **Data Sources** (`MatchesLocalDataSource` or `MatchesRemoteDataSource`)
3. **Repository** (Single source of truth, connects Data and Domain layers)
4. **Use Cases** (Business logic implementation)
5. **ViewModel** (Holds and manages UI-related data)
6. **Flow/LiveData** (Emits data changes to UI)
7. **Views** (Displays data and handles user interactions)

## Key Components

### Data Layer
- `MatchesApiService`: Retrofit interface for API calls
- `SdDAO`: Room database access interface
- `MatchesRepositoryImpl`: Repository implementation
- `MatchesRemoteDataSource`: Handles remote data operations
- `MatchesLocalDataSource`: Handles local database operations

### Domain Layer
- `MatchesRepository`: Repository interface
- Use cases (e.g., `GetMatchesUseCase`, `SaveMatchUseCase`)

### Presentation Layer
- `MatchesViewModel`: ViewModel for UI data
- `MatchesFragment/Activity`: UI components
- Custom views and adapters

### Match Score Logic
```kotlin
fun calculateCompatibility(currentUserAge: Int, currentUserCity: String, matchAge: Int, matchCity: String): Float {
    ...
}
```

This Kotlin function calculateCompatibility computes a compatibility score (ranging from 0.0 to 1.0) between two users based on their age difference (60% weight) and location match (40% weight). The age score decreases linearly from 1.0 (≤2 years difference) to 0.2 (>12 years difference), while the location score is binary (1.0 for same city, 0.0 otherwise). The final score is the weighted sum of these two factors, clamped to ensure it stays within bounds. This simplistic model prioritizes age proximity and geographic proximity but could be extended with additional factors

## Dependency Injection

The app uses Dagger Hilt for dependency injection with the following modules:

1. **DatabaseModule**: Room database initialization
2. **FactoryModule**: ViewModel factory and constructor injection
3. **LocalDataModule**: Local data source injection
4. **NetModule**: Retrofit initialization
5. **RemoteDataModule**: Remote data source injection
6. **RepositoryModule**: Repository injection in use cases
7. **UseCaseModule**: Use case injection in ViewModels

### Example Injection

```kotlin
// ViewModel injection in Activity
@Inject
lateinit var factory: MatchesViewModelFactory
```

private val viewModel: MatchesViewModel by viewModels { factory }


### Design Challenges
## Suppose you cannot show profile images due to a legal change.
a. We can load generic icon based on User initials/ or we can show default user icon male or female using glide image loader
b. We can enhance User profile with verification badge and Match score
c. We can even add personal traits like hobbies

## Improvements
a. Deeplinks and Notification feature
b. Video call option for premium membership
c. Login option - A secure login will share base url in login API response
d. Blue tick badge for real user





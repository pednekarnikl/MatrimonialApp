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

private val viewModel: MatchesViewModel by viewModels { factory }

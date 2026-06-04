# Shopique - Product Listing App

## Overview

Shopique is an Android application developed using Kotlin and Jetpack Compose. The application fetches product data from a remote API and displays it in a product list screen. Users can select a product to view detailed information along with an image carousel.

This project follows Clean Architecture and MVVM principles to ensure maintainability, scalability, and testability.

---

## Features

### Product List Screen
- Fetches products from API.
- Displays products using LazyColumn.
- Shows:
  - Product Image (First image from images array)
  - Product Title
  - Product Price
- Clickable product cards.

### Product Detail Screen
- Displays selected product details.
- Shows:
  - Product Images Carousel
  - Product Title
  - Product Description
  - Product Price
- Horizontal image swiping.
- Back navigation support.

---

## API Used

```http
GET https://api.escuelajs.co/api/v1/products
```

---

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Clean Architecture
- Retrofit
- Hilt Dependency Injection
- Coroutines
- StateFlow
- Navigation Compose
- Coil
- JUnit
- MockK

---

## Project Structure

```text
com.example.shopique/

├── data/
│   ├── remote/
│   │   ├── ApiService.kt
│   │   └── dto/
│   │       ├── ProductDto.kt
│   │       └── ProductMapper.kt
│   └── repository/
│       └── ProductRepositoryImpl.kt
│
├── domain/
│   ├── model/
│   │   └── Product.kt
│   ├── repository/
│   │   └── ProductRepository.kt
│   └── usecase/
│       ├── GetProductsUseCase.kt
│       └── GetProductByIdUseCase.kt
│
├── presentation/
│   ├── productlist/
│   │   ├── ProductListScreen.kt
│   │   ├── ProductListState.kt
│   │   └── ProductListViewModel.kt
│   │
│   └── productdetail/
│       ├── ProductDetailScreen.kt
│       ├── ProductDetailState.kt
│       └── ProductDetailViewModel.kt
│
├── navigation/
│   ├── Screen.kt
│   └── NavGraph.kt
│
├── di/
│   └── AppModule.kt
│
├── ShopApp.kt
└── MainActivity.kt
```

---

## Architecture

The application follows Clean Architecture with MVVM.

### Data Layer
Responsible for data retrieval and mapping.

- ApiService
- ProductDto
- ProductMapper
- ProductRepositoryImpl

### Domain Layer
Contains business logic.

- Product Model
- Repository Interface
- GetProductsUseCase
- GetProductByIdUseCase

### Presentation Layer
Responsible for UI and state management.

- ProductListScreen
- ProductDetailScreen
- ViewModels
- State Classes

---

## Data Flow

1. UI requests data from ViewModel.
2. ViewModel invokes Use Case.
3. Use Case communicates with Repository.
4. Repository fetches data using Retrofit API.
5. DTO objects are mapped to Domain Models.
6. Data is returned to ViewModel.
7. StateFlow updates UI automatically.

---

## Dependency Injection

Hilt is used for dependency management.

Dependencies provided in:

```kotlin
AppModule.kt
```

Injected Components:

- Retrofit
- ApiService
- Repository
- Use Cases

---

## State Management

StateFlow is used to manage UI states.

### ProductListState
- Loading
- Success
- Error

### ProductDetailState
- Loading
- Success
- Error

---

## Testing

Unit tests are implemented for:

### Repository
- API Success Response
- API Error Response

### Use Cases
- GetProductsUseCase
- GetProductByIdUseCase

### ViewModels
- Loading State Validation
- Success State Validation
- Error State Validation

---
## 📱 Application Screens

<div align="center">

<table>
<tr>
<td align="center">
<h3>Product Listing Screen</h3>
<img src="https://github.com/user-attachments/assets/233f81f5-ecf6-446b-a215-4bcb2587fc5d" width="250" alt="Product Listing Screen"/>
</td>

<td align="center">
<h3>Product Detail Screen</h3>
<img src="https://github.com/user-attachments/assets/eb31a9ff-1a39-4ab7-8bb1-46d72d088035" width="250" alt="Product Detail Screen"/>
</td>
</tr>
</table>

</div>


## Future Enhancements

- Pagination
- Search Products
- Category Filtering
- Offline Caching using Room
- Pull to Refresh
- Dark Theme Support

---

## Author

Jenisha J

Android Assessment Project

Built using Kotlin, Jetpack Compose, MVVM, and Clean Architecture.

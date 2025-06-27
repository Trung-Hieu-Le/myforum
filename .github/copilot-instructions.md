---
description: 
globs: 
alwaysApply: true
---
## General Code Quality Guidelines

- **Functionality and Correctness:**
  - Verify that the code works as intended.
  - Ensure all logic paths and edge cases are handled.
  - Confirm that unit or integration tests are provided.

- **Readability and Maintainability:**
  - Code should be self-explanatory or properly commented where needed.
  - Remove any dead or commented-out code.
  - Keep methods and functions short and focused (single responsibility).

- **Adherence to Standards:**
  - Follow PHP Standard Recommendations like PSR-1 and PSR-12 for coding style.
  - Use consistent naming conventions and file structures.

## Project architectural [architecture.mdc](mdc:.cursor/rules/architecture.mdc)


## Code Style and Naming Conventions

- **Consistent Formatting:**
  - Follow a consistent code style throughout the application.

- **Descriptive Naming:**
  - Use clear, descriptive names for classes, methods, and variables.
  - Avoid abbreviations unless they are widely recognized.

- **Comments and Documentation:**
  - Use comments to explain *why* certain decisions were made, not just *what* the code does.
  - Document all public APIs and methods with PHPDoc.

- [Additional documentation rules](mdc:.cursor/rules/documentation.mdc)

## Security Considerations

- **Input Validation and Sanitization:**
  - Never trust external input—always validate and sanitize.

- **Preventing Vulnerabilities:**
  - Escape output in views to protect against XSS.
  - Use parameterized queries or ORM methods to prevent SQL injection.

- **Session and Authentication Management:**
  - Ensure secure session handling and robust authentication mechanisms.
  - Verify that authorization checks are properly implemented.

- [Additional security rules](mdc:.cursor/rules/security.mdc)


## Testing and Documentation

- **Automated Testing:**
  - New changes should be accompanied by unit or integration tests.
  - Tests must cover both typical use cases and edge cases.

- [Additional testing rules](mdc:.cursor/rules/testing.mdc)

## Performance and Optimization

- **Efficient Code:**
  - Optimize loops and iterative processes to prevent unnecessary performance overhead.
  - Check for excessive database queries and optimize them as needed.

- **Query Optimization:** [query-optimization.mdc](mdc:.cursor/rules/query-optimization.mdc)



## Additional Best Practices

- **Modularity and Reusability:**
  - Abstract common functionality into reusable services or libraries.
  - Avoid code duplication across models, views, or controllers.

- **Error and Exception Handling:**
  - Use structured error handling with try-catch blocks.
  - Log errors appropriately without exposing sensitive information to the end user.

---
description: This rule is used when reviewing code
globs: .php
alwaysApply: false
---
## 1. Use Eager Loading, Avoid N+1 Query Problem
- Eager loading can reduce the number of queries executed by loading related models upfront.
- The N+1 problem occurs when an application executes a query for each record in a set of records, leading to many unnecessary queries.
Example:
```php
// Without Eager Loading
$posts = Post::all();
foreach ($posts as $post) {
    echo $post->author->name;
}

// With Eager Loading
$posts = Post::with('author')->get();
foreach ($posts as $post) {
    echo $post->author->name;
}
```

## 2. Use Select Only Needed Columns
- Fetching only necessary columns reduces data transfer and processing time.
Example:
```php
$users = User::select('id', 'name', 'email')->get();
```

## 3. Leverage Database Transactions
- Transactions ensure data integrity and can improve performance for multiple queries.
Example:
```php
DB::transaction(function () {
    $user = User::create([...]);
    Profile::create([...]);
});
```

## 4. Use Chunking for Large Datasets
- Chunking processes large datasets in smaller chunks, preventing memory overflow.
Example:
```php
Post::chunk(100, function ($posts) {
    foreach ($posts as $post) {
        // Process each post
    }
});
```





OpenCart Test Automation Project

UI Тести
Розташування: src/test/java/opencart/ui/UITests.java

Пошук товарів (testSearchProduct)
  Перевіряє коректність роботи пошуку
  Виконується на браузерах Chrome та Firefox
  Параметризований тест: перевіряє пошук для iPhone, MacBook, Samsung

Додавання до кошика (testAddToCart)
  Перевіряє функціональність додавання товару до кошика
  Перевіряє появу повідомлення про успішне виконання операції

Порівняння товарів (testCompareProducts)
  Перевіряє можливість додавання товарів до списку порівняння

API Тести
Розташування: src/test/java/opencart/api/ApiTests.java

Базові тести:
  testGetProducts - перевірка статусу 200 та content-type сторінки товару
  testGetCategories - перевірка доступності сторінки категорії
  testSearchProducts - параметризований тест для пошуку (iPhone, MacBook, Samsung)
  testGetProductById - перевірка товарів з ID: 40, 41, 42

End-to-End сценарії:
  testE2ESearchFlow - Home, Search, View Product
  testE2ECategoryFlow - Category, Subcategory, Product
  testE2ECartFlow - Product Page, Cart Page, Checkout Page

Тести продуктивності (JMeter)
Розташування: src/test/resources/jmeter/opencart_load_test.jmx

ThreadGroups (3 групи):
  1. Browse Products - 10 потоків, перегляд головної, категорій та товарів
  2. Search Products - 15 потоків, пошук iPhone, MacBook, Samsung
  3. View Cart - 5 потоків, перегляд кошика та checkout

Запуск з генерацією HTML звіту:
  30 хвилин (за замовчуванням): run_jmeter_report.bat
  60 хвилин: run_jmeter_report.bat -Jduration=3600

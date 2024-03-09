## Обработчик авиаперелетов

Это модуль, который занимается фильтрацией набора перелётов согласно различным правилам.

Правила могут выбираться и задаваться динамически в зависимости от контекста выполнения операции фильтрации.

Для запуска создал публичный класс Main c методом main(). Этот метод должен выдать в консоль результаты обработки набора перелётов. Получить набор нужно методом FlightFilterRunner.runFilters().

Модуль обрабатывает следующие наборы перелётов:
- Вылет до текущего момента времени.
- Сегменты с датой прилёта раньше даты вылета.
- Перелеты, где общее время, проведённое на земле, превышает два часа.
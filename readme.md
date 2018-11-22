##### Домашнее задание к лекции 5
1. _Зачем применяют метод sleep()? Опишите минусы его применения._
Thread.sleep() используется для ожидания загрузки элемента. 
Из минусов, при использовании Thread.sleep(4000), даже если элемент, загрузки которого мы ждём, загрузится быстрее, чем за 4 секунды, то ждать всё равно придётся.
2. _Написать тест, в котором будет авторизация пользователя, переход на вкладку организации, выбор любой организации и создание заявления для нее._
[Should_CreateApplication()](https://github.com/BlokhinaElizaveta/islod_2_tests/tree/master/src/test/java/Tests.java)
  
##### Домашнее задание к лекции 6
1. _Перечислите типы локаторов, которые используются для поиска элементов в DOM дереве._
- By.id
- By.name
- By.className
- By.TagName
- By.LinkText
- By.PartialLinkText
- By.cssSelector
- By.XPath
2. _В администрировании, в системных действиях есть возможность просклонять имя нарицательное.
Написать тест в котором проверяется правильность склонения любого слова на ваш выбор._
[Should_DeclineWord()](https://github.com/BlokhinaElizaveta/islod_2_tests/tree/master/src/test/java/Tests.java)
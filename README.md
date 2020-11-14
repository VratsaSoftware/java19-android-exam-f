# Photo Station application
Създайте приложение за поръчване на принтиране на снимки и следене на поръчките. \

В помощните материали са ви дадени структурата и първоначалните файлове на апп-а. Предварително написан за вас е класа AuthService, чрез който се извършва логин-а в апп-а. Той не трябва да се променя. \
	
Дадена ви е и базова версия на LoginActivity, която трябва да надградите. В нея е имплементиран логин с гугъл акаунт. Вие трябва да добавите допълнителна опция за логин и след това пренасочване към основният екран. \

Основният екран ще се състои от два под-екрана - за създаване на нова поръчка и за разглеждане на вече направените поръчки. \
Нова поръчка се създава в три стъпки:
  1. Избиране на снимка за принтиране от галерията на телефона.
  2. Калкулиране на цената на снимката, чрез изпращане на информацията й до REST API.
  3. Поръчване на снимката чрез REST API и изчистване на екрана.\
  
Списъкът с вече създадени поръчки също се връща от REST API като се подаде ид-то на потребителя. Поръчки, които вече са доставени, имат лента оцветена в сиво. \

Спазвайте установените вече пакети и при нужда създавайте допълнителни. Освен горепосочените файлове, имате също така и файлове с цветове и размери.

# Задачи
### Login:
##### 1. 
Когато се кликне SIGN IN да се вземат въведените стойности от полетата за имейл и парола и да се ползва AuthService.loginWithEmail(). \
Преди да се подадат, двете стойности, на AuthService-a трябва да има валидация за тях: \
	- Направете проверка за лош формат на имейла и показвайте съобщение на потребителя \
	- Направете проверка за парола по-къса от 6 символа и показвайте съобщение на потребителя \
За имейл login listener-a използвайте същия като този на Google Login-a.

##### 2. 
Ако login-a е успешен - показвайте съобщение на потребителя, отворете главния екран и затворете този.

### New Order:
##### 3.
Използвайте DataBinding за взимани и манипулиране на view-та.

##### 4.
Довършете layout-a на NewOrderFragment, така че да изглежда [така](https://github.com/VratsaSoftware/java19-android-exam/tree/main/screenshots). Само секция 3 липсва, секция 2 е невидима.
Може да на правите видима като на view с ID: grpPart2, се промени атрибута android:visibility да е "visible", а не "gone".

##### 5.
Имплементирайте ActivityListener с метод void showMessage(String message).

##### 6.
Имплементирайте трите метода в ApiWrapper. Използвайте конструктурите на Order обекта, който от двата сметнете за подходящ.
При грешка - викайте listner.onDataReceived с null за аргумент.

##### 7.
След успешно калкулиране - покажете секция 3 (за поръчване), която направихте.

##### 8.
Имплементирайте логиката за поръчване на снимка.

##### 9.
След успешна поръчка - занулете всичко - секции 2 и 3 да не се виждат, и изтрийте каквито полета са били сетнати + полетата imageName и imageSizeInBytes.

### My Orders:
##### 10.
Създайте адаптер на RecyclerView-то. Използвайте дадения list_item_order.xml за layout.


# REST API
Основният URL e https://us-central1-vsc-android-exam-2020.cloudfunctions.net/ \
Има три важни метода: \
calculate - за калкулиране на цена на поръчка \
Тип: POST \
Изисква два параметри \
filename - text \
filesize - number \
Получава се обект с данни \
filename: text, \
filesize: number \
prize: number in USD, \
createdOn: timestamp in milliseconds \
completionTime: time to complete order, in milliseconds \
makeOrder - за правене на поръчка \
Тип: POST \
Изисква три параметри \
userId - text \
filename - text \
filesize - number \
Получава се обект с данни \
filename: text, \
filesize: number, photo size in KB \
prize: number in USD, \
createdOn: timestamp in milliseconds \
completionTime: time to complete order, in milliseconds \
getAllOrders - за взимане на всички поръчки на потребителя \
Тип: POST \
Изисква един параметър \
userId - text \
Получава се списък с обекти с данни. Всеки обект от списъка има: \
filename: text, \
filesize: number \
prize: number in USD, \
createdOn: timestamp in milliseconds \
completionTime: time to complete order, in milliseconds \
Примери \
Calculate Request \
https://us-central1-vsc-android-exam-2020.cloudfunctions.net/calculate \
{"filename":"IMG_20190628_174940.jpg","filesize":5519} \
Calculate Response \
{"filename":"IMG_20190628_174940.jpg","filesize":5519,"prize":55.879999999999995,"createdOn":1561849118724,"completionTime":402336000} \
MakeOrder Request \
https://us-central1-vsc-android-exam-2020.cloudfunctions.net/makeOrder \ {"filename":"IMG_20190628_174940.jpg","filesize":5519,"userId":"Y4sVoUGOBROnwLNGQiRmDVYdRrI2"} \
MakeOrder Response \
{"filename":"IMG_20190628_174940.jpg","filesize":5519,"prize":55.879999999999995,"createdOn":1561849217174,"completionTime":402336000} \
GetAllOrders Request \
https://us-central1-vsc-android-exam-2020.cloudfunctions.net/getAllOrders \
{"userId":"Y4sVoUGOBROnwLNGQiRmDVYdRrI2"} \
GetAllOrders Response \
[{"completionTime":36347904000,"createdOn":1561135285177,"filename":"IMG_20190616_182441.jpg","filesize":504763,"prize":5048.32},{"completionTime":343440000,"createdOn":1561193194256,"filename":"IMG_20190615_221114.jpg","filesize":4701,"prize":47.699999999999996},{"completionTime":490320000,"createdOn":1561219352884,"filename":"IMG_20190622_124016.jpg","filesize":6741,"prize":68.1},{"completionTime":11088000,"createdOn":1561219379580,"filename":"1559134649301.jpg","filesize":103,"prize":1.54},{"completionTime":23544000,"createdOn":1561220327972,"filename":"booking-1878815987.jpg","filesize":261,"prize":3.2699999999999996},{"completionTime":402336000,"createdOn":1561849217174,"filename":"IMG_20190628_174940.jpg","filesize":5519,"prize":55.879999999999995}]

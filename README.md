#UJACS (update java application client server)

Данное приложение предназначено для удобной поставки пользовательских приложений с контролем доступов к ним, а так же обновлениями.

Сборка приложения в режиме тестирования на одном компьютере:  

1. В корневом файле **pom.xml** в переменной **java.directory.for.package** указать путь к папке с JRE, которая будет использоваться для выполнения приложений на клиенте 
1. Перейти в корневую папку **ujacs**
1. Запустить maven командой: **mvn clean package**
1. Дождаться сборки всех модулей
1. Взять zip архив из папки: **ujacs/app-server/target/app-server.zip** и распаковать его в удобную для Вас директорию (далее SERVER_DIR).  
1. Для первоначального запуска необходимо запустить .bat файл: **run-unpack.bat**
Данное действие распакует все необходимые ресурсы для сервера.
1. Скопируйте тестовые приложения:  
-**ujacs/app-test-console/target/app-test-console_0.1.zip**  
-**ujacs/app-test-gui/target/app-test-gui_0.1.zip**  
в папку **SERVER_DIR/server/apps**
1. Архив: **ujacs/app-service/target/app-service_0.1.zip** скопировать в папку **SERVER_DIR/server/install/install_service_app/**
1. Скопируйте архив: **ujacs/app-service-updater/target/app_service_installer.zip** в папку **SERVER_DIR/server/install/first_install/**
1. Запустите сервер в консольном режиме используя .bat: **run-console.bat**        
1. Для установки пользовательского приложения запустите **ujacs/app-service-installer/target/app-service-installer.jar**  

После установки будет предложено запустить сервис приложений.  
После запуска будет доступно два приложения.



Для сборки приложения в боевом режиме необходимо заменить host в:  
**config.properties**  
**installer.properties**  
**app.xml**
#UJACS (update java application client server)

Данное приложение предназначено для удобной поставки пользовательских приложений с контролем доступов к ним, а так же обновлениями.

Сборка приложения в режиме тестирования на одном компьютере:  
1. Перейти в корневую папку **ujacs**
1. Запустить maven командой: **mvn clean package**
1. Дождаться сборки всех модулей
1. Взять zip архив из папки: **ujacs/app-server/target/app-server.zip** и распаковать его в удобную для Вас директорию (далее SERVER_DIR).  
1. Для первоначального запуска необходимо запустить .bat файл: **run-unpack.bat**
Данное действие распакует все необходимые ресурсы для сервера.
1. Скопируйте тестовые приложения из:  
**ujacs/app-test-console/target/app-test-console_0.1.zip** 
**ujacs/app-test-gui/target/app-test-gui_0.1.zip**  
в папку **SERVER_DIR/server/apps**
1. Необходимо в архив: **ujacs/app-service/target/app-service_0.1.zip**
в папку java положить jre так, чтоб получилась иерархия:
    * java  
        * lib
        * bin
1. Архив: **ujacs/app-service/target/app-service_0.1.zip** скопировать в папку:  **SERVER_DIR/server/install/install_service_app/**











Необходимо заменить host в:  
config.properties  
installer.properties  
app.xml

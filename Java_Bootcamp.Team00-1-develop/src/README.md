Инструкция:

1. Создание сборки ChaseLogic

    1.1 Перейти в папку ChaseLogic
        
        cd ChaseLogic

    1.2 Выполнить сборку ChaseLogic
        
        mvn clean install

2. Создание архива Game

    2.1 Перейти в папку Game
        
        cd ../Game

    2.2 Выполнить сборку Game
        
        mvn clean install

3. Запуск приложения
    
    3.1 Выполнить запуск архива с флагами:

     {--enemiesCount=10} - число монстров

     {--wallsCount=10} - число препятствий

     {--profile=production/dev} - режим запуска игры

        java -jar target/game.jar --enemiesCount=5 --wallsCount=10 --size=15 --profile=production
    
    !!! В случае ошибки версии JDK, необходимо обновить на официальном сайте `Oracle` (https://www.oracle.com/java/technologies/downloads/?er=221886#java23)

4. Очистка

    4.1 Выполнить переход

        cd ..

    4.2 Выполнить удаление файлов

        rm -rf .idea ChaseLogic/target ChaseLogic/.idea Game/target Game/.idea
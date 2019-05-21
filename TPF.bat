@echo off
:NotYorN
set /p askIfSure="Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(y/n)? "
if "%askIfSure%" == "n" goto ENDwoERR
if "%askIfSure%" == "y" goto FINALISE

goto NotYorN

:FINALISE
set /p kw="In welches KW sollen die PDFs verschoben werden? "
echo "J:/02_Service Projects/SrvProjects_DE/apoBank/Korrespondenz/allgemein/Auslieferung/KW%kw%"

:ENDwoERR
echo Programm beendet
::exit 0
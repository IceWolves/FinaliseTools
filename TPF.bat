@echo off ::Ausschalten der Anzeige von Befehlen in der CMD

::Anzahl der Dokumente die finalisiert werden sollen
set /a anzahlPDF=0
echo %anzahlPDF% zu verschiebende/s PDF


::Nachfragen ob die Dokumente wirklich finalisiert werden sollen
:NotYorN
set /p askIfSure="Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(y/n)? "
if "%askIfSure%" == "n" goto ENDwoERR
if "%askIfSure%" == "y" goto FINALISE

goto NotYorN

::Finalisierung der Dokumente
:FINALISE
set /p kw="In welches KW sollen die PDFs verschoben werden? "

echo J:/02_Service Projects/SrvProjects_DE/apoBank/Korrespondenz/allgemein/Auslieferung/KW%kw%

::Programmende
:ENDwoERR
echo Programm beendet
::exit 0
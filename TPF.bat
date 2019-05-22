::Ausschalten der Anzeige von Befehlen in der CMD
@echo off

::Momentanen Pfad speichern
set path=%cd%

::TODO: Anzahl der Dokumente die finalisiert werden sollen -Wird erst nach erster "Vollversion" hinzugefügt-
::set /a anzahlPDF=0
::echo %anzahlPDF% zu verschiebende/s PDF

::TODO: Namensliste der gefundenen Dokumente -Wird erst nach erster "Vollversion" hinzugefügt-

::Nachfragen ob die Dokumente wirklich finalisiert werden sollen
:NOTYORN
set /p askIfSure="Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(y/n)? "
if "%askIfSure%" == "n" goto ENDwoERR
if "%askIfSure%" == "y" goto FINALISE

goto NOTYORN

::Finalisierung der Dokumente
:FINALISE
set /p kw="In welches KW sollen die PDFs verschoben werden? "

::Alle PDFs auslesen und in Variable speichern
cd %USERPROFILE%\Downloads\
for /f %%I in ('dir /b *.pdf') do goto NAMELIST %%~nI

:NAMELIST


::echo %USERPROFILE%\Downloads
::echo J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%

::Programmende
:ENDwoERR
cd %path%
echo Programm beendet
::exit 0
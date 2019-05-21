::Ausschalten der Anzeige von Befehlen in der CMD
@echo off

::TODO: Anzahl der Dokumente die finalisiert werden sollen -Wird erst nach erster "Vollversion" hinzugefügt-
::set /a anzahlPDF=0
::echo %anzahlPDF% zu verschiebende/s PDF

::TODO: Namensliste der gefundenen Dokumente -Wird erst nach erster "Vollversion" hinzugefügt-

::Nachfragen ob die Dokumente wirklich finalisiert werden sollen
:NotYorN
set /p askIfSure="Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(y/n)? "
if "%askIfSure%" == "n" goto ENDwoERR
if "%askIfSure%" == "y" goto FINALISE

goto NotYorN

::Finalisierung der Dokumente
:FINALISE
set /p kw="In welches KW sollen die PDFs verschoben werden? "

set t1=%USERPROFILE%/Downloads/
set t2=t1 /b
for %%I in (%test%) do echo %%~nxI

::echo %USERPROFILE%\Downloads
echo J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%

::Programmende
:ENDwoERR
echo Programm beendet
::exit 0
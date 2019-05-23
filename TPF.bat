::Ausschalten der Anzeige von Befehlen in der CMD
@echo off

::Momentanen Pfad speichern
set path=%cd%

::TODO: Anzahl der Dokumente die finalisiert werden sollen -Wird erst nach erster "Vollversion" hinzugefügt-
::set /a anzahlPDF=0
::echo %anzahlPDF% zu verschiebende/s PDF

::TODO: Namensliste der gefundenen Dokumente -Wird erst nach erster "Vollversion" hinzugefügt-

::Nachfragen ob die Dokumente wirklich finalisiert werden sollen
set /a anzahlDokumente=0
cd %USERPROFILE%\Downloads\
for /f %%A in ('dir /b TST_*.pdf') do (
    set /a anzahlDokumente=anzahlDokumente+1
)

echo Es wurden %anzahlDokumente% Dokumente gefunden.

::Nachfragen ob Dokumente Angezeigt werden sollen
:SHOWNAME
set /p askIfShow="Sollen die Dokumente aufgelistet werden(j/n)? "
if /I %askIfShow% == j (
    for /f %%A in ('dir /b TST_*.pdf') do (
        echo %%~nxA
    )
    goto ASKFINALISE
)
if /I %askIfShow% == n goto ASKFINALISE

goto SHOWNAME
goto ENDwoERR

:ASKFINALISE
set /p askIfSure="Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(j/n)? "
if /I %askIfSure% == n goto ENDwoERR
if /I %askIfSure% == j goto ASKKW

goto ASKFINALISE
goto ENDwoERR


::Finalisierung der Dokumente
:ASKKW
set /p kw="In welches KW sollen die PDFs verschoben werden? "

echo Die Dokumente werden in unter folgendem Pfad abgespeichert:
echo J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%
:ASKKWRIGHT
set /p kwKorrekt="Ist dies korrekt(j/n)? "
if /I %kwKorrekt% == n goto ASKKW
if /I %kwKorrekt% == j goto FINALISE

goto ASKKWRIGHT
goto ENDwoERR

:FINALISE
echo %anzahlDokumente% werden kopiert und umbenannt.

::Datei in KW Ordner kopieren und in TST_DATEINAME_final.pdf umbenennen
cd %USERPROFILE%\Downloads\
for /f %%A in ('dir /b TST_*.pdf') do (
    copy "%USERPROFILE%\Downloads\%%~nA.pdf" "J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%"
    cd J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%
    rename "%%~nA.pdf" "%%~nA_final.pdf"
    cd %USERPROFILE%\Downloads\
)

::Alle Dateien mit TST_DATEINAME am anfang in DATEINAME umbenennen
cd J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%
rename "TST_*.pdf" "////*.pdf"

::Programmende
:ENDwoERR
cd %path%
echo Programm beendet
goto EOF

:EOF
::exit 0

::%USERPROFILE%\Downloads\TEST\ ersetzen durch
::J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%

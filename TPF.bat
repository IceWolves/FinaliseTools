::Ausschalten der Anzeige von Befehlen in der CMD
@echo off

::Momentanen Pfad speichern
set path=%cd%

::Fragen ob Dokumente im Downloadordner liegen
:ASKIFDOWNLOADS
set /p askIfDownloads="Sind die Dokumente im Downloadordner(j/n)? "
if /I %askIfDownloads% == j (
    set dokumentPath="%USERPROFILE%\Downloads\"
    goto NUMBERDOCS
)
if /I %askIfDownloads% == n (
    ::Nach Dokumentordner fragen
    echo "Geben sie den kompletten Downloadpfad an (der Pfad muss mit \ abschliessen und die Dokumente muessen in einem Ordner des Standardverzeichnis C:\ liegen): "
    set /p askForPath=""
    set dokumentPath=%askForPath%
)
goto ASKIFDOWNLOADS
goto ENDwoERR


::Die Anzahl der gefundenen Dokumente angeben
:NUMBERDOCS
set /a anzahlDokumente=0
cd %dokumentPath%
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

::Nachfragen ob die Dokumente wirklich finalisiert werden sollen
:ASKFINALISE
set /p askIfSure="Wollen sie die TST PDFs im Ordner %dokumentPath% finalisieren(j/n)? "
if /I %askIfSure% == n goto ENDwoERR
if /I %askIfSure% == j goto ASKKW

goto ASKFINALISE
goto ENDwoERR


::Finalisierung der Dokumente
:ASKKW
set /p kw="In welches KW sollen die PDFs verschoben werden? "
echo Die Dokumente werden unter folgendem Pfad abgespeichert:
echo J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%

:ASKKWRIGHT
set /p kwKorrekt="Ist dies korrekt(j/n)? "
if /I %kwKorrekt% == n goto ASKKW
if /I %kwKorrekt% == j goto FINALISE

goto ASKKWRIGHT
goto ENDwoERR

:FINALISE
echo %anzahlDokumente% Dokumente werden kopiert und umbenannt.

::Datei in KW Ordner kopieren und in TST_DATEINAME_final.pdf umbenennen
cd %dokumentPath%
for /f %%A in ('dir /b TST_*.pdf') do (
    copy "%dokumentPath%\%%~nA.pdf" "J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%\"
    J:
    cd J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%\
    rename "%%~nA.pdf" "%%~nA_final.pdf"
    C:
    cd %dokumentPath%
)

::Alle Dateien mit TST_DATEINAME am anfang in DATEINAME umbenennen
J:
cd J:\02_Service Projects\SrvProjects_DE\apoBank\Korrespondenz\allgemein\Auslieferung\KW%kw%
rename "TST_*.pdf" "////*.pdf"

::Nachfragen ob Dateien aus dem Ursprungsordner gelöscht werden sollen
:DATEIENLOESCHEN
echo Ursprungsordner: %dokumentPath%
set /p askIfDelete="Sollen die Dateien aus dem Ursprungsordner geloescht werden(j/n)? "

if /I %askIfDelete% == n goto ENDwoERR
if /I %askIfDelete% == j (
    C:
    cd %dokumentPath%
    for /f %%A in ('dir /b TST_*.pdf') do (
        del %%~dpnxA
    )
    goto ENDwoERR
)

goto DATEIENLOESCHEN

::Programmende
:ENDwoERR
C:
cd %path%
echo Programm beendet
goto EOF

:EOF
::exit 0

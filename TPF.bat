@echo off
set /p askIfSure=Wollen sie die TST PDFs in ihrem Downloadordner finalisieren(y/n)?
::notYorN
if "%askIfSure%" == "n" echo Programm wird beendet.
    ::exit 0
if "%askIfSure%" == "y" set /p kw=Geben Sie die KW Nummer der Dokumente ein:
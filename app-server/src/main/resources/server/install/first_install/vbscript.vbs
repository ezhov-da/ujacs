Dim oShell, oShortCut, sDeskTopPath
Set oShell = CreateObject("Wscript.Shell")
sDeskTopPath = oShell.SpecialFolders("Desktop") 'на рабочий стол
Set oShortCut = oShell.CreateShortcut(sDeskTopPath & "\:NAME_APPLICATION" &  ".lnk")  'название ярлыка
oShortCut.IconLocation = ":PATH_TO_ICO" 'путь к иконке
oShortCut.TargetPath = ":PATH_TO_APP_RUN" 'путь к запускаемому файлу
oShortCut.WorkingDirectory = ":PATH_TO_APP_DIR" 'путь к рабочей папки
oShortCut.Save
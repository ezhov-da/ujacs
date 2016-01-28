
				Dim oShell, oShortCut, sDeskTopPath
				Set oShell = CreateObject("Wscript.Shell")
				sDeskTopPath = oShell.SpecialFolders("Desktop") 'на рабочий стол
				Set oShortCut = oShell.CreateShortcut(sDeskTopPath & "\TASKS.lnk")  'название ярлыка
				oShortCut.IconLocation = "E:\_own_repository_data\java\ujacs\UJAC\spt\task.ico" 'путь к иконке
				oShortCut.TargetPath = "E:\_own_repository_data\java\ujacs\UJAC\spt\RUN.bat" 'путь к запускаемому файлу
				oShortCut.WorkingDirectory = "E:\_own_repository_data\java\ujacs\UJAC\spt" 'путь к рабочей папки
				oShortCut.Save
			
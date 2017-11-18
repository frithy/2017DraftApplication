$players = import-csv C:\users\Andrew\Desktop\inputfiles\newplayerdata.csv

foreach($player in $players){


$name = ($player.name+"+AFL");
write-host (""+$player.ID+":"+$player.name);
$SearchItem = "$name"
$TargetFolder = 'C:\Users\Andrew\Pics'

if ( (Test-Path -Path $TargetFolder) -eq $false) { md $TargetFolder }

    $url = "https://www.google.com.au/search?q=$searchItem&safe=off&biw=1600&bih=755&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiXtqGGo-3KAhVKJpQKHcs8DpUQ_AUIBigB"


  
    $id = $player.ID;
    $end = ("player$id.jpg")

     
    $path = Join-Path -Path $TargetFolder -ChildPath $end

    $iwr = Invoke-WebRequest -Uri $Url
    $images = (($iwr).Images | select src)[0]
    Invoke-WebRequest -Uri $images.src  -outfile $path 

            
        }
        
   
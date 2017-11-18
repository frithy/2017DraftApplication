$playerArray = import-csv .\PlayerPage\playerData.csv
$gameFolders = Get-ChildItem Games -Directory

function UpdateGameData{


    param($players,
       $gameArray, $year)

    
       foreach($player in $players){
            
    $player | Add-Member -Name "GP_$year" -Value 0 -MemberType NoteProperty
    $player | Add-Member -Name "AVE_$year" -Value 0 -MemberType NoteProperty
    $player | Add-Member -Name "TOG_$year" -Value 0 -MemberType NoteProperty
    $player | Add-Member -Name "TOG_AVE_$year" -Value 0 -MemberType NoteProperty
    $player | Add-Member -name "TOG_GP_$year" -Value 0 -MemberType NoteProperty

            $games = $gameArray | where-object{$_.id -eq $player.id}
            $TOG = 0;
            $total =0; 
            $totaltog50=0;
            $gamesPlayedtog50=0;
            
            if($games.count -gt 0){    
            foreach($game in $games){
        
                $total += [int]::Parse($game.Points)
                $tog+= [Int]::Parse($game.TOG.replace("%",""))  
       
                if([Int]::Parse($game.TOG.replace("%","")) -gt 60){
            
                    $totaltog50 += [int]::Parse($game.Points)
                    $gamesPlayedtog50++;
            
        
                }
           
    }
     $player."GP_$year" = $games.count
     $player."Ave_$year" = $total/$games.count
     $player."TOG_$year" = $TOG/$games.count;
     $player."TOG_AVE_$year" = $totaltog50/$gamesPlayedtog50   
     $player."TOG_GP_$year" = $gamesPlayedtog50
    
    
    }else{
          $player."GP_$year" = "DNP"
        $player."Ave_$year" = "DNP"
        $player."TOG_$year" = "DNP"
        $player."TOG_AVE_$year" = "DNP"   
        $player."TOG_GP_$year"="DNP"
    
    }        
     
       
       }

       return $players

}



foreach($folder in $gameFolders){

    
    $gamedata = Import-Csv ($folder.fullname+"\GameData.csv")
    $year = $folder.name 

    $playerArray = UpdateGameData -players $playerArray -gameArray $gamedata -year $year 
}
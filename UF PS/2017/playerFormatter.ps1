$players = import-csv 2016_uf.csv

foreach($player in $players){

    $nameString = $player.player
    $player.position = $player.player.split(";")[2]
    $player.club = $player.player.split(";")[1]
    $player.player = $player.player.split(";")[0] 



}

foreach($pick in $players){
    $newPosition = "";

    $count = 0;
    if($pick.position -like "*R*"){
        
        if($count -eq 0){
            $newposition = "Ruck"
        }
        else{
        
         $newPosition+=",Ruck"
        }
        $count++;

    }
    if($pick.position -like "*F*"){

         if($count -eq 0){

        $newposition = "Forward"
        }
        else{
        $newposition += ",Forward"
        }
        $count++;

    }
    if($pick.position -like "*B*"){
        if($count -eq 0){

         $newposition = "Back"
        }
        else{
        $newposition += ",Back"
        }

        $count++;
        
    }
    if($pick.position -like "*C*"){
        
        if($count -eq 0){

         $newposition = "Midfielder"
        }
        else{
        $newposition += ",Midfielder"
        }
        $count++;
        }



        $pick.Position = $newPosition
}